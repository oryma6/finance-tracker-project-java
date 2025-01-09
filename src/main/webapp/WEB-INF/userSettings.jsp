<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
	<head>
		<%@ include file="/jsp/head-meta.jsp" %>
		<meta charset="UTF-8">
		<title>Account Settings | cashdash.</title>
	</head>
	<body>
		<%@ include file="/jsp/header-nav.jsp" %>
		<h1>アカウント設定</h1>
		
		<h2>アカウント編集</h2>
		<div class="container d-flex justify-content-center mt-4">
			<table class="table table-bordered" style="max-width: 50%; text-align: center;">
			    <tr>
			        <td>ユーザーネーム</td>
			        <c:choose>
			        	<c:when test="${empty updateUsername}">
	        				<td>${user.username}</td>
			        		<td>
			        			<form action="/CashDash/updateAccount" method="get">
			        				<input type="hidden" value="updatingUsername" name="updatingUsername">
			     					<input type="submit" value="更新">
			        			</form>	
			        		</td>
			        	</c:when>
			        	<c:otherwise>
			        		<td>
				        		<form action="/CashDash/updateAccount" method="post">
				        			<input type="text" placeholder="${updateUsername}" name="newUsername">
				        			<button type="submit">更新</button>
				    			</form>
			    			</td>
			        	</c:otherwise>
			        </c:choose>
			    </tr>
			    
			    <tr>
			        <td>名前</td>
			        <c:choose>
			        	<c:when test="${empty updateName}">
			        		<td>${user.name}</td>
			        		<td>
			        			<form action="/CashDash/updateAccount" method="get">
			        				<input type="hidden" value="updatingName" name="updatingName">
			     					<input type="submit" value="更新">
			        			</form>	
			        		</td>
			        	</c:when>
			        	<c:otherwise>
			        		<td>
				        		<form action="/CashDash/updateAccount" method="post">
				        			<input type="text" placeholder="${updateName}" name="newName">
				        			<button type="submit">更新</button>
				    			</form>
			    			</td>
			        	</c:otherwise>
			        </c:choose>
			    </tr>
			    <tr>
			        <th>メール</th>
			        <c:choose>
			        	<c:when test="${empty updateEmail}">
	        				<td>${user.email}</td>
			        		<td>
			        			<form action="/CashDash/updateAccount" method="get">
			        				<input type="hidden" value="updatingEmail" name="updatingEmail">
			     					<input type="submit" value="更新">
			        			</form>	
			        		</td>
			        	</c:when>
			        	<c:otherwise>
			        		<td>
				        		<form action="/CashDash/updateAccount" method="post">
				        			<input type="text" placeholder="${updateEmail}" name="newEmail">
				        			<button type="submit">更新</button>
				    			</form>
			    			</td>
			        	</c:otherwise>
			        </c:choose>			    
			    </tr>
			    <tr>
			        <th>年齢</th>
			        <c:choose>
			        	<c:when test="${empty updateAge}">
	        				<td>${user.age}</td>
			        		<td>
			        			<form action="/CashDash/updateAccount" method="get">
			        				<input type="hidden" value="updatingAge" name="updatingAge">
			     					<input type="submit" value="更新">
			        			</form>	
			        		</td>
			        	</c:when>
			        	<c:otherwise>
			        		<td>
				        		<form action="/CashDash/updateAccount" method="post">
				        			<input type="text" placeholder="${updateAge}" name="newAge">
				        			<button type="submit">更新</button>
				    			</form>
			    			</td>
			        	</c:otherwise>
			        </c:choose>			      
			    </tr>
			    <tr>
			        <th>パスワード</th>
			        <c:choose>
			        	<c:when test="${empty updatePassword}">
	        				<td>********</td>
			        		<td>
			        			<form action="/CashDash/updateAccount" method="get">
			        				<input type="hidden" value="updatingPassword" name="updatingPassword">
			     					<input type="submit" value="更新">
			        			</form>	
			        		</td>
			        	</c:when>
			        	<c:otherwise>
			        		<td>
				        		<form action="/CashDash/updateAccount" method="post">
				        			<input type="password" placeholder="${updatePassword}" name="newPassword">
				        			<button type="submit">更新</button>
				    			</form>
			    			</td>
			        	</c:otherwise>
			        </c:choose>
			    </tr>
			</table>
		</div>
		
		<c:if test="${not empty errorUpdate}">
			<br><p class="errorUpdateMsg">${errorUpdate}</p><br>
		</c:if>
		<c:if test="${not empty errorUpdateMsg}">
			<br><p class="errorUpdateMsg">${errorUpdateMsg}</p><br>
		</c:if>
		<c:if test="${not empty updateSuccessMsg}">
			<br><p class="errorUpdateMsg">${updateSuccessMsg}</p><br>
		</c:if>
		
		
		<div class="container d-flex justify-content-center mt-4">
			<button onclick="confirmDelete()" style="color:red;"><strong>アカウント削除</strong></button>
		</div>
		
		<script>
		    function confirmDelete() { // show a confirmation dialog
		        if (confirm("本当にアカウントを削除しますか？")) {
		            location.href = '/CashDash/DeleteAccount'; // if the user clicks "OK", redirect to the 'DeleteAccountController' servlet
		        } else {		          
		            console.log("アカウント削除はキャンセルされました"); // if the user clicks "Cancel", do nothing
		        }
		    }
		</script>
		<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js" integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous"></script>
		<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js" integrity="sha384-0pUGZvbkm6XF6gxjEnlmuGrJXVbNuzT9qBBavbLwCsOGabYfZo0T0to5eqruptLy" crossorigin="anonymous"></script>
	</body>
	<%@ include file="/jsp/footer.jsp" %>
</html>