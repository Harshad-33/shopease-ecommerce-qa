# Spring Boot Application Log Debugging Guide

**Application:** `shopease-backend`  
**Logging Framework:** SLF4J with Logback (Spring Boot default)  
**Log Levels:** `INFO`, `WARN`, `ERROR`  

---

## 1. Log Architecture & Format

Spring Boot outputs structured logs formatted as:
```text
yyyy-MM-dd HH:mm:ss [thread] LEVEL logger_class - message
```
Example:
```text
2026-09-14 20:04:20 [http-nio-8080-exec-1] INFO  c.s.service.AuthService - Attempting registration for email: rahul@example.com
```

> **Security Note:** In compliance with GDPR and PCI-DSS best practices, passwords, credit card numbers, and secret keys are **never** logged.

---

## 2. Investigating Real-World Issues via Logs

### Scenario 1: Investigating Login Failures
- **Log Pattern to Search:** `Login attempt for email` or `Failed login attempt`
- **Example Log Output:**
  ```text
  2026-09-14 20:04:22 [http-nio-8080-exec-2] INFO  c.s.service.AuthService - Login attempt for email: rahul@example.com
  2026-09-14 20:04:22 [http-nio-8080-exec-2] WARN  c.s.service.AuthService - Failed login attempt: incorrect password for email rahul@example.com
  2026-09-14 20:04:22 [http-nio-8080-exec-2] WARN  c.s.e.GlobalExceptionHandler - Authentication failed: invalid credentials
  ```
- **QA Action:** Confirms the user exists in the database, but provided an invalid password. Rejection returns HTTP 401.

---

### Scenario 2: Investigating Product Retrieval / 404 Errors
- **Log Pattern to Search:** `Fetching products` or `ResourceNotFoundException`
- **Example Log Output:**
  ```text
  2026-09-14 20:04:41 [http-nio-8080-exec-4] INFO  c.s.service.ProductService - Fetching products - search: 'Sony', categoryId: null, sortBy: newest, inStockOnly: false
  2026-09-14 20:05:51 [http-nio-8080-exec-5] WARN  c.s.e.GlobalExceptionHandler - Resource not found: Product not found with ID: 99999
  ```
- **QA Action:** Confirms the client requested a non-existent product ID `99999`. The backend safely handled it via `GlobalExceptionHandler` and returned HTTP 404.

---

### Scenario 3: Investigating Cart Stock Violations
- **Log Pattern to Search:** `Adding to cart for user` or `Insufficient stock`
- **Example Log Output:**
  ```text
  2026-09-14 20:04:52 [http-nio-8080-exec-6] INFO  c.s.service.CartService - Adding to cart for user rahul@example.com - productId: 3, quantity: 15
  2026-09-14 20:04:52 [http-nio-8080-exec-6] WARN  c.s.e.GlobalExceptionHandler - Bad request: Cannot add requested quantity. Only 8 items available in stock.
  ```
- **QA Action:** Pinpoints user attempting to add 15 units when the inventory is 8. Returns HTTP 400 with descriptive error payload.

---

### Scenario 4: Investigating Order Placement Failures
- **Log Pattern to Search:** `Placing order for user` or `Order placed successfully`
- **Example Log Output:**
  ```text
  2026-09-14 20:05:02 [http-nio-8080-exec-8] INFO  c.s.service.OrderService - Placing order for user: rahul@example.com
  2026-09-14 20:05:02 [http-nio-8080-exec-8] INFO  c.s.service.OrderService - Order placed successfully with ID: 1 and OrderNumber: SE-1726324502000-4821
  ```
- **QA Action:** Correlates the generated `OrderNumber` with the customer's UI confirmation screen.
