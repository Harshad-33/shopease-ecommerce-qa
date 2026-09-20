# ShopEase Manual Test Cases Suite

**Document ID:** TC-SHOPEASE-2026-V1  
**Total Test Cases:** 52  
**Target Application:** ShopEase E-Commerce Web Application (`http://localhost:4200`)  
**Format:** IEEE 829 Standard Test Case Specification  

---

## Quick Summary Matrix by Module

| Module | Test Case Range | Total Cases | High | Medium | Low |
| :--- | :--- | :---: | :---: | :---: | :---: |
| **Registration** | TC_REG_001 – TC_REG_006 | 6 | 4 | 2 | 0 |
| **Authentication & Logout** | TC_AUTH_001 – TC_AUTH_006 | 6 | 5 | 1 | 0 |
| **Product Catalog & Listing** | TC_CAT_001 – TC_CAT_004 | 4 | 2 | 2 | 0 |
| **Search & Filtering** | TC_SRCH_001 – TC_SRCH_005 | 5 | 3 | 2 | 0 |
| **Product Details & Stock** | TC_DET_001 – TC_DET_005 | 5 | 3 | 2 | 0 |
| **Shopping Cart** | TC_CART_001 – TC_CART_006 | 6 | 4 | 2 | 0 |
| **Checkout & Validation** | TC_CHK_001 – TC_CHK_006 | 6 | 5 | 1 | 0 |
| **Order Placement & Confirmation**| TC_ORD_001 – TC_ORD_004 | 4 | 4 | 0 | 0 |
| **Order History & Tracking** | TC_HIST_001 – TC_HIST_003 | 3 | 2 | 1 | 0 |
| **Admin Authentication & Dashboard**| TC_ADM_001 – TC_ADM_003 | 3 | 2 | 1 | 0 |
| **Admin Product Management** | TC_ADM_PRD_001 – TC_ADM_PRD_002 | 2 | 2 | 0 | 0 |
| **Admin Stock Management** | TC_ADM_STK_001 – TC_ADM_STK_001 | 1 | 1 | 0 | 0 |
| **Admin Order Management** | TC_ADM_ORD_001 – TC_ADM_ORD_001 | 1 | 1 | 0 | 0 |

---

## Detailed Manual Test Cases

### Module 1: User Registration

#### `TC_REG_001` - Verify user registration with all valid mandatory fields
- **Module:** Registration
- **Test Scenario:** New customer account creation
- **Preconditions:** Application is accessible at `http://localhost:4200/register`. Email has never been registered before.
- **Test Steps:**
  1. Navigate to `http://localhost:4200/register`.
  2. Enter Full Name: `"Kiran Kumar"`.
  3. Enter Email: `"kiran.test@example.com"`.
  4. Enter Phone: `"9876543219"`.
  5. Enter Password: `"Password@123"`.
  6. Enter Confirm Password: `"Password@123"`.
  7. Click `#register-button`.
- **Test Data:** Valid non-registered details.
- **Expected Result:** Success toast message `"Welcome to ShopEase, Kiran Kumar!"` appears. User is redirected to home page, authenticated with JWT in localStorage, and user name displays in navbar.
- **Actual Result:** As expected. User registered, JWT token set, cart initialized in DB.
- **Status:** PASS
- **Priority:** High

#### `TC_REG_002` - Verify registration failure with an existing/duplicate email
- **Module:** Registration
- **Test Scenario:** Duplicate email prevention
- **Preconditions:** An account with email `rahul@example.com` already exists.
- **Test Steps:**
  1. Open `/register`.
  2. Fill form using email `"rahul@example.com"`.
  3. Enter matching valid passwords.
  4. Click `#register-button`.
- **Test Data:** Email: `rahul@example.com`.
- **Expected Result:** Error alert displays `"An account with this email already exists."` Registration fails with HTTP 400.
- **Actual Result:** Error alert properly displayed.
- **Status:** PASS
- **Priority:** High

