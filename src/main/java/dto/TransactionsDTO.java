package dto;

public class TransactionsDTO {
	// class to store all of the transactions information
	private int transactionId; // will be used when updating/deleting
	private int accountId;
	private Integer amount; 
	private String type;
	private int typeId;
	
	// TODO change date data type later if needed. 
	// for now let's try use String 
	private String date; 
	private String description;
	
	private String category;
	private int categoryId;
	private String categoryColor;
	
	private String paymentMethod;
	private int paymentMethodId;
	
	private String payeeName;
	private int payeeId;
	
	public TransactionsDTO() {
		
	}
	
	// Constructor when receiving data input (1 row) from dataInput.jsp
	public TransactionsDTO(String date, int amount, int payeeId, int paymentMethodId, String description, String type) {
		this.date = date;
		this.amount = amount;
		this.payeeId = payeeId;
		this.paymentMethodId = paymentMethodId;
		this.description = description;
	}
	
	// Constructor when retrieving data from database (whole columns)
	public TransactionsDTO(
			int transactionId, 
			String date, int amount, String description, 
			int typeId, String type, 
			int payeeId, String payeeName,
			int paymentMethodId, String paymentMethod,
			int categoryId, String category) {
		this.transactionId = transactionId;
		this.date = date;
		this.amount = amount;
		this.description = description;
		
		this.typeId = typeId;
		this.type = type;
		
		this.payeeId = payeeId;
		this.payeeName = payeeName;
		
		this.paymentMethodId = paymentMethodId;
		this.paymentMethod = paymentMethod;
		
		this.categoryId = categoryId;
		this.category = category;
	}
	
	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}
	
	public int getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	
	public String getCategoryColor() {
		return categoryColor;
	}
	
	public void setCategoryColor(String categoryColor) {
		this.categoryColor = categoryColor;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public int getPaymentMethodId() {
		return paymentMethodId;
	}

	public void setPaymentMethodId(int paymentMethodId) {
		this.paymentMethodId = paymentMethodId;
	}

	public String getPayeeName() {
		return payeeName;
	}

	public void setPayeeName(String payeeName) {
		this.payeeName = payeeName;
	}

	public int getPayeeId() {
		return payeeId;
	}

	public void setPayeeId(int payeeId) {
		this.payeeId = payeeId;
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}
	
	
}
