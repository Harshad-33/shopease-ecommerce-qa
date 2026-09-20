from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import Select
from qa.automation.pages.base_page import BasePage

class ProductsPage(BasePage):
    """Page Object for Products Catalog and Details (/products, /products/:id)."""

    SEARCH_INPUT = (By.ID, "search-input")
    SEARCH_SUBMIT = (By.ID, "search-submit")
    SORT_SELECT = (By.ID, "sort-select")
    PRODUCT_CARDS = (By.CSS_SELECTOR, ".product-card")
    FIRST_PRODUCT_LINK = (By.CSS_SELECTOR, ".product-card a.product-img-container")
    FIRST_PRODUCT_TITLE = (By.CSS_SELECTOR, ".product-card h6 a")
    NAVBAR_SEARCH = (By.ID, "search-product")
    NAVBAR_SEARCH_BTN = (By.ID, "search-button")
    CATEGORY_FILTER_BTN = (By.XPATH, "//button[contains(text(), 'Electronics')]")
    
    # Details Page Selectors
    DETAILS_TITLE = (By.CSS_SELECTOR, "h2.fw-bold")
    QTY_INPUT = (By.ID, "product-qty-input")
    QTY_PLUS_BTN = (By.ID, "qty-plus-btn")
    QTY_MINUS_BTN = (By.ID, "qty-minus-btn")
    ADD_TO_CART_BTN = (By.ID, "add-to-cart-btn")

    def load(self):
        self.open_url("/products")
        return self

    def search_catalog(self, query):
        self.type_text(self.SEARCH_INPUT, query)
        self.click(self.SEARCH_SUBMIT)

    def search_navbar(self, query):
        self.type_text(self.NAVBAR_SEARCH, query)
        self.click(self.NAVBAR_SEARCH_BTN)

    def select_category_electronics(self):
        self.click(self.CATEGORY_FILTER_BTN)

    def sort_by(self, sort_value):
        element = self.find_visible(self.SORT_SELECT)
        Select(element).select_by_value(sort_value)

    def open_first_product(self):
        self.click(self.FIRST_PRODUCT_LINK)

    def get_product_card_count(self):
        return len(self.driver.find_elements(*self.PRODUCT_CARDS))

    def add_to_cart_from_details(self, quantity_clicks=0):
        for _ in range(quantity_clicks):
            self.click(self.QTY_PLUS_BTN)
        self.click(self.ADD_TO_CART_BTN)
        # Explicit wait for toast response ensuring backend persisted the item
        return self.get_toast_message()
