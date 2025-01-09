package controller;

import java.io.IOException;
import java.sql.Date;

import dto.AccountsDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import dao.PayeesDAO;
import dao.TransactionsDAO;

@WebServlet("/completeData")
public class CompleteCSVDataInputController extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	
	// complete the unfinished transaction inputs from completeData.jsp
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		
		HttpSession session = request.getSession();
        int accountId = ((AccountsDTO) session.getAttribute("user")).getAccountId();
        
        // get indexes
        String[] strIndexes = request.getParameterValues("transIndex");
        
        int count = 0;
        
        
        // insert payees and thrn transaction for each unregistered row
        try {
	        for (String index : strIndexes) {
	        	Date date = Date.valueOf(request.getParameter("transDate_" + index));
	        	int amount = Integer.parseInt(request.getParameter("transAmount_" + index));
	        	String desc = request.getParameter("transDesc_" + index);
	        	int paymentId = Integer.parseInt(request.getParameter("transPaymentMethod_" + index));
	        	
	        	String newPayeeName = request.getParameter("transPayeeName_" + index);
	        	int categoryId = Integer.parseInt(request.getParameter("category_" + index));
	        	
	        	// insert the payee with category and get the id for the new payee
	        	PayeesDAO payDao = new PayeesDAO();
	        	int result = payDao.insert(accountId, categoryId, newPayeeName);
	        	int payeeId = 0;
	        	
	        	if (result == 1) {
	        		payeeId = payDao.getId(newPayeeName, accountId);
	        	}
	        	
	        	// insert the transaction
	        	if (payeeId != 0) {
	        		result = new TransactionsDAO().insertRow(accountId, date, amount, payeeId, paymentId, desc);
	        	}
	        	
	        	if (result == 1) {
	        		count++;
	        	}
	        }
        	
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid date format for transaction date: " + e.getMessage());
            request.setAttribute("errorMsg", "エラーが発生しました。");
            request.getRequestDispatcher("/WEB-INF/dataInput.jsp").forward(request, response);
        } catch (NullPointerException e) {
            System.err.println("Missing required parameter: " + e.getMessage());
            request.setAttribute("errorMsg", "エラーが発生しました。");
            request.getRequestDispatcher("/WEB-INF/dataInput.jsp").forward(request, response);
        }
        
        request.setAttribute("successMsg", count + "件の取引が保存されました。");
        request.getRequestDispatcher("/WEB-INF/dataInput.jsp").forward(request, response);
        
	}
}
