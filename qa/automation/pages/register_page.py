from selenium.webdriver.common.by import By
from qa.automation.pages.base_page import BasePage

class RegisterPage(BasePage):
    """Page Object for ShopEase Registration (/register)."""

    FULL_NAME_INPUT = (By.ID, "full-name")
    EMAIL_INPUT = (By.ID, "email")
    PHONE_INPUT = (By.ID, "phone")
    PASSWORD_INPUT = (By.ID, "password")
    CONFIRM_PASSWORD_INPUT = (By.ID, "confirm-password")
    REGISTER_BUTTON = (By.ID, "register-button")
    ERROR_ALERT = (By.CSS_SELECTOR, ".alert-danger")

    def load(self):
        self.open_url("/register")
        return self

    def register(self, full_name, email, phone, password, confirm_password):
        self.type_text(self.FULL_NAME_INPUT, full_name)
        self.type_text(self.EMAIL_INPUT, email)
        self.type_text(self.PHONE_INPUT, phone)
        self.type_text(self.PASSWORD_INPUT, password)
        self.type_text(self.CONFIRM_PASSWORD_INPUT, confirm_password)
        self.click(self.REGISTER_BUTTON)

    def get_error_message(self):
        return self.get_text(self.ERROR_ALERT)
