package com.shopease.dto;

import com.shopease.entity.OrderStatus;
import com.shopease.entity.PaymentMethod;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderDto {

    public static class CreateOrderRequest {
        @NotBlank(message = "Shipping full name is required")
        private String shippingFullName;

        @NotBlank(message = "Shipping address is required")
        private String shippingAddress;

        @NotBlank(message = "City is required")
        private String shippingCity;

        @NotBlank(message = "State is required")
        private String shippingState;

        @NotBlank(message = "Pincode is required")
        private String shippingPincode;

        @NotBlank(message = "Phone number is required")
        private String shippingPhone;

        @NotNull(message = "Payment method is required")
        private PaymentMethod paymentMethod;

        public CreateOrderRequest() {}

        public String getShippingFullName() { return shippingFullName; }
        public void setShippingFullName(String shippingFullName) { this.shippingFullName = shippingFullName; }
        public String getShippingAddress() { return shippingAddress; }
        public void setShippingAddress(String shippingAddress) { this.shippingAddress = shippingAddress; }
        public String getShippingCity() { return shippingCity; }
        public void setShippingCity(String shippingCity) { this.shippingCity = shippingCity; }
        public String getShippingState() { return shippingState; }
        public void setShippingState(String shippingState) { this.shippingState = shippingState; }
        public String getShippingPincode() { return shippingPincode; }
        public void setShippingPincode(String shippingPincode) { this.shippingPincode = shippingPincode; }
        public String getShippingPhone() { return shippingPhone; }
        public void setShippingPhone(String shippingPhone) { this.shippingPhone = shippingPhone; }
        public PaymentMethod getPaymentMethod() { return paymentMethod; }
        public void setPaymentMethod(PaymentMethod paymentMethod) { this.paymentMethod = paymentMethod; }
    }

    public static class OrderItemResponse {
        private Long id;
        private Long productId;
        private String productName;
        private String productImageUrl;
        private Integer quantity;
        private BigDecimal unitPrice;
        private BigDecimal subtotal;

        public OrderItemResponse() {}

        public OrderItemResponse(Long id, Long productId, String productName, String productImageUrl,
                                 Integer quantity, BigDecimal unitPrice, BigDecimal subtotal) {
            this.id = id;
            this.productId = productId;
            this.productName = productName;
            this.productImageUrl = productImageUrl;
            this.quantity = quantity;
            this.unitPrice = unitPrice;
            this.subtotal = subtotal;
        }

        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public Long getProductId() { return productId; }
        public void setProductId(Long productId) { this.productId = productId; }
        public String getProductName() { return productName; }
        public void setProductName(String productName) { this.productName = productName; }
        public String getProductImageUrl() { return productImageUrl; }
        public void setProductImageUrl(String productImageUrl) { this.productImageUrl = productImageUrl; }
        public Integer getQuantity() { return quantity; }
        public void setQuantity(Integer quantity) { this.quantity = quantity; }
        public BigDecimal getUnitPrice() { return unitPrice; }
        public void setUnitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; }
        public BigDecimal getSubtotal() { return subtotal; }
        public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }
    }

    public static class OrderResponse {
        private Long id;
        private String orderNumber;
        private LocalDateTime orderDate;
        private BigDecimal totalAmount;
        private OrderStatus status;
        private PaymentMethod paymentMethod;
        private String shippingFullName;
        private String shippingAddress;
        private String shippingCity;
        private String shippingState;
        private String shippingPincode;
        private String shippingPhone;
        private String customerName;
        private String customerEmail;
        private List<OrderItemResponse> items = new ArrayList<>();

        public OrderResponse() {}

        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public String getOrderNumber() { return orderNumber; }
        public void setOrderNumber(String orderNumber) { this.orderNumber = orderNumber; }
        public LocalDateTime getOrderDate() { return orderDate; }
        public void setOrderDate(LocalDateTime orderDate) { this.orderDate = orderDate; }
        public BigDecimal getTotalAmount() { return totalAmount; }
        public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }
        public OrderStatus getStatus() { return status; }
        public void setStatus(OrderStatus status) { this.status = status; }
        public PaymentMethod getPaymentMethod() { return paymentMethod; }
        public void setPaymentMethod(PaymentMethod paymentMethod) { this.paymentMethod = paymentMethod; }
        public String getShippingFullName() { return shippingFullName; }
        public void setShippingFullName(String shippingFullName) { this.shippingFullName = shippingFullName; }
        public String getShippingAddress() { return shippingAddress; }
        public void setShippingAddress(String shippingAddress) { this.shippingAddress = shippingAddress; }
        public String getShippingCity() { return shippingCity; }
        public void setShippingCity(String shippingCity) { this.shippingCity = shippingCity; }
        public String getShippingState() { return shippingState; }
        public void setShippingState(String shippingState) { this.shippingState = shippingState; }
        public String getShippingPincode() { return shippingPincode; }
        public void setShippingPincode(String shippingPincode) { this.shippingPincode = shippingPincode; }
        public String getShippingPhone() { return shippingPhone; }
        public void setShippingPhone(String shippingPhone) { this.shippingPhone = shippingPhone; }
        public String getCustomerName() { return customerName; }
        public void setCustomerName(String customerName) { this.customerName = customerName; }
        public String getCustomerEmail() { return customerEmail; }
        public void setCustomerEmail(String customerEmail) { this.customerEmail = customerEmail; }
        public List<OrderItemResponse> getItems() { return items; }
        public void setItems(List<OrderItemResponse> items) { this.items = items; }
    }

    public static class OrderStatusUpdateRequest {
        @NotNull(message = "Order status is required")
        private OrderStatus status;

        public OrderStatusUpdateRequest() {}

        public OrderStatusUpdateRequest(OrderStatus status) {
            this.status = status;
        }

        public OrderStatus getStatus() { return status; }
        public void setStatus(OrderStatus status) { this.status = status; }
    }
}
