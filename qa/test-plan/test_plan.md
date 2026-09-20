# Master Test Plan: ShopEase E-Commerce Web Application

**Document ID:** TP-SHOPEASE-2026-V1  
**Project:** ShopEase – Medium-Level E-Commerce Web Application  
**Author:** QA Engineering Intern  
**Reviewed By:** Senior Full-Stack / QA Lead  
**Date:** September 2026  
**Status:** Approved / Active Baseline  

---

## 1. Introduction & Objectives

### 1.1 Objective
The primary objective of this Master Test Plan is to define the testing strategy, scope, environment, deliverables, and execution roadmap for verifying and validating the **ShopEase** e-commerce web platform. Testing ensures that the application functions according to user and business requirements, satisfies security and performance baselines, and provides a stable, user-friendly shopping experience across devices.

### 1.2 Purpose for QA Internship Demonstration
This document reflects industry-standard QA methodologies (aligned with IEEE 829 standards) demonstrating:
- Test planning, scoping, and risk assessment
- Manual functional and edge-case test case design
- Systematic defect tracking and reporting
- Automated regression test execution (Python, Selenium, PyTest)
- REST API validation via Postman
- Backend database and data integrity verification using MySQL
- Log debugging and root cause analysis

---

## 2. Scope of Testing

### 2.1 In-Scope Modules
The following functional modules and workflows will be rigorously tested:

| Module | Features In-Scope |
| :--- | :--- |
| **Authentication & Authorization** | User registration with validation, login (Customer & Admin), password encryption verification, session persistence via JWT, role-based route access, and logout. |
| **Product Catalog & Discovery** | Product listing, category chips/sidebar navigation, keyword search, price/date sorting, in-stock filtering, and pagination behavior. |
| **Product Details** | High-res imagery, descriptive details, stock counters, dynamic quantity selectors, out-of-stock disablement, and add-to-cart triggers. |
| **Shopping Cart** | Cart persistence across sessions, item additions, quantity increments/decrements bounded by stock, item removal, cart clearing, and live subtotal/total calculations. |
| **Checkout & Order Placement** | Shipping address form validations, delivery fee logic, payment method selection (COD and simulated Card Payment), stock deduction atomicity, order generation, and cart flushing. |
| **Order Tracking & History** | Customer order history list, status badges, invoice details, order summary breakdowns, and chronological tracking. |
| **Admin Operations** | KPI dashboard metrics calculation, inventory management (add, edit, adjust stock, soft delete), and order lifecycle state transitions (`PLACED` &rarr; `PROCESSING` &rarr; `SHIPPED` &rarr; `DELIVERED` &rarr; `CANCELLED`). |

### 2.2 Out-of-Scope
- Integration with third-party real banking gateways (Razorpay, Stripe, PayPal).
- External third-party shipping APIs (FedEx, BlueDart).
- SMS notification gateways and live OTP services.
- Multi-currency or multi-language localization.

---

## 3. Testing Types & Methodologies

1. **Smoke & Sanity Testing:**  
   High-level verification executed after every new build to confirm the core application boots, routes resolve, sample products load, and authentication works.
2. **Functional Testing:**  
   Verification of individual features against acceptance criteria (valid form submissions, calculations, filtering logic).
3. **Negative & Boundary Value Testing:**  
   Intentionally inputting invalid formats, negative numbers, exceeding stock limits, SQL injection strings, and unauthorized URL manipulation to ensure graceful degradation.
4. **Regression Testing:**  
   Re-running the automated PyTest/Selenium suite on bug fixes to guarantee existing features remain intact.
5. **Cross-Browser & Responsive Compatibility:**  
   Testing on Google Chrome, Microsoft Edge, and Mozilla Firefox across Desktop (1920x1080), Tablet (768x1024), and Mobile (375x812) viewports.
6. **API Testing:**  
   Validating Spring Boot REST endpoints using Postman for correct HTTP status codes (200, 201, 400, 401, 403, 404), response payloads, header handling, and payload structures.
7. **Database Testing:**  
   Direct SQL query executions in MySQL 8 to verify referential integrity, foreign key cascading, BCrypt password hashing, and stock deductions.
