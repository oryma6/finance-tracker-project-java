package controller;

import java.io.IOException;
//import java.util.ArrayList;

import dao.TransactionsDAO;
//import domain.Payees;
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

import java.sql.Date;

@WebServlet("/expenseRowInput")
public class DataInputExpenseController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	// GET -> 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// do nothing
	}
	
	
	
	// POST -> when user wants to insert a new row of transaction
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		// get all information
		int accountId = ((AccountsDTO) session.getAttribute("user")).getAccountId(); // get current session's account_id
		System.out.println("Account ID: '" + accountId + "', inserting new expense transaction.");
		
		String stringDate = request.getParameter("transactionDate"); // get transaction_date as String
		Date transactionDate = Date.valueOf(stringDate); // convert to java.sql.Date object so sql can be inserted
		
		int amount = Integer.parseInt(request.getParameter("amount")); // get amount
		int payeeId = Integer.parseInt(request.getParameter("payeeId")); // get payee_id
		int paymentMethodId = Integer.parseInt(request.getParameter("paymentMethodId")); // get payment_method_id
		String description = request.getParameter("description"); // get description
		
		// INSERT INTO database and get query result
		int result = new TransactionsDAO().insertRow(accountId, transactionDate, amount, payeeId, paymentMethodId, description);
		
		
		// if passed, success message, else failed message
		if (result == 1) {
			request.setAttribute("expenseInputResultMsg", "支出取引登録成功 √");
		} else {
			request.setAttribute("expenseInputResultMsg", "支出取引登録失敗 X");
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/dataInput.jsp");
		dispatcher.forward(request, response);
	}

}
