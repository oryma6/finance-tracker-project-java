package controller;

import java.io.IOException;

import dao.AccountsDAO;
import dao.CategoriesDAO;
import dao.PaymentMethodsDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import dto.AccountsDTO;
import service.RegisterService;

@WebServlet("/register")
public class RegisterController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/register.jsp");
		dispatcher.forward(request, response);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		// get input
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");
		String name = (String) session.getAttribute("name");
		String age = (String) session.getAttribute("age");
		String email = (String) session.getAttribute("email");
		String password = (String) session.getAttribute("password");
		
		System.out.println("username: " + username + "\nage: " + age);
			
		session.invalidate();
		
		// set input into a DTO instance
		AccountsDTO dto = new AccountsDTO(username, name, age, email, password);
		
		RegisterService service = new RegisterService(); 
		
		boolean result = service.register(dto);
		
		if(result) {
			int accountId = new AccountsDAO().getId(dto);
			dto.setAccountId(accountId);
			
			int newCategoriesResult = new CategoriesDAO().insertCategory(dto); // initialize deafult categories for this user
			
			PaymentMethodsDAO pmDao = new PaymentMethodsDAO(); // initialize default paymentmethods
			// expense
			pmDao.insert(accountId, "現金", 111);
			pmDao.insert(accountId, "クレジットカード", 111);
			pmDao.insert(accountId, "銀行口座", 111);
			// income
			pmDao.insert(accountId, "現金", 222);
			pmDao.insert(accountId, "銀行口座", 222);
			
			if(newCategoriesResult == 1) {
				System.out.println("登録成功 √");
				request.setAttribute("msg", "登録成功 √");
			}
		} else {
			System.out.println("登録失敗 X");
			request.setAttribute("msg", "登録失敗 X");
		}
		
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/welcome.jsp");
		dispatcher.forward(request, response);
		
	}

}
