package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import dto.CSVMappingDTO;

public class CSVMappingDAO extends BaseDAO{
	
	private static final ObjectMapper mapper = new ObjectMapper();
	
	
	// get all csv mapping, for user to select the format they want on dataInput.jsp
	public ArrayList<CSVMappingDTO> selectAll(int accountId){
		ArrayList<CSVMappingDTO> dtos = new ArrayList<>();
		
		try(Connection connection = DriverManager.getConnection(URL, USER, PASS)){
			PreparedStatement statement = connection.prepareStatement(
					"SELECT mapping_id, account_id, payment_method_id, mapping_format_name, mapping_data "
					+ "FROM csv_mapping "
					+ "WHERE account_id = ?"
					);
			statement.setInt(1, accountId);
			
			ResultSet rs = statement.executeQuery();
			
			while (rs.next()) {
				CSVMappingDTO dto = new CSVMappingDTO();
				dto.setMappingFormatId(rs.getInt("mapping_id"));
				dto.setPaymentMethodId(rs.getInt("payment_method_id"));
				dto.setMappingFormatName(rs.getString("mapping_format_name"));
				
				String mappingJson = rs.getString("mapping_data");
				dto.setMapping(mapper.readValue(mappingJson, new TypeReference<Map<String, List<String>>>(){}));
				
				
				dtos.add(dto);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		
		return dtos;
	}
	
	
	
	public CSVMappingDTO selectById(int accountId, int mappingId){
		CSVMappingDTO dto = null;
		
		try(Connection connection = DriverManager.getConnection(URL, USER, PASS)){
			PreparedStatement statement = connection.prepareStatement(
					"SELECT csv.mapping_id, csv.account_id, csv.payment_method_id, csv.mapping_format_name, csv.mapping_data, pay.payment_method "
					+ "FROM csv_mapping csv "
					+ "INNER JOIN payment_methods pay "
					+ "ON csv.payment_method_id = pay.payment_method_id "
					+ "WHERE csv.account_id = ? "
					+ "AND csv.mapping_id = ? "
					);
			statement.setInt(1, accountId);
			statement.setInt(2, mappingId);
			
			ResultSet rs = statement.executeQuery();
			
			if (rs.next()) {
				dto = new CSVMappingDTO();
				dto.setMappingFormatId(rs.getInt("mapping_id"));
				dto.setPaymentMethodId(rs.getInt("payment_method_id"));
				dto.setMappingFormatName(rs.getString("mapping_format_name"));
				dto.setPaymentMethodName(rs.getString("payment_method"));
				
				String mappingJson = rs.getString("mapping_data");
				dto.setMapping(mapper.readValue(mappingJson, new TypeReference<Map<String, List<String>>>(){}));
				
				}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		
		return dto;
	}
	
	
	
	// inserting a new format in table csv_mapping
	
	public int insertFormat(int accountId, CSVMappingDTO dto) {
		int result = 0;
		
		HashMap<String, List<String>> mapping = new HashMap<String, List<String>>();
		mapping.put("transaction_date", Collections.singletonList(dto.getDateCSVHeader()));
		mapping.put("amount", Collections.singletonList(dto.getAmountCSVHeader()));
		mapping.put("payee_name", Collections.singletonList(dto.getPayeeCSVHeader()));
		mapping.put("description", Arrays.asList(dto.getDescriptionCSVHeaders()));
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonString = "";
		try {
			jsonString = mapper.writeValueAsString(mapping);
			System.out.println("CSVMappingDAO, inserFormat() inserting: '" + jsonString + "' as JSONB into the database.");
			System.out.println("Format Name: " + dto.getMappingFormatName() + ", Payment Method ID: " + dto.getPaymentMethodId());
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return result;
		}

		
		try(Connection connection = DriverManager.getConnection(URL, USER, PASS)){
			PreparedStatement statement = connection.prepareStatement(
					"INSERT INTO csv_mapping "
					+ "(account_id, payment_method_id, mapping_format_name, mapping_data) "
					+ "VALUES "
					+ "(?, ?, ?, CAST(? AS JSONB))"
					);
			statement.setInt(1, accountId);
			statement.setInt(2, dto.getPaymentMethodId());
			statement.setString(3, dto.getMappingFormatName());
			statement.setString(4, jsonString); // this string will be casted to JSONB in Postgres , hopefully..
			
			result = statement.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
}