#### `TC_REG_003` - Verify validation when Password and Confirm Password do not match
- **Module:** Registration
- **Test Scenario:** Password confirmation check
- **Preconditions:** On registration page.
- **Test Steps:**
  1. Fill valid Full Name, Email, and Phone.
  2. Enter Password: `"Secret123"`.
  3. Enter Confirm Password: `"Secret999"`.
  4. Click `#register-button`.
- **Expected Result:** Form submission blocked with error message `"Passwords do not match."`
- **Actual Result:** Displays message "Passwords do not match."
- **Status:** PASS
- **Priority:** High

#### `TC_REG_004` - Verify password length less than minimum (6 characters)
- **Module:** Registration
- **Test Scenario:** Weak password rejection
- **Preconditions:** On registration page.
- **Test Steps:**
  1. Fill form with Password: `"12345"`, Confirm Password: `"12345"`.
  2. Click `#register-button`.
- **Expected Result:** Validation warning: `"Password must be at least 6 characters long."`
- **Actual Result:** Validated and blocked.
- **Status:** PASS
- **Priority:** Medium

#### `TC_REG_005` - Verify registration attempt with empty required fields
- **Module:** Registration
- **Test Scenario:** Mandatory field boundary
- **Preconditions:** On registration page.
- **Test Steps:**
  1. Leave all input fields empty.
  2. Click `#register-button`.
- **Expected Result:** Error message `"Please fill out all required fields."` displays and inputs are marked required.
- **Actual Result:** Client-side error prompt prevents empty submission.
- **Status:** PASS
- **Priority:** High

#### `TC_REG_006` - Verify navigation link from registration to login page
- **Module:** Registration
- **Test Scenario:** Navigation continuity
- **Preconditions:** On registration page.
- **Test Steps:**
  1. Click the `"Sign In here"` hyperlink (`#login-link-btn`).
- **Expected Result:** URL changes to `/login` and login card renders.
- **Actual Result:** Navigates smoothly to `/login`.
- **Status:** PASS
- **Priority:** Low

---

### Module 2: Authentication & Logout

#### `TC_AUTH_001` - Verify login with valid customer credentials
- **Module:** Authentication
- **Test Scenario:** Successful customer login
- **Preconditions:** Registered user exists: `rahul@example.com` / `Customer@123`.
- **Test Steps:**
  1. Navigate to `http://localhost:4200/login`.
  2. Enter Email: `"rahul@example.com"`.
  3. Enter Password: `"Customer@123"`.
  4. Click `#login-button`.
- **Expected Result:** Toast `"Welcome back, Rahul Sharma!"` displays. Navbar shows customer's name and cart button. Redirected to home page.
- **Actual Result:** Successful login, JWT saved in localStorage.
- **Status:** PASS
- **Priority:** High

#### `TC_AUTH_002` - Verify 1-Click Customer Demo fill button on login page
- **Module:** Authentication
- **Test Scenario:** Usability demo fill
- **Preconditions:** On `/login` page.
- **Test Steps:**
  1. Click `#demo-customer-btn`.
- **Expected Result:** Email input auto-populates with `rahul@example.com` and Password with `Customer@123`.
- **Actual Result:** Inputs populate immediately.
- **Status:** PASS
- **Priority:** Medium

#### `TC_AUTH_003` - Verify login failure with invalid password
- **Module:** Authentication
- **Test Scenario:** Wrong password rejection
- **Preconditions:** On `/login` page.
- **Test Steps:**
  1. Enter Email: `"rahul@example.com"`.
  2. Enter Password: `"WrongPassword999"`.
  3. Click `#login-button`.
- **Expected Result:** Error alert displays: `"Invalid email or password. Please try again."` User remains on `/login`.
- **Actual Result:** Error alert shown; HTTP 401 captured in network tab.
- **Status:** PASS
- **Priority:** High

#### `TC_AUTH_004` - Verify login failure with non-registered email address
- **Module:** Authentication
- **Test Scenario:** Non-existent user check
- **Preconditions:** On `/login` page.
- **Test Steps:**
  1. Enter Email: `"ghost.user@unknown.com"`.
  2. Enter Password: `"Password123"`.
  3. Click `#login-button`.
