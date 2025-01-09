<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<div class="row">
    <canvas id="myChart"></canvas>
</div><br>

<!-- Build JavaScript arrays -->
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

<script>
  const ctx = document.getElementById('myChart');

  new Chart(ctx, {
    type: 'pie',
    data: {
      labels: [
    	  // adding list of category name, type and percentage to the totalAmount
    	  <c:forEach var="cat" items="${categorySummary}" varStatus="loop">
	          '${cat.category} | ${cat.type} | ' + (${cat.amount}/${totalAmount}*100).toFixed(2) + "%"
	          <c:if test="${!loop.last}">,</c:if>
	      </c:forEach>
      ],
      datasets: [{
        
        data: [
        	<c:forEach var="cat" items="${categorySummary}" varStatus="loop">
            	'${cat.amount}'
            	<c:if test="${!loop.last}">,</c:if>
        	</c:forEach>
        ],
        	
        backgroundColor: [
        	<c:forEach var="cat" items="${categorySummary}" varStatus="loop">
	            '${cat.categoryColor}'<c:if test="${!loop.last}">,</c:if>
	        </c:forEach>
        ],
        
        borderWidth: 1
      }]
    },
    options: {
      scales: {
        y: {
          beginAtZero: true
        }
      }
    }
  });
</script>



