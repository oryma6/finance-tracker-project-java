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

import domain.Login;
import dto.AccountsDTO;
import service.LoginService;


@WebServlet("/login")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/login.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		Login login = new Login(username, password);
		
		System.out.println("Pre-Login check – Username: " + username);
		
//		LoginService loginService = new LoginService();		
//		UsersDTO dto = loginService.loginCheck(request, login);
		AccountsDTO dto = new LoginService().loginCheck(request, login); // simplified 2 lines above
		
		
		
		if(dto == null) {
			request.setAttribute("msg", "ログインに失敗しました");
			RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/login.jsp");
			dispatcher.forward(request, response);
			return;
		} 
		System.out.println("LoginController - " + "Username: " + dto.getUsername() + " login successful.");
		
		HttpSession session = request.getSession(); //new session
		session.setAttribute("user", dto); // set dto in session as "user"
	
		
		response.sendRedirect("/CashDash/home");
		
	}

}