- **Expected Result:** Error alert displays `"Invalid email or password. Please try again."`
- **Actual Result:** Handled gracefully with generic safe error message.
- **Status:** PASS
- **Priority:** High

#### `TC_AUTH_005` - Verify route guard redirection to login when accessing protected route
- **Module:** Authentication
- **Test Scenario:** Route authorization protection
- **Preconditions:** User is logged out.
- **Test Steps:**
  1. Directly enter URL `http://localhost:4200/orders` in browser address bar.
- **Expected Result:** Route guard intercepts request and redirects browser to `/login?returnUrl=%2Forders`.
- **Actual Result:** Successfully redirected with returnUrl query parameter.
- **Status:** PASS
- **Priority:** High

#### `TC_AUTH_006` - Verify user logout functionality
- **Module:** Authentication
- **Test Scenario:** Session termination
- **Preconditions:** User is logged in as Customer.
- **Test Steps:**
  1. Click the user profile dropdown in the navbar.
  2. Click `#logout-button`.
- **Expected Result:** `shopease_token` and `shopease_user` are purged from `localStorage`. Navbar returns to guest state ("Login", "Register"). Redirected to `/login`.
- **Actual Result:** Storage cleared, session ended.
- **Status:** PASS
- **Priority:** High

---

### Module 3: Product Catalog & Listing

#### `TC_CAT_001` - Verify initial catalog display on home page
- **Module:** Product Listing
- **Test Scenario:** Home page featured products loading
- **Preconditions:** Backend running with 20 seeded products.
- **Test Steps:**
  1. Navigate to `http://localhost:4200/`.
- **Expected Result:** Hero section displays. 6 category cards render. Featured products grid displays top 8 items with image, price, title, and stock badge.
- **Actual Result:** All 8 featured products render with accurate formatting in ₹ INR.
- **Status:** PASS
- **Priority:** High

#### `TC_CAT_002` - Verify full catalog loading on Products page
- **Module:** Product Listing
- **Test Scenario:** Complete catalog navigation
- **Preconditions:** On home page.
- **Test Steps:**
  1. Click `"Products"` link in the navbar.
- **Expected Result:** Navigates to `/products`. Showing counter displays `"Showing 20 products"`. Filter sidebar is accessible.
- **Actual Result:** 20 products loaded successfully.
- **Status:** PASS
- **Priority:** High

#### `TC_CAT_003` - Verify price sorting: Low to High
- **Module:** Product Listing
- **Test Scenario:** Price ascending order
- **Preconditions:** On `/products`.
- **Test Steps:**
  1. Open `#sort-select` dropdown.
  2. Select `"Price: Low to High"`.
- **Expected Result:** Products re-sort immediately with lowest priced item first (e.g. ₹399 Psychology of Money) and highest priced item last.
- **Actual Result:** Products sorted in ascending order of price.
- **Status:** PASS
- **Priority:** Medium

#### `TC_CAT_004` - Verify price sorting: High to Low
- **Module:** Product Listing
- **Test Scenario:** Price descending order
- **Preconditions:** On `/products`.
- **Test Steps:**
  1. Select `"Price: High to Low"` in `#sort-select`.
- **Expected Result:** Highest priced item (e.g. ₹109,999 Samsung Galaxy S24 Ultra) appears first.
- **Actual Result:** Products correctly sorted in descending price order.
- **Status:** PASS
- **Priority:** Medium

---

### Module 4: Product Search & Category Filtering

#### `TC_SRCH_001` - Verify global search bar in navbar
- **Module:** Search
- **Test Scenario:** Keyword search from navigation
- **Preconditions:** Any page.
- **Test Steps:**
  1. Type `"MacBook"` in `#search-product`.
  2. Click `#search-button` or press Enter.
- **Expected Result:** Navigates to `/products?search=MacBook`. Products grid filters to show `"Apple MacBook Air M2"`. Counter shows `"Showing 1 products"`.
- **Actual Result:** Correctly filtered to 1 product.
- **Status:** PASS
- **Priority:** High

