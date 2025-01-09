package dto;


// class to simplify the data needed for pie chart view on home.jsp
public class CategorySummaryDTO {
	private String category;
	private int amount;
	private String categoryColor;
	private String type;
	
	public CategorySummaryDTO(String category, int amount, String categoryColor, String type) {
		this.category = category;
		this.amount = amount;
		this.categoryColor = categoryColor;
		this.type = type;
	}
	
	public void addAmount(int amount) {
		this.amount += amount;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getCategoryColor() {
		return categoryColor;
	}

	public void setCategoryColor(String categoryColor) {
		this.categoryColor = categoryColor;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
}
