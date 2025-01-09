package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import domain.PaymentMethods;

public class PaymentMethodsDAO extends BaseDAO{
	
	//for suggestions in home.jsp
	public ArrayList<PaymentMethods> selectAllPaymentMethods(int accountId) {
		ArrayList<PaymentMethods> paymentMethods = new ArrayList<PaymentMethods>(); // empty list
		
		try(Connection connection = DriverManager.getConnection(URL, USER, PASS)){
			PreparedStatement statement = connection.prepareStatement(
					"SELECT p.payment_method, p.payment_method_id , t.type_name, t.type_id "
					+ "FROM payment_methods p "
					+ "INNER JOIN types t "
					+ "ON p.type_id = t.type_id "
					+ "WHERE account_id = ?"
					);
			statement.setInt(1, accountId);
			
			ResultSet resultSet = statement.executeQuery();
			
			while(resultSet.next()) {
				String paymentMethodName = resultSet.getString("payment_method"); //get payment method
				int paymentMethodId = resultSet.getInt("payment_method_id"); // get id
				String typeName = resultSet.getString("type_name");
				int typeId = resultSet.getInt("type_id");
				PaymentMethods paymentMethod = new PaymentMethods(paymentMethodName, paymentMethodId, typeName, typeId); // new instance of PaymentMethods, passing in the data from database
				paymentMethods.add(paymentMethod); // append to list
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return paymentMethods;
	}
	
	// for list in dataInput.jsp 
	public ArrayList<PaymentMethods> selectPaymentMethods(int type, int accountId) {
		
		ArrayList<PaymentMethods> paymentMethods = new ArrayList<PaymentMethods>(); // empty ist
		
		try(Connection connection = DriverManager.getConnection(URL, USER, PASS)){
			PreparedStatement statement = connection.prepareStatement(
					"SELECT payment_method, payment_method_id FROM payment_methods WHERE type_id = ? AND account_id = ?"
					);
			statement.setInt(1, type);
			statement.setInt(2, accountId);
			
			ResultSet resultSet = statement.executeQuery();
			
			while(resultSet.next()) {
				String paymentMethodName = resultSet.getString("payment_method"); //get payment method
				int paymentMethodId = resultSet.getInt("payment_method_id"); // get id
				PaymentMethods payee = new PaymentMethods(paymentMethodName, paymentMethodId); // new instance of PaymentMethods, passing in the data from database
				paymentMethods.add(payee); // append to list
			}
			
		} catch (SQLException e){
			e.printStackTrace();
		}
		return paymentMethods;
	}
	
	
	public Integer getId(String paymentMethod, int accountId) {
		Integer id = null;
		
		try(Connection connection = DriverManager.getConnection(URL, USER, PASS)){
			PreparedStatement statement = connection.prepareStatement(
					"SELECT payment_method, payment_method_id "
					+ "FROM payment_methods "
					+ "WHERE payment_method = ? "
					+ "AND account_id = ?"
					);
			statement.setString(1, paymentMethod);
			statement.setInt(2, accountId);
			
			ResultSet resultSet = statement.executeQuery();
			
			if(resultSet.next()) {
				id = resultSet.getInt("payment_method_id");
			}
			
		} catch (SQLException e){
			e.printStackTrace();
		}
		return id;
	}
	
	public int insert(int accountId, String paymentMethod, int type) {
		int result = 0;
		
		try (Connection connection = DriverManager.getConnection(URL, USER, PASS)){
			PreparedStatement st = connection.prepareStatement(
					"INSERT INTO payment_methods "
					+ "(account_id, payment_method, type_id) "
					+ "VALUES (?, ?, ?)");
			st.setInt(1, accountId);
			st.setString(2, paymentMethod);
			st.setInt(3, type);
			
			result = st.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}
}
