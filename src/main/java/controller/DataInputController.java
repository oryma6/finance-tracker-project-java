package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mozilla.universalchardet.UniversalDetector;

import dao.CSVMappingDAO;
import dao.CategoriesDAO;
import dao.PayeesDAO;
import dao.TransactionsDAO;
import domain.Payees;
import domain.PaymentMethods;

//import jakarta.security.auth.message.callback.PrivateKeyCallback.Request;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import service.DataInputService;
import dto.AccountsDTO;
import dto.CSVMappingDTO;
import dto.CategoriesDTO;
import dto.TransactionsDTO;

@WebServlet("/dataInput")
@MultipartConfig
public class DataInputController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	// GET -> when user arrive at dataInput.jsp, setting up the 'payees' and 'payment_methods' for both income and expenses
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		int accountId = ((AccountsDTO) session.getAttribute("user")).getAccountId();
		
		ArrayList<CSVMappingDTO> dtos = new CSVMappingDAO().selectAll(accountId);
		
		DataInputService service = new DataInputService();
		
		int expense = 111; // 111 is the unique id for expense type in 'types' table in database
		int income = 222;// 222 is the unique id for expense type in 'types' table in database
		
		// get expense Payees and income Payers
		ArrayList<Payees> expensePayees = service.selectPayees(expense, accountId);
		ArrayList<Payees> incomePayees = service.selectPayees(income, accountId); 
		
		// get expense and income PaymentMethods
		ArrayList<PaymentMethods> expensePaymentMethods = service.selectPaymentMethods(expense, accountId);
		ArrayList<PaymentMethods> incomePaymentMethods = service.selectPaymentMethods(income, accountId);
		
		// passing both expense and income of payees and paymentmethods into the current session
		session.setAttribute("expensePayees", expensePayees);
		session.setAttribute("incomePayees", incomePayees);
		session.setAttribute("expensePaymentMethods", expensePaymentMethods);
		session.setAttribute("incomePaymentMethods", incomePaymentMethods);
		session.setAttribute("csvFormats", dtos);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/dataInput.jsp");
		dispatcher.forward(request, response);
	}
	
	
	
	
	
	
	
	// POST -> when user wants to insert transactions with CSV?
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// step-by-step
//		1.	Receive the file upload and detect encoding.
//		2.	Read the CSV lines into a List<String[]>.
//		3.	Fetch the mapping from the DB (using mappingId).
//		4.	Validate that the CSV has the columns needed by the mapping.
//		5.	Convert each CSV row into a TransactionsDTO object using the mapping.
//		6.	Insert those transactions into the database (via TransactionsDAO.insertCSV).
//		7.	If any errors occur along the way, set errorMsg and forward back to /WEB-INF/dataInput.jsp.
		
		HttpSession session = request.getSession();
        int accountId = ((AccountsDTO) session.getAttribute("user")).getAccountId();
        int mappingId = Integer.parseInt(request.getParameter("csvFormat")); // selected mapping ID

        // 1) File upload
        Part filePart = request.getPart("csvFile");
        if (filePart == null || filePart.getSize() == 0) {
            System.out.println("errorDIC1");
            request.setAttribute("errorMsg", "No file selected or file is empty.");
            request.getRequestDispatcher("/WEB-INF/dataInput.jsp").forward(request, response);
            return;
        }

        String fileName = filePart.getSubmittedFileName();
        long fileSize = filePart.getSize();
        
        System.out.println("Account ID '" + accountId +"' has uploaded file '" + fileName + "' with size " + fileSize + " bytes.");
        
        if (!fileName.toLowerCase().endsWith(".csv")) {
            request.setAttribute("errorMsg", "Please upload a .csv file.");
            request.getRequestDispatcher("/WEB-INF/dataInput.jsp").forward(request, response);
            return;
        }

        // 2) Detect encoding
        String guessedEncoding = "";
        try (InputStream in = filePart.getInputStream()) {
            guessedEncoding = detectEncoding(in);
            if (guessedEncoding != null) {
                System.out.println("Detected encoding: " + guessedEncoding);
            } else {
                System.out.println("Could not detect file encoding.");
                // Default to UTF-8 if unsure
                guessedEncoding = "UTF-8";
            }
        }

        // 3) Read CSV lines
        List<String[]> lines;
        try (InputStream in = filePart.getInputStream();
             InputStreamReader isr = new InputStreamReader(in, guessedEncoding);
             BufferedReader br = new BufferedReader(isr)) {
            
            lines = readAllLines(br);
        }
        
        
        if (lines.isEmpty() || lines.size() < 2) {
            // Usually we expect at least 1 header row + 1 data row
            request.setAttribute("errorMsg", "CSV file is empty or missing data rows.");
            request.getRequestDispatcher("/WEB-INF/dataInput.jsp").forward(request, response);
            return;
        }

        // 4) Get mapping from DB
        CSVMappingDTO mappingDTO = new CSVMappingDAO().selectById(accountId, mappingId);
        if (mappingDTO == null) {
            request.setAttribute("errorMsg", "No such mapping found. Please select a valid format.");
            request.getRequestDispatcher("/WEB-INF/dataInput.jsp").forward(request, response);
            return;
        }
        
        Map<String, List<String>> csvMap = mappingDTO.getMapping();
        
        
        //check values
        for (Map.Entry<String, List<String>> entry : csvMap.entrySet()) {
            String key = entry.getKey(); // The key of the map
            List<String> values = entry.getValue(); // The list of values associated with the key

            System.out.println("Key: " + key);

            // Iterate over the list of values
            for (String value : values) {
                System.out.println("    Value: " + value);
            }
        }

     // 5) Match CSV headers to find column indices
        //    lines.get(0) is the header row
        String[] csvHeader = lines.get(0);
        
        //check values
        for (String header : csvHeader) {
        	System.out.println(header);
        }
        
        // We'll build a dictionary of "DB column -> List of indices"
        Map<String, List<Integer>> columnIndices = new HashMap<>();

        // For each DB column (e.g. "transaction_date", "amount", etc.), find them in csvHeader
        for (Map.Entry<String, List<String>> entry : csvMap.entrySet()) {
            String dbColumn = entry.getKey();              // e.g. "transaction_date"
            List<String> mappedHeaders = entry.getValue(); // e.g. ["PurchaseDate"]

            List<Integer> indexes = findHeaderIndexes(csvHeader, mappedHeaders);
            if (indexes.isEmpty()) {
                // means we couldn't find the CSV header in the actual CSV columns
                request.setAttribute("errorMsg", 
                    "CSV does not have the expected header(s): " + mappedHeaders);
                request.getRequestDispatcher("/WEB-INF/dataInput.jsp").forward(request, response);
                return;
            }
            // e.g. columnIndices["transaction_date"] = [2]
            // or for "description", might be [4,5]
            columnIndices.put(dbColumn, indexes);
        }

        // 6) Parse data rows, skipping header => start from i=1
        List<TransactionsDTO> transactions = new ArrayList<>();
        List<TransactionsDTO> unregistered = new ArrayList<>();

        PayeesDAO payeesDao = new PayeesDAO();

        int rowCount = 0;
        int unregisteredRowCount = 0;

        for (int i = 1; i < lines.size(); i++) {
            String[] line = lines.get(i);
            TransactionsDTO t = new TransactionsDTO();
            t.setAccountId(accountId);

            // transaction_date
            if (columnIndices.containsKey("transaction_date")) {
                String rawDate = getConcatenated(line, columnIndices.get("transaction_date"));
                // parse or replace '/' with '-' etc.
                for (String date : line) {
                	if (date.contains("/")) {
                		date.replace("/", "-");
                	}
                }
                
                // convert to "YYYY-MM-DD" if needed
                t.setDate(rawDate);
            }

            // amount
            if (columnIndices.containsKey("amount")) {
                String rawAmount = getConcatenated(line, columnIndices.get("amount"));
                rawAmount = rawAmount.replace(",", "").replace("¥", "").replace("円", "").replace(" ", "").replace("　", "").trim();
                try {
                    t.setAmount(Integer.parseInt(rawAmount));
                } catch (NumberFormatException e) {
                    // handle error or set 0
                    t.setAmount(0);
                }
            }

            // payee_name -> look up payeeId
            if (columnIndices.containsKey("payee_name")) {
                String payeeName = getConcatenated(line, columnIndices.get("payee_name")).trim();
                // find in DB
                Integer payeeId = payeesDao.getId(payeeName, accountId);
                if (payeeId == null) {
                    // unregistered payee, user must assign category
                    t.setPayeeName(payeeName);
                } else {
                    t.setPayeeId(payeeId);
                }
            }

            // payment_method -> paymentMethodId
	         t.setPaymentMethod(mappingDTO.getPaymentMethodName());
	         t.setPaymentMethodId(mappingDTO.getPaymentMethodId());
               
            

            // description -> can have multiple columns
            if (columnIndices.containsKey("description")) {
                String desc = getConcatenated(line, columnIndices.get("description"));
                t.setDescription(desc);
            }

            // check if payeeId/paymentMethodId are set
            if (t.getPayeeId() == 0 || t.getPaymentMethodId() == 0) {
                // user must register them 
                unregistered.add(t);
                unregisteredRowCount++;
            } else {
                transactions.add(t);
                rowCount++;
            }
        }
        
        System.out.println("row registered = " + rowCount);
        System.out.println("row unregistered = " + unregisteredRowCount);

        // 7) Insert the known transactions
        int rowsInserted = 0;
        if (!transactions.isEmpty()) {
            rowsInserted = new TransactionsDAO().insertCSV(transactions);
        }
        
        // get categories
        ArrayList<CategoriesDTO> categories = new CategoriesDAO().selectCategoriesPayees(accountId);

        // 8) Forward depending on the result
        if (rowsInserted > 0) {
            String successMsg = rowsInserted + " 行を取り込みました。";
            request.setAttribute("successMsg", successMsg);

            if (!unregistered.isEmpty()) {
                request.setAttribute("unregisteredTransactions", unregistered);
                request.setAttribute("categories", categories);
                // Possibly forward to a page that helps user assign category/payment type
                request.getRequestDispatcher("/WEB-INF/completeData.jsp").forward(request, response);
            } else {
                // everything inserted successfully
                request.getRequestDispatcher("/WEB-INF/dataInput.jsp").forward(request, response);
            }
        } else {
            // no rows inserted
            if (unregistered.isEmpty()) {
                // means all lines were invalid or something
                request.setAttribute("errorMsg", "No rows were inserted. Possible error in CSV data.");
                request.getRequestDispatcher("/WEB-INF/dataInput.jsp").forward(request, response);
            } else {
                // everything ended up in unregistered
            	request.setAttribute("categories", categories);
                request.setAttribute("unregisteredTransactions", unregistered);
                request.getRequestDispatcher("/WEB-INF/completeData.jsp").forward(request, response);
            }
        }
	}

    
    
    
    
    
    
    
    
    
 // ~~~~~ Helper Methods ~~~~~
    
    
    
    /**
     * Reads the entire CSV using a BufferedReader, returning a List of String[].
     * Naive split on commas. In real apps, use a robust CSV parser (OpenCSV, Commons CSV, etc.).
     */
    private List<String[]> readAllLines(BufferedReader br) throws IOException {
        List<String[]> lines = new ArrayList<>();
        String line;
        boolean firstLine = true;
        while ((line = br.readLine()) != null) {
        	if (firstLine) {
                // Remove BOM if it exists
                line = stripBOM(line);
                firstLine = false;
            }
        	if (line.contains("ＰａｙＰａｙ　")) {
        		line = line.replace("ＰａｙＰａｙ　", "");
        	}
        	if (line.contains("/")) {
        		line = line.replace("/", "-");
        	}
        	
            // simple split
//            String[] cols = line.split(",", -1);
            // remove surrounding quotes from each col, if needed
//            for (int i = 0; i < cols.length; i++) {
//                cols[i] = stripQuotes(cols[i].trim());
//            }
        	String[] columns = parseCsvLine(line);
            lines.add(columns);
        }
        return lines;
    }
    
    

	private String stripBOM(String line) {
	    // BOM is \uFEFF in UTF-8
	    if (line.length() > 0 && line.charAt(0) == '\uFEFF') {
	        return line.substring(1);
	    }
	    return line;
	}

