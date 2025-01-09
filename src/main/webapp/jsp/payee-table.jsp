<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<div class="row">
	<div class="col">
		<h2>「支払先・収入源」一覧</h2>
		<div class="container d-flex justify-content-center mt-4">
			<table class="table table-bordered table-responsive" style="max-width: 70%; text-align: center;">
				<thead>
					<tr id="payee-header">
						<th><input type="checkbox" id="selectAllCheckbox"/></th>
						<th><strong>支払先・収入源</strong></th>
						<th><strong>カテゴリ</strong></th>
						<th>編集</th>
					</tr>
				</thead>
				
				<tbody class="table-group-divider">
					<c:forEach var="payee" items="${payeeList}"> 
						<c:choose>
							<c:when test="${payee.payeeId == editPayeeId}">
								<form action="updatePayee" method="post">
									<tr id="payment-${payee.payeeId}">
										<!-- 1st column: empty -->
										<td><input type="hidden" name="updatePayeeId" value="${payee.payeeId}"></td>
										<!-- 2nd column: name text input -->
										<td><input type="text" name="updatePayeeName" placeholder="${payee.payeeName}"></td>
										<!-- 3rd column: select type -->
										<td>
											<!-- TODO: fix later -->
											<select name="updatePayeeCategoryId">
												<c:forEach var="category" items="${categories}">
													<option value="${category.categoryId}">${category.categoryName}</option> 
												</c:forEach>
											</select>
										</td>
										<!-- 4th column: buttons -->
										<td><input type="submit" value="保存"><button type="reset" onclick="location.href='/CashDash/category'">キャンセル</button></td>
									</tr>
								</form>
							</c:when>
							<c:otherwise>
								<tr id="payee-${payee.payeeId}">
									<td><input type="checkbox" class="rowCheckbox" value="${payee.payeeId}"/></td>
									<td>${payee.payeeName}</td>
									<td>${payee.categoryName}</td>
									<td>
			                            <a href="/CashDash/updatePayment?payeeId=${payee.payeeId}#payee-${payee.payeeId}">編集</a>
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