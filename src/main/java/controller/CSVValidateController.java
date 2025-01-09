package controller;

import dto.AccountsDTO;
import dto.CSVMappingDTO;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dao.CSVMappingDAO;
import domain.PaymentMethods;

@WebServlet("/validateCSVMapping")
public class CSVValidateController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // get all the user input and required data
		HttpSession session = request.getSession();
		int accountId = ((AccountsDTO) session.getAttribute("user")).getAccountId();
		
		ArrayList<PaymentMethods> paymentMethods = (ArrayList<PaymentMethods>) session.getAttribute("paymentMethods");
		
		String mappingFormatName = request.getParameter("mappingFormatName");
		int dateIndex = Integer.parseInt(request.getParameter("csvCol_transactionDate")); // getting the indexes of the column, pass in the indexes into String[] below to get the Column Header and the value example. test the value example.
		int amountIndex = Integer.parseInt(request.getParameter("csvCol_amount"));
		int payeeIndex = Integer.parseInt(request.getParameter("csvCol_payeeName"));
		int paymentIndex = Integer.parseInt(request.getParameter("csvCol_paymentMethod"));
		int[] descriptionIndexes = Arrays.stream(request.getParameterValues("csvCol_description")).mapToInt(Integer::parseInt).toArray();
				
		String fileName = request.getParameter("uploadedFileName");
		String[] firstDataRow = request.getParameterValues("firstDataRow");
		String[] headerRow = request.getParameterValues("headerRow");
		
        List<String> errors = new ArrayList<>(); // collect any validation errors here

        
        // ------------------ Validate DATE ------------------
        // 1) Replace '/' with '-'
        String rawDate = firstDataRow[dateIndex].replace('/', '-').trim();
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false); // Strict parsing
        Date parsedDate = null;
        String formattedDate = null;
        try {
            parsedDate = sdf.parse(rawDate);
            // 2) Format the date as 'yyyy-MM-dd'
            formattedDate = sdf.format(parsedDate);
        } catch (ParseException e) {
            errors.add("Date format error in example row: '" + firstDataRow[dateIndex] 
                       + "'. Expected YYYY-MM-DD.");
        }
        
        // ------------------ Validate AMOUNT ------------------
        // remove commas, yen symbols, whitespace
        String rawAmount = firstDataRow[amountIndex]
                .replace(",", "")
                .replace("¥", "")
                .replace("円", "")
                .trim();
        
        Integer parsedAmount = null;
        if (!rawAmount.isEmpty()) {
            try {
                parsedAmount = Integer.valueOf(rawAmount);
            } catch (NumberFormatException e) {
                errors.add("Amount format error in example row: '" + firstDataRow[amountIndex] 
                           + "'. Must be integer without currency symbols.");
            }
        } else {
            errors.add("Amount is empty in example row.");
        }
        
        // ------------------ Validate PAYEE (string) ------------------
        String rawPayee = firstDataRow[payeeIndex].trim();
        if (rawPayee.isEmpty()) {
            errors.add("Payee name is empty in example row.");
        } else if (rawPayee.length() > 100) {
            errors.add("Payee name too long (max 100 chars). Value: " + rawPayee);
        }
        
        // ------------------ Validate PAYMENT METHOD (string) ------------------
        String rawPaymentMethod = "";
        for (PaymentMethods pm : paymentMethods) {
        	if (pm.getPaymentMethodId() == paymentIndex) rawPaymentMethod = pm.getPaymentMethodName();
        }
        if (rawPaymentMethod.length() > 30) {
            errors.add("Payment method is too long (max 50 chars). Value: " + rawPaymentMethod);
        }
        
        // ------------------ Validate DESCRIPTION (can be multiple columns) ------------------
        // We'll concatenate them for the example row
        StringBuilder descBuilder = new StringBuilder();
        for (int idx : descriptionIndexes) {
            if (idx < firstDataRow.length) {
                String part = firstDataRow[idx].trim();
                if (!part.isEmpty()) {
                    if (descBuilder.length() > 0) descBuilder.append(" | ");
                    descBuilder.append(part);
                }
            }
        }
        String finalDescription = descBuilder.toString();
        if (finalDescription.length() > 200) {
            finalDescription = finalDescription.substring(0, 200);
        }
        
        // If any errors found, forward back to dataSettings with messages
        if (!errors.isEmpty()) {
            request.setAttribute("csvErrors", errors);
            request.setAttribute("mappingFormatMsg", "Validation failed. Please fix the issues below.");
            request.getRequestDispatcher("/WEB-INF/dataSettings.jsp").forward(request, response);
            return;
        }
        
        
        System.out.println("Validated values: " + formattedDate + parsedAmount + rawPayee + rawPaymentMethod + finalDescription);
        
        System.out.print("headerRow Check: ");
        for (String hdr : headerRow) {
        	System.out.print(hdr + " - ");
        }
        System.out.println(headerRow[payeeIndex]);
        
        // Otherwise, validation passed. We can store the mapping in DB or session.
        String[] descriptionCSVHeaders = new String[descriptionIndexes.length];
        int temp_idx = 0; // to determine index of the String[] to be passed into the DAO function
        for (int i : descriptionIndexes) { // for determining which headerRow[] index
        	if (temp_idx <= descriptionIndexes.length) {
        		descriptionCSVHeaders[temp_idx] = headerRow[i];
        		temp_idx++;
        	}
        }
        
        CSVMappingDTO dto = new CSVMappingDTO(
        		mappingFormatName,
        		paymentIndex,
        		headerRow[dateIndex],
        		headerRow[amountIndex],
        		headerRow[payeeIndex],
    			descriptionCSVHeaders);
        dto.setUploadedFileName(fileName); // don't need to but just in case
        
        int result = new CSVMappingDAO().insertFormat(accountId, dto);
        
        if (result == 1) {
        	// if inserting succeed, then forward or redirect to confirm page or next step
            request.setAttribute("mappingFormatMsg", "検証が成功しました！フォーマット「" + mappingFormatName + "」は、CSV形式で取引をアップロードする際に使用できるようになりました。\n"
            		+ "<br>アップロードは<a href=\"/CashDash/dataInput\">取引入力ページ</a>から行うことができます！");
            request.getRequestDispatcher("/WEB-INF/dataSettings.jsp").forward(request, response);
        } else {
        	request.setAttribute("mappingFormatMsg", "エラーが発生しました。");
            request.getRequestDispatcher("/WEB-INF/dataSettings.jsp").forward(request, response);
        }
        
		
		
	}
}
