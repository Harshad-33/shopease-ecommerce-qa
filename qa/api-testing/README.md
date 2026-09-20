# ShopEase REST API Testing Guide

This directory contains the automated REST API testing suite for the ShopEase Spring Boot backend.

---

## Files Included

1. **`ShopEase_Postman_Collection.json`**: The complete Postman test collection with positive tests, negative validation tests, boundary limits, and dynamic JWT token chaining scripts.
2. **`ShopEase_Environment.json`**: Pre-configured environment variables for local testing (`http://localhost:8080/api`).

---

## How to Import & Run in Postman GUI

1. Open **Postman Desktop Client** (or Postman Web).
2. Click **Import** in the top-left corner.
3. Drag and drop both:
   - `ShopEase_Postman_Collection.json`
   - `ShopEase_Environment.json`
4. Select **ShopEase Local Environment** from the active environment dropdown (top right).
5. Open the collection **"ShopEase REST API Test Suite"**.
6. Click **Run Collection**.
7. Ensure all requests are selected and click **Run ShopEase REST API Test Suite**.
8. Observe all assertions passing with green checkmarks!

---

## Automated Token Chaining Logic

The collection uses Postman `pm.test` scripts to automatically capture tokens:
```javascript
// Postman Tests Tab (Customer Login):
var json = pm.response.json();
pm.collectionVariables.set('customerToken', json.token);
```
Subsequent requests in the Cart, Orders, and Auth modules automatically inherit this token via the header:
```text
Authorization: Bearer {{customerToken}}
```

---

## Running Headless via Newman CLI (Optional for CI/CD)

If you have Node.js installed, you can run the collection directly from the command line using Newman:

```powershell
# Install newman globally
npm install -g newman

# Execute collection with HTML or console reporting
newman run qa/api-testing/ShopEase_Postman_Collection.json -e qa/api-testing/ShopEase_Environment.json
```
