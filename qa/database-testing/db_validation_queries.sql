-- ======================================================================
-- ShopEase QA Database Validation Queries
-- Purpose: Backend verification, data reconciliation, and regression tests
-- Target Database: shopease_db (MySQL 8.0+)
-- ======================================================================

USE `shopease_db`;

-- ----------------------------------------------------------------------
-- 1. USER & AUTHENTICATION VALIDATION
-- ----------------------------------------------------------------------

-- 1.1 Verify newly registered customer and BCrypt password encryption
SELECT id, full_name, email, phone, role, created_at,
       CASE 
           WHEN password LIKE '$2a$%' OR password LIKE '$2b$%' THEN 'ENCRYPTED_BCRYPT'
           ELSE 'VULNERABLE_PLAINTEXT'
       END AS password_security_status
FROM users
WHERE email = 'rahul@example.com';

-- 1.2 Verify unique email constraint validation
SELECT email, COUNT(*) AS count_occurrences
FROM users
GROUP BY email
HAVING COUNT(*) > 1;

-- 1.3 Verify user roles distribution
SELECT role, COUNT(*) AS user_count
FROM users
GROUP BY role;


-- ----------------------------------------------------------------------
-- 2. PRODUCT CATALOG & STOCK VALIDATION
-- ----------------------------------------------------------------------

-- 2.1 Verify product pricing, active status, and stock consistency
SELECT p.id, p.name, c.name AS category, p.price, p.stock_quantity, p.active
FROM products p
JOIN categories c ON p.category_id = c.id
ORDER BY p.price ASC;

-- 2.2 Identify out-of-stock and low-stock products (<= 10 units)
SELECT id, name, stock_quantity, active
FROM products
WHERE stock_quantity <= 10
ORDER BY stock_quantity ASC;

-- 2.3 Verify negative stock prevention (Zero Tolerance Check)
SELECT id, name, stock_quantity
FROM products
WHERE stock_quantity < 0;


-- ----------------------------------------------------------------------
-- 3. SHOPPING CART & INTEGRITY CHECKS
-- ----------------------------------------------------------------------

-- 3.1 Verify persistent cart items and recalculate expected subtotals
SELECT c.id AS cart_id, u.email, p.name AS product_name, 
       ci.unit_price, ci.quantity, 
       (ci.unit_price * ci.quantity) AS expected_subtotal,
       p.stock_quantity AS current_available_stock
FROM carts c
JOIN users u ON c.user_id = u.id
JOIN cart_items ci ON c.id = ci.cart_id
JOIN products p ON ci.product_id = p.id;

-- 3.2 Detect orphan cart items without parent cart or product
SELECT ci.id, ci.cart_id, ci.product_id
FROM cart_items ci
LEFT JOIN carts c ON ci.cart_id = c.id
LEFT JOIN products p ON ci.product_id = p.id
WHERE c.id IS NULL OR p.id IS NULL;


-- ----------------------------------------------------------------------
-- 4. ORDER MANAGEMENT & FINANCIAL RECONCILIATION
-- ----------------------------------------------------------------------

-- 4.1 Verify placed order summary vs order items sum
SELECT o.id AS order_id, o.order_number, o.order_date, o.status, o.payment_method,
       o.total_amount AS recorded_total,
       SUM(oi.subtotal) AS calculated_items_total,
       (o.total_amount - SUM(oi.subtotal)) AS variance
FROM orders o
JOIN order_items oi ON o.id = oi.order_id
GROUP BY o.id, o.order_number, o.order_date, o.status, o.payment_method, o.total_amount;

-- 4.2 Verify snapshot price integrity (comparing order item price with current catalog price)
SELECT o.order_number, p.name, 
       oi.unit_price AS checkout_snapshot_price, 
       p.price AS current_live_catalog_price,
       (p.price - oi.unit_price) AS price_shift
FROM order_items oi
JOIN orders o ON oi.order_id = o.id
JOIN products p ON oi.product_id = p.id;

-- 4.3 Verify User-to-Order Referential Integrity
SELECT o.order_number, u.full_name, u.email, o.shipping_full_name, o.shipping_phone
FROM orders o
JOIN users u ON o.user_id = u.id;
