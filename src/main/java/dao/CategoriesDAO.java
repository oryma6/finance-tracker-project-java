package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dto.AccountsDTO;
import dto.CategoriesDTO;

public class CategoriesDAO extends BaseDAO {
	
	//INSERT INTO categories (from user input on category.jsp)
	public int insertCategory(int accountId, int typeId, String categoryName, String categoryColor) {
		int result = 0;
		
		try(Connection connection = DriverManager.getConnection(URL, USER, PASS)){
			
			PreparedStatement statement = connection.prepareStatement(
					"INSERT INTO categories "
					+ "(account_id, type_id, category, color) "
					+ "VALUES (?, ?, ? ,?)"
				);
			statement.setInt(1, accountId);
			statement.setInt(2, typeId);
			statement.setString(3, categoryName);
			statement.setString(4, categoryColor);
			
			result = statement.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	// INSERT INTO categories when newly registered account (default categories)
	public int insertCategory(AccountsDTO dto) {
		int result = 0;
		
		try(Connection connection = DriverManager.getConnection(URL, USER, PASS)){
			PreparedStatement statement = connection.prepareStatement(
					"INSERT INTO categories "
					+ "(account_id, category, color, type_id) "
					+ "VALUES (?, ?, ?, ?)"
			);
			
			// expenses
			for (String[] categoryNameColor : CategoriesDTO.defaultExpenseCategories) {
				statement.setInt(1, dto.getAccountId());
				statement.setString(2, categoryNameColor[0]);
				statement.setString(3, categoryNameColor[1]);
				statement.setInt(4, 111);
				
				result = statement.executeUpdate();
				
				if (result == 1) {
					System.out.println(categoryNameColor[0] + " successfully registered");
				}
			}
			
			// incomes
			for (String[] categoryNameColor : CategoriesDTO.defaultIncomeCategories) {
				statement.setInt(1, dto.getAccountId());
				statement.setString(2, categoryNameColor[0]);
				statement.setString(3, categoryNameColor[1]);
				statement.setInt(4, 222);
				
				result = statement.executeUpdate();
				
				if (result == 1) {
					System.out.println(categoryNameColor[0] + " successfully registered");
				}
			}	
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	// SELECT all categories + payees/payers for that category
	// to display category -> payees (one-to-many relation) on category.jsp
	public ArrayList<CategoriesDTO> selectCategoriesPayees(int accountId) {
		ArrayList<CategoriesDTO> dtos = new ArrayList<CategoriesDTO>();
		
		try(Connection connection = DriverManager.getConnection(URL, USER, PASS)){
			PreparedStatement categoryStatement = connection.prepareStatement(
					"SELECT c.category_id, c.category, c.color, c.type_id, t.type_name "
					+ "FROM categories c "
					+ "INNER JOIN types t "
					+ "ON c.type_id = t.type_id "
					+ "WHERE account_id = ? "
					+ "ORDER BY color ASC"
				);
			categoryStatement.setInt(1, accountId);
			
			ResultSet categoryResult = categoryStatement.executeQuery();
			
			// for each category, get all info and 
			while (categoryResult.next()) {
				CategoriesDTO dto = new CategoriesDTO();
				dto.setCategoryId(categoryResult.getInt("category_id"));
				dto.setCategoryName(categoryResult.getString("category"));
				dto.setCategoryColor(categoryResult.getString("color"));
				dto.setType(categoryResult.getString("type_name"));
				
				ArrayList<String> tempPayeeList = new ArrayList<String>(); 
				ArrayList<Integer> payeeIdList = new ArrayList<Integer>();
					
				PreparedStatement payeeStatement = connection.prepareStatement(
						"SELECT payee_name, payee_id "
						+ "FROM payees "
						+ "WHERE account_id = ? "
						+ "AND category_id = ?"
					);
				payeeStatement.setInt(1, accountId);
				payeeStatement.setInt(2, dto.getCategoryId());
				
				ResultSet payeeResult = payeeStatement.executeQuery();
				
				while(payeeResult.next()) {
					tempPayeeList.add(payeeResult.getString("payee_name")); // append to list each row that
					payeeIdList.add(payeeResult.getInt("payee_id"));
				}
				dto.setPayees(tempPayeeList);
				
				dtos.add(dto);
			}
			
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dtos;
	}
	
	//update 
	public int update(int accountId, String categoryId, String categoryName, String typeId, String color){
		int result = 0;
		boolean isFirst = true;
		
		try (Connection connection = DriverManager.getConnection(URL, USER, PASS)) {
			StringBuilder sql = new StringBuilder("UPDATE categories SET ");
			
			if (categoryId != null && !categoryId.isEmpty()) {
				if (categoryName != null && !categoryName.isEmpty()) {
					if (isFirst) {
						sql.append("category = ? ");
						isFirst = false;
					}
				}
				if (typeId != null && !typeId.isEmpty()) {
					if (isFirst) {
						sql.append("type_id = ? ");
						isFirst = false;
					} else {
						sql.append(", type_id = ? ");
					}
				}
				if (color != null && !color.isEmpty()) {
					if (isFirst) {
						sql.append("color = ? ");
						isFirst = false;
					} else {
						sql.append(", color = ? ");
					}
				}
				sql.append("WHERE category_id = ? AND account_id = ?");
			}
			
			PreparedStatement statement = connection.prepareStatement(sql.toString());
			isFirst = true;
			int paramIndex = 1;
			
			System.out.println("update() CategoriesDAO Final Query: \n " + sql.toString());
			
			if (categoryId != null && !categoryId.isEmpty()) {
				if (categoryName != null && !categoryName.isEmpty()) {
					statement.setString(paramIndex++, categoryName);
				}
				if (typeId != null && !typeId.isEmpty()) {
					statement.setInt(paramIndex++, Integer.parseInt(typeId));
				}
				if (color != null && !color.isEmpty()) {
					statement.setString(paramIndex++, color);
				}
				
				statement.setInt(paramIndex++, Integer.parseInt(categoryId));
				statement.setInt(paramIndex++, accountId);
			}
			
			result = statement.executeUpdate();
			
			if (result == 1) {
				System.out.println("category '" + categoryName + "' has successfully been updated.");
			} else {
				System.out.println("Updating category '" + categoryName + "' has failed.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}

}
