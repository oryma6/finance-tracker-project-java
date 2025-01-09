package controller;

import java.io.IOException;

import dao.PayeesDAO;
import dto.AccountsDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/payeeInsert")
public class DataInputPayeeController extends HttpServlet{
	private static final long serialVersionUID = 1L;
		
		protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			HttpSession session = request.getSession();
			int accountId = ((AccountsDTO) session.getAttribute("user")).getAccountId();
			
			String payee = request.getParameter("newPayeeName");
			int categoryId = Integer.parseInt(request.getParameter("categoryId"));
			
			int result = 0;
			if (payee != null && !payee.isEmpty()) {
				result = new PayeesDAO().insert(accountId, categoryId, payee);
			}
			
			if (result == 1) {
				request.setAttribute("payeeInsertMsg", "入力完了。");
//				request.getRequestDispatcher("/WEB-INF/category.jsp").forward(request, response);
				response.sendRedirect("/CashDash/category");
			} else {
				request.setAttribute("payeeInsertMsg", "エラーが発生しました。データ保存失敗。");
				request.getRequestDispatcher("/WEB-INF/category.jsp").forward(request, response);
			}
		}
}
