package test;

import java.util.HashMap;

import domain.Login;

public class Testing {
	public static void main(String[] args) {
		
		
		// checking how HashMap works
		HashMap<String, Login> map = new HashMap<String, Login>();
		
		Login login1 = new Login("user1", "passwe");
		Login login2 = new Login("user4", "passjgg");
		
		map.put("tea", login1);
		map.put("coddee", login2);
		
		
		for (Login login : map.values()) {
			System.out.println(login.getUserName() + login.getPassword());
		}
	}
}
