package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dto.CategorySummaryDTO;
//import dto.AccountsDTO;
import dto.TransactionsDTO;

import java.sql.Date;



public class TransactionsDAO extends BaseDAO {
	
	
	
	
	
	
	
	
	
	// SELECT ALL (for home.jsp)
	public ArrayList<TransactionsDTO> selectAll(int accountId) {
		ArrayList<TransactionsDTO> dtos = new ArrayList<TransactionsDTO>();
		
		try(Connection connection = DriverManager.getConnection(URL, USER, PASS)){
			PreparedStatement statement = connection.prepareStatement(
					"SELECT t.transaction_date, t.amount, c.category, c.color, ty.type_name, p.payee_name, pm.payment_method, t.description, t.payee_id, c.category_id, ty.type_id, t.payment_method_id, t.transaction_id "
					+ "FROM transaction t "
					+ "INNER JOIN payees p ON t.payee_id = p.payee_id "
					+ "INNER JOIN categories c ON p.category_id = c.category_id "
					+ "INNER JOIN types ty ON c.type_id = ty.type_id "
					+ "INNER JOIN payment_methods pm on t.payment_method_id = pm.payment_method_id "
					+ "WHERE t.account_id = ? "
					+ "ORDER BY t.transaction_date DESC"
			);
			statement.setInt(1, accountId);
			
			ResultSet resultSet = statement.executeQuery();
			
			while(resultSet.next()) {
				TransactionsDTO dto = new TransactionsDTO();
				dto.setDate(resultSet.getString("transaction_date"));
				dto.setAmount(resultSet.getInt("amount"));
				dto.setCategory(resultSet.getString("category"));
				dto.setCategoryColor(resultSet.getString("color"));
				dto.setType(resultSet.getString("type_name"));
				dto.setPayeeName(resultSet.getString("payee_name"));
				dto.setPaymentMethod(resultSet.getString("payment_method"));
				dto.setDescription(resultSet.getString("description"));
				
				dto.setPayeeId(resultSet.getInt("payee_id"));
				dto.setPaymentMethodId(resultSet.getInt("payment_method_id"));
				dto.setCategoryId(resultSet.getInt("category_id"));
				dto.setTypeId(resultSet.getInt("type_id"));
				dto.setTransactionId(resultSet.getInt("transaction_id"));
				
				dtos.add(dto);
			}
				
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return dtos;
	}
	
	
	
	
	
	
	
	
	
	
	//SELECT ALL, (FILTERED) from home.jsp
	public ArrayList<TransactionsDTO> selectFilter(
			int accountId,
            String dateFilterMode, String dateFrom, String dateTo,
            String amountFilterMode, String amountFrom, String amountTo,
            String categorySearch,
            String typeFilter,
            String payeeSearch,
            String paymentMethodSearch,
            String descriptionSearch,
            String orderField, String orderDirection){
		//initialize empty list of rows
		ArrayList<TransactionsDTO> dtos = new ArrayList<TransactionsDTO>();
		
		
		try (Connection connection = DriverManager.getConnection(URL, USER, PASS)) {
			StringBuilder query = new StringBuilder(
					"SELECT t.transaction_date, t.amount, c.category, c.color, ty.type_name, p.payee_name, pm.payment_method, t.description, t.payee_id, c.category_id, ty.type_id, t.payment_method_id, t.transaction_id "
					+ "FROM transaction t "
					+ "INNER JOIN payees p ON t.payee_id = p.payee_id "
					+ "INNER JOIN categories c ON p.category_id = c.category_id "
					+ "INNER JOIN types ty ON c.type_id = ty.type_id "
					+ "INNER JOIN payment_methods pm on t.payment_method_id = pm.payment_method_id "
					+ "WHERE t.account_id = ? "
					);
			
			// We'll use a paramIndex to set parameters in order
	        // The first parameter (account_id) is known:
	        // paramIndex = 1 for account_id
	        // The next ones increment as we append conditions
	        int paramIndex = 1;

	        // We know we'll always set accountId as the first parameter
	        // But we set it after we prepare the statement.
			
	        if (dateFilterMode != null && !dateFilterMode.isEmpty()) {
				if (dateFilterMode.equals("after") && !(dateFrom == null)) {
					query.append("AND t.transaction_date >= ? ");
				} else if (dateFilterMode.equals("before") && !(dateFrom == null)) {
					query.append("AND t.transaction_date <= ? ");
				} else if (dateFilterMode.equals("between") && !(dateFrom == null) && !(dateTo == null)) {
					query.append("AND t.transaction_date >= ? AND t.transaction_date <= ? ");
				}
	        }
			
			if (amountFilterMode != null && !amountFilterMode.isEmpty()) {
				if (amountFilterMode.equals("greater") && !(amountFrom == null)) {
					query.append("AND t.amount >= ? ");
				} else if (amountFilterMode.equals("less") && !(amountFrom == null)) {
					query.append("AND t.amount <= ? ");
				} else if (amountFilterMode.equals("between") && !(amountFrom == null) && !(amountTo == null)) {
					query.append("AND t.amount >= ? AND t.amount <= ? ");
				}
			}
			
			if (categorySearch != null && !categorySearch.isEmpty()) { query.append("AND c.category LIKE ? ");}
			if (typeFilter != null && !typeFilter.isEmpty()) { query.append("AND ty.type_name LIKE ? ");}
			if (payeeSearch != null && !payeeSearch.isEmpty()) { query.append("AND p.payee_name LIKE ? ");}
			if (paymentMethodSearch != null && !paymentMethodSearch.isEmpty()) { query.append("AND pm.payment_method LIKE ? ");}
			if (descriptionSearch != null && !descriptionSearch.isEmpty()) { query.append("AND t.description LIKE ? ");}
			
			if (orderField != null && (orderField.equals("date") || orderField.equals("amount"))) {
	            query.append("ORDER BY ");
	            if (orderField.equals("date")) {
	                query.append("t.transaction_date ");
	            } else if (orderField.equals("amount")) {
	                query.append("t.amount ");
	            }
	            if (orderDirection != null && (orderDirection.equals("asc") || orderDirection.equals("desc"))) {
	                query.append(orderDirection.toUpperCase()).append(" ");
	            }
	        }
			
			PreparedStatement statement = connection.prepareStatement(query.toString());
			
			System.out.println("Final Query:" + query.toString()); // check the SQL finished query in console
			
			// setting parameters:
	        // first parameter is always account_id (see the base SQL query)
	        paramIndex = 1;
	        statement.setInt(paramIndex++, accountId); // increment 

	        if (dateFilterMode != null && !dateFilterMode.isEmpty()) {
	            if (dateFilterMode.equals("after") && dateFrom != null && !dateFrom.isEmpty()) {
	                statement.setDate(paramIndex++, java.sql.Date.valueOf(dateFrom));
	            } else if (dateFilterMode.equals("before") && dateFrom != null && !dateFrom.isEmpty()) {
	                statement.setDate(paramIndex++, java.sql.Date.valueOf(dateFrom));
	            } else if (dateFilterMode.equals("between") && dateFrom != null && !dateFrom.isEmpty() && dateTo != null && !dateTo.isEmpty()) {
	                statement.setDate(paramIndex++, java.sql.Date.valueOf(dateFrom));
	                statement.setDate(paramIndex++, java.sql.Date.valueOf(dateTo));
	            }
	        }

	        if (amountFilterMode != null && !amountFilterMode.isEmpty()) {
	            if (amountFilterMode.equals("greater") && amountFrom != null && !amountFrom.isEmpty()) {
	                statement.setInt(paramIndex++, Integer.parseInt(amountFrom));
	            } else if (amountFilterMode.equals("less") && amountFrom != null && !amountFrom.isEmpty()) {
	                statement.setInt(paramIndex++, Integer.parseInt(amountFrom));
	            } else if (amountFilterMode.equals("between") && amountFrom != null && !amountFrom.isEmpty() && amountTo != null && !amountTo.isEmpty()) {
	                statement.setInt(paramIndex++, Integer.parseInt(amountFrom));
	                statement.setInt(paramIndex++, Integer.parseInt(amountTo));
	            }
	        }

	        if (categorySearch != null && !categorySearch.isEmpty()) { statement.setString(paramIndex++, "%" + categorySearch + "%");}
	        if (typeFilter != null && !typeFilter.isEmpty()) { statement.setString(paramIndex++, "%" + typeFilter + "%");}
	        if (payeeSearch != null && !payeeSearch.isEmpty()) { statement.setString(paramIndex++, "%" + payeeSearch + "%");}
	        if (paymentMethodSearch != null && !paymentMethodSearch.isEmpty()) { statement.setString(paramIndex++, "%" + paymentMethodSearch + "%");}
	        if (descriptionSearch != null && !descriptionSearch.isEmpty()) { statement.setString(paramIndex++, "%" + descriptionSearch + "%");}

	        // Execute 
	        ResultSet rs = statement.executeQuery();

	        while (rs.next()) {
	            TransactionsDTO dto = new TransactionsDTO();
	            // map columns from rs to dto
	            dto.setDate(rs.getDate("transaction_date").toString());
	            dto.setAmount(rs.getInt("amount"));
	            dto.setCategory(rs.getString("category"));
	            dto.setCategoryColor(rs.getString("color"));
	            dto.setType(rs.getString("type_name"));
	            dto.setPayeeName(rs.getString("payee_name"));
	            dto.setPaymentMethod(rs.getString("payment_method"));
	            dto.setDescription(rs.getString("description"));
	            dto.setPayeeId(rs.getInt("payee_id"));
	            dto.setCategoryId(rs.getInt("category_id"));
	            dto.setTypeId(rs.getInt("type_id"));
	            dto.setPaymentMethodId(rs.getInt("payment_method_id"));
	            dto.setTransactionId(rs.getInt("transaction_id"));

	            dtos.add(dto);
	        }
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return dtos;
	}
	
	
	
	
	
	
	
	// get data to display on home.jsp (home-pie) pie chart
	public HashMap<String, CategorySummaryDTO> getSummary(ArrayList<TransactionsDTO> transactions){
		HashMap<String, CategorySummaryDTO> categoryMap = new HashMap<String, CategorySummaryDTO>();
		
		for (TransactionsDTO t : transactions) {
			if(!categoryMap.containsKey(t.getCategory())) {
				categoryMap.put(t.getCategory(), new CategorySummaryDTO(t.getCategory(), t.getAmount(), t.getCategoryColor(), t.getType()));
			} else {
				CategorySummaryDTO existingCat = categoryMap.get(t.getCategory());
				existingCat.addAmount(t.getAmount());
			}
		}
		
		return categoryMap;
	}
	
	
	
	// returning the balance depending on the transaction (if income, add. if expense, decrease.)
	public int getBalance(ArrayList<TransactionsDTO> transactions) {
		int balance = 0; // intialize as 0
		
		for (TransactionsDTO t : transactions) {
			if (t.getType().equals("収入")) {
				balance += t.getAmount();
			} else if (t.getType().equals("支出")) {
				balance -= t.getAmount();
			}
		}
		return balance;
	}
	
	
	// returning the total income
	public int getIncome(ArrayList<TransactionsDTO> transactions) {
		int income = 0;
		
		for (TransactionsDTO t : transactions) {
			if (t.getType().equals("収入")) {
				income += t.getAmount();
			}
		}
		
		return income;
	}
	
	//returning the total expense
	public int getExpense(ArrayList<TransactionsDTO> transactions) {
		int expense = 0;
		
		for (TransactionsDTO t : transactions) {
			if (t.getType().equals("支出")) {
				expense += t.getAmount();
			}
		}
		
		return expense;
	}
	
	
	
	
	
	
	
	
	
	// INSERT INTO transaction (1 row by row)
	// get user input of new transaction row information and passing them in to DAO
	public int insertRow(int accountId, Date transactionDate, int amount, int payeeId, int paymentMethodId, String description) {
		int result = 0;
		
		try (Connection connection = DriverManager.getConnection(URL, USER, PASS)) {
			PreparedStatement statement = connection.prepareStatement(
					"INSERT INTO transaction "
					+ "(account_id, transaction_date, amount, payee_id, payment_method_id, description) "
					+ "VALUES (?, ?, ? ,?, ? ,?);");
			statement.setInt(1, accountId);
			statement.setDate(2, transactionDate);
			statement.setInt(3, amount);
			statement.setInt(4, payeeId);
			statement.setInt(5, paymentMethodId);
			statement.setString(6, description);
			
			result = statement.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	
	
	
	// Delete transactions (from home.jsp)
	public int deleteTransactions(int[] ids) {
		int result = 0;
		
		try (Connection connection = DriverManager.getConnection(URL, USER, PASS)) {
			PreparedStatement statement = connection.prepareStatement(
					"DELETE FROM transaction WHERE transaction_id = ?"
					);
			
			for (int id : ids) {
				statement.setInt(1, id);
				result = statement.executeUpdate();
				
				if (result == 0) {
					return result;
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	
	
	// UPDATE Transactions (from home.jsp)
	public int updateTransaction(String transactionId, String date, String amount, String payee, String paymentMethod, String description) {
		int result = 0;
		boolean isFirst = true;
		
		try (Connection connection = DriverManager.getConnection(URL, USER, PASS)) {
			StringBuilder sql = new StringBuilder("UPDATE transaction SET ");
			
			if (transactionId != null && !transactionId.isEmpty()) {
				if (date != null && !date.isEmpty()) {
					if (isFirst) {
						sql.append("transaction_date = ? ");
						isFirst = false;
					}
				}
				if (amount != null && !amount.isEmpty()) {
					if (isFirst) {
						sql.append("amount = ? ");
						isFirst = false;
					} else {
						sql.append(", amount = ? ");
					}
				}
				if (payee != null && !payee.isEmpty()) {
					if (isFirst) {
						sql.append("payee_id = ? ");
						isFirst = false;
					} else {
						sql.append(", payee_id = ? ");
					}
				}
				if (paymentMethod != null && !paymentMethod.isEmpty()) {
					if (isFirst) {
						sql.append("payment_method_id = ? ");
						isFirst = false;
					} else {
						sql.append(", payment_method_id = ? ");
					}
				}
				if (description != null && !description.isEmpty()) {
					if (isFirst) {
						sql.append("description = ? ");
						isFirst = false;
					} else {
						sql.append(", description = ? ");
					}
				}
				sql.append("WHERE transaction_id = ?");
			}
			
			PreparedStatement statement = connection.prepareStatement(sql.toString());
			isFirst = true;
			int paramIndex = 1;
			
			System.out.println("updateTransaction() TransactionsDAO Final Query: \n " + sql.toString());
			
			if (transactionId != null && !transactionId.isEmpty()) {
				if (date != null && !date.isEmpty()) {
					statement.setDate(paramIndex++, java.sql.Date.valueOf(date));
				}
				if (amount != null && !amount.isEmpty()) {
					statement.setInt(paramIndex++, Integer.parseInt(amount));
				}
				if (payee != null && !payee.isEmpty()) {
					statement.setInt(paramIndex++, Integer.parseInt(payee));
				}
				if (paymentMethod != null && !paymentMethod.isEmpty()) {
					statement.setInt(paramIndex++, Integer.parseInt(paymentMethod));
				}
				if (description != null && !description.isEmpty()) {
					statement.setString(paramIndex++, description);
				}
				
				statement.setInt(paramIndex++, Integer.parseInt(transactionId));
			}
			
			result = statement.executeUpdate();
			
			if (result == 1) {
				System.out.println("Transaction #" + transactionId + " has successfully been updated.");
			} else {
				System.out.println("Updating Transaction #" + transactionId + " has failed.");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	
	// insert using csv
	public int insertCSV(List<TransactionsDTO> transactions) {
        int totalInserted = 0;

        String sql = "INSERT INTO transaction "
                   + "(account_id, transaction_date, amount, payee_id, payment_method_id, description) "
                   + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            for (TransactionsDTO t : transactions) {
                stmt.setInt(1, t.getAccountId());
                // Parse date if you need to store as a real date column
                // e.g. java.sql.Date.valueOf(t.getDate()) if it's "YYYY-MM-DD"
                stmt.setDate(2, java.sql.Date.valueOf(t.getDate()));
                stmt.setInt(3, t.getAmount());
                stmt.setInt(4, t.getPayeeId());
                stmt.setInt(5, t.getPaymentMethodId());
                stmt.setString(6, t.getDescription());
                
                stmt.addBatch();
            }

            int[] results = stmt.executeBatch();
            for (int r : results) {
                if (r == PreparedStatement.SUCCESS_NO_INFO || r > 0) {
                    totalInserted++;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalInserted;
    }

}
