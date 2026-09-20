from selenium.webdriver.common.by import By
from qa.automation.pages.base_page import BasePage

class CheckoutPage(BasePage):
    """Page Object for Checkout and Payment (/checkout)."""

    SHIPPING_NAME = (By.ID, "shipping-name")
    SHIPPING_PHONE = (By.ID, "shipping-phone")
    SHIPPING_ADDRESS = (By.ID, "shipping-address")
    SHIPPING_CITY = (By.ID, "shipping-city")
    SHIPPING_STATE = (By.ID, "shipping-state")
    SHIPPING_PINCODE = (By.ID, "shipping-pincode")
    AUTO_FILL_BTN = (By.XPATH, "//button[contains(text(), 'Auto-fill Demo Address')]")
    PAYMENT_COD = (By.ID, "payment-cod")
    PAYMENT_CARD = (By.ID, "payment-card")
    PLACE_ORDER_BUTTON = (By.ID, "place-order-button")

    def load(self):
        self.open_url("/checkout")
        return self

    def fill_shipping_address(self, name, phone, address, city, state, pincode):
        self.type_text(self.SHIPPING_NAME, name)
        self.type_text(self.SHIPPING_PHONE, phone)
        self.type_text(self.SHIPPING_ADDRESS, address)
        self.type_text(self.SHIPPING_CITY, city)
        self.type_text(self.SHIPPING_STATE, state)
        self.type_text(self.SHIPPING_PINCODE, pincode)

    def click_auto_fill_address(self):
        self.click(self.AUTO_FILL_BTN)

    def select_cod(self):
        self.click(self.PAYMENT_COD)

    def select_card(self):
        self.click(self.PAYMENT_CARD)

    def place_order(self):
        self.click(self.PLACE_ORDER_BUTTON)
