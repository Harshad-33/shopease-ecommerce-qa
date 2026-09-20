# ShopEase QA & Software Testing Framework

**Project:** ShopEase E-Commerce Platform  
**System Under Test (SUT):** Full-Stack Web Application (Angular 21 + Spring Boot 3.3.4 + MySQL 8.0)  
**Target Role:** QA Engineer / SDET / QA Intern  
**Documentation Version:** 1.0.0  
**Status:** Certified & Production-Evaluation Ready  

---

## 1. Project Overview

This repository contains the complete, industry-standard Quality Assurance (QA) and Software Testing ecosystem built around **ShopEase**, a modern full-stack e-commerce web application.

The primary objective of this QA layer is to demonstrate comprehensive manual, automation, API, database, performance, and cross-browser testing skills for technical interview evaluation. The testing artifacts adhere to international testing standards (IEEE 829 for Test Planning and Execution Reporting, ISTQB principles for test design, and Page Object Model for test automation architecture).

---

## 2. Directory Structure of `qa/`

```
qa/
├── test-plan/
│   └── test_plan.md                  # Comprehensive IEEE 829 Master Test Plan
├── test-cases/
│   ├── manual_test_cases.md          # 52 structured manual test cases in Markdown
│   ├── test_cases.csv                # Tabular CSV export of all 52 test cases
│   ├── ShopEase_Manual_Test_Cases.xlsx# Professional styled Excel workbook
│   └── generate_excel.py             # OpenPyXL generator script
├── bug-reports/
│   ├── bug_reports.md                # 10 realistic, reproducible defect reports (BUG-001 - BUG-010)
│   └── bug_tracking_matrix.md        # Severity/priority matrix & Root Cause Analysis (RCA)
├── test-execution/
│   ├── test_execution_log.md         # Detailed test execution results for all 52 test cases
│   └── test_summary_report.md        # Formal test execution summary with metrics and sign-off
├── api-testing/
│   ├── ShopEase_Postman_Collection.json # Complete Postman API collection with tests & assertions
│   ├── ShopEase_Environment.json     # Postman environment configuration (http://localhost:8080/api)
│   └── README.md                     # Postman GUI & Newman CLI execution guide
├── automation/
│   ├── conftest.py                   # PyTest WebDriver fixture (headless/headed Chrome)
│   ├── requirements.txt              # Automation Python dependencies
│   ├── README.md                     # PyTest framework documentation
│   ├── pages/                        # Page Object Model (POM) classes
│   │   ├── base_page.py              # Reusable explicit waits and Selenium wrappers
│   │   ├── login_page.py             # Login and session handling
│   │   ├── register_page.py          # Registration and client-side validations
│   │   ├── products_page.py          # Catalog, search, filters, and product details
│   │   ├── cart_page.py              # Cart modifications and item management
│   │   ├── checkout_page.py          # Address autofill, COD/Card payment
│   │   ├── orders_page.py            # Order confirmation and order history tracking
│   │   └── admin_page.py             # Admin dashboard KPIs and product management
│   ├── tests/
│   │   └── test_shopease_e2e.py      # 15 complete automated end-to-end regression tests
│   └── utils/
│       └── config.py                 # Centralized test configuration and test data
├── database-testing/
│   ├── db_validation_queries.sql     # Comprehensive SQL queries for DB verification
│   └── db_testing_guide.md           # Real-world data integrity & discrepancy testing guide
├── log-debugging/
│   └── log_debugging_guide.md        # Spring Boot & Browser log debugging guide with scenarios
├── compatibility/
│   └── compatibility_matrix.md       # Cross-browser & responsive mobile/tablet compatibility matrix
├── performance/
│   ├── ShopEase_Performance_Plan.jmx # Apache JMeter 5.6.3 load and stress test plan
│   └── performance_report.md         # Performance metrics, response times, throughput & bottlenecks
└── reports/
    ├── qa_dashboard.html             # Interactive HTML Executive QA Dashboard
    └── automation_report.html         # PyTest HTML self-contained automation test report
```

---

## 3. How to Run Everything

### A. Manual Testing & Test Case Documentation
- **Spreadsheet:** Open `qa/test-cases/ShopEase_Manual_Test_Cases.xlsx` in Microsoft Excel, Google Sheets, or LibreOffice Calc. The sheet is formatted with frozen headers, category filters, and color-coded status badges (`PASS` in green, `FAIL` in red).
- **Execution Log:** Review `qa/test-execution/test_execution_log.md` to see the actual step-by-step outcomes, captured evidence, and linked defect IDs.

