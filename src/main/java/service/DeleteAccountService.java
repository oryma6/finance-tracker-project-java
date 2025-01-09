package service;

import dto.AccountsDTO;
import dao.AccountsDAO;

public class DeleteAccountService {
	
	public int delete(AccountsDTO dto) {
		int result = 0; // initialized as 'failed' return value
		
		AccountsDAO dao = new AccountsDAO();
		result = dao.deleteAccount(dto.getUsername()); // returns 0 if failed, 1 if succeed
		
		return result;
	}
}
