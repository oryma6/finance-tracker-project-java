package service;

import jakarta.servlet.http.HttpServletRequest;
import dao.AccountsDAO;

import validation.Validation;

public class UpdateAccountService {
	private String field = null; 
	
	// 1 function for each field to update(definitely there are better ways
	// but for now i'm going this way
	public int updateUsername(int accountId, String newUsername, HttpServletRequest request) {
		int result = 0;
		
		Validation validation = new Validation();
		validation.isBlank("'Username' ", newUsername);
		validation.length("'Username' ", newUsername, 3, 20);
		
		if (validation.hasErrorMsg()) {
			request.setAttribute("errorUpdateMsg", validation.getErrorMsgList());
			return result;
		} 
		
		field = "username";
		result = new AccountsDAO().update(accountId, newUsername, field);
		return result;
	}
	
	
	
	
	public int updateName(int accountId, String newName, HttpServletRequest request) {
		int result = 0;
		
		Validation validation = new Validation();
		validation.isBlank("'Name' ", newName);
		validation.length("'Name' ", newName, 2, 50);
		
		if (validation.hasErrorMsg()) {
			request.setAttribute("errorUpdateMsg", validation.getErrorMsgList());
			return result;
		} 
		
		field = "name";
		result = new AccountsDAO().update(accountId, newName, field);
		return result;
	}
	
	
	
	
	public int updateAge(int accountId, String newAge, HttpServletRequest request) {
		int result = 0;
		
		Validation validation = new Validation();
		validation.isBlank("'Age' ", newAge);
		validation.isAge("'Age' ", Integer.parseInt(newAge));
		
		if (validation.hasErrorMsg()) {
			request.setAttribute("errorUpdateMsg", validation.getErrorMsgList());
			return result;
		} 
		
		field = "age";
		result = new AccountsDAO().update(accountId, newAge, field);
		return result;
	}
	
	
	
	
	public int updateEmail(int accountId, String newEmail, HttpServletRequest request) {
		int result = 0;
		
		Validation validation = new Validation();
		validation.isBlank("'Email' ", newEmail);
		validation.length("'Email' ", newEmail, 8, 50);
		
		if (validation.hasErrorMsg()) {
			request.setAttribute("errorUpdateMsg", validation.getErrorMsgList());
			return result;
		} 
		
		field = "email";
		result = new AccountsDAO().update(accountId, newEmail, field);
		return result;
	}
	
	
	
	
	public int updatePassword(int accountId, String newPassword, HttpServletRequest request) {
		int result = 0;
		
		Validation validation = new Validation();
		validation.isBlank("'Password' ", newPassword);
		validation.length("'Password' ", newPassword, 6, 30);
		
		if (validation.hasErrorMsg()) {
			request.setAttribute("errorUpdateMsg", validation.getErrorMsgList());
			return result;
		} 
		
		field = "password";
		result = new AccountsDAO().update(accountId, newPassword, field);
		return result;
	}
}
