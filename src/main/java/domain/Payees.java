package domain;

public class Payees {
	private String payeeName;
	private int payeeId;
	
	private String categoryName;
	private int categoryId;
	
	// for dataInput.jsp display
	public Payees(String payeeName, int payeeId) {
		this.payeeName = payeeName;
		this.payeeId = payeeId;
	}
	
	// for category.jsp table
	public Payees(String payeeName, int payeeId, String categoryName, int categoryId) {
		this.payeeName = payeeName;
		this.payeeId = payeeId;
		this.categoryName = categoryName;
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
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
	
	
}	