#### `TC_SRCH_002` - Verify category filter selection
- **Module:** Filtering
- **Test Scenario:** Filter products by category
- **Preconditions:** On `/products`.
- **Test Steps:**
  1. Click `"Electronics"` in the category sidebar list.
- **Expected Result:** URL updates with `?categoryId=1`. Only the 4 electronics products are displayed.
- **Actual Result:** 4 electronics products displayed.
- **Status:** PASS
- **Priority:** High

#### `TC_SRCH_003` - Verify combined search query and category filter
- **Module:** Search & Filtering
- **Test Scenario:** Multi-parameter filtering
- **Preconditions:** On `/products`.
- **Test Steps:**
  1. Select Category: `"Clothing"`.
  2. In search input `#search-input`, type `"Shirt"`.
  3. Click `#search-submit`.
- **Expected Result:** Displays `"Men's Slim-Fit Cotton Casual Shirt"`. Does not show items from other categories.
- **Actual Result:** Accurately filtered across both parameters.
- **Status:** PASS
- **Priority:** High

#### `TC_SRCH_004` - Verify search with no matching results
- **Module:** Search
- **Test Scenario:** Zero-state UI verification
- **Preconditions:** On `/products`.
- **Test Steps:**
  1. Search for `"NonExistentGizmo2026"`.
- **Expected Result:** Display `"No Products Found"` card with `"Clear All Filters"` button.
- **Actual Result:** Zero-state placeholder displays cleanly.
- **Status:** PASS
- **Priority:** Medium

#### `TC_SRCH_005` - Verify "Reset All" filters button
- **Module:** Filtering
- **Test Scenario:** Filter reset functionality
- **Preconditions:** Active filters applied (search and category).
- **Test Steps:**
  1. Click `"Reset All"` in filter sidebar.
- **Expected Result:** Search input clears, category resets to `"All Categories"`, sort returns to `"Newest Arrivals"`, and all 20 products reload.
- **Actual Result:** All filters cleared and catalog fully reset.
- **Status:** PASS
- **Priority:** Medium

---

### Module 5: Product Details & Stock Validation

#### `TC_DET_001` - Verify navigation to product details page
- **Module:** Product Details
- **Test Scenario:** Product details view
- **Preconditions:** On `/products`.
- **Test Steps:**
  1. Click product card title or image for `"Sony WH-1000XM5 Wireless Headphones"`.
- **Expected Result:** Navigates to `/products/2`. Page displays large high-res image, full description, price ₹26,990.00, in-stock badge with available count (18), and quantity controls.
- **Actual Result:** Product details loaded accurately.
- **Status:** PASS
- **Priority:** High

#### `TC_DET_002` - Verify quantity increment within available stock
- **Module:** Product Details
- **Test Scenario:** Quantity selector increment
- **Preconditions:** Product has stock 18. Default quantity is 1.
- **Test Steps:**
  1. Click `#qty-plus-btn` three times.
- **Expected Result:** Quantity input updates to `4`.
- **Actual Result:** Displays quantity `4`.
- **Status:** PASS
- **Priority:** Medium

#### `TC_DET_003` - Verify quantity decrement bounded at minimum 1
- **Module:** Product Details
- **Test Scenario:** Lower bound boundary check
- **Preconditions:** Quantity is set to 1.
- **Test Steps:**
  1. Attempt to click `#qty-minus-btn`.
- **Expected Result:** Button `#qty-minus-btn` is disabled (`[disabled]="quantity <= 1"`). Value remains 1.
- **Actual Result:** Button is disabled; cannot decrement below 1.
- **Status:** PASS
- **Priority:** Medium

#### `TC_DET_004` - Verify quantity increment bounded by available stock
- **Module:** Product Details
- **Test Scenario:** Upper bound stock check
- **Preconditions:** Product stock is 8 (e.g. Samsung Galaxy S24 Ultra).
- **Test Steps:**
  1. Click `#qty-plus-btn` repeatedly until quantity reaches 8.
