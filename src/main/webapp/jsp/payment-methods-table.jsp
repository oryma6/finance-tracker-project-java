<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<div class="row">
	<div class="col">
		<h2>「支払方法・受取方法」一覧</h2>
		<div class="container d-flex justify-content-center mt-4">
			<table class="table table-bordered table-responsive" style="max-width: 70%; text-align: center;">
				<thead>
					<tr id="payment-header">
						<th><input type="checkbox" id="selectAllCheckbox"/></th>
						<th><strong>支払方法・受取方法</strong></th>
						<th><strong>種別</strong></th>
						<th>編集</th>
					</tr>
				</thead>
				
				<tbody class="table-group-divider">
					<c:forEach var="payment" items="${paymentMethods}"> 
						<c:choose>
							<c:when test="${payment.paymentMethodId == editPaymentId}">
								<form action="updatePaymentMethod" method="post">
									<tr id="payment-${payment.paymentMethodId}">
										<!-- 1st column: empty -->
										<td><input type="hidden" name="updatePaymentId" value="${payment.paymentMethodId}"></td>
										<!-- 2nd column: name text input -->
										<td><input type="text" name="updatePaymentName" placeholder="${category.categoryName}"></td>
										<!-- 3rd column: select type -->
										<td>
											<select name="updateType">
												<option disabled selected value="">--選択--</option>
												<option value="111">支出</option>
												<option value="222">収入</option>
											</select>
										</td>
										<!-- 4th column: buttons -->
										<td><input type="submit" value="保存"><button type="reset" onclick="location.href='/CashDash/category'">キャンセル</button></td>
									</tr>
								</form>
							</c:when>
							<c:otherwise>
								<tr id="payment-${payment.paymentMethodId}">
									<td><input type="checkbox" class="rowCheckbox" value="${payment.paymentMethodId}"/></td>
									<td>${payment.paymentMethodName}</td>
									<td>${payment.typeName}</td>
									<td>
			                            <a href="/CashDash/updatePayment?paymentId=${payment.paymentMethodId}#payment-${payment.paymentMethodId}">編集</a>
			                        </td>
								</tr>
							</c:otherwise>
						</c:choose>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</div>