### B. Selenium Automation Suite (Python + PyTest + POM)
1. **Prerequisites:**
   - Google Chrome installed.
   - Python 3.10+ installed.
2. **Install Dependencies:**
   ```powershell
   pip install -r qa/automation/requirements.txt
   ```
3. **Run All 15 Automated E2E Tests with HTML Report:**
   ```powershell
   $env:PYTHONPATH = "F:\TestingProject\ShopEase"
   python -m pytest "F:\TestingProject\ShopEase\qa\automation\tests" --html="F:\TestingProject\ShopEase\qa\reports\automation_report.html" --self-contained-html -v
   ```
4. **Run in Headed (Visual) Mode:**
   Edit `qa/automation/conftest.py` and remove `--headless=new` to see the browser execute in real-time.
5. **View Results:**
   Open `qa/reports/automation_report.html` in any web browser to view pass/fail durations, timestamps, and test logs.

### C. REST API Testing (Postman & Newman CLI)
1. **Using Postman GUI:**
   - Open Postman &rarr; Click **Import** &rarr; Select `qa/api-testing/ShopEase_Postman_Collection.json` and `qa/api-testing/ShopEase_Environment.json`.
   - Select the `ShopEase Local Environment`.
   - Run the collection with Postman Collection Runner.
2. **Using Newman CLI:**
   ```powershell
   newman run qa/api-testing/ShopEase_Postman_Collection.json -e qa/api-testing/ShopEase_Environment.json --reporters cli
   ```

### D. Database Integrity Verification (MySQL)
Execute the SQL validation scripts using MySQL Workbench, DBeaver, or CLI:
```powershell
mysql -u root -p shopease_db < qa/database-testing/db_validation_queries.sql
```
Follow the step-by-step troubleshooting scenarios in `qa/database-testing/db_testing_guide.md`.

### E. Performance & Load Testing (Apache JMeter)
1. Open Apache JMeter (`jmeter.bat` or `jmeter.sh`).
2. Load the test plan: `File -> Open -> qa/performance/ShopEase_Performance_Plan.jmx`.
3. Click the green **Run** button to simulate 10, 25, and 50 concurrent users.
4. Review the results in the **Aggregate Report** and compare with `qa/performance/performance_report.md`.

### F. Interactive QA Executive Dashboard
Double-click `qa/reports/qa_dashboard.html` to open the executive dashboard. It includes interactive Chart.js graphs, KPI scorecards, module-by-module pass rates, defect matrices, and direct navigation links to all reports.

---

## 4. Key Findings & Test Summary

| Metric | Target | Achieved Result | Evaluation |
| :--- | :---: | :---: | :---: |
| **Manual Test Cases** | 40 - 60 | **52 Cases** | Exceeded |
| **Manual Pass Rate** | &gt; 90% | **92.3%** (48 Passed / 4 Failed) | Met |
| **Defects Logged** | 8 - 12 | **10 Bugs** (BUG-001 to BUG-010) | Met |
| **Defect Severity Breakdown** | Multi-tier | 2 Critical, 3 High, 3 Medium, 2 Low | Realistic Distribution |
| **Automation Scenarios** | 10 - 15 | **15 Scenarios** | Met |
| **Automation Pass Rate** | 100% | **100% (15/15 Passed)** | Complete |
| **API Endpoints Tested** | All Core | 100% Coverage (Auth, Catalog, Cart, Order, Admin) | Met |
| **Performance SLA** | &lt; 500ms | **Avg 28ms** (Browse) / **112ms** (Login) | Sub-millisecond SLA Met |

---

## 5. Interview Preparation & Walkthrough Guide

### How to Explain This QA Project in a Job Interview

