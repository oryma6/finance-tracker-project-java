<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<!DOCTYPE html>

<html>
	<head>
		<%@ include file="/jsp/head-meta.jsp" %>
		<meta charset="UTF-8">
		<title>Home | cashdash.</title>
	</head>
	
	<%@ include file="/jsp/header-nav.jsp" %>
	
	<body>
		<h1>ダッシュボード</h1>
		
		<div class="container-fluid text-center">
			<div class="row">
				<div class="col-6">
					<div class="border">
						<h2>アカウント情報</h2>
						<ul>
							<li>ユーザーネーム：${user.username}</li>
							<li>名前：${user.name}</li>
							<li>メール：${user.email}</li>
							<li>年齢：${user.age}</li>
						</ul>
					</div>
				
					<!-- <select id="data-view-mode" onchange="toggleDataView()">
						<option value="table-view">テーブル</option>
						<option value="pie-view">円グラフ</option>
						<option value="calendar-view">カレンダー</option>
					</select> -->
				
					<div id="transaction-filter-form" class="border">
						<form action="filterForm" method="get">
							<%@ include file="/jsp/filter.jsp" %>
							<!-- Submit and Reset Buttons -->
						        <div>
						            <button type="submit">フィルター適用</button>
						            <button type="reset" onclick="location.href='/CashDash/home'">リセット</button>
						        </div>
						        <br>
						        <br>
						    </fieldset>
						</form>
					</div>
				</div>
				
				<div class="col-6 text-center">
					<div class="border">
						<h2>円グラフ</h2>
						<fmt:formatNumber value="${income}" type="currency" pattern="#,###.##" var="fmtIncome"/>
						<fmt:formatNumber value="${expenses}" type="currency" pattern="#,###.##" var="fmtExpenses"/>
						<fmt:formatNumber value="${amountBalance}" type="currency" pattern="#,###.##" var="fmtAmountBalance"/>
						<div class="container-fluid">
							<div class="border">
								<div class="row">
									<div class="col"><p id="incomeTotal">収入：<span style="color:green"><strong>${fmtIncome}</strong></span>円</p></div>
									<div class="col"><p id="expensesTotal">支出：<span style="color:red"><strong>${fmtExpenses}</strong></span>円</p></div>
									<div class="col"><p id="balanceTotal">バナランス：<span style="color:${balanceColor}"><strong>${fmtAmountBalance}</strong></span>円</p></div>
								</div>
							</div>
						</div>
						<%@ include file="/jsp/home-pie-chart.jsp" %>
					</div>
				</div>
			</div>
				
				
			<div class="row">
				<%@ include file="/jsp/home-table.jsp" %>
			</div>
				
				<%-- <!-- data display -->
				<div id="table-view" style="display: none">
					<%@ include file="/jsp/home-table.jsp" %>
				</div>
				
				<div id="pie-view" style="display: none">
					<%@ include file="/jsp/home-pie-chart.jsp" %>
				</div>
				
				<div id="calendar-view" style="display: none">
				</div> --%>
			
		</div>
		<script>
			// to change view on homepage, either table view, graph view or calendar view
			// table view is the default
		    function toggleDataView() {
		        const dataViewMode = document.getElementById("data-view-mode").value;
		        const tableView = document.getElementById("table-view");
		        const pieView = document.getElementById("pie-view");
		        const calendarView = document.getElementById("calendar-view");
		
		        if (dataViewMode === "table-view") {
		        	tableView.style.display = "inline";
		        	pieView.style.display = "none";
		        	calendarView.style.display = "none";
		        } else if (dataViewMode === "pie-view"){
		        	pieView.style.display = "inline";
		        	tableView.style.display = "none";
		        	calendarView.style.display = "none";
		        } else if (dataViewMode === "calendar-view"){
		        	calendarView.style.display = "inline";
		        	pieView.style.display = "none";
		        	tableView.style.display = "none"
		        }
		    }
		</script>
		<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js" integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous"></script>
		<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js" integrity="sha384-0pUGZvbkm6XF6gxjEnlmuGrJXVbNuzT9qBBavbLwCsOGabYfZo0T0to5eqruptLy" crossorigin="anonymous"></script>
	</body>
	<%@ include file="/jsp/footer.jsp" %>
</html>