- **Expected Result:** At quantity 8, `#qty-plus-btn` becomes disabled (`[disabled]="quantity >= product.stockQuantity"`). Value cannot exceed 8.
- **Actual Result:** Button disabled at exact stock limit.
- **Status:** PASS
- **Priority:** High

#### `TC_DET_005` - Verify out-of-stock product UI state
- **Module:** Product Details
- **Test Scenario:** Out of stock handling
- **Preconditions:** A product has stock quantity 0.
- **Test Steps:**
  1. Navigate to out-of-stock product details.
- **Expected Result:** Badge `"Out of Stock"` displays in red. Quantity selectors and `"Add to Cart"` button are hidden or replaced with warning: `"This product is currently out of stock."`
- **Actual Result:** Correctly indicates out of stock and disables order action.
- **Status:** PASS
- **Priority:** High

---

### Module 6: Shopping Cart Management

#### `TC_CART_001` - Verify adding product to cart as an authenticated user
- **Module:** Shopping Cart
- **Test Scenario:** Item addition to cart
- **Preconditions:** User is logged in. Cart is initially empty.
- **Test Steps:**
  1. On product details page, select quantity: `2`.
  2. Click `#add-to-cart-btn`.
- **Expected Result:** Success toast `"Added 2 item(s) to your cart!"` appears. Navbar `#cart-count-badge` increments from empty to `2`.
- **Actual Result:** Toast displays, cart count updates to 2.
- **Status:** PASS
- **Priority:** High

#### `TC_CART_002` - Verify prompt to log in when guest attempts to add to cart
- **Module:** Shopping Cart
- **Test Scenario:** Guest cart restriction
- **Preconditions:** User is NOT logged in.
- **Test Steps:**
  1. Click `"Add"` button on any product card on Home or Products page.
- **Expected Result:** Warning toast displays `"Please log in to add items to your cart."` Redirects user to `/login`.
- **Actual Result:** Warning toast triggers and redirects to login.
- **Status:** PASS
- **Priority:** High

#### `TC_CART_003` - Verify cart items table rendering and subtotal calculation
- **Module:** Shopping Cart
- **Test Scenario:** Cart mathematical calculation
- **Preconditions:** User has added 1 item priced at ₹1,499.00 and 2 items priced at ₹799.00.
- **Test Steps:**
  1. Navigate to `/cart`.
- **Expected Result:**
  - Row 1: Qty 1 &times; ₹1,499.00 = Subtotal ₹1,499.00
  - Row 2: Qty 2 &times; ₹799.00 = Subtotal ₹1,598.00
  - Order Summary Total = ₹3,097.00. Delivery = FREE.
- **Actual Result:** Calculations match exactly.
- **Status:** PASS
- **Priority:** High

#### `TC_CART_004` - Verify increasing item quantity in Cart page
- **Module:** Shopping Cart
- **Test Scenario:** In-cart quantity increment
- **Preconditions:** Item in cart has quantity 1.
- **Test Steps:**
  1. Click `+` button in cart row.
- **Expected Result:** Quantity updates to `2`. Row subtotal and order summary total double immediately without full page reload.
- **Actual Result:** Subtotal and total updated synchronously.
- **Status:** PASS
- **Priority:** High

#### `TC_CART_005` - Verify item removal from cart
- **Module:** Shopping Cart
- **Test Scenario:** Single item removal
- **Preconditions:** Cart contains 2 distinct items.
- **Test Steps:**
  1. Click the red trash icon button on Row 1.
- **Expected Result:** Info toast `"Removed <product> from cart."` Row vanishes. Order summary total recalculates. Cart badge updates.
- **Actual Result:** Item removed, totals adjusted.
- **Status:** PASS
- **Priority:** Medium

#### `TC_CART_006` - Verify "Clear Cart" button with confirmation dialog
- **Module:** Shopping Cart
- **Test Scenario:** Bulk cart flush
- **Preconditions:** Cart has items.
- **Test Steps:**
  1. Click `#clear-cart-btn`.
  2. Confirm browser alert dialog.
