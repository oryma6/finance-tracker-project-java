<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<%@ include file="/jsp/head-meta.jsp" %>
		<title>Registration Confirmation | cashdash.</title>
		<meta charset="UTF-8">
	</head>
	
	<body>
		<nav class="navbar">
			<div class="container-fluid">
				<img src="/CashDash/img/cashdash.png" style="max-width: 150px; max-height: 90px;">
			</div>
		</nav>
		
		<h1>新規登録確認</h1>
		<div class="container d-flex justify-content-center mt-4">
			<table class="table table-bordered" style="max-width: 50%; text-align: center;">
				<tr>
					<th>ユーザネーム</th>
					<td>${username}</td>
				</tr>
				<tr>
					<th>名前</th>
					<td>${name}</td>
				</tr>
				<tr>
					<th>年齢</th>
					<td>${age}</td>
				</tr>
				<tr>
					<th>メール</th>
					<td>${email}</td>
				</tr>
			</table>
		</div>
		<div class="container d-flex justify-content-center mt-4">
			<form action="/CashDash/register" method="post">
				<input type="hidden" name="username" value="${username}">
				<input type="hidden" name="name" value="${name}">
				<input type="hidden" name="age" value="${age}">
				<input type="hidden" name="email" value="${email}">
				<input type="hidden" name="password" value="${password}">
				<input type="submit" value="確認">
			</form>
		</div>
	</body>
	<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js" integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous"></script>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js" integrity="sha384-0pUGZvbkm6XF6gxjEnlmuGrJXVbNuzT9qBBavbLwCsOGabYfZo0T0to5eqruptLy" crossorigin="anonymous"></script>
	<%@ include file="/jsp/footer.jsp" %>
</html>