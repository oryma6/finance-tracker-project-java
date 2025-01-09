<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
	<head>
		<%@ include file="/jsp/head-meta.jsp" %>
		<meta charset="UTF-8">
		<title>Data Export | cashdash.</title>
	</head>
	<body>
		
		<%@ include file="/jsp/header-nav.jsp" %>
		
		<h1>データエクスポート</h1>
		
		<div class="container mt-4">
			<p>このページでは、取引をCSV形式でエクスポートできます。</p>
		
			<div class="card">
		  		<div class="card-header bg-success text-white">
			    	<strong>取引のフィルタリング</strong>
			  	</div>
				<div class="card-body">
				    <p>エクスポートしたい取引を条件で絞り込み、必要なデータだけをCSV形式でダウンロードできます。</p>
				    <p>効率的に取引データを管理するためにご活用ください。</p>
				</div>
			</div>
		</div>
		
		<c:if test="${not empty exportMsg}">
			<span style="color:green">${exportMsg}</span>
		</c:if>
		
		<div id="transaction-filter-form" class="container d-flex justify-content-center">
			<form action="export" method="post">
				<%@ include file="/jsp/filter.jsp" %>
				<!-- Submit and Reset Buttons -->
			        <div class="container d-flex justify-content-center mt-4">
			            <button type="submit">エクスポート</button>
			            <button type="reset" onclick="location.href='/CashDash/export'">リセット</button>
			        </div>
			    </fieldset>
			</form>
		</div>
		<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js" integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous"></script>
		<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js" integrity="sha384-0pUGZvbkm6XF6gxjEnlmuGrJXVbNuzT9qBBavbLwCsOGabYfZo0T0to5eqruptLy" crossorigin="anonymous"></script>
	</body>
	<%@ include file="/jsp/footer.jsp" %>
</html>