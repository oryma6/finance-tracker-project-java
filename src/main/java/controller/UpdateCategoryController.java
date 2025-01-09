package controller;

import java.io.IOException;

import dao.CategoriesDAO;
import dto.AccountsDTO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/updateCategory")
public class UpdateCategoryController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		int accountId = ((AccountsDTO) session.getAttribute("user")).getAccountId();
		
		String strCategoryId = request.getParameter("categoryId");
		
		if (strCategoryId == null || strCategoryId.isEmpty()) {
			response.sendRedirect("/CashDash/category");
			return;
		}
		
		int categoryId = Integer.parseInt(strCategoryId);
		
		request.setAttribute("editCategoryId", categoryId);
		
		System.out.println("Account ID '" + accountId + "' requesting to update category #" + strCategoryId);
		
		request.getRequestDispatcher("/WEB-INF/category.jsp").forward(request, response);
	}
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		int accountId = ((AccountsDTO) session.getAttribute("user")).getAccountId();
		
		String categoryId = request.getParameter("updateCategoryId");
		String categoryName = request.getParameter("updateCategoryName");
		String typeId = request.getParameter("updateType");
		String color = request.getParameter("updateColor");
		
		int result = new CategoriesDAO().update(accountId, categoryId, categoryName, typeId, color);
		
		if (result == 1) {
			response.sendRedirect("/CashDash/category#category-" + categoryId);
			System.out.println("Redirecting Account ID '" + accountId + "' back to home.jsp with updated transaction.");
		}
	}
}
