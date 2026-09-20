import os

class Config:
    BASE_URL = os.getenv("SHOPEASE_URL", "http://localhost:4200")
    TIMEOUT = 10  # Explicit wait seconds
    
    # Customer Test Credentials
    CUSTOMER_EMAIL = "rahul@example.com"
    CUSTOMER_PASSWORD = "Customer@123"
    CUSTOMER_NAME = "Rahul Sharma"
    
    # Admin Test Credentials
    ADMIN_EMAIL = "admin@shopease.com"
    ADMIN_PASSWORD = "Admin@123"
    ADMIN_NAME = "Admin User"
