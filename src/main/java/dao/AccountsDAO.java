package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import domain.Login;
import dto.AccountsDTO;

public class AccountsDAO extends BaseDAO{
	
	public AccountsDTO selectByIdAndPassword(Login login) {
		AccountsDTO dto = null; //returns this null value if the SQL query doesn't return anything (failed)
		
		try (Connection connection = DriverManager.getConnection(URL, USER, PASS)) {
			PreparedStatement statement = connection.prepareStatement(
					"SELECT * FROM accounts WHERE username = ? AND password = ?");
			statement.setString(1, login.getUserName());
			statement.setString(2, login.getPassword());
			
			ResultSet resultSet = statement.executeQuery();

			
			if (resultSet.next()) {
				int accountId = resultSet.getInt("account_id");
				String username = resultSet.getString("username");
				String name = resultSet.getString("name");
				String age = resultSet.getString("age");
				String mail = resultSet.getString("email");
				String password = resultSet.getString("password");
				
				dto = new AccountsDTO(accountId, username, name, age, mail, password);
			}	
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
		return dto;
	}
	
	public int getId(AccountsDTO dto) {
		int accountId = 0;
		
		try (Connection connection = DriverManager.getConnection(URL, USER, PASS)) {
			PreparedStatement st = connection.prepareStatement(
					"SELECT account_id FROM accounts WHERE username = ? AND password = ?");
			st.setString(1, dto.getUsername());
			st.setString(2, dto.getPassword());
			
			ResultSet rs = st.executeQuery();
			
			if (rs.next()) {
				accountId = rs.getInt("account_id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return accountId;
	}

	// user register
	public int insert(AccountsDTO dto) {
		int result = 0;
		
		try(Connection connection = DriverManager.getConnection(URL, USER, PASS)){ //try-with-resources, closes connection automatically
			PreparedStatement statement = connection.prepareStatement(
					"INSERT INTO accounts (username, name, age, email, password) VALUES (?, ?, ?, ?, ?)"
					);
			
			//insert values to SQL query
			statement.setString(1, dto.getUsername());
			statement.setString(2, dto.getName());
			statement.setInt(3, Integer.parseInt(dto.getAge()));
			statement.setString(4, dto.getEmail());
			statement.setString(5, dto.getPassword());

			result = statement.executeUpdate(); // execute the SQL query, returns '1' if succeed
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	// update user info
	public int update(int accountId, String newValue, String field) {
		int result = 0;
				
		try (Connection connection = DriverManager.getConnection(URL, USER, PASS)){
			// StringBuilder to not make new String everytime appending
			StringBuilder query = new StringBuilder("UPDATE accounts SET ");
			
			if (field.equals("username")) {
				query.append("username = ? ");
			} else if (field.equals("name")) {
				query.append("name = ? ");
			} else if (field.equals("age")) {
				query.append("age = ? ");
			} else if (field.equals("email")) {
				query.append("email = ? ");
			} else if (field.equals("password")) {
				query.append("password = ? ");
			}
			query.append("WHERE account_id = ?");
			
			System.out.println("Query:" + query.toString()); // check the SQL finished query in console
			
			
			// prepare the statement, as String Object
			PreparedStatement statement = connection.prepareStatement(query.toString()); 
			
			
			
			if (field.equals("age")) { // plug in the newValue and accountId
				statement.setInt(1, Integer.parseInt(newValue));
			} else {
				statement.setString(1, newValue);
			}
			statement.setInt(2, accountId);
			
			
			//execute, returns 0 if failed, 1 if succeed
			result = statement.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	//deleting account
	public int deleteAccount(String username) {
		int result = 0;
		
		try (Connection connection = DriverManager.getConnection(URL, USER, PASS)){
			PreparedStatement statement = connection.prepareStatement("DELETE FROM accounts WHERE username = ?");
			statement.setString(1, username);
			result = statement.executeUpdate();
			
			if (result == 1) {
				System.out.println("Account '" + username + "' Deleted");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
}
