# ShopEase Test Execution Log

**Execution Cycle:** Cycle 1 - Full Regression Baseline  
**Environment:** Chrome 128 / Windows 11 / Frontend: `localhost:4200` / Backend: `localhost:8080` / MySQL 8.0  
**Executed By:** QA Engineering Intern  
**Execution Date:** September 2026  

---

## Detailed Step-by-Step Execution Log

| Test Case ID | Module | Execution Date | Tester | Result | Defect Logged | Comments / Verification Details |
| :--- | :--- | :---: | :--- | :---: | :---: | :--- |
| **TC_REG_001** | Registration | 2026-09-14 | QA Intern | **PASS** | None | Registered `kiran@example.com`. DB user record verified. |
| **TC_REG_002** | Registration | 2026-09-14 | QA Intern | **PASS** | None | Duplicate email `rahul@example.com` threw 400 with friendly toast. |
| **TC_REG_003** | Registration | 2026-09-14 | QA Intern | **PASS** | None | Mismatched password blocked on client before API call. |
| **TC_REG_004** | Registration | 2026-09-14 | QA Intern | **PASS** | None | Password length 5 characters rejected. |
| **TC_REG_005** | Registration | 2026-09-14 | QA Intern | **PASS** | None | Empty fields marked required by HTML5 validation. |
| **TC_REG_006** | Registration | 2026-09-14 | QA Intern | **PASS** | None | Hyperlink to `/login` navigates immediately. |
| **TC_AUTH_001**| Authentication | 2026-09-14 | QA Intern | **PASS** | None | Customer login successful; JWT set in localStorage. |
| **TC_AUTH_002**| Authentication | 2026-09-14 | QA Intern | **PASS** | None | 1-Click demo customer fill button works as intended. |
| **TC_AUTH_003**| Authentication | 2026-09-14 | QA Intern | **PASS** | None | Invalid password rejected with HTTP 401 Unauthorized. |
| **TC_AUTH_004**| Authentication | 2026-09-14 | QA Intern | **PASS** | None | Non-existent email safely rejected without stack trace. |
| **TC_AUTH_005**| Authentication | 2026-09-14 | QA Intern | **PASS** | None | Angular authGuard intercepts `/orders` and redirects to login. |
| **TC_AUTH_006**| Authentication | 2026-09-14 | QA Intern | **PASS** | None | Logout clears tokens, resets navbar, and redirects to `/login`. |
| **TC_CAT_001** | Product Listing| 2026-09-14 | QA Intern | **PASS** | None | 8 featured products render on home page with images & prices. |
| **TC_CAT_002** | Product Listing| 2026-09-14 | QA Intern | **PASS** | None | All 20 products render cleanly in catalog grid on `/products`. |
| **TC_CAT_003** | Product Listing| 2026-09-14 | QA Intern | **PASS** | None | Price ascending sort verified (₹399.00 appears first). |
| **TC_CAT_004** | Product Listing| 2026-09-14 | QA Intern | **PASS** | None | Price descending sort verified (₹109,999.00 appears first). |
| **TC_SRCH_001**| Search | 2026-09-14 | QA Intern | **PASS** | None | Navbar search for 'MacBook' routes and filters to 1 item. |
| **TC_SRCH_002**| Filtering | 2026-09-14 | QA Intern | **PASS** | None | Electronics category filter displays exactly 4 items. |
| **TC_SRCH_003**| Search & Filter| 2026-09-14 | QA Intern | **PASS** | None | 'Clothing' category + search 'Shirt' displays 1 item. |
| **TC_SRCH_004**| Search | 2026-09-14 | QA Intern | **PASS** | None | Zero results placeholder displays with reset filter button. |
| **TC_SRCH_005**| Filtering | 2026-09-14 | QA Intern | **PASS** | None | 'Reset All' button clears all active filters and reloads 20 items. |
| **TC_DET_001** | Product Details| 2026-09-14 | QA Intern | **PASS** | None | Product details page renders image, description, and price. |
| **TC_DET_002** | Product Details| 2026-09-14 | QA Intern | **PASS** | None | Quantity increment button operates properly. |
| **TC_DET_003** | Product Details| 2026-09-14 | QA Intern | **PASS** | None | Quantity decrement is disabled at minimum quantity 1. |
| **TC_DET_004** | Product Details| 2026-09-14 | QA Intern | **PASS** | None | Quantity increment disables when stock maximum is reached. |
| **TC_DET_005** | Product Details| 2026-09-14 | QA Intern | **PASS** | None | Out of stock badge and disabled add button verified. |
| **TC_CART_001**| Shopping Cart | 2026-09-14 | QA Intern | **PASS** | None | Product added to cart; navbar badge increments in real time. |
| **TC_CART_002**| Shopping Cart | 2026-09-14 | QA Intern | **PASS** | None | Guest prompt to login verified when adding to cart. |
| **TC_CART_003**| Shopping Cart | 2026-09-14 | QA Intern | **PASS** | None | Subtotals and Grand Total math verified with decimal precision. |
| **TC_CART_004**| Shopping Cart | 2026-09-14 | QA Intern | **PASS** | None | In-cart quantity `+` updates row and total dynamically. |
| **TC_CART_005**| Shopping Cart | 2026-09-14 | QA Intern | **PASS** | None | Single cart item deletion verified; total recalculated. |
| **TC_CART_006**| Shopping Cart | 2026-09-14 | QA Intern | **PASS** | None | Clear Cart dialog verified; cart emptied in DB. |
| **TC_CHK_001** | Checkout | 2026-09-14 | QA Intern | **PASS** | None | Navigation to Checkout pre-fills customer name & phone. |
| **TC_CHK_002** | Checkout | 2026-09-14 | QA Intern | **PASS** | None | Empty cart checkout blocked; redirects to `/cart`. |
| **TC_CHK_003** | Checkout | 2026-09-14 | QA Intern | **PASS** | None | Empty shipping details blocked with toast notification. |
| **TC_CHK_004** | Checkout | 2026-09-14 | QA Intern | **PASS** | None | Auto-fill demo address populates all fields cleanly. |
| **TC_CHK_005** | Checkout | 2026-09-14 | QA Intern | **PASS** | None | Payment method radio toggles demo card inputs correctly. |
| **TC_CHK_006** | Checkout | 2026-09-14 | QA Intern | **PASS** | None | Missing card fields caught by client validation. |
| **TC_ORD_001** | Order Placement| 2026-09-14 | QA Intern | **PASS** | None | COD order placed; stock decremented in DB; order confirmed. |
| **TC_ORD_002** | Order Placement| 2026-09-14 | QA Intern | **PASS** | None | Card order placed; confirmation renders card payment badge. |
| **TC_ORD_003** | Order Placement| 2026-09-14 | QA Intern | **PASS** | None | Cart emptied immediately after placing order. |
| **TC_ORD_004** | Order Placement| 2026-09-14 | QA Intern | **PASS** | None | Catalog reflects decremented inventory count. |
| **TC_HIST_001**| Order History | 2026-09-14 | QA Intern | **PASS** | None | Placed order displays at top of `/orders` with date and badge. |
| **TC_HIST_002**| Order History | 2026-09-14 | QA Intern | **PASS** | None | Click 'View Order Details' opens invoice breakdown. |
| **TC_HIST_003**| Order History | 2026-09-14 | QA Intern | **PASS** | None | Zero orders placeholder displayed for fresh user. |
| **TC_ADM_001** | Admin | 2026-09-14 | QA Intern | **PASS** | None | Admin login successful; redirects to `/admin/dashboard`. |
| **TC_ADM_002** | Admin | 2026-09-14 | QA Intern | **PASS** | None | Customer account blocked from `/admin/*` by adminGuard. |
| **TC_ADM_003** | Admin Dashboard| 2026-09-14 | QA Intern | **PASS** | None | 6 KPI metric cards show live values matching database queries. |
| **TC_ADM_PRD_001**| Admin Products| 2026-09-14 | QA Intern | **PASS** | None | Admin created new product; verified in customer catalog. |
| **TC_ADM_PRD_002**| Admin Products| 2026-09-14 | QA Intern | **PASS** | None | Admin edited product price; updated immediately. |
| **TC_ADM_STK_001**| Admin Stock | 2026-09-14 | QA Intern | **PASS** | None | Admin replenished low stock item from 8 to 25. |
| **TC_ADM_ORD_001**| Admin Orders | 2026-09-14 | QA Intern | **PASS** | None | Admin updated order status to SHIPPED; reflected in customer view. |
