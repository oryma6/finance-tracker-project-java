# finance-tracker-project-java
*Japanese Below

A simple and customizable web application for tracking income and expenses. This app allows users to upload all their transactions at once using CSV files downloaded from their bank or credit card company. Users can assign each transaction (payee) to a category for better organization. Additionally, users can customize CSV formats, mapping columns from the CSV file to specific database fields.

The goal of this app is to minimize the time I spend on tracking my finance. Because I'm tired of inputting all my transactions from different banks and credit cards one-by-one.

This app's user interface is written in Japanese.

## Current Status
While core features are functional, some bugs need to be resolved before recommending use or contribution.

## Features
- **Batch Transaction Input**: Upload CSV files to record multiple transactions at once.
- **Customizable Categories**: Assign a payee/payer (someone on the other side the transaction) to a category for transaction categorization. Categories are editable.
- **CSV Format Customization**: Map each column in the CSV file to specific fields in the database (e.g., date, amount, description).
- **Data Visualization**: View income and expenses visually using pie chart and table. The data shown can be filtered.

## Technologies Used
- **Backend**:
  - Java (Servlet, JSP)
- **Frontend**:
  - HTML, CSS (Bootstrap), JavaScript (Canvas.js)
  - JSTL, EL Expressions
- **Database**:
  - PostgreSQL


# 家計簿ウェブアプリケーション
収入と支出を記録するためのシンプルでカスタマイズ可能なウェブアプリです。このアプリでは、銀行やクレジットカード会社からダウンロードしたCSVファイルを使用して、一度にすべての取引を記録することができます。また、各取引先をカテゴリに割り当てて整理することが可能です。さらに、CSVフォーマットをカスタマイズして、CSVファイルの各列をデータベースの特定のフィールドにマッピングすることができます。

## 現在の状況
このプロジェクトはまだ開発中です。主要な機能は動作していますが、いくつかのバグが解消されるまで、利用やコントリビュートを推奨していません。

## 機能
- **複数取引の一括入力**: CSVファイルをアップロードして、複数の取引を一度に記録できます。
- **カスタマイズ可能なカテゴリ**: 各取引先（例: 店名、会社名）をカテゴリに割り当てて、わかりやすく管理。
- **CSVフォーマットのカスタマイズ**: CSVファイルの各列をデータベースの特定のフィールド（例: 日付、金額、説明）にマッピング可能。
- **データの可視化**: グラフ（円グラフ、棒グラフなど）を使用して、収支データを視覚的に表示。

# 使用技術
- **バックエンド**:
  - Java (Servlet, JSP)
- **フロントエンド**:
  - HTML, CSS (Bootstrap), JavaScript (Canvas.js)
  - JSTL, EL式
- **データベース**:
  - PostgreSQL
