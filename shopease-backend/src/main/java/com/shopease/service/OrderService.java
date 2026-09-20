package com.shopease.service;

import com.shopease.dto.OrderDto.*;
import com.shopease.entity.*;
import com.shopease.exception.BadRequestException;
import com.shopease.exception.ResourceNotFoundException;
import com.shopease.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public OrderResponse placeOrder(String email, CreateOrderRequest request) {
        log.info("Placing order for user: {}", email);

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));

        Cart cart = cartRepository.findByUserId(user.getId())
                .orElseThrow(() -> new BadRequestException("No shopping cart found for user."));

        if (cart.getItems().isEmpty()) {
            throw new BadRequestException("Your shopping cart is empty. Please add products before checking out.");
        }

        // Validate stock availability for all items first
        for (CartItem item : cart.getItems()) {
            Product product = item.getProduct();
            if (Boolean.FALSE.equals(product.getActive())) {
                throw new BadRequestException("Product '" + product.getName() + "' is no longer available.");
            }
            if (product.getStockQuantity() < item.getQuantity()) {
                throw new BadRequestException("Insufficient stock for '" + product.getName() + "'. Available: " + product.getStockQuantity());
            }
        }

        // Create Order
        Order order = new Order();
        String orderNumber = "SE-" + System.currentTimeMillis() + "-" + (new Random().nextInt(9000) + 1000);
        order.setOrderNumber(orderNumber);
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.PLACED);
        order.setPaymentMethod(request.getPaymentMethod());
        order.setShippingFullName(request.getShippingFullName().trim());
        order.setShippingAddress(request.getShippingAddress().trim());
        order.setShippingCity(request.getShippingCity().trim());
        order.setShippingState(request.getShippingState().trim());
        order.setShippingPincode(request.getShippingPincode().trim());
        order.setShippingPhone(request.getShippingPhone().trim());

        BigDecimal totalAmount = BigDecimal.ZERO;
        List<OrderItem> orderItems = new ArrayList<>();

        // Deduct stock and build order items
        for (CartItem item : cart.getItems()) {
            Product product = item.getProduct();

            // Deduct stock
            int updatedStock = product.getStockQuantity() - item.getQuantity();
            product.setStockQuantity(updatedStock);
            productRepository.save(product);

            BigDecimal itemSubtotal = product.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
            totalAmount = totalAmount.add(itemSubtotal);

            OrderItem orderItem = new OrderItem(
                    order,
                    product,
                    item.getQuantity(),
                    product.getPrice(),
                    itemSubtotal
            );
            orderItems.add(orderItem);
        }

        order.setTotalAmount(totalAmount);
        order.setItems(orderItems);

        Order savedOrder = orderRepository.save(order);

        // Clear user's cart after successful order creation
        cartItemRepository.deleteByCartId(cart.getId());
        cart.getItems().clear();
        cart.setUpdatedAt(LocalDateTime.now());
        cartRepository.save(cart);

        log.info("Order placed successfully with ID: {} and OrderNumber: {}", savedOrder.getId(), savedOrder.getOrderNumber());
        return mapToOrderResponse(savedOrder);
    }

    public List<OrderResponse> getUserOrders(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));

        return orderRepository.findByUserIdOrderByOrderDateDesc(user.getId()).stream()
                .map(this::mapToOrderResponse)
                .collect(Collectors.toList());
    }

    public OrderResponse getUserOrderById(String email, Long orderId) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with ID: " + orderId));

        if (!order.getUser().getId().equals(user.getId()) && user.getRole() != Role.ADMIN) {
            throw new BadRequestException("Unauthorized access to this order.");
        }

        return mapToOrderResponse(order);
    }

    public OrderResponse mapToOrderResponse(Order order) {
        OrderResponse response = new OrderResponse();
        response.setId(order.getId());
        response.setOrderNumber(order.getOrderNumber());
        response.setOrderDate(order.getOrderDate());
        response.setTotalAmount(order.getTotalAmount());
        response.setStatus(order.getStatus());
        response.setPaymentMethod(order.getPaymentMethod());
        response.setShippingFullName(order.getShippingFullName());
        response.setShippingAddress(order.getShippingAddress());
        response.setShippingCity(order.getShippingCity());
        response.setShippingState(order.getShippingState());
        response.setShippingPincode(order.getShippingPincode());
        response.setShippingPhone(order.getShippingPhone());

        if (order.getUser() != null) {
            response.setCustomerName(order.getUser().getFullName());
            response.setCustomerEmail(order.getUser().getEmail());
        }

        List<OrderItemResponse> itemResponses = order.getItems().stream()
                .map(item -> new OrderItemResponse(
                        item.getId(),
                        item.getProduct().getId(),
                        item.getProduct().getName(),
                        item.getProduct().getImageUrl(),
                        item.getQuantity(),
                        item.getUnitPrice(),
                        item.getSubtotal()
                ))
                .collect(Collectors.toList());

        response.setItems(itemResponses);
        return response;
    }
}
