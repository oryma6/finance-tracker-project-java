<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 
<div class="border">
	<h2>テーブル</h2>
	<div class="table-responsive">
		<table class="table table-bordered table-hover align-middle text-center">
			<thead>
				<tr>
					<th><input type="checkbox" id="selectAllCheckbox"/></th>
					<td><strong>日付</strong></td>
					<td><strong>金額</strong></td>
					<td><strong>カテゴリー</strong></td>
					<td><strong>種別</strong></td>
					<td><strong>支払先・収入源</strong></td>
					<td><strong>支払方法・受取方法</strong></td>
					<td><strong>メモ</strong></td>
					<th></th>
				</tr>
			</thead>
			
			<tbody>
				<c:choose>
					<c:when test="${empty transactions}">
						<tr><td colspan="8" style="color:red;">データ見つかりませんでした。</td></tr>
					</c:when>
					<c:otherwise>
						<c:forEach var="transaction" items="${transactions}">
							<c:choose>
								<c:when test="${transaction.transactionId == editTransactionId}">
									<form action="/CashDash/updateTransaction" method="post">
										<tr data-transaction-id="${transaction.transactionId}">
											<td id="t-${transaction.transactionId}"><input type="hidden" value="${transaction.transactionId}" name="updateTransactionId"></td> <!-- leaving empty so it aligns with checkboxes -->
											<td><input type="date" value="${transaction.date}" name="updateDate"></td>
											<td><input type="number" placeholder="${transaction.amount}円" name="updateAmount"></td>
											<td style="color:${transaction.categoryColor}" id="${transaction.categoryId}" class="displayCell"> <!--  leaving this as is since category cannot be edited here -->
												<strong>${transaction.category}</strong>
											</td>
											<td class="displayCell">${transaction.type}</td> <!-- this also cannot be edited here -->
											<td>
												<select class="form-select form-select-sm" name="updatePayeeId">
													<c:forEach var="expensePayee" items="${expensePayees}">
														<option value="${expensePayee.payeeId}" 
															<c:if test="${expensePayee.payeeName == transaction.payeeName}">selected</c:if>>
															${expensePayee.payeeName}（支出）
														</option>
													</c:forEach>
													<c:forEach var="incomePayee" items="${incomePayees}"> 
														<option value="${incomePayee.payeeId}"
															<c:if test="${incomePayee.payeeName == transaction.payeeName}">selected</c:if>>
															${incomePayee.payeeName}（収入）
														</option>
													</c:forEach>
												</select>
											</td>
											<td>
												<select class="form-select form-select-sm" name="updatePaymentMethodId">
													<c:forEach var="paymentMethod" items="${paymentMethods}">
														<option value="${paymentMethod.paymentMethodId}"
															<c:if test="${paymentMethod.paymentMethodName == transaction.paymentMethod}">selected</c:if>>
															${paymentMethod.paymentMethodName}（${paymentMethod.typeName}）
														</option>
													</c:forEach>
												</select>
											</td>
											<td><textarea name="updateDescription" rows="1" placeholder="${transaction.description}" maxlength="200"></textarea></td>
											<td><input type="submit" value="保存"><button type="reset" onclick="location.href='/CashDash/home'">キャンセル</button></td>
										</tr>
									</form>
								</c:when>
								<c:otherwise>
									<tr data-transaction-id="${transaction.transactionId}">
										<td id="t-${transaction.transactionId}">
				                            <input type="checkbox" class="rowCheckbox" value="${transaction.transactionId}"/>
				                        </td>
				                        
										<td class="displayCell">${transaction.date}</td>
										
										<fmt:formatNumber value="${transaction.amount}" type="currency" pattern="#,###.##" var="fmtAmount"/>
										<td class="displayCell">${fmtAmount}円</td>
										<td style="color:${transaction.categoryColor}" id="${transaction.categoryId}" class="displayCell">
											<strong>${transaction.category}</strong>
										</td>
										<td class="displayCell">${transaction.type}</td>
										<td id="${transaction.payeeId}" class="displayCell">${transaction.payeeName}</td>
										<td id="${transaction.paymentMethodId}" class="displayCell">${transaction.paymentMethod}</td>
										<td class="displayCell">${transaction.description}</td>
										
										<td>
				                            <a href="/CashDash/updateTransaction?transactionId=${transaction.transactionId}#t-${transaction.transactionId}">編集</a>
				                        </td>
									</tr>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</c:otherwise> 
				</c:choose>
			</tbody>
		</table>
	</div>
	<button type="button" id="deleteSelectedBtn" class="btn-primary">選択した取引を削除する</button>
</div>
<script>
	document.addEventListener('DOMContentLoaded', () => {
	  
	  // 1. Handle "Select All" for bulk deletion
	  const selectAllCheckbox = document.getElementById('selectAllCheckbox');
	  const checkboxes = document.querySelectorAll('.rowCheckbox');
	
	  if (selectAllCheckbox) {
	    selectAllCheckbox.addEventListener('change', () => {
	      checkboxes.forEach(cb => {
	        cb.checked = selectAllCheckbox.checked;
	      });
	    });
	  }
	
	  // 2. Handle "Delete Selected"
	  const deleteBtn = document.getElementById('deleteSelectedBtn');
	  if (deleteBtn) {
	    deleteBtn.addEventListener('click', () => {
	      const selectedIds = [];
	      checkboxes.forEach(cb => {
	        if (cb.checked) {
	          selectedIds.push(cb.value);
	        }
	      });
	      if (selectedIds.length === 0) {
	        alert('No rows selected.');
	        return;
	      }
	      const confirmed = confirm('選択された行を削除します。よろしいですか？');
	      if (confirmed) {
	        // Submit selectedIds to server (via fetch or form).
	        // For example:
	        deleteTransactions(selectedIds);
	      }
	    });
	  }
	
	  function deleteTransactions(idsArray) {
	    // Example using a hidden form:
	    const form = document.createElement('form');
	    form.method = 'post';
	    form.action = 'deleteTransactions'; // servlet path
	
	    idsArray.forEach(id => {
	      const input = document.createElement('input');
	      input.type = 'hidden';
	      input.name = 'transactionIds'; // same param name server expects
	      input.value = id;
	      form.appendChild(input);
	    });
	    document.body.appendChild(form);
	    form.submit();
	  }
	});
</script>
