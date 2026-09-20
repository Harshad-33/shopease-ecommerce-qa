from selenium.webdriver.common.by import By
from qa.automation.pages.base_page import BasePage

class CartPage(BasePage):
    """Page Object for Shopping Cart (/cart)."""

    CART_LINK = (By.ID, "cart-link")
    CART_BADGE = (By.ID, "cart-count-badge")
    CHECKOUT_BUTTON = (By.ID, "checkout-button")
    CLEAR_CART_BUTTON = (By.ID, "clear-cart-btn")
    CART_ROWS = (By.CSS_SELECTOR, "tr.cart-item-row")
    FIRST_ROW_PLUS_BTN = (By.CSS_SELECTOR, "tr.cart-item-row .quantity-btn-group button:last-child")
    FIRST_ROW_QTY_TEXT = (By.CSS_SELECTOR, "tr.cart-item-row .quantity-btn-group span")
    FIRST_ROW_DELETE_BTN = (By.CSS_SELECTOR, "tr.cart-item-row button.btn-link")
    EMPTY_CART_TITLE = (By.CSS_SELECTOR, "h4.fw-bold")

    def load(self):
        self.open_url("/cart")
        return self

    def wait_for_items(self):
        self.find_visible(self.CART_ROWS)
        return self

    def get_cart_badge_count(self):
        try:
            return self.get_text(self.CART_BADGE)
        except:
            return "0"

    def get_item_count(self):
        return len(self.driver.find_elements(*self.CART_ROWS))

    def increment_first_item_quantity(self):
        self.click(self.FIRST_ROW_PLUS_BTN)

    def get_first_item_quantity(self):
        return self.get_text(self.FIRST_ROW_QTY_TEXT)

    def remove_first_item(self):
        self.click(self.FIRST_ROW_DELETE_BTN)

    def proceed_to_checkout(self):
        self.click(self.CHECKOUT_BUTTON)
