from selenium.webdriver.common.by import By
from qa.automation.pages.base_page import BasePage

class AdminPage(BasePage):
    """Page Object for Admin Dashboard, Product Management, and Order Management."""

    DASHBOARD_TITLE = (By.XPATH, "//h2[contains(text(), 'Admin Dashboard')]")
    STAT_CARDS = (By.CSS_SELECTOR, ".stat-card")
    ADD_PRODUCT_BTN = (By.ID, "add-product-btn")
    PRODUCT_MODAL = (By.CSS_SELECTOR, ".modal-content")
    MODAL_NAME_INPUT = (By.CSS_SELECTOR, ".modal-content input[name='name']")
    MODAL_PRICE_INPUT = (By.CSS_SELECTOR, ".modal-content input[name='price']")
    MODAL_STOCK_INPUT = (By.CSS_SELECTOR, ".modal-content input[name='stockQuantity']")
    MODAL_SAVE_BTN = (By.CSS_SELECTOR, ".modal-content button[type='submit']")
    ORDERS_TABLE_ROWS = (By.CSS_SELECTOR, "table tbody tr")

    def load_dashboard(self):
        self.open_url("/admin/dashboard")
        return self

    def load_products(self):
        self.open_url("/admin/products")
        return self

    def load_orders(self):
        self.open_url("/admin/orders")
        return self

    def get_stat_card_count(self):
        return len(self.driver.find_elements(*self.STAT_CARDS))

    def open_add_product_modal(self):
        self.click(self.ADD_PRODUCT_BTN)

    def fill_new_product(self, name, price, stock):
        self.type_text(self.MODAL_NAME_INPUT, name)
        self.type_text(self.MODAL_PRICE_INPUT, price)
        self.type_text(self.MODAL_STOCK_INPUT, stock)
        self.click(self.MODAL_SAVE_BTN)
