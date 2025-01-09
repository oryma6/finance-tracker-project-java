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
import service.DeleteAccountService;

@WebServlet("/DeleteAccount")
public class DeleteAccountController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false); // get current session, not making a new one
		AccountsDTO dto = (AccountsDTO) session.getAttribute("user");
		
		int result = new DeleteAccountService().delete(dto);
		
		if (result == 1) {
			request.setAttribute("deleted_account_msg", "Account '" + dto.getUsername() + "' has been Deleted");
			RequestDispatcher dispatcher = request.getRequestDispatcher("/welcome.jsp");
			dispatcher.forward(request, response);
		}
	}
}