package service;

import java.util.ArrayList;

public class CategoryService {
	
	// checking if the user input has no duplicates categories
	public boolean checkDuplicate(String newCategoryName, ArrayList<String> categoryNames) {
		for (String category : categoryNames) {
	        if (category.equalsIgnoreCase(newCategoryName)) { // Case-insensitive comparison
	            return true; // Duplicate found
	        }
	    }
		return false;
	}
}
