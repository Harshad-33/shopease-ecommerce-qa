from selenium.webdriver.common.by import By
from qa.automation.pages.base_page import BasePage

class OrdersPage(BasePage):
    """Page Object for Order Confirmation and Order History (/order-confirmation, /orders)."""

    CONFIRMATION_TITLE = (By.XPATH, "//h2[contains(text(), 'Thank You For Your Order!')]")
    ORDER_NUMBER_TEXT = (By.CSS_SELECTOR, ".font-monospace")
    ORDER_CARDS = (By.CSS_SELECTOR, ".order-card")
    VIEW_DETAILS_BTN = (By.XPATH, "//a[contains(text(), 'View Order Details')]")
    STATUS_TRACKER = (By.CSS_SELECTOR, ".status-tracker")

    def load(self):
        self.open_url("/orders")
        return self

    def is_order_confirmed(self):
        return self.is_visible(self.CONFIRMATION_TITLE)

    def get_order_count(self):
        return len(self.driver.find_elements(*self.ORDER_CARDS))

    def view_first_order_details(self):
        self.click(self.VIEW_DETAILS_BTN)
