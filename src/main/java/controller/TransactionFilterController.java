package controller;

import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;

import dao.TransactionsDAO;
import domain.FilterValues;
import dto.TransactionsDTO;

//import dao.CategoriesDAO;
import dto.AccountsDTO;
//import dto.CategoriesDTO;
import dto.CategorySummaryDTO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/filterForm")
public class TransactionFilterController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	// GET -> when filter request is recieved
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		int accountId = ((AccountsDTO) session.getAttribute("user")).getAccountId();
		
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
        
        // get the filtered value for pie chart
        HashMap<String, CategorySummaryDTO> categoryMap = new TransactionsDAO().getSummary(filteredTransactions); // for default view of transaction on pie chart
		
		ArrayList<CategorySummaryDTO> catList = new ArrayList<CategorySummaryDTO>(); //empty list (to be filled and passed to Session Scope later)
		for (CategorySummaryDTO cat : categoryMap.values()) {
			catList.add(cat);
		}
		int totalAmount = categoryMap.values().stream().mapToInt(CategorySummaryDTO::getAmount).sum();// total sum of all (for percentage in pie chart
		
		int amountBalance = dao.getBalance(filteredTransactions); // balance, income - expense
		int income = dao.getIncome(filteredTransactions);
		int expenses = dao.getExpense(filteredTransactions);
		
		if (amountBalance < 0) {
			session.setAttribute("balanceColor", "red");
		} else if (amountBalance >= 0) {
			session.setAttribute("balanceColor", "green");
		}
        
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
        
        
        request.setAttribute("filterValues", filterValues);
        session.setAttribute("transactions", filteredTransactions);
        session.setAttribute("categorySummary", catList);
		session.setAttribute("totalAmount", totalAmount);
		session.setAttribute("amountBalance", amountBalance);
		session.setAttribute("expenses", expenses);
		session.setAttribute("income", income);
        
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/home.jsp");
        rd.forward(request, response);

	}
	
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}
}
