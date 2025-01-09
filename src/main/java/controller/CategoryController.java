package controller;

import java.io.IOException;
import java.util.ArrayList;

import dao.CategoriesDAO;
import dao.PayeesDAO;
import dao.PaymentMethodsDAO;
import domain.Payees;
import domain.PaymentMethods;
import dto.AccountsDTO;
import dto.CategoriesDTO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/category")
public class CategoryController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	// GET -> response with category.jsp file, but also passing in the list of categories to show user
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		int accountId = ((AccountsDTO) session.getAttribute("user")).getAccountId();
		
		ArrayList<CategoriesDTO> categories = new CategoriesDAO().selectCategoriesPayees(accountId); // get all the categories and payees/payers for each category 
		
		session.setAttribute("categories", categories);
		
		ArrayList<PaymentMethods> paymentMethods = new PaymentMethodsDAO().selectAllPaymentMethods(accountId);
		
		session.setAttribute("paymentMethods", paymentMethods);
		
		ArrayList<Payees> payeeList = new PayeesDAO().selectPayees(accountId);
		
		session.setAttribute("payeeList", payeeList); // 'payees' might already be in session(?)
		
		System.out.println("Account ID: '" + accountId + "' forwarded to category.jsp");
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/category.jsp");
		dispatcher.forward(request, response);
	}
	
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}
}
