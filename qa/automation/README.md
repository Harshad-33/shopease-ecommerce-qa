# ShopEase Python Selenium & PyTest Automation Suite

This directory houses the beginner-friendly, production-style automated UI testing framework for ShopEase.

---

## Framework Design

- **Language:** Python 3.14+
- **Runner:** PyTest 8+
- **Driver:** Selenium WebDriver 4+
- **Architecture:** Page Object Model (POM)
- **Reporting:** `pytest-html` generating interactive standalone HTML execution reports

---

## Directory Organization

```text
qa/automation/
├── conftest.py          # PyTest browser fixtures, headless configuration, and hooks
├── requirements.txt     # selenium, pytest, pytest-html, openpyxl
├── utils/
│   └── config.py        # URLs, timeouts, and test account credentials
├── pages/               # Page Object Model encapsulation
│   ├── base_page.py     # Common explicit wait wrappers (click, type, get_text)
│   ├── login_page.py
│   ├── register_page.py
│   ├── products_page.py
│   ├── cart_page.py
│   ├── checkout_page.py
│   ├── orders_page.py
│   └── admin_page.py
└── tests/
    └── test_shopease_e2e.py  # 15 Core Workflows automated end-to-end
```

---

## Setup & Prerequisites

Make sure the ShopEase frontend (`http://localhost:4200`) and backend (`http://localhost:8080`) are actively running.

1. **Install Dependencies:**
   ```powershell
   pip install -r qa/automation/requirements.txt
   ```

---

## Execution Commands

### 1. Run in Headless Mode (Fastest, Default)
```powershell
python -m pytest "F:\TestingProject\ShopEase\qa\automation\tests" -v
```

### 2. Run with Headed Browser (Visible Window for Demos/Viva)
```powershell
python -m pytest "F:\TestingProject\ShopEase\qa\automation\tests" --headed -v
```

### 3. Generate Official HTML Automation Report
```powershell
python -m pytest "F:\TestingProject\ShopEase\qa\automation\tests" --html="F:\TestingProject\ShopEase\qa\reports\automation_report.html" --self-contained-html -v
```

---

## 15 Automated Workflows Covered

1. `test_01_valid_customer_login`: Customer login with credentials and greeting check.
2. `test_02_invalid_login_error`: Wrong password validation and error toast display.
3. `test_03_registration_password_mismatch`: Mismatched password client-side validation.
4. `test_04_product_search`: Search keyword filtering in catalog.
5. `test_05_category_filtering`: Category chip selection and count check.
6. `test_06_product_details_view`: Product image, description, and price loading.
7. `test_07_add_product_to_cart`: Quantity selection, add trigger, and badge increment.
8. `test_08_update_cart_quantity`: Cart increment button and real-time total recalculation.
9. `test_09_remove_cart_item`: Cart row deletion and item count decrement.
10. `test_10_checkout_empty_shipping_validation`: Mandatory shipping field check.
11. `test_11_successful_order_placement`: Auto-fill address, COD selection, and order placement.
12. `test_12_order_history_display`: Order history listing verified with latest order.
13. `test_13_admin_login_and_kpis`: Admin authentication and 6 KPI cards rendering.
14. `test_14_admin_product_modal`: Admin Add Product modal launch and form display.
15. `test_15_user_logout`: Token purge, session clearing, and redirection to login.
