<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
	<head>
		<%@ include file="/jsp/head-meta.jsp" %>
		<title>Welcome | cashdash.</title>
		<meta charset="UTF-8">
	</head>
	
	<body>
		<nav class="navbar">
			<div class="container-fluid">
				<img src="/CashDash/img/cashdash.png" style="max-width: 150px; max-height: 90px;">
				<div class="d-flex align-items-center ms-auto">
					<a href="/CashDash/login" class="text-black me-3 text-decoration-none fs-5">ログイン</a>　
		    		<a href="/CashDash/register" class="text-black me-3 text-decoration-none fs-5">新規登録</a>
				</div>
			</div>
		</nav>
		
		<c:if test="${not empty msg}">
			<p style="color:red; text-align:center;">${msg}</p>
		</c:if>
		<c:if test="${not empty deleted_account_msg}">
			<p style="color:red; text-align:center;">${deleted_account_msg}</p>
		</c:if>
		
		<div class="container-fluid">
			<div class="row align-items-center">
				<div class="col-md-6 text-center">
					<img src="/CashDash/img/logo.png" style="max-width: 350px; max-height: 350px;">
				</div>
				<div class="col-md-6 d-flex justify-content-center align-items-center text-column">
					<span style="font-size: 1.5em">すべての取引を一か所で管理。</span>
				</div>
			</div>
		</div>
		
	</body>
	
	<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js" integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous"></script>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js" integrity="sha384-0pUGZvbkm6XF6gxjEnlmuGrJXVbNuzT9qBBavbLwCsOGabYfZo0T0to5eqruptLy" crossorigin="anonymous"></script>
	<%@ include file="/jsp/footer.jsp" %>
</html>