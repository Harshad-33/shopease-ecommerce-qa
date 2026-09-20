# ShopEase Defect / Bug Reports

**Document ID:** BR-SHOPEASE-2026-V1  
**Project:** ShopEase E-Commerce Web Application  
**Total Defects Documented:** 10  
**Environment:** Chrome 128 / Windows 11 / Frontend `localhost:4200` / Backend `localhost:8080`  

---

### Bug Report 1: `BUG-001`
- **Bug ID:** `BUG-001`
- **Title:** Registration phone input accepts non-numeric characters without real-time format validation
- **Module:** User Registration
- **Severity:** Medium
- **Priority:** Medium
- **Environment:** Windows 11 / Google Chrome 128.0 / `http://localhost:4200/register`
- **Preconditions:** User is on the registration page.
- **Steps to Reproduce:**
  1. Navigate to `/register`.
  2. Enter valid Name: `"Karan Johar"`, Email: `"karan@example.com"`.
  3. In the Phone Number input (`#phone`), enter alphabetic text: `"abcdefghij"`.
  4. Fill matching passwords: `"Password@123"`.
  5. Click `#register-button`.
- **Expected Result:** Client-side form should validate phone number format using regex (`/^[0-9]{10}$/`) and highlight field in red with message `"Please enter a valid 10-digit mobile number"` before sending network request.
- **Actual Result:** Form attempts submission; client allows alphabetic characters, only failing later if backend validation catches length boundaries.
- **Status:** Open
- **Screenshot/Reference:** `qa/screenshots/BUG-001_phone_validation.png`

---

### Bug Report 2: `BUG-002`
- **Bug ID:** `BUG-002`
- **Title:** Global search bar in navbar does not trim leading and trailing whitespace
- **Module:** Product Search
- **Severity:** Low
- **Priority:** Low
- **Environment:** Windows 11 / Google Chrome 128.0 / `http://localhost:4200`
- **Preconditions:** Catalog contains product `"Apple MacBook Air M2"`.
- **Steps to Reproduce:**
  1. In the navbar search input (`#search-product`), enter query with spaces: `"   MacBook   "`.
  2. Click search icon (`#search-button`).
- **Expected Result:** Input should automatically sanitize and trim query string to `"MacBook"` before URL encoding and API dispatch.
- **Actual Result:** URL navigates to `/products?search=%20%20%20MacBook%20%20%20`. While backend handles SQL trimming, redundant URL encoding degrades UX.
- **Status:** Open
- **Screenshot/Reference:** `qa/screenshots/BUG-002_search_whitespace.png`

---

### Bug Report 3: `BUG-003`
- **Bug ID:** `BUG-003`
- **Title:** Checkout Postal PIN Code field accepts alphabetic characters
- **Module:** Checkout & Delivery Form
- **Severity:** Medium
- **Priority:** Medium
- **Environment:** Windows 11 / Google Chrome 128.0 / `http://localhost:4200/checkout`
- **Preconditions:** User has items in cart and is on Checkout page.
- **Steps to Reproduce:**
  1. Click `"Auto-fill Demo Address"` on checkout page.
  2. Clear the PIN Code input (`#shipping-pincode`).
  3. Enter `"PINCODE"` or `"ABCXYZ"`.
  4. Click `#place-order-button`.
- **Expected Result:** Client validation should prevent order submission and flag error: `"Please enter a valid 6-digit postal PIN code"`.
- **Actual Result:** Order is submitted and accepted by backend because pincode is stored as a generic `VARCHAR(20)` without digit-only restriction.
- **Status:** Open
- **Screenshot/Reference:** `qa/screenshots/BUG-003_pincode_alphabetic.png`

---

### Bug Report 4: `BUG-004`
- **Bug ID:** `BUG-004`
- **Title:** Missing user warning toast when Cart quantity reaches maximum available stock
- **Module:** Shopping Cart
- **Severity:** Low
- **Priority:** Medium
- **Environment:** Windows 11 / Edge 128.0 / `http://localhost:4200/cart`
- **Preconditions:** Product in cart has available stock of 5 units. User currently has quantity 4 in cart.
- **Steps to Reproduce:**
  1. Navigate to `/cart`.
  2. Click the `+` button once to reach quantity 5.
  3. Note that `+` button becomes disabled.
  4. Click or hover on the disabled `+` button.
- **Expected Result:** Informative tooltip or toast should notify the customer: `"Maximum available stock (5 units) reached for this product"`.
- **Actual Result:** Button silently disables with no explicit feedback, which can confuse users unaware of stock limits.
- **Status:** Open
- **Screenshot/Reference:** `qa/screenshots/BUG-004_cart_stock_feedback.png`

---

### Bug Report 5: `BUG-005`
- **Bug ID:** `BUG-005`
- **Title:** Demo Card Expiry field accepts expired past dates
- **Module:** Checkout & Payment Simulator
- **Severity:** Medium
- **Priority:** Low
- **Environment:** Windows 11 / Chrome 128.0 / `http://localhost:4200/checkout`
- **Preconditions:** User selects Demo Card Payment option on checkout page.
- **Steps to Reproduce:**
  1. Select `"Demo Card Payment"` radio button.
  2. In the Expiry field, enter an expired date: `"01/20"`.
  3. Fill other required fields.
  4. Click `#place-order-button`.
