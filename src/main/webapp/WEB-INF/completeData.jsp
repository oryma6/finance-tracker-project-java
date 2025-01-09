<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
	<head>
		<%@ include file="/jsp/head-meta.jsp" %>
		<meta charset="UTF-8">
		<title>Complete Transactions | cashdash.</title>
	</head>
	
	<body>
		<h1 class="text-center">未登録「支払先・収入源」の処理</h1>
		
		<!-- If there's any error or info message passed from the servlet -->
		<c:if test="${not empty errorMsg}">
		    <p class="error">${errorMsg}</p>
		</c:if>
		<c:if test="${not empty infoMsg}">
		    <p class="info">${infoMsg}</p>
		</c:if>
		
		<!-- Explanation for the user -->
		<div class="container d-flex justify-content-center mt-4">
			<p>
			<br>
			未登録の「支払先・収入源」が検出されました。<br>
			それぞれの「支払先・収入源」に対して「カテゴリ」を指定してください。<br>
			「カテゴリ」により、取引種別（支出や収入）が決まります。<br>
			</p>
		</div>
		
		<c:if test="${not empty unregisteredTransactions}">
			<div class="container-fluid">
				<form action="completeData" method="post">
				    <table class="table table-striped">
				        <thead>
				            <tr>
				            	<th>No.</th>				                
						        <th>日付</th>
						        <th>金額</th>
						        <th>メモ</th>
						        <th>支払方法・受取方法</th>
						        <th>支払先（未登録）</th>
						        <th>カテゴリー</th>
				            </tr>
				        </thead>
				        <tbody>
				        <c:forEach var="t" items="${unregisteredTransactions}" varStatus="loop">
				            <!-- We'll reference each transaction by an index or a unique ID, so we can handle them upon submission -->
				            <tr>
				                <!-- Basic transaction info -->
				                <td>${loop.index}</td>
				                <td>${t.date}</td>
				                <td>${t.amount}</td>
				                <td>${t.description}</td>
				                <td>${t.paymentMethod}</td>
				                <td style="background-color:pink;">${t.payeeName}</td>
				                <td>
					                <select required name="category_${loop.index}">
					                	<option selected disabled value="">--選択--</option>
					                	<c:forEach var="cat" items="${categories}">
					                		<option value="${cat.categoryId}">${cat.categoryName}（${cat.type}）</option>
					                	</c:forEach>
					                </select>
					                <!-- all the other input -->
					                <input type="hidden" name="transIndex" value="${loop.index}"> 
					                <input type="hidden" name="transDate_${loop.index}" value="${t.date}" />
					                <input type="hidden" name="transAmount_${loop.index}" value="${t.amount}" />
					                <input type="hidden" name="transDesc_${loop.index}" value="${t.description}" />
					                
					                <!-- Unregistered payeeName -->
					                <input type="hidden" name="transPayeeName_${loop.index}" value="${t.payeeName}" />
					                
					                <!-- Unregistered paymentMethod -->
					                <input type="hidden" name="transPaymentMethod_${loop.index}" value="${t.paymentMethodId}" />
					            </td>
				            </tr>
				        </c:forEach>
				        </tbody>
				    </table>
				    
				    <div class="container d-flex justify-content-center mt-4">
				        <!-- The user can confirm all rows at once -->
				     	<button type="submit">登録を完了する</button>
				    </div>
				    <br>
				    <br>
				</form>
			</div>
		</c:if>
		
		<c:if test="${empty unregisteredTransactions}">
		  <p class="text-center">エラーが発生しました。<a href="/CashDash/dataInput">入力画面へ戻る。</a></p>
		</c:if>
		
		<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js" integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous"></script>
		<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js" integrity="sha384-0pUGZvbkm6XF6gxjEnlmuGrJXVbNuzT9qBBavbLwCsOGabYfZo0T0to5eqruptLy" crossorigin="anonymous"></script>
	</body>
	
	<footer class="bg-dark text-white py-4">
	    <div class="container text-center">
	        <p class="mb-1">&copy; 2025 高野竜舞</p>
    	</div>
	</footer>
</html>