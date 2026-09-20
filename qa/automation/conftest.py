import os
import pytest
from selenium import webdriver
from selenium.webdriver.chrome.options import Options

def pytest_addoption(parser):
    parser.addoption(
        "--headed",
        action="store_true",
        default=False,
        help="Run browser in visible (headed) mode"
    )

@pytest.fixture(scope="function")
def driver(request):
    chrome_options = Options()
    
    # Headless by default for fast, reliable CI execution
    if not request.config.getoption("--headed"):
        chrome_options.add_argument("--headless=new")
        
    chrome_options.add_argument("--no-sandbox")
    chrome_options.add_argument("--disable-dev-shm-usage")
    chrome_options.add_argument("--disable-gpu")
    chrome_options.add_argument("--window-size=1920,1080")
    
    driver = webdriver.Chrome(options=chrome_options)
    driver.implicitly_wait(3)
    
    yield driver
    
    driver.quit()

def pytest_html_report_title(report):
    report.title = "ShopEase E-Commerce Automated Test Report"
