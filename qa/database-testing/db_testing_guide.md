# Database Testing Guide for QA Testers

**Database Engine:** MySQL 8.0  
**Schema Name:** `shopease_db`  
**Host:** `localhost:3306`  

---

## 1. Why Database Testing is Essential for E-Commerce QA

In modern web applications, UI forms can display success messages even if the underlying database state is corrupt or desynchronized. Database testing guarantees:
- **Data Integrity:** Primary keys, foreign key cascading, and unique constraints are enforced.
- **Financial Reconciliation:** Order total matches the exact sum of line items.
- **Inventory Consistency:** Stock decreases accurately after checkout without underflows.
- **Security Compliance:** Passwords are never stored as plaintext.

---

## 2. Real-World Troubleshooting Scenarios (UI vs Database)

### Scenario A: Investigating "Insufficient Stock" Error on Checkout
- **Symptom:** User complains they cannot checkout 2 units of "Apple MacBook Air M2", even though the product page says "In Stock".
- **QA SQL Investigation:**
  ```sql
  SELECT id, name, stock_quantity, active 
  FROM products 
  WHERE name LIKE '%MacBook%';
  ```
- **Analysis:** Check if `stock_quantity < 2` or `active = false`. If the UI is caching old stock state in browser memory while the database has 1 unit remaining, this explains the discrepancy.

---

### Scenario B: Verifying Cart Cleanup After Order Placement
- **Symptom:** Customer placed an order, but when navigating to `/cart`, items are still appearing.
- **QA SQL Investigation:**
  ```sql
  -- Step 1: Get user id
  SELECT id FROM users WHERE email = 'rahul@example.com';
  
  -- Step 2: Check cart and cart_items
  SELECT c.id AS cart_id, ci.id AS item_id, ci.product_id, ci.quantity
  FROM carts c
  LEFT JOIN cart_items ci ON c.id = ci.cart_id
  WHERE c.user_id = 2;
  ```
- **Analysis:** If `cart_items` records still exist in MySQL after the order status became `PLACED`, the `@Transactional` method in `OrderService` failed to commit the delete operation or an exception occurred during cart flushing.

---

### Scenario C: Auditing Price Changes & Order Snapshots
- **Symptom:** An Admin updated the price of a laptop from ₹92,999 to ₹99,999. A customer who placed an order yesterday complains their invoice shows the new price.
- **QA SQL Investigation:**
  ```sql
  SELECT o.order_number, oi.product_id, oi.unit_price, p.price AS current_price
  FROM order_items oi
  JOIN orders o ON oi.order_id = o.id
  JOIN products p ON oi.product_id = p.id
  WHERE o.order_number = 'SE-123456';
  ```
- **Analysis:** `order_items.unit_price` MUST retain the historical price at checkout time (₹92,999) rather than dynamically fetching `p.price`. This proves snapshot pricing integrity.
