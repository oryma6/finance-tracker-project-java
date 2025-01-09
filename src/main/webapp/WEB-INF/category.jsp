<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 
<!DOCTYPE html>

<html>
	<head>
		<%@ include file="/jsp/head-meta.jsp" %>
		<meta charset="UTF-8">
		<title>Category Settings | cashdash.</title>
	</head>
	
	<body>
		
		<%@ include file="/jsp/header-nav.jsp" %>
		<h1>データ設定</h1>
		
		<div class="container mt-4">
		  <p>このページでは、取引データの設定を行えます。</p>
		
		  <div class="card mb-3">
		    <div class="card-header bg-success text-white">
		      <strong>カテゴリの<a href="#new-category" style="color: inherit; text-decoration: none;">作成</a>・<a href="#category-header" style="color: inherit; text-decoration: none;">設定</a></strong>
		    </div>
		    <div class="card-body">
		      <p>取引データの管理に必要なカテゴリを作成、編集、または削除することができます。</p>
		    </div>
		  </div>
		
		  <div class="card mb-3">
		    <div class="card-header bg-success text-white">
		      <strong>支払先・収入源の作成・設定</strong>
		    </div>
		    <div class="card-body">
		      <p>支払先・収入源を作成、編集、削除し、カテゴリに割り当てることができます。</p>
		    </div>
		  </div>
		
		  <div class="card">
		    <div class="card-header bg-success text-white">
		      <strong>支払方法・受取方法の作成・設定</strong>
		    </div>
		    <div class="card-body">
		      <p>支払方法や受取方法を設定し、取引データの詳細を管理できます。</p>
		    </div>
		  </div>
		</div>
		<br><br>
		
		<div class="border"><br>
			<div class="container-fluid">
				<div class="row">
					<div id="new-category" class="col">
						<h2>カテゴリ作成</h2>
						<div class="container d-flex justify-content-center mt-4">
							<form action="/CashDash/categoryInsert" method="post">
								<table class="table table-bordered" style="max-width: 100%; text-align: center;">
									<tr>
										<th>カテゴリ名：</th>
										<td><input type="text" name="newCatName" placeholder="例：食費、交際費など" required></td>
									</tr>
									<tr>
										<th>色：</th>
										<td><input type="color" name="newCatColor" value="#ff0000" id="color-picker"></td>
									</tr>
									<tr>
										<th>種別（支出・収入）：</th>
										<td>
											<select required name="newCatType">
												<option disabled selected value="">--選択--</option>
												<option value="111">支出</option>
												<option value="222">収入</option>
											</select>
										</td>
									</tr>
								</table>
								<div class="container d-flex justify-content-center mt-4">
									<input type="submit" value="保存">
								</div>
							</form>
						</div>	
						<c:if test="${not empty categoryInsertMsg}">
							<p style="color:red;">${categoryInsertMsg}</p>
						</c:if>
					</div>
					
					
					<div id="new-payee" class="col">
						<h2>「支払先・収入源」作成</h2>
						<div class="container d-flex justify-content-center mt-4">
							<form action="/CashDash/payeeInsert" method="post">
								<table class="table table-bordered" style="max-width: 100%; text-align: center;">
									<tr>
										<th>支払先・収入源名：</th>
										<td><input type="text" name="newPayeeName" placeholder="例：自動販売機など" required></td>
									</tr>				
									<tr>
										<th>カテゴリ：</th>
										<td>
											<select required name="categoryId">
												<option selected disabled value="">--選択--</option>
												<c:forEach var="cat" items="${categories}">
													<option value="${cat.categoryId}">${cat.categoryName}</option>
												</c:forEach>
											</select>
										</td>
									</tr>
								</table>
								<div class="container d-flex justify-content-center mt-4">
									<input type="submit" value="保存"><br>
								</div>
								<small style="color:red">※「カテゴリ」を作成してから「支払先・収入源」を「カテゴリ」に割り当てる必要があります</small>
							</form>
							<c:if test="${not empty payeeInsertMsg}">
								<p style="color:red;">${payeeInsertMsg}</p>
							</c:if>
						</div>
					</div>
					
					<div id="new-payment" class="col">
						<!-- TODO: make form to insert payment methods -->
						<h2>「支払方法・受取方法」作成</h2>
						<div class="container d-flex justify-content-center mt-4">
							<form action="/CashDash/paymentInsert" method="post">
								<table class="table table-bordered" style="max-width: 100%; text-align: center;">
									<tr>
										<th>支払方法・受取方法名：</th>
										<td><input type="text" name="newPayment" placeholder="例：SMBC口座など" required></td>
									</tr>				
									<tr>
										<th>種別（支出・収入）：</th>
										<td>
											<select required name="newPaymentType">
												<option disabled selected value="">--選択--</option>
												<option value="111">支出</option>
												<option value="222">収入</option>
											</select>
										</td>
									</tr>
								</table>
								<div class="container d-flex justify-content-center mt-4">
									<input type="submit" value="保存">
								</div>
							</form>							
						</div>
						<c:if test="${not empty paymentInsertMsg}">
							<p style="color:red;">${paymentInsertMsg}</p>
						</c:if>
					</div>
				</div>
			</div>
			<br>
		</div>
		
		<%@ include file="/jsp/category-table.jsp" %>
		<%@ include file="/jsp/payment-methods-table.jsp" %>
		<%@ include file="/jsp/payee-table.jsp" %>
		
		<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js" integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous"></script>
		<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js" integrity="sha384-0pUGZvbkm6XF6gxjEnlmuGrJXVbNuzT9qBBavbLwCsOGabYfZo0T0to5eqruptLy" crossorigin="anonymous"></script>
	</body>
	<%@ include file="/jsp/footer.jsp" %>
</html>