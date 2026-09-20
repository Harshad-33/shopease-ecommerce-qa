from selenium.webdriver.common.by import By
from qa.automation.pages.base_page import BasePage

class LoginPage(BasePage):
    """Page Object for ShopEase Login (/login)."""

    EMAIL_INPUT = (By.ID, "email")
    PASSWORD_INPUT = (By.ID, "password")
    LOGIN_BUTTON = (By.ID, "login-button")
    DEMO_CUSTOMER_BUTTON = (By.ID, "demo-customer-btn")
    DEMO_ADMIN_BUTTON = (By.ID, "demo-admin-btn")
    ERROR_ALERT = (By.CSS_SELECTOR, ".alert-danger")
    USER_NAV_DROPDOWN = (By.CSS_SELECTOR, ".navbar-nav .dropdown-toggle")
    LOGOUT_BUTTON = (By.ID, "logout-button")

    def load(self):
        self.open_url("/login")
        return self

    def login(self, email, password, wait_for_success=False):
        self.type_text(self.EMAIL_INPUT, email)
        self.type_text(self.PASSWORD_INPUT, password)
        self.click(self.LOGIN_BUTTON)
        if wait_for_success:
            self.find_visible(self.USER_NAV_DROPDOWN)

    def click_demo_customer(self):
        self.click(self.DEMO_CUSTOMER_BUTTON)

    def click_demo_admin(self):
        self.click(self.DEMO_ADMIN_BUTTON)

    def get_error_message(self):
        return self.get_text(self.ERROR_ALERT)

    def logout(self):
        self.click(self.USER_NAV_DROPDOWN)
        self.click(self.LOGOUT_BUTTON)
