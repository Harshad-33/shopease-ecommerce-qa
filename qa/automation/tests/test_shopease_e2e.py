import time
import pytest
from selenium.webdriver.common.by import By
from qa.automation.pages.login_page import LoginPage
from qa.automation.pages.register_page import RegisterPage
from qa.automation.pages.products_page import ProductsPage
from qa.automation.pages.cart_page import CartPage
from qa.automation.pages.checkout_page import CheckoutPage
from qa.automation.pages.orders_page import OrdersPage
from qa.automation.pages.admin_page import AdminPage
from qa.automation.utils.config import Config

class TestShopEaseE2E:
    """Comprehensive 15-Workflow End-to-End Automation Suite for ShopEase."""

    # -------------------------------------------------------------
    # 1. Valid Login
    # -------------------------------------------------------------
    def test_01_valid_customer_login(self, driver):
        """Verify successful customer login with valid credentials."""
        login_page = LoginPage(driver).load()
        login_page.login(Config.CUSTOMER_EMAIL, Config.CUSTOMER_PASSWORD)
        
        # Verify navbar greeting contains customer name
        user_badge = login_page.find_visible((By.CSS_SELECTOR, ".navbar-nav .dropdown-toggle"))
        assert Config.CUSTOMER_NAME in user_badge.text, "Customer name not found in navbar after login"

    # -------------------------------------------------------------
    # 2. Invalid Login
    # -------------------------------------------------------------
    def test_02_invalid_login_error(self, driver):
        """Verify error message displayed on invalid password."""
        login_page = LoginPage(driver).load()
        login_page.login(Config.CUSTOMER_EMAIL, "IncorrectPassword123")
        
        error_msg = login_page.get_error_message()
        assert "Invalid email or password" in error_msg, "Error alert message not displayed"

    # -------------------------------------------------------------
    # 3. Registration Validation
    # -------------------------------------------------------------
    def test_03_registration_password_mismatch(self, driver):
        """Verify client-side validation when passwords do not match."""
        register_page = RegisterPage(driver).load()
        register_page.register(
            full_name="Test User",
            email="test.mismatch@example.com",
            phone="9876543210",
            password="Password123",
            confirm_password="DifferentPassword456"
        )
        
        error_msg = register_page.get_error_message()
        assert "Passwords do not match" in error_msg, "Mismatch error message not displayed"

    # -------------------------------------------------------------
    # 4. Product Search
    # -------------------------------------------------------------
    def test_04_product_search(self, driver):
        """Verify search input filters products by keyword."""
        products_page = ProductsPage(driver).load()
        products_page.search_catalog("MacBook")
        time.sleep(1)
        
        assert products_page.get_product_card_count() >= 1, "No products found for 'MacBook'"
        title = products_page.get_text(ProductsPage.FIRST_PRODUCT_TITLE)
        assert "MacBook" in title, f"Search result title '{title}' does not contain 'MacBook'"

    # -------------------------------------------------------------
    # 5. Product Filtering
    # -------------------------------------------------------------
    def test_05_category_filtering(self, driver):
        """Verify category filtering isolates specific category items."""
        products_page = ProductsPage(driver).load()
        products_page.select_category_electronics()
        time.sleep(1)
        
        count = products_page.get_product_card_count()
        assert count == 4, f"Expected 4 electronics products, but found {count}"

    # -------------------------------------------------------------
    # 6. Product Details
    # -------------------------------------------------------------
    def test_06_product_details_view(self, driver):
        """Verify product details page loads full information."""
        products_page = ProductsPage(driver).load()
        products_page.open_first_product()
        
        details_title = products_page.find_visible(ProductsPage.DETAILS_TITLE)
        assert len(details_title.text) > 0, "Product title not loaded on details page"
        assert products_page.is_visible(ProductsPage.ADD_TO_CART_BTN), "Add to Cart button missing"

    # -------------------------------------------------------------
    # 7. Add Product to Cart
    # -------------------------------------------------------------
    def test_07_add_product_to_cart(self, driver):
        """Verify adding an item to the shopping cart as an authenticated user."""
        # Login first
        LoginPage(driver).load().login(Config.CUSTOMER_EMAIL, Config.CUSTOMER_PASSWORD, wait_for_success=True)
        
        products_page = ProductsPage(driver).load()
        products_page.open_first_product()
        toast = products_page.add_to_cart_from_details(quantity_clicks=1)
        
        assert "Added" in toast or "cart" in toast.lower(), "Add to cart toast did not trigger"

    # -------------------------------------------------------------
    # 8. Update Cart Quantity
    # -------------------------------------------------------------
    def test_08_update_cart_quantity(self, driver):
        """Verify quantity increment on the Shopping Cart page."""
        LoginPage(driver).load().login(Config.CUSTOMER_EMAIL, Config.CUSTOMER_PASSWORD, wait_for_success=True)
        
        # Ensure at least 1 item in cart
        products_page = ProductsPage(driver).load()
        products_page.open_first_product()
        products_page.add_to_cart_from_details()
        
        cart_page = CartPage(driver).load().wait_for_items()
        initial_qty = int(cart_page.get_first_item_quantity())
        cart_page.increment_first_item_quantity()
        time.sleep(1)
        
        updated_qty = int(cart_page.get_first_item_quantity())
        assert updated_qty == initial_qty + 1, f"Expected {initial_qty + 1}, got {updated_qty}"

    # -------------------------------------------------------------
    # 9. Remove Cart Item
    # -------------------------------------------------------------
    def test_09_remove_cart_item(self, driver):
        """Verify item removal from shopping cart."""
        LoginPage(driver).load().login(Config.CUSTOMER_EMAIL, Config.CUSTOMER_PASSWORD, wait_for_success=True)
        
        products_page = ProductsPage(driver).load()
        products_page.open_first_product()
        products_page.add_to_cart_from_details()
        
        cart_page = CartPage(driver).load().wait_for_items()
        initial_count = cart_page.get_item_count()
        cart_page.remove_first_item()
        time.sleep(1)
        
        new_count = cart_page.get_item_count()
        assert new_count == initial_count - 1 or new_count == 0, "Item was not removed from cart"

    # -------------------------------------------------------------
    # 10. Checkout Validation
    # -------------------------------------------------------------
    def test_10_checkout_empty_shipping_validation(self, driver):
        """Verify empty shipping address triggers validation error."""
        LoginPage(driver).load().login(Config.CUSTOMER_EMAIL, Config.CUSTOMER_PASSWORD, wait_for_success=True)
        
        # Ensure an item exists
        products_page = ProductsPage(driver).load()
        products_page.open_first_product()
        products_page.add_to_cart_from_details()
        
        checkout_page = CheckoutPage(driver).load()
        checkout_page.find_visible(CheckoutPage.SHIPPING_ADDRESS)
        checkout_page.type_text(CheckoutPage.SHIPPING_ADDRESS, "")
        checkout_page.place_order()
        
        toast = checkout_page.get_toast_message()
        assert "fill" in toast.lower() or "shipping" in toast.lower(), "Empty address validation toast not shown"

    # -------------------------------------------------------------
    # 11. Successful Order Placement
    # -------------------------------------------------------------
    def test_11_successful_order_placement(self, driver):
        """Verify complete end-to-end checkout and order placement with COD."""
        LoginPage(driver).load().login(Config.CUSTOMER_EMAIL, Config.CUSTOMER_PASSWORD, wait_for_success=True)
        
        products_page = ProductsPage(driver).load()
        products_page.open_first_product()
        products_page.add_to_cart_from_details()
        
        checkout_page = CheckoutPage(driver).load()
        checkout_page.find_visible(CheckoutPage.SHIPPING_ADDRESS)
        checkout_page.click_auto_fill_address()
        checkout_page.select_cod()
        checkout_page.place_order()
        time.sleep(2)
        
        orders_page = OrdersPage(driver)
        assert orders_page.is_order_confirmed(), "Order confirmation celebration page was not rendered"

    # -------------------------------------------------------------
    # 12. Order History
    # -------------------------------------------------------------
    def test_12_order_history_display(self, driver):
        """Verify customer order history renders placed orders."""
        LoginPage(driver).load().login(Config.CUSTOMER_EMAIL, Config.CUSTOMER_PASSWORD, wait_for_success=True)
        orders_page = OrdersPage(driver).load()
        orders_page.find_visible(OrdersPage.ORDER_CARDS)
        assert orders_page.get_order_count() >= 1, "Order history does not display placed orders"

    # -------------------------------------------------------------
    # 13. Admin Login & Dashboard
    # -------------------------------------------------------------
    def test_13_admin_login_and_kpis(self, driver):
        """Verify administrator login and dashboard KPI rendering."""
        login_page = LoginPage(driver).load()
        login_page.login(Config.ADMIN_EMAIL, Config.ADMIN_PASSWORD, wait_for_success=True)
        
        admin_page = AdminPage(driver)
        assert admin_page.is_visible(AdminPage.DASHBOARD_TITLE), "Admin Dashboard did not load"
        assert admin_page.get_stat_card_count() == 6, "Expected 6 KPI metric cards on Admin Dashboard"

    # -------------------------------------------------------------
    # 14. Admin Product Operations
    # -------------------------------------------------------------
    def test_14_admin_product_modal(self, driver):
        """Verify Admin can open the Add Product modal in management."""
        LoginPage(driver).load().login(Config.ADMIN_EMAIL, Config.ADMIN_PASSWORD, wait_for_success=True)
        admin_page = AdminPage(driver).load_products()
        admin_page.open_add_product_modal()
        
        assert admin_page.is_visible(AdminPage.PRODUCT_MODAL), "Add Product modal did not open"

    # -------------------------------------------------------------
    # 15. User Logout
    # -------------------------------------------------------------
    def test_15_user_logout(self, driver):
        """Verify customer logout terminates session and returns to login."""
        login_page = LoginPage(driver).load()
        login_page.login(Config.CUSTOMER_EMAIL, Config.CUSTOMER_PASSWORD, wait_for_success=True)
        login_page.logout()
        time.sleep(1)
        
        assert "/login" in driver.current_url, "User not redirected to /login after logout"
        assert login_page.is_visible(LoginPage.LOGIN_BUTTON), "Login button missing after logout"
