package controller;

import java.io.IOException;

import dao.PaymentMethodsDAO;
import dto.AccountsDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/paymentInsert")
public class DataInputPaymentController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		int accountId = ((AccountsDTO) session.getAttribute("user")).getAccountId();
		
		String paymentMethod = request.getParameter("newPayment");
		int typeId = Integer.parseInt(request.getParameter("newPaymentType"));
		
		int result = 0;
		if (paymentMethod != null && !paymentMethod.isEmpty()) {
			result = new PaymentMethodsDAO().insert(accountId, paymentMethod, typeId);
		}
		
		if (result == 1) {
			request.setAttribute("paymentInsertMsg", "入力完了。");
			request.getRequestDispatcher("/WEB-INF/category.jsp").forward(request, response);
		} else {
			request.setAttribute("paymentInsertMsg", "エラーが発生しました。データ保存失敗。");
			request.getRequestDispatcher("/WEB-INF/category.jsp").forward(request, response);
		}
	}
}
