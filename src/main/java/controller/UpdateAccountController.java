package controller;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import dto.AccountsDTO;
import service.UpdateAccountService;

@WebServlet("/updateAccount")
public class UpdateAccountController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	
	
	// GET
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession(); // get current session, not making a new one
		AccountsDTO dto = (AccountsDTO) session.getAttribute("user"); // get the user info
		
		System.out.println("update request from Account ID : " + dto.getAccountId()); // console account ID
		
		String updatingUsername = request.getParameter("updatingUsername");
		String updatingName = request.getParameter("updatingName");
		String updatingAge = request.getParameter("updatingAge");
		String updatingEmail = request.getParameter("updatingEmail");
		String updatingPassword = request.getParameter("updatingPassword");

		
		if (!(updatingUsername == null)) { // if updateUsername exist
			request.setAttribute("updateUsername", dto.getUsername());
		} else if (!(updatingName == null)) {
			request.setAttribute("updateName", dto.getName());
		} else if (!(updatingAge == null)) {
			request.setAttribute("updateAge", dto.getAge());
		} else if (!(updatingEmail == null)) {
			request.setAttribute("updateEmail", dto.getEmail());
		} else if (!(updatingPassword == null)) {
			request.setAttribute("updatePassword", "********");
		} 
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/userSettings.jsp");
		dispatcher.forward(request, response);
	}
	
	
	
	
	
	// POST
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession(); // get current session, not making a new one
		AccountsDTO dto = (AccountsDTO) session.getAttribute("user"); // get the user info
		int accountId = dto.getAccountId();
		
		int result = 0;
		
		if (!(request.getParameter("newUsername") == null)) { // if the new Username is not empty
			result = new UpdateAccountService().updateUsername(accountId, request.getParameter("newUsername"), request);
			if (result == 1) {
				dto.setUsername(request.getParameter("newUsername"));
				session.setAttribute("user", dto);
				System.out.println("'username' update successful.");
			}
		} else if (!(request.getParameter("newName") == null)) { 
			result = new UpdateAccountService().updateName(accountId, request.getParameter("newName"), request);
			if (result == 1) {
				dto.setName(request.getParameter("newName"));
				session.setAttribute("user", dto);
				System.out.println("'name' update successful.");
			}
		} else if (!(request.getParameter("newAge") == null)) { 
			result = new UpdateAccountService().updateAge(accountId, request.getParameter("newAge"), request);
			if (result == 1) {
				dto.setAge(request.getParameter("newAge"));
				session.setAttribute("user", dto);
				System.out.println("'age' update successful.");
			}
		} else if (!(request.getParameter("newEmail") == null)) { 
			result = new UpdateAccountService().updateEmail(accountId, request.getParameter("newEmail"), request);
			if (result == 1) {
				dto.setEmail(request.getParameter("newEmail"));
				session.setAttribute("user", dto);
				System.out.println("'email' update successful.");
			}
		} else if (!(request.getParameter("newPassword") == null)) { 
			result = new UpdateAccountService().updatePassword(accountId, request.getParameter("newPassword"), request);
			if (result == 1) {
				dto.setPassword(request.getParameter("newPassword"));
				session.setAttribute("user", dto);
				System.out.println("'password' update successful.");
			}
		} else {
			request.setAttribute("errorUpdate", "Error updating, field not found. #CONTROLLER");
		}
		
		request.setAttribute("updateSuccessMsg", "Account Update Succesful. #controller");
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/userSettings.jsp");
		dispatcher.forward(request, response);
	}
}