<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- filter form -->
		
		    <fieldset>
		        <h2>フィルター条件</h2>
		        <br>
		        <!-- date Filter -->
		        <div>
		            <label>日付:</label>
		      
		            <input type="date" name="date_from" id="date_from" value="${filterValues.dateFrom}">
		            <label for="date_from">から</label>
		            
		            <span id="date_to_wrapper" style="display: none;">
			            <input type="date" name="date_to" id="date_to" value="${filterValues.dateTo}">
			            <label for="date_to">まで</label>
		            </span>
		            
		            <select name="date_filter_mode" id="date_filter_mode" onchange="toggleDateTo()">
		                <option value="" ${empty filterValues.dateFilterMode ? 'selected' : ''}>-</option>
		                <option value="before" ${filterValues.dateFilterMode == 'before' ? 'selected' : ''}>～以前</option>
		                <option value="after" ${filterValues.dateFilterMode == 'after' ? 'selected' : ''}>〜以降</option>
		                <option value="between" ${filterValues.dateFilterMode == 'between' ? 'selected' : ''}>範囲指定</option>
		            </select>
		            
		            <script>
					    function toggleDateTo() {
					        const dateFilterMode = document.getElementById("date_filter_mode").value;
					        const dateToWrapper = document.getElementById("date_to_wrapper");
					
					        if (dateFilterMode === "between") {
					            dateToWrapper.style.display = "inline"; // Show the 'date_to' input
					        } else {
					            dateToWrapper.style.display = "none"; // Hide the 'date_to' input
					        }
					    }
					</script>
		            <!-- If mode is "before" or "after," only one of these fields will be relevant. If "between," both are used. You could use JavaScript to show/hide inputs as needed. -->
		        </div>
		        
		        <!-- Amount Filter -->
		        <div>
		            <label>金額:</label>
		            
		            <input type="number" name="amount_from" placeholder="金額を入力" step="1" id="amount_from" value="${filterValues.amountFrom}">
		            <label for="amount_from">から</label>
		            
		            <span id="amount_to_wrapper" style="display: none;">
		            	<input type="number" name="amount_to" placeholder="金額を入力" step="1" id="amount_to" value="${filterValues.amountTo}">
		            	<label for="amount_to">まで</label>
		            </span>
		            
		            <select name="amount_filter_mode" id="amount_filter_mode" onchange="toggleAmountTo()">
		                <option value="" ${empty filterValues.amountFilterMode ? 'selected' : ''}>-</option>
		                <option value="less" ${filterValues.amountFilterMode == 'less' ? 'selected' : ''}>～未満</option>
		                <option value="greater" ${filterValues.amountFilterMode == 'greater' ? 'selected' : ''}>～以上</option>
		                <option value="between" ${filterValues.amountFilterMode == 'between' ? 'selected' : ''}>範囲指定</option>
		            </select>
		            
		            <script>
					    function toggleAmountTo() {
					        const amountFilterMode = document.getElementById("amount_filter_mode").value;
					        const amountToWrapper = document.getElementById("amount_to_wrapper");
					
					        if (amountFilterMode === "between") {
					            amountToWrapper.style.display = "inline"; // Show the 'amount_to' input
					        } else {
					            amountToWrapper.style.display = "none"; // Hide the 'amount_to' input
					        }
					    }
					</script>
		        </div>
		        
		        <!-- category filter -->
		        <div>
		            <label>カテゴリ:</label>
		            <input type="text" name="category_search" list="categorySuggestions" placeholder="キーワードを入力" value="${filterValues.categorySearch}">
		            <datalist id="categorySuggestions">
						<c:choose>
							<c:when test="${not empty categories}">
								<c:forEach var="category" items="${categories}">
									<option value="${category.categoryName}"></option>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<option value="データ取得失敗"></option>
							</c:otherwise>
						</c:choose>
		            </datalist>
		        </div>
		        
		        <!-- type filter -->
		        <div>
		            <label>種別:</label>
		            <select name="type_filter">
		                <option value="" ${empty filterValues.typeFilter ? 'selected' : ''}>-</option>
		                <option value="支出" ${filterValues.typeFilter == '支出' ? 'selected' : ''}>支出</option>
		                <option value="収入" ${filterValues.typeFilter == '収入' ? 'selected' : ''}>収入</option>
		            </select>
		        </div>
		        
		        <!-- payee filter -->
		        <div>
		            <label>支払先・収入源:</label>
		            <input type="text" name="payee_search" list="payeeSuggestions" placeholder="キーワードを入力" value="${filterValues.payeeSearch}">
		            <datalist id="payeeSuggestions">
		                <c:choose>
							<c:when test="${not empty categories}">
								<c:forEach var="category" items="${categories}">
									<c:forEach var="payee" items="${category.payees}">
										<option value="${payee}"></option>
									</c:forEach>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<option value="データ取得失敗"></option>
							</c:otherwise>
						</c:choose>
		            </datalist>
		        </div>
		        
		        <!-- Payment Method Filter -->
		        <div>
		            <label>支払方法・受取方法:</label>
		            <input type="text" name="payment_method_search" list="paymentMethodSuggestions" placeholder="キーワードを入力" value="${filterValues.paymentMethodSearch}">
		            <datalist id="paymentMethodSuggestions">
		                <c:choose>
							<c:when test="${not empty paymentMethods}">
								<c:forEach var="paymentMethod" items="${paymentMethods}">
									<option value="${paymentMethod.paymentMethodName}"></option>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<option value="データ取得失敗"></option>
							</c:otherwise>
						</c:choose>
		            </datalist>
		        </div>
		        
		        <!-- Description Filter -->
		        <div>
		            <label>メモフィルター:</label>
		            <textarea name="description_search" rows="1" placeholder="キーワードを入力">${filterValues.descriptionSearch}</textarea>
		        </div>
		        
		        <!-- Sorting Options -->
		        <br>
			        <fieldset>
				        <div>
				            <label>並び替え:</label>
				            <select name="order_field">
				                <option value="date" ${filterValues.orderField == 'date' ? 'selected' : ''}>日付</option>
				                <option value="amount" ${filterValues.orderField == 'amount' ? 'selected' : ''}>金額</option>
				            </select>
				            
				            <select name="order_direction">
				                <option value="desc" ${filterValues.orderField == 'desc' ? 'selected' : ''}>降順</option>
				                <option value="asc" ${filterValues.orderField == 'asc' ? 'selected' : ''}>昇順</option>
				            </select>
				        </div>
				      </fieldset>
				<br>
		        
		        