- **Expected Result:** Cart empties. Empty cart screen renders with message `"Your Shopping Cart is Empty"` and `"Start Shopping Now"` button.
- **Actual Result:** Cart cleared in DB and UI.
- **Status:** PASS
- **Priority:** Medium

---

### Module 7: Checkout & Shipping Form Validation

#### `TC_CHK_001` - Verify navigation to Checkout page with valid cart
- **Module:** Checkout
- **Test Scenario:** Checkout initiation
- **Preconditions:** User is logged in and has items in cart.
- **Test Steps:**
  1. On `/cart`, click `#checkout-button`.
- **Expected Result:** Navigates to `/checkout`. Recipient name and phone auto-populate from user profile. Order breakdown displays items on the right side.
- **Actual Result:** Navigates to `/checkout` with pre-filled details.
- **Status:** PASS
- **Priority:** High

#### `TC_CHK_002` - Verify redirection if accessing Checkout with an empty cart
- **Module:** Checkout
- **Test Scenario:** Empty cart checkout prevention
- **Preconditions:** User is logged in, but cart is empty.
- **Test Steps:**
  1. Navigate directly to `http://localhost:4200/checkout`.
- **Expected Result:** Warning toast `"Your shopping cart is empty."` Redirected back to `/cart`.
- **Actual Result:** Blocked and redirected to `/cart`.
- **Status:** PASS
- **Priority:** High

#### `TC_CHK_003` - Verify validation when mandatory shipping fields are omitted
- **Module:** Checkout
- **Test Scenario:** Shipping form required field check
- **Preconditions:** On `/checkout`. Clear all address fields.
- **Test Steps:**
  1. Clear Street Address, City, State, and Pincode.
  2. Click `#place-order-button`.
- **Expected Result:** Toast error displays: `"Please fill in all shipping details."` Order is NOT submitted.
- **Actual Result:** Validation triggers; order submission aborted.
- **Status:** PASS
- **Priority:** High

#### `TC_CHK_004` - Verify 1-Click "Auto-fill Demo Address" helper button
- **Module:** Checkout
- **Test Scenario:** Usability test address fill
- **Preconditions:** On `/checkout`.
- **Test Steps:**
  1. Click `"Auto-fill Demo Address"`.
- **Expected Result:** Address fields populate with sample Bangalore, Karnataka address and pincode `560001`.
- **Actual Result:** Fields populated instantly.
- **Status:** PASS
- **Priority:** Medium

#### `TC_CHK_005` - Verify payment method toggle between COD and Card Payment
- **Module:** Checkout
- **Test Scenario:** Dynamic payment fields rendering
- **Preconditions:** On `/checkout`.
- **Test Steps:**
  1. Select `#payment-card` radio option.
- **Expected Result:** Demo Card Payment simulator card appears below showing Card Number, Expiry, and CVV fields.
  2. Select `#payment-cod` radio option.
- **Expected Result:** Demo card input box disappears.
- **Actual Result:** Card UI toggles dynamically based on radio selection.
- **Status:** PASS
- **Priority:** High

#### `TC_CHK_006` - Verify validation on incomplete demo card payment details
- **Module:** Checkout
- **Test Scenario:** Card payment boundary check
- **Preconditions:** Payment method set to Card Payment.
- **Test Steps:**
  1. Clear CVV or Card Number.
  2. Click `#place-order-button`.
- **Expected Result:** Error toast: `"Please enter complete demo card details."`
- **Actual Result:** Validated and blocked.
- **Status:** PASS
- **Priority:** Medium

---

### Module 8: Order Placement & Order Confirmation

#### `TC_ORD_001` - Verify successful order placement via Cash on Delivery
- **Module:** Order Placement
- **Test Scenario:** End-to-end order placement with COD
- **Preconditions:** User has 1 item in cart. Address fields are filled. Payment method is `CASH_ON_DELIVERY`.
- **Test Steps:**
  1. Click `#place-order-button`.
