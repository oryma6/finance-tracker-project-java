<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
		<%@ include file="/jsp/head-meta.jsp" %>
		<title>Registration | cashdash.</title>
		<meta charset="UTF-8">
	</head>
	<body>
		<nav class="navbar">
			<div class="container-fluid">
				<img src="/CashDash/img/cashdash.png" style="max-width: 150px; max-height: 90px;">
			</div>
		</nav>
		
		<h1>新規登録</h1>
		<div class="container d-flex justify-content-center mt-4">
			
			<form action="/CashDash/register-confirmation" method="post">
				<table class="table table-bordered" style="max-width: 90%; text-align: center;">
					<tr>
						<th>ユーザーネーム：</th>
						<td><input type="text" name="username" autofocus ></td>
					</tr>
					<tr>
						<th>名前：</th>
						<td><input type="text" name="name"></td>
					</tr>
					<tr>
						<th>年齢：</th>
						<td><input type="number" name="age"></td>
					</tr>
					<tr>
						<th>メール：</th>
						<td><input type="email" name="email"></td>
					</tr>
					<tr>
						<th>パスワード：</th>
						<td><input type="password" name="password"></td>
					</tr>
				</table>
				<div class="container d-flex justify-content-center mt-4">
					<input type="submit" value="登録">
				</div>
			</form>
		</div>
		<br>
		
		<div class="container d-flex justify-content-center mt-4">
			<button onclick="location.href='/CashDash/welcome.jsp'">戻る</button>
		</div>
		
		<c:if test="${not empty errorMsgList}">
			<c:forEach var="msg" items="${errorMsgList}">
				<p style="color:red; text-align:center;">${msg}</p>
			</c:forEach>
		</c:if>
	
	</body>
	<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js" integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous"></script>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js" integrity="sha384-0pUGZvbkm6XF6gxjEnlmuGrJXVbNuzT9qBBavbLwCsOGabYfZo0T0to5eqruptLy" crossorigin="anonymous"></script>
	<%@ include file="/jsp/footer.jsp" %>
</html>