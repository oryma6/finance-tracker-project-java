package domain;

public class PaymentMethods {
	private String paymentMethodName;
	private int paymentMethodId;
	
	private String typeName;
	private int typeId;
	
	// for dataInput.jsp display
	public PaymentMethods(String paymentMethodName, int paymentMethodId) {
		this.paymentMethodName = paymentMethodName;
		this.paymentMethodId = paymentMethodId;
	}
	
	// for 
	public PaymentMethods(String paymentMethodName, int paymentMethodId, String typeName, int typeId) {
		this.paymentMethodName = paymentMethodName;
		this.paymentMethodId = paymentMethodId;
		this.typeName = typeName;
		this.typeId = typeId;
	}

	public String getPaymentMethodName() {
		return paymentMethodName;
	}

	public void setPaymentMethodName(String paymentMethodName) {
		this.paymentMethodName = paymentMethodName;
	}

	public int getPaymentMethodId() {
		return paymentMethodId;
	}

	public void setPaymentMethodId(int paymentMethodId) {
		this.paymentMethodId = paymentMethodId;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}
	
	
}
