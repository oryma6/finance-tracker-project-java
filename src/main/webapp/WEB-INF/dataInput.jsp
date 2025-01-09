<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
		<%@ include file="/jsp/head-meta.jsp" %>
		<meta charset="UTF-8">
		<title>Data Input | cashdash.</title>
	</head>
	
	<body>
		<%@ include file="/jsp/header-nav.jsp" %>
		
		<h1>取引入力</h1>
		<div class="container mt-4">
		  	<p>このページでは、2つの方法で取引を入力できます。</p>
		
		  	<div class="card mb-3">
		    	<div class="card-header bg-success text-white">
		      		<strong>1. CSVアップロード</strong>
		    	</div>
		    	<div class="card-body">
				    <p>すべての取引をまとめてアップロードできます。CSVを設定し、CSV列の値をデータベース内の取引列にマッピングしてください。</p>
				    <p>この方法で取引をアップロードする場合、未割り当ての<span class="text-primary">支払先・収入源</span>がある場合は、次のページでカテゴリを割り当てる必要があります。</p>
				    <p><span class="text-danger">※取引をアップロードする前に、必ず<a href="/CashDash/dataSettings" class="text-decoration-none">CSV設定</a>ページでフォーマットを作成してください。</span></p>
				  </div>
		  	</div>
		
	  		<div class="card">
	    		<div class="card-header bg-success text-white">
		      		<strong>2. マニュアル入力</strong>
		    	</div>
		    	<div class="card-body">
		      		<p>取引を手動で入力できます。カテゴリや種別は、割り当てられた<span class="text-success">支払先・収入源</span>によって決まります。</p>
		      		<p>これらは、<a href="/CashDash/category" class="text-decoration-none">データ設定ページ</a>で編集可能です。</p>
		   		</div>
	  		</div>
		</div>
		<br>
		
		<div id="csv-input" class="border">
			<h2>CSVで入力</h2>
			<div class="container d-flex justify-content-center mt-4">
				<form action=dataInput method="post" enctype="multipart/form-data">
					<label for="csvFormat">フォーマット：</label>
					<select required name="csvFormat">
						<option selected disabled value="">--選択--</option>
						<c:forEach var="format" items="${csvFormats}">
							<option value="${format.mappingFormatId}">${format.mappingFormatName}</option>
						</c:forEach>
					</select><br>
					<input required type="file" name="csvFile" accept=".csv" value="ファイル選択"><br>
					<div class="container d-flex justify-content-center mt-4">
						<input type="submit" value="入力">
					</div>
				</form>
			</div>
			<br>
				<c:if test="${not empty successMsg}">
					<span>${successMsg}</span>
				</c:if>
				<c:if test="${not empty errorMsg}">
					<span>${errorMsg}</span>
				</c:if>
			<br>
		</div>
		
		<div id="manual-input" class="container-fluid border">
			<h2>マニュアル入力</h2>
				<div class="row">
					<div id="expense-manual" class="col">
						<h2>支出</h2>
						<div class="container d-flex justify-content-center">
							<form action="expenseRowInput" method="post"> <!-- submit to expense insert controller (different from income)-->
								<table>
									<tr>
										<!-- dynamically and automatically set the date to today's date with Javascript way below -->
										<th>日付</th>
										<td>
											<input required autofocus type="date" name="transactionDate" id="expense_date">
										</td>
									</tr>
									<tr>
										<th>金額</th>
										<td><input required placeholder="例：1000" type="number" name="amount">円</td>
									</tr>
									<tr>
										<th>支払先</th>
										<td>
											<select required name="payeeId">
												<option disabled selected value="">選択（必須）</option>
												<c:forEach var="payee" items="${expensePayees}"> 
													<option value="${payee.payeeId}">${payee.payeeName}</option>
												</c:forEach>
											</select>
										</td>
									</tr>
									<tr>
										<th>支払方法</th>
										<td>
											<select required name="paymentMethodId">
												<option disabled selected value="">選択（必須）</option>
												<c:forEach var="paymentMethod" items="${expensePaymentMethods}"> 
													<option value="${paymentMethod.paymentMethodId}">${paymentMethod.paymentMethodName}</option>
												</c:forEach>
											</select>
										</td>
									</tr>
									<tr>
										<th>メモ</th>
										<td><textarea name="description" rows="5" cols="25" placeholder="任意（200文字まで。）" maxlength="200"></textarea></td>
									</tr>
								</table>
								<input type="hidden" value="支出" name="type">
								<div class="container d-flex justify-content-center">
									<button type="submit">登録</button>
								</div><br>
							</form>
						</div>
						<c:if test="${not empty expenseInputResultMsg}">
							<p style="color:red; text-align:center;">${expenseInputResultMsg}</p>
						</c:if>
				</div>
			
			
				<div id="income-manual" class="col">
					<h2>収入</h2>
					<div class="container d-flex justify-content-center">
						<form action="incomeRowInput" method="post"> <!-- submit to income insert controller (different from expenses)-->
							<table>
								<tr>
									<th>日付</th>
									<td><input required autofocus type="date" name="transactionDate" id="income_date"></td>
								</tr>
								<tr>
									<th>金額</th>
									<td><input required placeholder="例：1000" type="number" name="amount">円</td>
								</tr>
								<tr>
									<th>収入源</th>
									<td>
										<select required name="payeeId">
											<option disabled selected value="">選択（必須）</option>
											<c:forEach var="payee" items="${incomePayees}"> 
												<option value="${payee.payeeId}">${payee.payeeName}</option>
											</c:forEach>
										</select>
									</td>
								</tr>
								<tr>
									<th>受取方法</th>
									<td>
										<select required name="paymentMethodId">
											<option disabled selected value="">選択（必須）</option>
											<c:forEach var="paymentMethod" items="${incomePaymentMethods}"> 
												<option value="${paymentMethod.paymentMethodId}">${paymentMethod.paymentMethodName}</option>
											</c:forEach>
										</select>
									</td>
								</tr>
								<tr>
									<th>メモ</th>
									<td><textarea name="description" rows="5" cols="25" placeholder="任意（200文字まで。）" maxlength="200"></textarea></td>
								</tr>
							</table>
							<input type="hidden" value="収入" name="type">
							<div class="container d-flex justify-content-center">
								<button type="submit">登録</button>
							</div><br><br>
						</form>
						
						
						</div>
					<c:if test="${not empty incomeInputResultMsg}">
						<p style="color:red; text-align:center;">${incomeInputResultMsg}</p>
					</c:if>
				</div>
			</div>
		</div>
		
		<div class="container d-flex justify-content-center">
			<br><button onclick="location.href='/CashDash/deleteAllData'">全てデータ削除</button>
		</div>
	</body>
	
	<script>
        // get today's date in YYYY-MM-DD format
        const today = new Date().toISOString().split('T')[0];

        // set the value of the date input to today's date
        document.getElementById('expense_date').value = today;
        document.getElementById('income_date').value = today;
    </script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js" integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous"></script>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js" integrity="sha384-0pUGZvbkm6XF6gxjEnlmuGrJXVbNuzT9qBBavbLwCsOGabYfZo0T0to5eqruptLy" crossorigin="anonymous"></script>
	<%@ include file="/jsp/footer.jsp" %>
</html>