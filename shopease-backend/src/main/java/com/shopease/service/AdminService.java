package com.shopease.service;

import com.shopease.dto.AdminDto.DashboardStatsResponse;
import com.shopease.dto.OrderDto.OrderResponse;
import com.shopease.entity.Order;
import com.shopease.entity.OrderStatus;
import com.shopease.entity.Role;
import com.shopease.exception.ResourceNotFoundException;
import com.shopease.repository.OrderRepository;
import com.shopease.repository.ProductRepository;
import com.shopease.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminService {

    private static final Logger log = LoggerFactory.getLogger(AdminService.class);

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderService orderService;

    public DashboardStatsResponse getDashboardStats() {
        long totalProducts = productRepository.countByActiveTrue();
        long totalCustomers = userRepository.countByRole(Role.CUSTOMER);
        long totalOrders = orderRepository.count();
        long pendingOrders = orderRepository.countByStatus(OrderStatus.PLACED) +
                             orderRepository.countByStatus(OrderStatus.PROCESSING);
        long lowStock = productRepository.countByActiveTrueAndStockQuantityLessThanEqual(10);

        BigDecimal totalRevenue = orderRepository.findAll().stream()
                .filter(o -> o.getStatus() != OrderStatus.CANCELLED)
                .map(Order::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new DashboardStatsResponse(
                totalProducts,
                totalCustomers,
                totalOrders,
                pendingOrders,
                totalRevenue,
                lowStock
        );
    }

    public List<OrderResponse> getAllOrders() {
        return orderRepository.findByOrderByOrderDateDesc().stream()
                .map(orderService::mapToOrderResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public OrderResponse updateOrderStatus(Long orderId, OrderStatus newStatus) {
        log.info("Admin updating order status for ID: {} to {}", orderId, newStatus);

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with ID: " + orderId));

        order.setStatus(newStatus);
        Order updated = orderRepository.save(order);
        return orderService.mapToOrderResponse(updated);
    }
}
