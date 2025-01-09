
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
	<head>
		<%@ include file="/jsp/head-meta.jsp" %>
	    <title>CSV Mapping | cashdash.</title>
	</head>
	
	<body>
	
		<%@ include file="/jsp/header-nav.jsp" %>
		
	    <h1>CSVファイル列のマッピング: <span style="color:red">${uploadedFileName}</span></h1>

		<c:if test="${empty headerRow}">
		  <p style="color:red;">ファイルにはヘッダーがありません。</p>
		  <p><a href="/CashDash/dataSettings">データ設定へ戻る</a></p>
		</c:if>
		
		<c:if test="${not empty headerRow}">
		  <!-- Show the CSV columns and sample row for reference -->
		  <div class="container d-flex justify-content-center mt-4">
			<table class="table table-bordered table-responsive" style="max-width: 100%; text-align: center;">
			    <thead>
			      <tr><th>インデックス</th><th>CSV列ヘッダー</th><th>サンプル行</th></tr>
			    </thead>
			    <tbody>
			      <c:forEach var="hdr" items="${headerRow}" varStatus="loop">
			        <tr>
			          <td>${loop.index}</td>
			          <td>${hdr}</td>
			          <td>
			            <c:if test="${loop.index < firstDataRow.length}">
			              ${firstDataRow[loop.index]}
			            </c:if>
			          </td>
			        </tr>
			      </c:forEach>
			    </tbody>
			  </table>
			</div>
		  
		
		
		
		
			<!-- FORM -->
			<h2>データベースの列にCSVの列割り当て</h2>
			<div class="container d-flex justify-content-center mt-4">
			  <form action="validateCSVMapping" method="post">
			    <!-- text input for the mapping format name -->
			    <div class="container d-flex justify-content-center mt-4">
			      <label>フォーマット名：</label>
			      <input type="text" name="mappingFormatName" required placeholder="例：SMBC口座など"/>
			    </div>
			    
				<!-- payment_method (every transaction in this csv will have the same payment_method) -->
			    <div class="container d-flex justify-content-center mt-4">
			    	<label>支払方法・受取方法：</label>
			    	<select name="csvCol_paymentMethod" required>
			            <option value="" selected disabled>--選択--</option>
						<c:forEach var="paymentMethod" items="${paymentMethods}">
			              <option value="${paymentMethod.paymentMethodId}">${paymentMethod.paymentMethodName}(${paymentMethod.typeName}用)</option>
			            </c:forEach>
			        </select><br>
			    </div>
			    <small class="text-center" style="color:red">*このフォーマットで取引を入力する際にはここで選択した「支払方法・受取方法」に指定されます。</small><br>
			    <br/>
			
			    <table class="table table-bordered table-responsive" style="max-width: 100%; text-align: center;">
			      <tr>
			        <th>データベース列</th>
			        <th>CSV列を選択</th>
			      </tr>
			
			      <!-- transaction_date (one to one) -->
			      <tr>
			        <td>日付</td>
			        <td>
			        	に
			          <select name="csvCol_transactionDate" required>
			            <option value="" selected disabled>--選択--</option>
			            <c:forEach var="hdr" items="${headerRow}" varStatus="loop">
			              <option value="${loop.index}">${hdr}</option>
			            </c:forEach>
			          </select>
			          	列を代入する。
			        </td>
			      </tr>
			
			      <!-- amount (one to one) -->
			      <tr>
			        <td>金額</td>
			        <td>
			        	に
			          <select name="csvCol_amount" required>
			            <option value="" selected disabled>--選択--</option>
			            <c:forEach var="hdr" items="${headerRow}" varStatus="loop">
			              <option value="${loop.index}">${hdr}</option>
			            </c:forEach>
			          </select>
			          	列を代入する。
			        </td>
			      </tr>
			
			      <!-- payee_name (one to one), if payee doesn't exist in db, add and set category -->
			      <tr>
			        <td>支払先・収入源</td>
			        <td>
			        	に
			          <select name="csvCol_payeeName" required>
			            <option value="" selected disabled>--選択--</option>
			            <c:forEach var="hdr" items="${headerRow}" varStatus="loop">
			              <option value="${loop.index}">${hdr}</option>
			            </c:forEach>
			          </select>
			          	列を代入する。
			        </td>
			      </tr>
			
			      <!-- description (multiple) 
			           We'll use checkboxes to allow user to pick multiple CSV columns that 
			           will be concatenated into a single description. -->
			      <tr>
			        <td>メモ</td>
			        <td>
			          <c:forEach var="hdr" items="${headerRow}" varStatus="loop">
			            <input type="checkbox" name="csvCol_description" value="${loop.index}" />
			            ${hdr} <br/>
			          </c:forEach>
			          <small style="color:red">＊上記選択した列を「メモ」にまとめて代入する。</small>
			        </td>
			      </tr>
			    </table>
			
			    <!-- Possibly store the file name in a hidden field, if needed -->
			    <input type="hidden" name="uploadedFileName" value="${uploadedFileName}" />
			    <c:forEach var="hdr" items="${headerRow}" varStatus="loop">
			    	<input type="hidden" name="headerRow" value="${hdr}"> 
			    </c:forEach>
			    <c:forEach var="value" items="${firstDataRow}">
			    	<input type="hidden" name="firstDataRow" value="${value}"> 
			    </c:forEach>
		
		    <br/>
		    <div class="container d-flex justify-content-center mt-4">
			    <button type="submit">フォーマット保存</button><br>
			    <button type="button" onclick="location.href='/CashDash/dataSettings'">キャンセル</button>
			</div><br>
		  </form>
		</div>
		</c:if>
		<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js" integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous"></script>
		<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js" integrity="sha384-0pUGZvbkm6XF6gxjEnlmuGrJXVbNuzT9qBBavbLwCsOGabYfZo0T0to5eqruptLy" crossorigin="anonymous"></script>
	</body>
	
	<%@ include file="/jsp/footer.jsp" %>
    
</html>