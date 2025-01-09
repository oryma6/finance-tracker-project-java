package dto;

public class AccountsDTO {
	private int accountId;
	private String username;
	private String name;
	private String age;
	private String email;
	private String password; 
	
	public AccountsDTO(int accountId, String username, String name, String age, String email, String password) {
		this.accountId = accountId;
		this.username = username;
		this.name = name;
		this.age = age;
		this.email = email;
		this.password = password;
	}
	
	public AccountsDTO(String username, String name, String age, String email, String password) {
		this.username = username;
		this.name = name;
		this.age = age;
		this.email = email;
		this.password = password;
	}
	
	public int getAccountId() {
		return accountId;
	}
	
	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}	
	
}
