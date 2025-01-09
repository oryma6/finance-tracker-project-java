package domain;
// class to pass into request scope after filtering, 
// repopulating the filter values that user has inputted


public class FilterValues {
	private String dateFilterMode;
	private String dateFrom;
	private String dateTo;
	
	private String amountFilterMode;
	private String amountFrom;
	private String amountTo;
	
	private String categorySearch;	
	private String typeFilter;
	private String payeeSearch;
	private String paymentMethodSearch;
	private String descriptionSearch;
	
	private String orderField;
	private String orderDirection;
	
	
	public FilterValues(
			String dateFilterMode, String dateFrom, String dateTo,
            String amountFilterMode, String amountFrom, String amountTo,
            String categorySearch,
            String typeFilter,
            String payeeSearch,
            String paymentMethodSearch,
            String descriptionSearch,
            String orderField, String orderDirection) {
		
		this.dateFilterMode = dateFilterMode;
		this.dateFrom = dateFrom;
		this.dateTo = dateTo;
		
		this.amountFilterMode = amountFilterMode;
		this.amountFrom = amountFrom;
		this.amountTo = amountTo;
		
		this.categorySearch = categorySearch;		
		this.typeFilter = typeFilter;
		this.payeeSearch = payeeSearch;
		this.paymentMethodSearch = paymentMethodSearch;
		this.descriptionSearch = descriptionSearch;
		this.orderField = orderField;
		this.orderDirection = orderDirection;
	}
	
	public String getDateFilterMode() {
		return dateFilterMode;
	}
	public void setDateFilterMode(String dateFilterMode) {
		this.dateFilterMode = dateFilterMode;
	}
	public String getDateFrom() {
		return dateFrom;
	}
	public void setDateFrom(String dateFrom) {
		this.dateFrom = dateFrom;
	}
	public String getDateTo() {
		return dateTo;
	}
	public void setDateTo(String dateTo) {
		this.dateTo = dateTo;
	}
	public String getAmountFilterMode() {
		return amountFilterMode;
	}
	public void setAmountFilterMode(String amountFilterMode) {
		this.amountFilterMode = amountFilterMode;
	}
	public String getAmountFrom() {
		return amountFrom;
	}
	public void setAmountFrom(String amountFrom) {
		this.amountFrom = amountFrom;
	}
	public String getAmountTo() {
		return amountTo;
	}
	public void setAmountTo(String amountTo) {
		this.amountTo = amountTo;
	}
	public String getCategorySearch() {
		return categorySearch;
	}
	public void setCategorySearch(String categorySearch) {
		this.categorySearch = categorySearch;
	}
	public String getTypeFilter() {
		return typeFilter;
	}
	public void setTypeFilter(String typeFilter) {
		this.typeFilter = typeFilter;
	}
	public String getPayeeSearch() {
		return payeeSearch;
	}
	public void setPayeeSearch(String payeeSearch) {
		this.payeeSearch = payeeSearch;
	}
	public String getPaymentMethodSearch() {
		return paymentMethodSearch;
	}
	public void setPaymentMethodSearch(String paymentMethodSearch) {
		this.paymentMethodSearch = paymentMethodSearch;
	}
	public String getDescriptionSearch() {
		return descriptionSearch;
	}
	public void setDescriptionSearch(String descriptionSearch) {
		this.descriptionSearch = descriptionSearch;
	}
	public String getOrderField() {
		return orderField;
	}
	public void setOrderField(String orderField) {
		this.orderField = orderField;
	}
	public String getOrderDirection() {
		return orderDirection;
	}
	public void setOrderDirection(String orderDirection) {
		this.orderDirection = orderDirection;
	}
	
	
	
}