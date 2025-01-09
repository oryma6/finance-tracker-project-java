package controller;

import dao.TransactionsDAO;
import dto.AccountsDTO;
//import dto.TransactionsDTO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

//import java.io.BufferedReader;
import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;

@WebServlet("/updateTransaction")
public class UpdateTransactionController extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	// POST - when user updates a transaction from home.jsp
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		int accountId = ((AccountsDTO) session.getAttribute("user")).getAccountId();
		
		String transactionId = request.getParameter("updateTransactionId");
		String date = request.getParameter("updateDate");
		String amount = request.getParameter("updateAmount");
		String payee = request.getParameter("updatePayeeId");
		String paymentMethod = request.getParameter("updatePaymentMethodId");
		String description = request.getParameter("updateDescription");
		
		int result = new TransactionsDAO().updateTransaction(transactionId, date, amount, payee, paymentMethod, description);
		
		if (result == 1) {
			response.sendRedirect("/CashDash/home#t-" + transactionId);
			System.out.println("Redirecting Account ID '" + accountId + "' back to home.jsp with updated transaction.");
		}
//		} else {
//			response.sendError(500, "updating transaction error");
//		}
		
		
		
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		int accountId = ((AccountsDTO) session.getAttribute("user")).getAccountId();
		
		String strTransactionId = request.getParameter("transactionId");
		
		if (strTransactionId == null || strTransactionId.isEmpty()) {
			response.sendRedirect("/CashDash/home");
			return;
		}
		
		int transactionId = Integer.parseInt(strTransactionId);
		
		request.setAttribute("editTransactionId", transactionId);
		
		System.out.println("Account ID '" + accountId + "' requesting to update transaction #" + strTransactionId);
		
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/home.jsp");
		rd.forward(request, response);
	}
}
