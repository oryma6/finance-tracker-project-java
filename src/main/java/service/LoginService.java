package service;

import domain.Login;
import dto.AccountsDTO;

import jakarta.servlet.http.HttpServletRequest;
import validation.Validation;

import dao.AccountsDAO;

public class LoginService {
	
	public AccountsDTO loginCheck(HttpServletRequest request, Login login) {
		AccountsDTO dto = null;
		
		// Validation
		Validation validation = new Validation();
		validation.isBlank("ユーザーネーム", login.getUserName());
		validation.isBlank("パスワード", login.getPassword());
		validation.length("ユーザーネーム", login.getUserName(), 3, 20);
		validation.length("パスワード", login.getPassword(), 6, 30);
		
		if(validation.hasErrorMsg()) {
			request.setAttribute("errorMsgList", validation.getErrorMsgList());
			return dto;
		}
		
		AccountsDAO dao = new AccountsDAO();
		dto = dao.selectByIdAndPassword(login);
		return dto;
	}
}
