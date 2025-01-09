package controller;

import java.io.IOException;

//import jakarta.security.auth.message.callback.PrivateKeyCallback.Request;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import service.DataInputService;
import dto.AccountsDTO;
import dto.CategoriesDTO;
import dao.CategoriesDAO;
import dao.PaymentMethodsDAO;
import dao.TransactionsDAO;
import domain.Payees;
import domain.PaymentMethods;
import dto.TransactionsDTO;

import dto.CategorySummaryDTO;

import java.util.ArrayList;
import java.util.HashMap;


@WebServlet("/home")
public class HomeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		int accountId = ((AccountsDTO) session.getAttribute("user")).getAccountId();
		
		ArrayList<TransactionsDTO> transactions = new TransactionsDAO().selectAll(accountId); // all the data for default view of transaction on table
		HashMap<String, CategorySummaryDTO> categoryMap = new TransactionsDAO().getSummary(transactions); // for default view of transaction on pie chart
		
		ArrayList<CategoriesDTO> categories = new CategoriesDAO().selectCategoriesPayees(accountId); // get all the categories and payees/payers for each category (to show as suggestion when filtering)
		
		ArrayList<PaymentMethods> paymentMethods = new PaymentMethodsDAO().selectAllPaymentMethods(accountId);
		
		ArrayList<CategorySummaryDTO> catList = new ArrayList<CategorySummaryDTO>(); //empty list (to be filled and passed to Session Scope later)
		for (CategorySummaryDTO cat : categoryMap.values()) {
			catList.add(cat);
		}
		
		TransactionsDAO dao = new TransactionsDAO();
		
		int totalAmount = categoryMap.values().stream().mapToInt(CategorySummaryDTO::getAmount).sum();// total sum of all (for percentage in pie chart
		int amountBalance = dao.getBalance(transactions); // balance, income - expense
		int income = dao.getIncome(transactions);
		int expenses = dao.getExpense(transactions);
		
		if (amountBalance < 0) {
			session.setAttribute("balanceColor", "red");
		} else if (amountBalance >= 0) {
			session.setAttribute("balanceColor", "green");
		}
		
		session.setAttribute("transactions", transactions);
		session.setAttribute("categorySummary", catList);
		session.setAttribute("totalAmount", totalAmount);
		session.setAttribute("amountBalance", amountBalance);
		session.setAttribute("expenses", expenses);
		session.setAttribute("income", income);
		
		session.setAttribute("categories", categories);
		session.setAttribute("paymentMethods", paymentMethods);
		
		
		// this is fill up the options of payees and payers when updating a transaction (home.jsp->home-table.jsp)
		DataInputService service = new DataInputService();
		// get expense Payees and income Payers
		ArrayList<Payees> expensePayees = service.selectPayees(111, accountId);
		ArrayList<Payees> incomePayees = service.selectPayees(222, accountId); 
		// passing both expense and income of payees and paymentmethods into the current session
		session.setAttribute("expensePayees", expensePayees);
		session.setAttribute("incomePayees", incomePayees);
		
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/home.jsp");
		dispatcher.forward(request, response);
	}
}