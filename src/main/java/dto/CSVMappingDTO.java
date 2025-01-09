package dto;

import java.util.List;
import java.util.Map;

public class CSVMappingDTO {
	private int mappingFormatId;
	private String mappingFormatName;
	
	private int paymentMethodId;
	private String paymentMethodName;
	
	private String dateCSVHeader;
	private String amountCSVHeader;
	private String payeeCSVHeader;
	private String[] descriptionCSVHeaders;
	
	private Map<String, List<String>> mapping;
	
	private String uploadedFileName;
	
	
	public CSVMappingDTO() {
		
	}
	
	//constructor when user is making a new format, and passing this to DAO to be inserted into the Database
	public CSVMappingDTO(
			String mappingFormatName,
			int paymentMethodId,
			String dateCSVHeader,
			String amountCSVHeader,
			String payeeCSVHeader,
			String[] descriptionCSVHeaders){
		this.mappingFormatName = mappingFormatName;
		this.paymentMethodId = paymentMethodId;
		this.dateCSVHeader = dateCSVHeader;
		this.amountCSVHeader = amountCSVHeader;
		this.payeeCSVHeader = payeeCSVHeader;
		this.descriptionCSVHeaders = descriptionCSVHeaders;
	}
			
	public int getMappingFormatId() {
		return mappingFormatId;
	}

	public void setMappingFormatId(int mappingFormatId) {
		this.mappingFormatId = mappingFormatId;
	}

	public String getMappingFormatName() {
		return mappingFormatName;
	}

	public void setMappingFormatName(String mappingFormatName) {
		this.mappingFormatName = mappingFormatName;
	}

	public String getDateCSVHeader() {
		return dateCSVHeader;
	}

	public void setDateCSVHeader(String dateCSVHeader) {
		this.dateCSVHeader = dateCSVHeader;
	}

	public String getAmountCSVHeader() {
		return amountCSVHeader;
	}

	public void setAmountCSVHeader(String amountCSVHeader) {
		this.amountCSVHeader = amountCSVHeader;
	}

	public String getPayeeCSVHeader() {
		return payeeCSVHeader;
	}

	public void setPayeeCSVHeader(String payeeCSVHeader) {
		this.payeeCSVHeader = payeeCSVHeader;
	}

	public int getPaymentMethodId() {
		return paymentMethodId;
	}

	public void setPaymentMethodId(int paymentMethodId) {
		this.paymentMethodId = paymentMethodId;
	}

	public String[] getDescriptionCSVHeaders() {
		return descriptionCSVHeaders;
	}

	public void setDescriptionCSVHeaders(String[] descriptionCSVHeaders) {
		this.descriptionCSVHeaders = descriptionCSVHeaders;
	}

	public String getUploadedFileName() {
		return uploadedFileName;
	}

	public void setUploadedFileName(String uploadedFileName) {
		this.uploadedFileName = uploadedFileName;
	}

	public Map<String, List<String>> getMapping() {
		return mapping;
	}

	public void setMapping(Map<String, List<String>> mapping) {
		this.mapping = mapping;
	}

	public String getPaymentMethodName() {
		return paymentMethodName;
	}

	public void setPaymentMethodName(String paymentMethodName) {
		this.paymentMethodName = paymentMethodName;
	}
	
	
}
