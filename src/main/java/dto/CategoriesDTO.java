package dto;

import java.util.ArrayList;

public class CategoriesDTO {
	private String categoryName;
	private int categoryId;
	private String categoryColor;
	private ArrayList<String> payees;
	private ArrayList<Integer> payeeIds;
	
	private String type;
	private int typeId;
	
	// default categories made for user when registering an account
	public final static String[][] defaultExpenseCategories = {
		    {"Groceries・食費",            "#F44336"}, // Red
		    {"Dining Out・外食",           "#E91E63"}, // Pink
		    {"Transportation・交通費",     "#9C27B0"}, // Purple
		    {"Healthcare・医療費",         "#673AB7"}, // Deep Purple
		    {"Housing・住居費",            "#3F51B5"}, // Indigo
		    {"Utilities・光熱費",          "#2196F3"}, // Blue
		    {"Clothing・衣料品",           "#03A9F4"}, // Light Blue
		    {"Entertainment・娯楽費",     "#00BCD4"}, // Cyan
		    {"Health Ins.・健康保険",      "#F06292"}, // Pink
		    {"Employment Ins.・雇用保険", "#87CEEB"}, // Sky Blue 
		    {"Pension・年金",              "#EF5350"}, // Red
		    {"Debt Repayment・借金返済",   "#FF9800"}, // Orange
		    {"Savings・貯蓄",              "#FF5722"}, // Deep Orange
		    {"Supplies・日用品",           "#FBC02D"}, // Bright Amber
		    {"Education・教育費",          "#FFEB3B"}, // Yellow
		    {"Maintenance・修繕費",        "#FFD600"}, // Bright Yellow/Gold
		    {"Taxes・税金",                "#FF7043"}, // Orange
		    {"Others・その他の出金",       "#FF4081"}  // Pink accent
		};
	
	public final static String[][] defaultIncomeCategories = {
		    {"Salary・給料",           "#00FF00"}, // Fresh Green (kept from your request)
		    {"Bonuses・ボーナス",      "#69F0AE"}, // Light Green accent
		    {"Freelance・フリーランス", "#64FFDA"}, // Bright Teal accent
		    {"Investment・投資",       "#1DE9B6"}, // Teal (lighten)
		    {"Government Aid・給付金", "#00BFA5"}, // Darker Teal
		    {"Side Hustle・副業収入",  "#76FF03"}, // Lime accent
		    {"Others・その他の入金",    "#B9F6CA"}  // Minty Green
		};
	
	
	public CategoriesDTO() {
		
	}
	
	public CategoriesDTO(String categoryName, String categoryColor) {
		this.categoryName = categoryName;
		this.categoryColor = categoryColor;
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
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public ArrayList<String> getPayees() {
		return payees;
	}
	public void setPayees(ArrayList<String> payees) {
		this.payees = payees;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

	public ArrayList<Integer> getPayeeIds() {
		return payeeIds;
	}

	public void setPayeeIds(ArrayList<Integer> payeeIds) {
		this.payeeIds = payeeIds;
	}
	
	
}
