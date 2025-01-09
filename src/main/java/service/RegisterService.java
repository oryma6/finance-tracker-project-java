package service;

import dao.AccountsDAO;
import dto.AccountsDTO;
import jakarta.servlet.http.HttpServletRequest;
import validation.Validation;

public class RegisterService {

	public boolean register(AccountsDTO dto) {//register
		AccountsDAO dao = new AccountsDAO();
		//insertメソッドを呼び出し、結果を整数型の変数resultへ代入してください。
		int result = dao.insert(dto);
		//もしもresultが1だったらtrueを戻し、それ以外ならばfalseを返すようif文を記述してください。
		if (result == 1) {
			return true;
		} else {
			return false;
		}
	}
	

	public boolean validate(HttpServletRequest request) {
		Validation validation = new Validation();
		
		String[] fieldnames = {"ユーザーネーム", "名前", "年齢", "メール", "パスワード"}; // array of fieldnames
		
		String[] userStringInput = {
				request.getParameter("username"),
				request.getParameter("name"),
				request.getParameter("age"),
				request.getParameter("email"),
				request.getParameter("password")
		}; //array of the values that user has inputted
		
		for (int i = 0; i < fieldnames.length; i++) {
			validation.isBlank(fieldnames[i], userStringInput[i]);
		}
		
		if (!validation.hasErrorMsg()) {
			int[] minChar = {3, 2, 1, 8, 6};
			int[] maxChar = {20, 50, 3, 50, 30};
			
			for (int i = 0; i < fieldnames.length; i++) {
				validation.length(fieldnames[i], userStringInput[i], minChar[i], maxChar[i]);
			}
		}
		
		if (!validation.hasErrorMsg()) {
			int age = Integer.parseInt(request.getParameter("age"));
			validation.isAge("年齢", age);
		}
		
		if (validation.hasErrorMsg()) {
			request.setAttribute("errorMsgList", validation.getErrorMsgList());
			return true;
		} else {
			return false;
		}
	}
}