- **Expected Result:**
  1. Button shows spinner `"Placing Order..."`.
  2. Order is saved in MySQL database with status `PLACED`.
  3. Product stock is decremented in DB.
  4. Cart items are deleted.
  5. User is redirected to `/order-confirmation/{id}`.
  6. Green celebration icon, unique Order Number (`SE-xxxx`), ordered items, and shipping address are displayed.
- **Actual Result:** Order placed successfully, stock deducted, cart cleared, confirmation rendered.
- **Status:** PASS
- **Priority:** High

#### `TC_ORD_002` - Verify successful order placement via Demo Card Payment
- **Module:** Order Placement
- **Test Scenario:** Order placement with simulated card
- **Preconditions:** Cart has items. Shipping address filled. Card radio selected with valid demo card.
- **Test Steps:**
  1. Click `#place-order-button`.
- **Expected Result:** Order successfully placed. Confirmation page displays Payment Method as `"Card Payment (Demo)"`.
- **Actual Result:** Order created and confirmed.
- **Status:** PASS
- **Priority:** High

#### `TC_ORD_003` - Verify cart count reset to 0 after placing an order
- **Module:** Order Placement
- **Test Scenario:** Cart lifecycle post-order
- **Preconditions:** On `/order-confirmation/{id}` after placing order.
- **Test Steps:**
  1. Inspect navbar cart badge.
- **Expected Result:** Badge disappears or displays 0. Clicking cart shows empty cart message.
- **Actual Result:** Cart is completely empty.
- **Status:** PASS
- **Priority:** High

#### `TC_ORD_004` - Verify stock quantity decrement in product catalog after order
- **Module:** Order Placement
- **Test Scenario:** Inventory synchronization
- **Preconditions:** Product stock before order was 15. User ordered 2 units.
- **Test Steps:**
  1. Navigate back to `/products` and view product details for that item.
- **Expected Result:** Available stock now displays `13 available`.
- **Actual Result:** Stock reflects accurate decremented value `13`.
- **Status:** PASS
- **Priority:** High

---

### Module 9: Order History & Tracking

#### `TC_HIST_001` - Verify order history displays recently placed order
- **Module:** Order History
- **Test Scenario:** Customer order history retrieval
- **Preconditions:** User has placed at least one order.
- **Test Steps:**
  1. Click user dropdown in navbar.
  2. Click `#my-orders-link`.
- **Expected Result:** Navigates to `/orders`. Placed order appears at top of the list with Order Date, Total Amount, Ship To name, and status badge `"PLACED"`.
- **Actual Result:** Order history displayed in reverse chronological order.
- **Status:** PASS
- **Priority:** High

#### `TC_HIST_002` - Verify navigation from Order History to Order Details
- **Module:** Order History
- **Test Scenario:** Order invoice inspection
- **Preconditions:** On `/orders`.
- **Test Steps:**
  1. Click `"View Order Details &rarr;"` on any order card.
- **Expected Result:** Navigates to `/orders/:id`. Order details card renders with status tracker, ordered items breakdown, unit prices, and shipping address.
- **Actual Result:** Details rendered with accurate data.
- **Status:** PASS
- **Priority:** High

#### `TC_HIST_003` - Verify empty state for new user with zero orders
- **Module:** Order History
- **Test Scenario:** Zero order history display
- **Preconditions:** User registered a new account and has not placed any orders yet.
- **Test Steps:**
  1. Navigate to `/orders`.
- **Expected Result:** Message displays `"No Orders Placed Yet"` with button `"Explore Products"`.
- **Actual Result:** Displays empty state view.
- **Status:** PASS
- **Priority:** Medium

---

### Module 10: Admin Authentication & Dashboard

#### `TC_ADM_001` - Verify Admin login with admin credentials
- **Module:** Admin
- **Test Scenario:** Administrator authentication
- **Preconditions:** Admin account exists: `admin@shopease.com` / `Admin@123`.
- **Test Steps:**
  1. Navigate to `/login`.
  2. Enter Admin email & password (or click `#demo-admin-btn`).
  3. Click `#login-button`.
- **Expected Result:** Logged in as Admin. Redirected to `/admin/dashboard`. Navbar displays yellow `"ADMIN"` badge and Admin dropdown links.
- **Actual Result:** Successfully logged in with admin authority.
- **Status:** PASS
- **Priority:** High

