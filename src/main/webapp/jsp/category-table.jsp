<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<div class="row">
	<div class="col">
		<h2>カテゴリ一覧</h2>
		<div class="container d-flex justify-content-center mt-4">
			<table class="table table-bordered table-responsive" style="max-width: 100%; text-align: center;">
				<thead>
					<tr id="category-header">
						<th><input type="checkbox" id="selectAllCheckbox"/></th>
						<th><strong>カテゴリ名</strong></th>
						<th><strong>種別</strong></th>
						<th><strong>支払先・収入源</strong></th>
						<th><strong>色</strong></th>
						<th>編集</th>
					</tr>
				</thead>
				
				<tbody class="table-group-divider">
					<c:forEach var="category" items="${categories}"> 
						<c:choose>
							<c:when test="${category.categoryId == editCategoryId}">
								<form action="updateCategory" method="post">
									<tr id="category-${category.categoryId}">
										<!-- 1st column: empty -->
										<td><input type="hidden" name="updateCategoryId" value="${category.categoryId}"></td>
										<!-- 2nd column: name text input -->
										<td><input type="text" name="updateCategoryName" placeholder="${category.categoryName}"></td>
										<!-- 3rd column: select type -->
										<td>
											<select name="updateType">
												<option disabled selected value="">--選択--</option>
												<option value="111">支出</option>
												<option value="222">収入</option>
											</select>
										</td>
										<!-- 4th column: list of payees , (edit payee will be on other form) -->
										<td>
											<c:choose>
												<c:when test="${empty category.payees}">
													<span style="color:red;">未登録</span>
												</c:when>
												<c:otherwise>
													<p>
														<c:forEach var="payee" items="${category.payees}">
															${payee}, 
														</c:forEach>
														 ...
													</p>
												</c:otherwise>
											</c:choose>
										</td>
										<!-- 5th column: color -->
										<td><input type="color" name="updateColor" value="${category.categoryColor}"></td>
										<!-- 6th column: buttons -->
										<td><input type="submit" value="保存"><button type="reset" onclick="location.href='/CashDash/category'">キャンセル</button></td>
									</tr>
								</form>
							</c:when>
							<c:otherwise>
								<tr id="category-${category.categoryId}">
									<td><input type="checkbox" class="rowCheckbox" value="${category.categoryId}"/></td>
									<td>${category.categoryName}</td>
									<td>${category.type}</td>
									<td>
										<c:choose>
											<c:when test="${empty category.payees}">
												<span style="color:red;">未登録</span>
											</c:when>
											<c:otherwise>
												<p>
													<c:forEach var="payee" items="${category.payees}">
														${payee}, 
													</c:forEach>
													 ...
												</p>
											</c:otherwise>
										</c:choose>
									</td>
									<!-- Display the color as a colored box or just the hex code -->
									<td>
										<div style="width:60px; height:20px; background-color:${category.categoryColor};">${category.categoryColor}</div>
									</td>
									
									<td>
			                            <a href="/CashDash/updateCategory?categoryId=${category.categoryId}#category-${category.categoryId}">編集</a>
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