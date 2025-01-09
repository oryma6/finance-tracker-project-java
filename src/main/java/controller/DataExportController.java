package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

//import dao.CategoriesDAO;
//import dao.PaymentMethodsDAO;
import dao.TransactionsDAO;
import domain.FilterValues;
//import domain.Payees;
//import domain.PaymentMethods;
//import domain.PaymentMethods;
//import jakarta.security.auth.message.callback.PrivateKeyCallback.Request;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

//import service.DataInputService;
import dto.AccountsDTO;
//import dto.CategoriesDTO;
import dto.TransactionsDTO;

@WebServlet("/export")
public class DataExportController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	// GET -> when user arrive arriving at dataExport.jsp
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		int accountId = ((AccountsDTO) session.getAttribute("user")).getAccountId();
		
		System.out.println("DataExport: Account ID Check: '" + accountId + "'");
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/dataExport.jsp");
		dispatcher.forward(request, response);
	}
	
	
	
	// POST -> when user exporting a new csv
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		AccountsDTO dto = ((AccountsDTO) session.getAttribute("user"));
		int accountId = dto.getAccountId();
		String username = dto.getUsername(); // for export filename
		
		// get all the filter values
		String dateFilterMode = request.getParameter("date_filter_mode");
		String dateFrom = request.getParameter("date_from");
        String dateTo = request.getParameter("date_to");
        String amountFilterMode = request.getParameter("amount_filter_mode");
        String amountFrom = request.getParameter("amount_from");
        String amountTo = request.getParameter("amount_to");
        String categorySearch = request.getParameter("category_search");
        String typeFilter = request.getParameter("type_filter");
        String payeeSearch = request.getParameter("payee_search");
        String paymentMethodSearch = request.getParameter("payment_method_search");
        String descriptionSearch = request.getParameter("description_search");
        String orderField = request.getParameter("order_field");
        String orderDirection = request.getParameter("order_direction");
        
        // get values from DB
        TransactionsDAO dao = new TransactionsDAO();
        ArrayList<TransactionsDTO> filteredTransactions = dao.selectFilter(
            accountId, 
            dateFilterMode, dateFrom, dateTo,
            amountFilterMode, amountFrom, amountTo,
            categorySearch,
            typeFilter,
            payeeSearch,
            paymentMethodSearch,
            descriptionSearch,
            orderField, orderDirection
        );
        
     // give it straight back to refill the filter 
        FilterValues filterValues = new FilterValues(
        		dateFilterMode, dateFrom, dateTo,
                amountFilterMode, amountFrom, amountTo,
                categorySearch,
                typeFilter,
                payeeSearch,
                paymentMethodSearch,
                descriptionSearch,
                orderField, orderDirection
        		);
        
        // generate file name
        String todayStr = java.time.LocalDate.now().toString();
        String fileName = username + "_transactions_" + todayStr + ".csv";
        
        
        // set headers so the browser knows it's a CSV file for download
        response.setContentType("text/csv; charset=UTF-8");
        // the 'Content-Disposition' header triggers a file download dialog
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

        // write CSV directly to the response output stream
        // for large data sets, you might prefer a BufferedWriter, but for smaller sets PrintWriter is fine
        try (PrintWriter writer = response.getWriter()) {
            
            // optional: add a BOM if you want to support Excel with UTF-8
            // writer.write('\uFEFF'); // Byte-Order Mark for Excel UTF-8 quirk but fine for now
            
            // write CSV header line
            writer.println("日付,金額,カテゴリー,種類,支払先・収入源,支払方法・受取方法,メモ");
            
            // Write each transaction as a CSV row
            for (TransactionsDTO t : filteredTransactions) {
                // safely replace or escape commas if needed, ex: wrap fields in quotes 
                // in case the text can contain commas. 
                // for simplicity, let's assume no escaping needed or fields won't contain commas.

                String date = t.getDate();               
                int amount = t.getAmount();              
                String category = t.getCategory();       
                String type = t.getType();             
                String payeeName = t.getPayeeName();    
                String paymentMethod = t.getPaymentMethod(); 
                String description = t.getDescription();

                // watch out for potential null values, just in case
                if (date == null) date = "";
                if (category == null) category = "";
                if (type == null) type = "";
                if (payeeName == null) payeeName = "";
                if (paymentMethod == null) paymentMethod = "";
                if (description == null) description = "";

                // CSV line
                writer.println(
                    date + "," +
                    amount + "," +
                    category + "," +
                    type + "," +
                    payeeName + "," +
                    paymentMethod + "," +
                    description
                );
            }

            // flush() will be called automatically by closing the PrintWriter
        }
        // the response ends here, and the user receives the CSV file
        
        request.setAttribute("filterValues", filterValues);
//        request.setAttribute("exportMsg", "データエクスポート完成");
//        session.setAttribute("transactions", filteredTransactions);
	}
}