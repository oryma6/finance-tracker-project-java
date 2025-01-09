<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<header>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark" style="padding: 1rem;">
        <div class="container-fluid">
            <!-- logo -->
	        <div id="top">  
	            <a class="navbar-brand" href="/CashDash/home">
	                <img src="/CashDash/img/logo.png" alt="Logo" style="height: 80px;"> <!-- increased logo height -->
	            </a>
			</div>  
			
            <!-- toggler for mobile view -->
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>

            <!-- navigation links -->
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav me-auto">
                    <!-- home -->
                    <li class="nav-item">
                        <a class="nav-link text-white me-3 text-decoration-none fs-5" href="/CashDash/home">ホーム</a> 
                    </li>
                    <!-- data input -->
                    <li class="nav-item">
                        <a class="nav-link text-white me-3 text-decoration-none fs-5" href="/CashDash/dataInput">取引入力</a>
                    </li>
                    <!-- export -->
                    <li class="nav-item">
                        <a class="nav-link text-white me-3 text-decoration-none fs-5" href="/CashDash/export">エクスポート</a>
                    </li>
                    <!-- dropdown for settings -->
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle text-white me-3 text-decoration-none fs-5" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                            設定
                        </a>
                        <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                            <li><a class="dropdown-item text-black me-3 text-decoration-none fs-5" href="/CashDash/dataSettings">CSV設定</a></li>
                            <li><a class="dropdown-item text-black me-3 text-decoration-none fs-5" href="/CashDash/category">データ設定</a></li>
                        </ul>
                    </li>
                </ul>

                <!-- right-side icons -->
                <div class="d-flex align-items-center ms-auto">
				    <!-- account settings -->
				    <a href="/CashDash/userSettings" class="text-white me-3 text-decoration-none fs-5">
				        アカウント
				    </a>
				    <!-- logout -->
				    <a href="/CashDash/logout" class="text-white text-decoration-none fs-5">
				        ログアウト
				    </a>
				</div>
            </div>
        </div>
    </nav>
</header>