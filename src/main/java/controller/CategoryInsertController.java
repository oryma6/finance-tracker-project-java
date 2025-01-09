package controller;

import java.io.IOException;
import java.util.ArrayList;

import dao.CategoriesDAO;
import dto.AccountsDTO;
import dto.CategoriesDTO;
//import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import service.CategoryService;

@WebServlet("/categoryInsert")
public class CategoryInsertController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}
	
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		int accountId = ((AccountsDTO) session.getAttribute("user")).getAccountId();
		
		String newCategoryName = request.getParameter("newCatName");
		String newCategoryColor = request.getParameter("newCatColor");
		int newCategoryType = Integer.parseInt(request.getParameter("newCatType")); 
		
		
		// check duplicates
		// get list of Categories
		@SuppressWarnings("unchecked")
		ArrayList<CategoriesDTO> categoriesDTO = (ArrayList<CategoriesDTO>) session.getAttribute("categories");
		ArrayList<String> categoryNames = new ArrayList<String>();
		for (CategoriesDTO dto : categoriesDTO) {
			categoryNames.add(dto.getCategoryName());
		}
		boolean result = new CategoryService().checkDuplicate(newCategoryName, categoryNames);
		if (result) {
			System.out.println("エラー：「" + newCategoryName + "」は登録済みです。");
			
			response.sendRedirect("/CashDash/category");
			
			return;
		}
		
		// insert user input
		int insertResult = new CategoriesDAO().insertCategory(accountId, newCategoryType, newCategoryName, newCategoryColor);
		if (insertResult == 1) {
			System.out.println("カテゴリー「" + newCategoryName + "」登録成功。");
			response.sendRedirect("/CashDash/category");
			
			return;
		}
	}
}