- **Expected Result:** Validation should check that card expiration year/month is greater than or equal to current date and display: `"Card expiry date cannot be in the past"`.
- **Actual Result:** Order succeeds without warning because dummy card fields only verify presence, not temporal validity.
- **Status:** Open
- **Screenshot/Reference:** `qa/screenshots/BUG-005_expired_card_accepted.png`

---

### Bug Report 6: `BUG-006`
- **Bug ID:** `BUG-006`
- **Title:** Text truncation missing on very long customer names in Order History cards on mobile viewport (<360px)
- **Module:** Order History UI
- **Severity:** Low
- **Priority:** Low
- **Environment:** Chrome DevTools Mobile Viewport (360x740) / `http://localhost:4200/orders`
- **Preconditions:** Customer placed order with a long shipping name (e.g. `"Alexander Bartholomew Christopher Montgomery"`).
- **Steps to Reproduce:**
  1. Resize browser viewport to 360px width.
  2. Navigate to `/orders`.
  3. Observe the card header "Ship To" block.
- **Expected Result:** Recipient name should wrap cleanly or truncate with ellipsis without pushing Order # or date badge out of card alignment.
- **Actual Result:** Order header flexbox wraps awkwardly, creating inconsistent spacing between rows on ultra-narrow viewports.
- **Status:** Open
- **Screenshot/Reference:** `qa/screenshots/BUG-006_mobile_text_wrap.png`

---

### Bug Report 7: `BUG-007`
- **Bug ID:** `BUG-007`
- **Title:** Admin Add Product modal allows pasting zero or negative values for product price
- **Module:** Admin Catalog Management
- **Severity:** Medium
- **Priority:** High
- **Environment:** Windows 11 / Chrome 128.0 / `http://localhost:4200/admin/products`
- **Preconditions:** Logged in as Admin. Open "Add New Product" modal.
- **Steps to Reproduce:**
  1. In the Price input field, paste `"-500"` or `"0"`.
  2. Fill Name: `"Zero Price Item"`, Category: `"Books"`, Stock: `10`.
  3. Click `"Save Product"`.
- **Expected Result:** Modal form should reject input and flag error: `"Price must be greater than zero"`.
- **Actual Result:** Backend rejects with HTTP 400 (`@Min(value = 0)`), but client modal throws raw error toast instead of inline input field highlighting.
- **Status:** Open
- **Screenshot/Reference:** `qa/screenshots/BUG-007_negative_price_modal.png`

---

### Bug Report 8: `BUG-008`
- **Bug ID:** `BUG-008`
- **Title:** Admin Order status change to CANCELLED occurs without confirmation alert
- **Module:** Admin Order Management
- **Severity:** Low
- **Priority:** Medium
- **Environment:** Windows 11 / Chrome 128.0 / `http://localhost:4200/admin/orders`
- **Preconditions:** Active orders exist in Admin Orders table.
- **Steps to Reproduce:**
  1. Navigate to `/admin/orders`.
  2. Click status dropdown for any active order.
  3. Select `"CANCELLED"`.
- **Expected Result:** System should display a confirmation modal: `"Are you sure you want to cancel Order #SE-xxxx? This action cannot be undone."`
- **Actual Result:** Status immediately transitions to CANCELLED via API with no second confirmation step, risking accidental customer order cancellation.
- **Status:** Open
- **Screenshot/Reference:** `qa/screenshots/BUG-008_no_cancel_confirmation.png`

---

### Bug Report 9: `BUG-009`
- **Bug ID:** `BUG-009`
- **Title:** Clearing search input does not automatically reload catalog until Enter or search icon is clicked
- **Module:** Product Search UI
- **Severity:** Low
- **Priority:** Low
- **Environment:** Windows 11 / Firefox 129.0 / `http://localhost:4200/products`
- **Preconditions:** Active search filter applied (e.g. `search=Sony`).
- **Steps to Reproduce:**
  1. Highlight and backspace/delete text in `#search-input` in sidebar.
  2. Click outside the input (blur event).
- **Expected Result:** Catalog should auto-refresh to show all products or provide a small clear 'x' icon in input to reset search.
- **Actual Result:** User must manually click the magnifying glass `#search-submit` or press Enter to trigger the filter change.
- **Status:** Open
- **Screenshot/Reference:** `qa/screenshots/BUG-009_search_clear_ux.png`

---

### Bug Report 10: `BUG-010`
- **Bug ID:** `BUG-010`
- **Title:** Browser back button after successful order placement re-navigates to Checkout with empty cart
- **Module:** Order Placement / Navigation
- **Severity:** Medium
- **Priority:** Medium
- **Environment:** Windows 11 / Chrome 128.0 / `http://localhost:4200/order-confirmation/:id`
- **Preconditions:** User has just successfully placed an order and is on `/order-confirmation/:id`.
- **Steps to Reproduce:**
  1. On the order confirmation page, click the browser's native **Back** button.
- **Expected Result:** Browser history should replace checkout state, preventing return to the checkout form with an already-cleared cart, or cleanly redirect to `/orders`.
- **Actual Result:** Navigates back to `/checkout`, which detects empty cart and triggers redirect warning to `/cart`.
- **Status:** Open
- **Screenshot/Reference:** `qa/screenshots/BUG-010_browser_back_checkout.png`
