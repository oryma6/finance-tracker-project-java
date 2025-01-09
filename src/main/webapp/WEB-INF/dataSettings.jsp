<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ page contentType="text/html; charset=UTF-8" %>

<!DOCTYPE html>
<html>
	<head>
		<%@ include file="/jsp/head-meta.jsp" %>
		<meta charset="UTF-8">
		<title>CSV Settings | cashdash.</title>
	</head>
	
	<%@ include file="/jsp/header-nav.jsp" %>
	
	<body>
		<h1>CSV設定</h1>
		<div class="container mt-4">
		  <p>このページでは、銀行口座やクレジットカードからダウンロードしたCSVファイルを使用して、取引データを正しく読み取るためのフォーマットを設定できます。</p>
		
		  <div class="card">
		    <div class="card-header bg-success text-white">
		      <strong>CSVフォーマットの作成</strong>
		    </div>
		    <div class="card-body">
		      <p>CSVファイルをアップロードして、取引のどの列がどのデータ項目（例: 日付、金額、支払先など）に対応するかをプログラムに教えるフォーマットを作成します。</p>
		      <p>一度フォーマットを作成すると、同じ列名と値を持つCSVを使用して、<a href="/CashDash/dataInput" class="text-decoration-none">取引入力</a>ページで簡単に取引データをアップロードできるようになります。</p>
		      <p>これにより、取引データをまとめてアップすることが可能です。</p>
		    </div>
		  </div><br>
		  <div class="alert alert-danger" role="alert">
		    ※フォーマットを作成する前に、必ず<a href="/CashDash/category">「支払方法・受取方法」を作成</a>してください。各フォーマットは1つの「支払方法・受取方法」に割り当てられます。
		  </div>
		</div>
		<br>
		
		<div class="border">
			<h2>CSVフォーマット作成</h2>
			<div class="text-center">
				<p>ファイルを選択し、「マッピング開始」ボタンを押してください。</p>
			</div>
			<div class="container d-flex justify-content-center">
				<form action="/CashDash/csvMapping" method="post" enctype="multipart/form-data"> <!-- enctype="multipart/form-data" attribute. This attribute is crucial for handling file uploads. -->
					<input required type="file" name="newCSV" id="newCSV" accept=".csv">
					<div class="container d-flex justify-content-center">
						<button type="submit">マッピング開始</button>
					</div><br><br>
				</form>
			</div>
			<c:if test="${not empty csvErrors}">
				<c:if test="${not empty mappingFormatMsg}">
					<div class="container d-flex justify-content-center">
						<span style="color:red;">${mappingFormatMsg}</span><br>
						<span style="color:red;">${csvErrors[0]}</span>
					</div>
				</c:if>
			</c:if>
		</div>
		
		
		
		<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js" integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous"></script>
		<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js" integrity="sha384-0pUGZvbkm6XF6gxjEnlmuGrJXVbNuzT9qBBavbLwCsOGabYfZo0T0to5eqruptLy" crossorigin="anonymous"></script>
		
	</body>
	<%@ include file="/jsp/footer.jsp" %>
</html>