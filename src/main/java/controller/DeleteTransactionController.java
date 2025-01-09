package controller;

import java.io.IOException;

import dao.TransactionsDAO;
import dto.AccountsDTO;
//import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@WebServlet("/deleteTransactions")
public class DeleteTransactionController extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	// POST - users select the transactions they want to delete, retrieving String[] 
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		int accountId = ((AccountsDTO) session.getAttribute("user")).getAccountId();
	
		
		String[] transactionIds = request.getParameterValues("transactionIds");
		
		if (transactionIds == null || transactionIds.length == 0) { // No IDs received, redirect or handle error
            response.sendRedirect("/CashDash/home");
            return;
		}
		
		TransactionsDAO dao = new TransactionsDAO();
		
		try {
			int[] ids = new int[transactionIds.length]; // empty array with the length of the transactionIds
			for (int i = 0; i < transactionIds.length; i++) { // convert each string to int, 
				ids[i] = Integer.parseInt(transactionIds[i]);
	        }
	            
	        int result = dao.deleteTransactions(ids); // passing in the int type ids DAO method to delete in DB, returns result (1 success or 0 failed)
	        
	        if (result == 1) {
	        	System.out.println("Account ID '" + accountId + "' has successfully deleted " + ids.length + " transactions");
	        	response.sendRedirect("/CashDash/home"); 
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
