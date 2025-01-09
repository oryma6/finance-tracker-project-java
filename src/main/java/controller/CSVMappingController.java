package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.mozilla.universalchardet.UniversalDetector;

import dto.AccountsDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

@WebServlet("/csvMapping")
@MultipartConfig
public class CSVMappingController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        int accountId = ((AccountsDTO) session.getAttribute("user")).getAccountId();
        
        // Get the file part and check for null/emptiness
        Part filePart = request.getPart("newCSV");
        if (filePart == null || filePart.getSize() == 0) {
            System.out.println("errorCMC1");
            request.setAttribute("errorMsg", "No file selected or file is empty.");
            request.getRequestDispatcher("/WEB-INF/dataSettings.jsp").forward(request, response);
            return;
        }
        
        // Detect encoding
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
        
        // Just for confirming
        String fileName = filePart.getSubmittedFileName();
        long fileSize = filePart.getSize();
        System.out.println("Account ID: '" + accountId + "' has uploaded file '" 
                           + fileName + "'. Size: " + fileSize + " bytes.");

        // Check if file is CSV
        if (!fileName.toLowerCase().endsWith(".csv")) {
            System.out.println("errorCMC2");
            request.setAttribute("errorMsg", "Please upload a .csv file.");
            request.getRequestDispatcher("/WEB-INF/dataSettings.jsp").forward(request, response);
            return;
        }

        // Read the first two lines using the guessed encoding
        // two lines only since this is only for displaying it back to user
        List<String[]> lines;
        try (InputStream in = filePart.getInputStream()) {
            lines = readFirstTwoLines(in, guessedEncoding);
        }

        if (lines.isEmpty()) {
            System.out.println("errorCMC3");
            request.setAttribute("errorMsg", "CSV file is empty.");
            request.getRequestDispatcher("/WEB-INF/dataSettings.jsp").forward(request, response);
            return;
        }
        
        String[] headerRow = lines.size() > 0 ? lines.get(0) : new String[0];
        String[] firstDataRow = lines.size() > 1 ? lines.get(1) : new String[0];
        
        

        // Put them in request scope
        request.setAttribute("headerRow", headerRow);
        request.setAttribute("firstDataRow", firstDataRow);
        request.setAttribute("uploadedFileName", fileName);

        request.getRequestDispatcher("/WEB-INF/csvMappingMatch.jsp").forward(request, response);
    }

    /**
     * Reads up to two lines from the CSV using the given encoding.
     */
    private List<String[]> readFirstTwoLines(InputStream inputStream, String encoding) throws IOException {
        List<String[]> result = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, encoding))) {
            String line;
            int count = 0;

            while ((line = br.readLine()) != null && count < 2) {
            	// cleaning data
            	if (line.contains("ＰａｙＰａｙ　")) {
            		line = line.replace("ＰａｙＰａｙ　", "");
            	}
                // Parse the CSV line, handling quotes and commas
                String[] columns = parseCsvLine(line);
                result.add(columns);
                count++;
            }
        }

        return result;
    }

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