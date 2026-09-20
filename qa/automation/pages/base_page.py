from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.common.by import By
from qa.automation.utils.config import Config

class BasePage:
    """Base class for all Page Objects providing reusable explicit waits and interaction helpers."""

    def __init__(self, driver):
        self.driver = driver
        self.wait = WebDriverWait(self.driver, Config.TIMEOUT)

    def open_url(self, path=""):
        full_url = f"{Config.BASE_URL}{path}"
        self.driver.get(full_url)

    def find(self, locator):
        return self.wait.until(EC.presence_of_element_located(locator))

    def find_visible(self, locator):
        return self.wait.until(EC.visibility_of_element_located(locator))

    def find_clickable(self, locator):
        return self.wait.until(EC.element_to_be_clickable(locator))

    def click(self, locator):
        element = self.find_clickable(locator)
        element.click()
        return element

    def type_text(self, locator, text):
        element = self.find_visible(locator)
        element.clear()
        element.send_keys(text)
        return element

    def get_text(self, locator):
        return self.find_visible(locator).text.strip()

    def get_toast_message(self):
        """Captures active bootstrap toast notification text."""
        toast_locator = (By.CSS_SELECTOR, ".toast-body")
        try:
            return self.wait.until(EC.visibility_of_element_located(toast_locator)).text
        except:
            return ""

    def is_visible(self, locator):
        try:
            return self.driver.find_element(*locator).is_displayed()
        except:
            return False