#### `TC_ADM_002` - Verify non-admin customer is blocked from accessing Admin routes
- **Module:** Admin
- **Test Scenario:** Authorization security check
- **Preconditions:** Logged in as regular customer (`rahul@example.com`).
- **Test Steps:**
  1. Attempt to navigate directly to `http://localhost:4200/admin/dashboard`.
- **Expected Result:** `adminGuard` blocks navigation and redirects user to home page (`/`).
- **Actual Result:** Access denied; redirected to `/`.
- **Status:** PASS
- **Priority:** High

#### `TC_ADM_003` - Verify KPI metric cards render on Admin Dashboard
- **Module:** Admin Dashboard
- **Test Scenario:** Business analytics display
- **Preconditions:** Logged in as Admin on `/admin/dashboard`.
- **Test Steps:**
  1. Inspect Dashboard metric cards.
- **Expected Result:** 6 KPI cards display: Total Revenue, Total Orders, Pending Orders, Active Products, Registered Customers, and Low Stock Alert.
- **Actual Result:** All 6 KPI cards render with accurate live values from backend.
- **Status:** PASS
- **Priority:** Medium

---

### Module 11: Admin Product Management

#### `TC_ADM_PRD_001` - Verify Admin can add a new product to store catalog
- **Module:** Admin Products
- **Test Scenario:** Catalog expansion
- **Preconditions:** Logged in as Admin on `/admin/products`.
- **Test Steps:**
  1. Click `#add-product-btn`.
  2. Modal opens. Fill Name: `"Sony Alpha A7 IV Camera"`, Category: `"Electronics"`, Description: `"33MP full-frame hybrid camera."`, Price: `189999`, Stock: `5`.
  3. Click `"Save Product"`.
- **Expected Result:** Success toast `"Product added successfully!"` Modal closes. New product appears in the table and immediately becomes visible on the customer catalog.
- **Actual Result:** Product created and saved to MySQL.
- **Status:** PASS
- **Priority:** High

#### `TC_ADM_PRD_002` - Verify Admin can edit existing product details and price
- **Module:** Admin Products
- **Test Scenario:** Product update
- **Preconditions:** On `/admin/products`.
- **Test Steps:**
  1. Click pencil icon on any product.
  2. Modify Price from ₹1,499.00 to ₹1,299.00.
  3. Click `"Update Product"`.
- **Expected Result:** Success toast `"Product updated successfully!"` Table reflects updated price ₹1,299.00.
- **Actual Result:** Product updated in DB and UI.
- **Status:** PASS
- **Priority:** High

---

### Module 12: Admin Stock & Order Management

#### `TC_ADM_STK_001` - Verify Admin can adjust stock quantity for low-stock product
- **Module:** Admin Stock
- **Test Scenario:** Inventory replenishment
- **Preconditions:** On `/admin/products`.
- **Test Steps:**
  1. Click edit on product with stock 8.
  2. Change stock quantity to `25`.
  3. Click `"Update Product"`.
- **Expected Result:** Stock quantity badge updates to `25` (turns green). Low stock warning badge is cleared.
- **Actual Result:** Stock replenished successfully.
- **Status:** PASS
- **Priority:** High

#### `TC_ADM_ORD_001` - Verify Admin can update customer order status to SHIPPED
- **Module:** Admin Orders
- **Test Scenario:** Order fulfillment lifecycle
- **Preconditions:** An order exists with status `PLACED`. Admin is on `/admin/orders`.
- **Test Steps:**
  1. Locate order row in table.
  2. In Status dropdown, change value from `PLACED` to `SHIPPED`.
- **Expected Result:** Spinner appears briefly. Success toast `"Order #SE-... status updated to SHIPPED"`. Status dropdown remains `SHIPPED`. Customer viewing order details will now see status progress at 75%.
- **Actual Result:** Status updated in MySQL and broadcast to customer view.
- **Status:** PASS
- **Priority:** High
