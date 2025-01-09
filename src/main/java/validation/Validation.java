package validation;

import java.util.ArrayList;
import java.util.List;

public class Validation {
	private List<String> errorMsgList;
	
	public Validation() {
		this.errorMsgList = new ArrayList<>();
	}
	
	//check if there are any error message in list
	public boolean hasErrorMsg() { 
		if (errorMsgList.isEmpty()) { // or can be if errorMsgList.size() > 0 return true 
			return false;
		} else {
			return true;
		}
	}
	
	//check if the user input is blank, if yes, add error message to list
	public void isBlank(String textName, String text) {
		if (text == null || text.isEmpty()) {
			this.errorMsgList.add(textName + " is blank.");
		}
	}
	
	//check if user input length is in between the min and max (exclusive)
	public void length(String textName, String text, int min, int max) {
		if (text == null || text.length() < min || text.length() > max) {
			this.errorMsgList.add(textName + " has to be more than " + min + " and less than " + max + " characters." );
		}
	}
	
	// overload, in case when only need to check max character
	public void length(String textName, String text, int max) {
		if (text == null || text.length() > max) {
			this.errorMsgList.add(textName + " has to be less than " + max + " characters.");
		}
	}
	
	public void isAge(String textName, int age) {
		if (age < 0) {
			this.errorMsgList.add(textName + " cannot be a negative number.");
		} else if (age > 100) {
			this.errorMsgList.add(textName + " cannot be over 100. (too old!)");
		}
	}
	
	//add to the list
	public void addErrorMsg(String msg) {
		errorMsgList.add(msg);
	}
	
	public List<String> getErrorMsgList(){
		return errorMsgList;
	}
}