8. **Performance & Load Testing:**  
   Basic load benchmarking using Apache JMeter (10, 25, 50 virtual concurrent users) measuring average response time and error rates on key read and write endpoints.

---

## 4. Test Environment

| Component | Specification / Configuration |
| :--- | :--- |
| **Operating System** | Windows 11 64-bit |
| **Frontend Server** | Angular 21 Dev Server on `http://localhost:4200` |
| **Backend API Server**| Spring Boot 3.3.4 (OpenJDK 21) on `http://localhost:8080` |
| **Database Server** | MySQL Community Server 8.0.44 on `localhost:3306`, DB: `shopease_db` |
| **Browsers** | Google Chrome (Latest), Microsoft Edge (Latest), Mozilla Firefox (Latest) |
| **Automation Tools** | Python 3.14, Selenium WebDriver 4.x, PyTest 8.x, PyTest-HTML |
| **API Tools** | Postman v10+, cURL |
| **Performance Tool** | Apache JMeter 5.6+ |

### Test Accounts Configured
- **Admin:** `admin@shopease.com` / `Admin@123`
- **Customer 1:** `rahul@example.com` / `Customer@123`
- **Customer 2:** `priya@example.com` / `Customer@123`
- **Customer 3:** `amit@example.com` / `Customer@123`

---

## 5. Entry & Exit Criteria

### 5.1 Entry Criteria
- Application frontend (`http://localhost:4200`) and backend (`http://localhost:8080`) are built and running.
- MySQL database `shopease_db` is populated with initial seed data.
- Stable element identifiers (`id` / `name` / `data-testid`) are present in HTML templates.
- Test documentation and automation workspace are initialized.

### 5.2 Exit Criteria
- 100% of planned manual test cases executed.
- Minimum 90% test case pass rate achieved.
- Zero open Critical or High severity defects.
- All 15 Selenium automated regression workflows pass successfully.
- API Postman collection passes all assertions.
- Test Summary Report generated and signed off.

---

## 6. Defect Severity & Priority Classification

| Level | Severity (Impact on System) | Priority (Urgency to Fix) |
| :--- | :--- | :--- |
| **Critical / S1** | System crash, application unbootable, complete failure of checkout or authentication. | Fix immediately; blocks testing. |
| **High / S2** | Core feature broken without workaround (e.g. cart fails to update, stock deduction fails). | Must be fixed before release. |
| **Medium / S3** | Functional defect with a known workaround (e.g. search sorting edge case, minor validation flaw). | Fix in normal development cycle. |
| **Low / S4** | Cosmetic issues, minor UI alignment, typographical errors, styling quirks. | Fix as time permits. |

---

## 7. Risks & Mitigation Strategies

| Risk Description | Probability | Impact | Mitigation Strategy |
| :--- | :---: | :---: | :--- |
| **Port or service collision (8080 / 4200)** | Low | High | Document health checks and provide diagnostic commands in QA guide. |
| **Database state pollution between test runs** | Medium | Medium | Implement data teardown/cleanup SQL scripts to reset test records. |
| **Flaky UI automation due to async rendering** | Medium | High | Enforce Selenium `WebDriverWait` explicit waits on element visibility instead of hardcoded sleeps. |
| **Browser driver mismatch** | Low | Medium | Utilize Selenium Manager (built-in Selenium 4) for automatic driver resolution. |

---

## 8. Test Deliverables
1. **Master Test Plan** (`qa/test-plan/test_plan.md`)
2. **50 Detailed Manual Test Cases** (`qa/test-cases/manual_test_cases.md` & `test_cases.csv`)
3. **Defect Reports & Tracking Matrix** (`qa/bug-reports/`)
4. **Test Execution Log & Summary Report** (`qa/test-execution/`)
5. **Postman API Test Collection & Guide** (`qa/api-testing/`)
6. **Python Selenium POM Automation Suite** (`qa/automation/`)
7. **Database Validation SQL Scripts** (`qa/database-testing/`)
8. **Log Debugging Guide** (`qa/log-debugging/`)
9. **Cross-Browser Compatibility Matrix** (`qa/compatibility/`)
10. **JMeter Performance Test Plan & Report** (`qa/performance/`)
11. **Interactive QA Executive Dashboard** (`qa/reports/qa_dashboard.html`)
