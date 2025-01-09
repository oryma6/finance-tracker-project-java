package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import domain.Payees;

public class PayeesDAO extends BaseDAO{
	
	// for list in dataInput.jsp
	public ArrayList<Payees> selectPayees(int type, int accountId) {
		
		ArrayList<Payees> payees = new ArrayList<Payees>(); // empty ist
		
		try(Connection connection = DriverManager.getConnection(URL, USER, PASS)){
			PreparedStatement statement = connection.prepareStatement(
					"SELECT p.payee_name, p.payee_id, p.category_id, c.category "
					+ "FROM payees p INNER JOIN categories c "
					+ "ON p.category_id = c.category_id "
					+ "WHERE c.type_id = ? AND p.account_id = ?"
					);
			statement.setInt(1, type);
			statement.setInt(2, accountId);
			
			ResultSet resultSet = statement.executeQuery();
			
			while(resultSet.next()) {
				String payeeName = resultSet.getString("payee_name"); //get payee_name
				int payeeId = resultSet.getInt("payee_id"); // get payee_id
				String categoryName = resultSet.getString("category");
				int categoryId = resultSet.getInt("category_id");
				Payees payee = new Payees(payeeName, payeeId, categoryName, categoryId); // new instance of Payees, passing in the data from database
				payees.add(payee); // append to list
			}
			
		} catch (SQLException e){
			e.printStackTrace();
		}
		return payees;
	}
	
	// for category.jsp
	public ArrayList<Payees> selectPayees(int accountId) {
		
		ArrayList<Payees> payees = new ArrayList<Payees>(); // empty ist
		
		try(Connection connection = DriverManager.getConnection(URL, USER, PASS)){
			PreparedStatement statement = connection.prepareStatement(
					"SELECT p.payee_name, p.payee_id, p.category_id, c.category "
					+ "FROM payees p INNER JOIN categories c "
					+ "ON p.category_id = c.category_id "
					+ "WHERE p.account_id = ? "
					+ "ORDER BY c.category "
					);
			statement.setInt(1, accountId);
			
			ResultSet resultSet = statement.executeQuery();
			
			while(resultSet.next()) {
				String payeeName = resultSet.getString("payee_name"); //get payee_name
				int payeeId = resultSet.getInt("payee_id"); // get payee_id
				String categoryName = resultSet.getString("category");
				int categoryId = resultSet.getInt("category_id");
				Payees payee = new Payees(payeeName, payeeId, categoryName, categoryId); // new instance of Payees, passing in the data from database
				payees.add(payee); // append to list
			}
			
		} catch (SQLException e){
			e.printStackTrace();
		}
		return payees;
	}
	
	
	public int insert(int accountId, int categoryId, String payeeName) {
		int result = 0;
		
		try(Connection connection = DriverManager.getConnection(URL, USER, PASS)){
			PreparedStatement statement = connection.prepareStatement(
					"INSERT INTO payees "
					+ "(account_id, category_id, payee_name) "
					+ "VALUES (?, ?, ?)");
			statement.setInt(1, accountId);
			statement.setInt(2, categoryId);
			statement.setString(3, payeeName);
			
			result = statement.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	
	public Integer getId(String payeeName, int accountId) {
		Integer payeeId = null;
		
		try(Connection connection = DriverManager.getConnection(URL, USER, PASS)){
			PreparedStatement statement = connection.prepareStatement(
					"SELECT payee_name, payee_id "
					+ "FROM payees "
					+ "WHERE payee_name = ? "
					+ "AND account_id = ?"
					);
			statement.setString(1, payeeName);
			statement.setInt(2, accountId);
			
			ResultSet resultSet = statement.executeQuery();
			
			if(resultSet.next()) {
				payeeId = resultSet.getInt("payee_id");
			}
			
		} catch (SQLException e){
			e.printStackTrace();
		}
		return payeeId;
	}
}
