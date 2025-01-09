package service;

import java.util.ArrayList;

import dao.PayeesDAO;
import dao.PaymentMethodsDAO;

import domain.Payees;
import domain.PaymentMethods;


public class DataInputService {
	
	// get list of payees, filtered by account id and type
	public ArrayList<Payees> selectPayees(int type, int accountId) {
		PayeesDAO dao = new PayeesDAO();
		
		ArrayList<Payees> payees = dao.selectPayees(type, accountId);
		
		return payees;
		
	}
	
	// get list of paymentmethods, filtered by account id and type
	public ArrayList<PaymentMethods> selectPaymentMethods(int type, int accountId) {
		PaymentMethodsDAO dao = new PaymentMethodsDAO();
		
		ArrayList<PaymentMethods> paymentMethods = dao.selectPaymentMethods(type, accountId);
		
		return paymentMethods;
		
	}
}