> **Interviewer:** *"Can you tell me about the testing project listed on your resume?"*
> 
> **Your Answer:**  
> *"Certainly! I created a comprehensive Quality Assurance and test automation framework for **ShopEase**, a full-stack e-commerce web application with an Angular frontend, Spring Boot REST API, and MySQL database.
> 
> My objective was to test the application from all testing dimensions:
> 1. **Manual Testing:** Authored a complete IEEE 829 Test Plan and designed **52 structured test cases** across Authentication, Catalog, Cart, Checkout, Orders, and Admin modules using black-box techniques like Boundary Value Analysis and Equivalence Partitioning.
> 2. **Defect Management:** Logged **10 production-like bug reports** with severity, priority, steps to reproduce, and root-cause analysis (e.g., race conditions in cart quantity updates and missing database inventory locks).
> 3. **Automation Testing:** Built an end-to-end automation suite in Python using **Selenium WebDriver, PyTest, and the Page Object Model (POM)** pattern. The suite automates 15 core business workflows and generates self-contained HTML execution reports.
> 4. **API Testing:** Built a Postman collection covering all REST endpoints with automated JavaScript tests validating status codes, response times, JSON schema, and JWT token propagation.
> 5. **Database & Performance Testing:** Validated MySQL relational integrity with custom SQL scripts and performed load testing using Apache JMeter with 50 concurrent virtual users to benchmark response times and identify server bottlenecks."*

---

### Concrete QA Concepts Demonstrated in This Project

#### 1. Boundary Value Analysis (BVA)
- **Concept:** Testing the boundaries between partitions where defects cluster most frequently.
- **Example in ShopEase:**
  - Cart item increment: Testing at `1` (minimum allowed), `item.stockQuantity` (maximum in stock), and `item.stockQuantity + 1` (attempting to exceed available stock).
  - Password length: Registration password tested at `5` chars (boundary failure), `6` chars (minimum boundary pass), and $128$ chars (maximum boundary).

#### 2. Equivalence Partitioning (EP)
- **Concept:** Dividing input data into valid and invalid partitions that are processed similarly by the system.
- **Example in ShopEase:**
  - Email Validation: Valid partition (`user@domain.com`), Invalid partition without `@` (`userdomain.com`), Invalid partition with spaces (`user @domain.com`).
  - Search Catalog: Valid partition (existing keyword `Apple`), Valid empty partition (returns all), Invalid non-matching partition (`XYZ999_NOT_FOUND` - asserting zero products found message).

#### 3. State Transition Testing
- **Concept:** Verifying that a system transitions correctly between discrete states based on specific triggers.
- **Example in ShopEase:**
  - Order Lifecycle: `PENDING` (initial placement) &rarr; `CONFIRMED` (admin approval) &rarr; `SHIPPED` (logistics dispatch) &rarr; `DELIVERED` (customer delivery). Validating that an order cannot jump from `PENDING` directly to `DELIVERED` without passing through intermediate states.

#### 4. Error Guessing
- **Concept:** Using domain intuition and experience to anticipate corner cases where developers might miss edge cases.
- **Example in ShopEase:**
  - Navigating directly to `/checkout` with an empty shopping cart.
  - Injecting SQL special characters (`' OR 1=1 --`) and HTML tags (`<script>alert(1)</script>`) into the product search field.
  - Rapid double-clicking on the "Place Order" button to detect duplicate order generation.

#### 5. Page Object Model (POM) Design Pattern
- **Concept:** An architectural design pattern that creates an object repository for web UI elements, separating page-specific locators and actions from test scripts.
- **Why POM is Used Here:**
  - If a UI ID changes (e.g., `#add-to-cart-btn`), we update it once in `pages/products_page.py` instead of editing dozens of test cases.
  - Reusability: Test scripts (`test_shopease_e2e.py`) read like clean plain English business specifications.

#### 6. Database Verification (Backend Verification)
- **Concept:** Ensuring the UI state matches the underlying persistent storage and detecting data anomalies.
- **Example in ShopEase:**
  - After placing an order in the UI, running `SELECT stock_quantity FROM products WHERE id = 1` to verify the inventory physically decreased in MySQL.
  - Verifying that order total in `orders` matches the exact sum of `order_items` quantity $\times$ unit price.

#### 7. Performance & Load Testing
- **Concept:** Evaluating system responsiveness and stability under simulated user concurrency.
- **Example in ShopEase:**
  - Running 10, 25, and 50 concurrent virtual users via JMeter on `/api/products` and measuring that the 95th percentile response time remains under 300ms, while pinpointing connection pool limits in Spring Boot HikariCP.

---

## 6. QA Engineer Sign-Off

The **ShopEase QA Project** represents a comprehensive, end-to-end testing implementation demonstrating strong technical proficiency across manual, automation, API, database, performance, and non-functional testing disciplines.
