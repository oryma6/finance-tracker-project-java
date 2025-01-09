package controller;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import service.RegisterService;


@WebServlet("/register-confirmation")
public class RegisterConfirmationController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		//Validation
		boolean notValid = new RegisterService().validate(request); // passing in the request
		
		if (notValid) {
			RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/register.jsp");
			dispatcher.forward(request, response);
			return; //end the Post function here
		}
		
		// if there is no error 
		// get user input
		String username = request.getParameter("username"); 
		String name = request.getParameter("name");
		String age = request.getParameter("age");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		// transferring all user input into it
		HttpSession session = request.getSession();
		session.setAttribute("username", username);
		session.setAttribute("name", name);
		session.setAttribute("age", age);
		session.setAttribute("email", email);
		session.setAttribute("password", password);
		
		//forward to confirmation page
		RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/register-confirmation.jsp");
		dispatcher.forward(request, response);
	}
}
