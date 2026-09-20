package com.shopease.config;

import com.shopease.entity.*;
import com.shopease.repository.CategoryRepository;
import com.shopease.repository.ProductRepository;
import com.shopease.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 * DataInitializer - Seeds the database with demo accounts, categories, and 20 sample products.
 * This runs automatically upon application startup if the database is unpopulated.
 */
@Component
public class DataInitializer implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (userRepository.count() == 0) {
            log.info("Populating database with initial sample data for ShopEase...");
            seedUsers();
            seedCategoriesAndProducts();
            log.info("Database seeding completed successfully.");
        } else {
            log.info("Database already contains data. Skipping initial seeding.");
        }
    }

    private void seedUsers() {
        // 1 Admin account
        User admin = new User(
                "Admin User",
                "admin@shopease.com",
                passwordEncoder.encode("Admin@123"),
                "9876543210",
                Role.ADMIN
        );
        userRepository.save(admin);

        // 4 Demo Customer accounts
        List<User> customers = Arrays.asList(
                new User("Rahul Sharma", "rahul@example.com", passwordEncoder.encode("Customer@123"), "9876500001", Role.CUSTOMER),
                new User("Priya Patel", "priya@example.com", passwordEncoder.encode("Customer@123"), "9876500002", Role.CUSTOMER),
                new User("Amit Verma", "amit@example.com", passwordEncoder.encode("Customer@123"), "9876500003", Role.CUSTOMER),
                new User("Sneha Reddy", "sneha@example.com", passwordEncoder.encode("Customer@123"), "9876500004", Role.CUSTOMER)
        );
        userRepository.saveAll(customers);
        log.info("Seeded 1 Admin and 4 Customer accounts.");
    }

    private void seedCategoriesAndProducts() {
        // 6 Categories
        Category electronics = categoryRepository.save(new Category(
                "Electronics",
                "Gadgets, laptops, smartphones, audio devices, and computer peripherals.",
                "https://images.unsplash.com/photo-1498049794561-7780e7231661?w=500&auto=format&fit=crop&q=60"
        ));

        Category clothing = categoryRepository.save(new Category(
                "Clothing",
                "Trendy fashion apparel for men and women.",
                "https://images.unsplash.com/photo-1441986300917-64674bd600d8?w=500&auto=format&fit=crop&q=60"
        ));

        Category shoes = categoryRepository.save(new Category(
                "Shoes",
                "Sports sneakers, running shoes, and outdoor boots.",
                "https://images.unsplash.com/photo-1542291026-7eec264c27ff?w=500&auto=format&fit=crop&q=60"
        ));

        Category books = categoryRepository.save(new Category(
                "Books",
                "Bestselling non-fiction, self-help, and computer programming books.",
                "https://images.unsplash.com/photo-1512820790803-83ca734da794?w=500&auto=format&fit=crop&q=60"
        ));

        Category accessories = categoryRepository.save(new Category(
                "Accessories",
                "Smartwatches, sunglasses, leather wallets, and daily carry items.",
                "https://images.unsplash.com/photo-1523275335684-37898b6baf30?w=500&auto=format&fit=crop&q=60"
        ));

        Category homeKitchen = categoryRepository.save(new Category(
                "Home & Kitchen",
                "Modern kitchen appliances, coffee makers, and home essentials.",
                "https://images.unsplash.com/photo-1556911220-e15b29be8c8f?w=500&auto=format&fit=crop&q=60"
        ));

        // 20 Sample Products
        List<Product> products = Arrays.asList(
                // Electronics
                new Product(
                        "Apple MacBook Air M2",
                        "13.6-inch Liquid Retina Display, 8GB Unified Memory, 256GB SSD Storage, Midnight color with MagSafe charging.",
                        new BigDecimal("92999.00"),
                        12,
                        "https://images.unsplash.com/photo-1517336714731-489689fd1ca8?w=600&auto=format&fit=crop&q=60",
                        electronics,
                        true
                ),
                new Product(
                        "Sony WH-1000XM5 Wireless Headphones",
                        "Industry-leading noise cancellation with two processors and 8 microphones, 30-hour battery life, and crystal clear hands-free calling.",
                        new BigDecimal("26990.00"),
                        18,
                        "https://images.unsplash.com/photo-1505740420928-5e560c06d30e?w=600&auto=format&fit=crop&q=60",
                        electronics,
                        true
                ),
                new Product(
                        "Samsung Galaxy S24 Ultra",
                        "Titanium Gray, 256GB, 200MP Camera with Galaxy AI, S-Pen included, Snapdragon 8 Gen 3 processor.",
                        new BigDecimal("109999.00"),
                        8,
                        "https://images.unsplash.com/photo-1598327105666-5b89351aff97?w=600&auto=format&fit=crop&q=60",
                        electronics,
                        true
                ),
                new Product(
                        "Logitech MX Master 3S Mouse",
                        "Performance wireless mouse with 8K DPI tracking on glass, quiet clicks, and ultra-fast MagSpeed electromagnetic scrolling.",
                        new BigDecimal("8495.00"),
                        25,
                        "https://images.unsplash.com/photo-1527864550417-7fd91fc51a46?w=600&auto=format&fit=crop&q=60",
                        electronics,
                        true
                ),

                // Clothing
                new Product(
                        "Men's Slim-Fit Cotton Casual Shirt",
                        "100% breathable combed cotton, button-down collar, comfortable fit for both business casual and weekend wear.",
                        new BigDecimal("1499.00"),
                        40,
                        "https://images.unsplash.com/photo-1596755094514-f87e34085b2c?w=600&auto=format&fit=crop&q=60",
                        clothing,
                        true
                ),
                new Product(
                        "Women's Pure Linen Oversized Blazer",
                        "Lightweight natural linen blend blazer with notch lapels, flap pockets, and relaxed tailoring for all-season layering.",
                        new BigDecimal("3299.00"),
                        15,
                        "https://images.unsplash.com/photo-1584273143981-41c073dfe8f8?w=600&auto=format&fit=crop&q=60",
                        clothing,
                        true
                ),
                new Product(
                        "Classic Crewneck Organic Cotton T-Shirt",
                        "Pre-shrunk organic cotton unisex t-shirt featuring reinforced stitching and high durability.",
                        new BigDecimal("799.00"),
                        60,
                        "https://images.unsplash.com/photo-1521572267360-ee0c2909d518?w=600&auto=format&fit=crop&q=60",
                        clothing,
                        true
                ),
                new Product(
                        "High-Waist Stretch Denim Jeans",
                        "Premium cotton-elastane blend denim offering all-day flexibility, classic 5-pocket styling, and fade resistance.",
                        new BigDecimal("2199.00"),
                        30,
                        "https://images.unsplash.com/photo-1541099649105-f69ad21f3246?w=600&auto=format&fit=crop&q=60",
                        clothing,
                        true
                ),

                // Shoes
                new Product(
                        "Nike Air Max 270 Sneakers",
                        "Large Max Air unit delivers responsive cushioning with a lightweight mesh upper for superior breathability.",
                        new BigDecimal("11495.00"),
                        14,
                        "https://images.unsplash.com/photo-1542291026-7eec264c27ff?w=600&auto=format&fit=crop&q=60",
                        shoes,
                        true
                ),
                new Product(
                        "Adidas Ultraboost Light Running Shoes",
                        "Light BOOST foam cushioning for energy return, Continental rubber outsole for wet and dry traction.",
                        new BigDecimal("14999.00"),
                        10,
                        "https://images.unsplash.com/photo-1584735935682-2f2b69dff9d2?w=600&auto=format&fit=crop&q=60",
                        shoes,
                        true
                ),
                new Product(
                        "Woodland Waterproof Leather Trekking Boots",
                        "Heavy-duty nubuck leather outdoor boots with deep-lug rubber sole for maximum grip on rugged terrain.",
                        new BigDecimal("4295.00"),
                        20,
                        "https://images.unsplash.com/photo-1520639888713-7851133b1ed0?w=600&auto=format&fit=crop&q=60",
                        shoes,
                        true
                ),

                // Books
                new Product(
                        "Atomic Habits by James Clear",
                        "An Easy & Proven Way to Build Good Habits & Break Bad Ones. Over 15 million copies sold worldwide.",
                        new BigDecimal("499.00"),
                        50,
                        "https://images.unsplash.com/photo-1544716278-ca5e3f4abd8c?w=600&auto=format&fit=crop&q=60",
                        books,
                        true
                ),
                new Product(
                        "Clean Code by Robert C. Martin",
                        "A Handbook of Agile Software Craftsmanship. Essential reading for developers wanting to write readable, maintainable software.",
                        new BigDecimal("2199.00"),
                        22,
                        "https://images.unsplash.com/photo-1532012164546-f432f2e3777f?w=600&auto=format&fit=crop&q=60",
                        books,
                        true
                ),
                new Product(
                        "The Psychology of Money by Morgan Housel",
                        "Timeless lessons on wealth, greed, and happiness doing well with money is about how you behave.",
                        new BigDecimal("399.00"),
                        45,
                        "https://images.unsplash.com/photo-1589829085413-56de8ae18c73?w=600&auto=format&fit=crop&q=60",
                        books,
                        true
                ),

                // Accessories
                new Product(
                        "Fossil Gen 6 Smartwatch",
                        "Stainless steel smartwatch powered by Wear OS with SpO2 sensor, heart rate tracking, and rapid magnetic charging.",
                        new BigDecimal("18495.00"),
                        16,
                        "https://images.unsplash.com/photo-1523275335684-37898b6baf30?w=600&auto=format&fit=crop&q=60",
                        accessories,
                        true
                ),
                new Product(
                        "Ray-Ban Aviator Classic Sunglasses",
                        "Timeless gold metal frame with G-15 polarized green glass lenses offering 100% UV protection.",
                        new BigDecimal("7890.00"),
                        20,
                        "https://images.unsplash.com/photo-1511499767150-a48a237f0083?w=600&auto=format&fit=crop&q=60",
                        accessories,
                        true
                ),
                new Product(
                        "Bellroy Premium Leather Slim Wallet",
                        "Bifold slim wallet crafted from environmentally certified leather, holds 4-12 cards with RFID protection.",
                        new BigDecimal("5499.00"),
                        35,
                        "https://images.unsplash.com/photo-1627123424574-724758594e93?w=600&auto=format&fit=crop&q=60",
                        accessories,
                        true
                ),

                // Home & Kitchen
                new Product(
                        "Nespresso Inissia Espresso Maker",
                        "Compact espresso machine with 19-bar high-pressure pump and 25-second rapid preheating for rich crema.",
                        new BigDecimal("14500.00"),
                        9,
                        "https://images.unsplash.com/photo-1517668808822-9ebb02f2a0e6?w=600&auto=format&fit=crop&q=60",
                        homeKitchen,
                        true
                ),
                new Product(
                        "Philips Digital Air Fryer XL",
                        "Rapid Air Technology for 90% less fat cooking, digital touch screen with 7 presets, 1.2 kg capacity.",
                        new BigDecimal("8999.00"),
                        15,
                        "https://images.unsplash.com/photo-1585515320310-259814833e62?w=600&auto=format&fit=crop&q=60",
                        homeKitchen,
                        true
                ),
                new Product(
                        "Milton Double-Wall Stainless Steel Flask",
                        "1000ml vacuum insulated water bottle keeping beverages hot or cold for 24 hours, leak-proof and BPA-free.",
                        new BigDecimal("999.00"),
                        40,
                        "https://images.unsplash.com/photo-1602143407151-7111542de6e8?w=600&auto=format&fit=crop&q=60",
                        homeKitchen,
                        true
                )
        );

        productRepository.saveAll(products);
        log.info("Seeded 6 Categories and {} Sample Products.", products.size());
    }
}
