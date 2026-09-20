package com.shopease.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CartDto {

    public static class AddToCartRequest {
        @NotNull(message = "Product ID is required")
        private Long productId;

        @NotNull(message = "Quantity is required")
        @Min(value = 1, message = "Quantity must be at least 1")
        private Integer quantity = 1;

        public AddToCartRequest() {}

        public AddToCartRequest(Long productId, Integer quantity) {
            this.productId = productId;
            this.quantity = quantity;
        }

        public Long getProductId() { return productId; }
        public void setProductId(Long productId) { this.productId = productId; }
        public Integer getQuantity() { return quantity; }
        public void setQuantity(Integer quantity) { this.quantity = quantity; }
    }

    public static class UpdateCartItemRequest {
        @NotNull(message = "Quantity is required")
        @Min(value = 1, message = "Quantity must be at least 1")
        private Integer quantity;

        public UpdateCartItemRequest() {}

        public UpdateCartItemRequest(Integer quantity) {
            this.quantity = quantity;
        }

        public Integer getQuantity() { return quantity; }
        public void setQuantity(Integer quantity) { this.quantity = quantity; }
    }

    public static class CartItemResponse {
        private Long id;
        private Long productId;
        private String productName;
        private String productImageUrl;
        private BigDecimal unitPrice;
        private Integer quantity;
        private BigDecimal subtotal;
        private Integer stockQuantity;

        public CartItemResponse() {}

        public CartItemResponse(Long id, Long productId, String productName, String productImageUrl,
                                BigDecimal unitPrice, Integer quantity, BigDecimal subtotal, Integer stockQuantity) {
            this.id = id;
            this.productId = productId;
            this.productName = productName;
            this.productImageUrl = productImageUrl;
            this.unitPrice = unitPrice;
            this.quantity = quantity;
            this.subtotal = subtotal;
            this.stockQuantity = stockQuantity;
        }

        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public Long getProductId() { return productId; }
        public void setProductId(Long productId) { this.productId = productId; }
        public String getProductName() { return productName; }
        public void setProductName(String productName) { this.productName = productName; }
        public String getProductImageUrl() { return productImageUrl; }
        public void setProductImageUrl(String productImageUrl) { this.productImageUrl = productImageUrl; }
        public BigDecimal getUnitPrice() { return unitPrice; }
        public void setUnitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; }
        public Integer getQuantity() { return quantity; }
        public void setQuantity(Integer quantity) { this.quantity = quantity; }
        public BigDecimal getSubtotal() { return subtotal; }
        public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }
        public Integer getStockQuantity() { return stockQuantity; }
        public void setStockQuantity(Integer stockQuantity) { this.stockQuantity = stockQuantity; }
    }

    public static class CartResponse {
        private Long id;
        private List<CartItemResponse> items = new ArrayList<>();
        private BigDecimal totalAmount = BigDecimal.ZERO;
        private Integer totalItems = 0;

        public CartResponse() {}

        public CartResponse(Long id, List<CartItemResponse> items, BigDecimal totalAmount, Integer totalItems) {
            this.id = id;
            this.items = items;
            this.totalAmount = totalAmount;
            this.totalItems = totalItems;
        }

        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public List<CartItemResponse> getItems() { return items; }
        public void setItems(List<CartItemResponse> items) { this.items = items; }
        public BigDecimal getTotalAmount() { return totalAmount; }
        public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }
        public Integer getTotalItems() { return totalItems; }
        public void setTotalItems(Integer totalItems) { this.totalItems = totalItems; }
    }
}