//    private String stripQuotes(String val) {
//        if (val.startsWith("\"") && val.endsWith("\"") && val.length() >= 2) {
//            return val.substring(1, val.length()-1);
//        }
//        return val;
//    }
    
    /**
     * A basic CSV parsing method that:
     *  - Respects double quotes for fields
     *  - Allows for commas inside quoted text
     *  - Handles escaped quotes ("") inside quoted fields
     */
    private String[] parseCsvLine(String line) {
        List<String> columns = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        boolean insideQuotes = false;

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);

            if (c == '"') {
                // If we're inside quotes and we see another quote, we look ahead to see if it’s escaped quotes like ""
                if (insideQuotes && i + 1 < line.length() && line.charAt(i + 1) == '"') {
                    // It's an escaped quote, so append a literal quote to the current token
                    current.append('"');
                    i++; // Skip the next quote
                } else {
                    // Toggle inside/outside quotes
                    insideQuotes = !insideQuotes;
                }
            }
            else if (c == ',' && !insideQuotes) {
                // We're at a comma and not inside quotes -> column boundary
                columns.add(current.toString());
                current.setLength(0); // reset
            }
            else {
                // Just a regular character
                current.append(c);
            }
        }
        

        // Add the final column (even if empty)
        columns.add(current.toString());

        return columns.toArray(new String[0]);
    }

    /** 
     * Given a CSV header row and a list of expected column names from the mapping,
     * find matching indices. If multiple mapping names, we can have multiple indices 
     * (like for "description").
     */
    private List<Integer> findHeaderIndexes(String[] csvHeader, List<String> mappedHeaders) {
        List<Integer> indexes = new ArrayList<>();
        for (String needed : mappedHeaders) {
            boolean found = false;
            for (int i = 0; i < csvHeader.length; i++) {
                if (csvHeader[i].equalsIgnoreCase(needed)) {
                    indexes.add(i);
                    found = true;
                    break; // if you only want one index per needed header
                }
            }
            if (!found) {
                // We didn't find "needed" in the CSV header => fail
                return Collections.emptyList();
            }
        }
        return indexes;
    }


    /** 
     * Given a row and a list of indices, concatenate the values with a space or delimiter. 
     */
    private String getConcatenated(String[] row, List<Integer> idxs) {
        StringBuilder sb = new StringBuilder();
        for (int idx : idxs) {
            if (idx < row.length) {
                if (sb.length() > 0) sb.append(" ");
                sb.append(row[idx]);
            }
        }
        return sb.toString().trim();
    }
    
    
    /**
     * Attempt to detect encoding. If detection fails, default to "UTF-8".
     */
//    private String detectOrDefaultEncoding(Part filePart) throws IOException {
//        try (InputStream in = filePart.getInputStream()) {
//            String enc = detectEncoding(in);
//            if (enc != null) {
//                return enc;
//            }
//        }
//        return "Shift_JIS";
//    }
    /**
     * Use Mozilla's UniversalDetector to guess the encoding of an InputStream.
     */
    public static String detectEncoding(InputStream in) throws IOException {
        UniversalDetector detector = new UniversalDetector(null);
        byte[] buf = new byte[4096];
        int nread;
        while ((nread = in.read(buf)) > 0 && !detector.isDone()) {
            detector.handleData(buf, 0, nread);
        }
        detector.dataEnd();
        return detector.getDetectedCharset(); // e.g. "UTF-8" or "Shift_JIS"
    }
}