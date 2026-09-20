package com.shopease.service;

import com.shopease.dto.CartDto.*;
import com.shopease.entity.Cart;
import com.shopease.entity.CartItem;
import com.shopease.entity.Product;
import com.shopease.entity.User;
import com.shopease.exception.BadRequestException;
import com.shopease.exception.ResourceNotFoundException;
import com.shopease.repository.CartItemRepository;
import com.shopease.repository.CartRepository;
import com.shopease.repository.ProductRepository;
import com.shopease.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartService {

    private static final Logger log = LoggerFactory.getLogger(CartService.class);

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    public Cart getOrCreateCart(User user) {
        return cartRepository.findByUserId(user.getId())
                .orElseGet(() -> {
                    Cart newCart = new Cart(user);
                    return cartRepository.save(newCart);
                });
    }

    @Transactional(readOnly = true)
    public CartResponse getCartForUser(String email) {
        User user = getUserByEmail(email);
        Cart cart = getOrCreateCart(user);

        return mapToCartResponse(cart);
    }

    @Transactional
    public CartResponse addToCart(String email, AddToCartRequest request) {
        log.info("Adding to cart for user {} - productId: {}, quantity: {}", email, request.getProductId(), request.getQuantity());

        User user = getUserByEmail(email);
        Cart cart = getOrCreateCart(user);

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + request.getProductId()));

        if (Boolean.FALSE.equals(product.getActive())) {
            throw new BadRequestException("Product is currently unavailable.");
        }

        if (product.getStockQuantity() <= 0) {
            throw new BadRequestException("Product is out of stock.");
        }

        Optional<CartItem> existingItemOpt = cartItemRepository.findByCartIdAndProductId(cart.getId(), product.getId());

        if (existingItemOpt.isPresent()) {
            CartItem existingItem = existingItemOpt.get();
            int newQuantity = existingItem.getQuantity() + request.getQuantity();

            if (newQuantity > product.getStockQuantity()) {
                throw new BadRequestException("Cannot add requested quantity. Only " + product.getStockQuantity() + " items available in stock.");
            }

            existingItem.setQuantity(newQuantity);
            existingItem.setUnitPrice(product.getPrice());
            cartItemRepository.save(existingItem);
        } else {
            if (request.getQuantity() > product.getStockQuantity()) {
                throw new BadRequestException("Cannot add requested quantity. Only " + product.getStockQuantity() + " items available in stock.");
            }

            CartItem newItem = new CartItem(cart, product, request.getQuantity(), product.getPrice());
            cart.getItems().add(newItem);
            cartItemRepository.save(newItem);
        }

        cart.setUpdatedAt(LocalDateTime.now());
        cartRepository.save(cart);

        return mapToCartResponse(cart);
    }

    @Transactional
    public CartResponse updateCartItemQuantity(String email, Long itemId, int quantity) {
        log.info("Updating cart item {} to quantity {} for user {}", itemId, quantity, email);

        if (quantity <= 0) {
            throw new BadRequestException("Quantity must be at least 1. To remove the product, use the delete option.");
        }

        User user = getUserByEmail(email);
        Cart cart = getOrCreateCart(user);

        CartItem item = cartItemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found with ID: " + itemId));

        if (!item.getCart().getId().equals(cart.getId())) {
            throw new BadRequestException("Unauthorized access to this cart item.");
        }

        if (quantity > item.getProduct().getStockQuantity()) {
            throw new BadRequestException("Cannot update quantity. Only " + item.getProduct().getStockQuantity() + " items available in stock.");
        }

        item.setQuantity(quantity);
        item.setUnitPrice(item.getProduct().getPrice());
        cartItemRepository.save(item);

        cart.setUpdatedAt(LocalDateTime.now());
        cartRepository.save(cart);

        return mapToCartResponse(cart);
    }

    @Transactional
    public CartResponse removeCartItem(String email, Long itemId) {
        log.info("Removing cart item {} for user {}", itemId, email);

        User user = getUserByEmail(email);
        Cart cart = getOrCreateCart(user);

        CartItem item = cartItemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found with ID: " + itemId));

        if (!item.getCart().getId().equals(cart.getId())) {
            throw new BadRequestException("Unauthorized access to this cart item.");
        }

        cart.getItems().remove(item);
        cartItemRepository.delete(item);

        cart.setUpdatedAt(LocalDateTime.now());
        cartRepository.save(cart);

        return mapToCartResponse(cart);
    }

    @Transactional
    public CartResponse clearCart(String email) {
        log.info("Clearing cart for user {}", email);

        User user = getUserByEmail(email);
        Cart cart = getOrCreateCart(user);

        cartItemRepository.deleteByCartId(cart.getId());
        cart.getItems().clear();
        cart.setUpdatedAt(LocalDateTime.now());
        cartRepository.save(cart);

        return mapToCartResponse(cart);
    }

    private User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
    }

    private CartResponse mapToCartResponse(Cart cart) {
        List<CartItemResponse> itemResponses = cart.getItems().stream()
                .map(item -> new CartItemResponse(
                        item.getId(),
                        item.getProduct().getId(),
                        item.getProduct().getName(),
                        item.getProduct().getImageUrl(),
                        item.getUnitPrice(),
                        item.getQuantity(),
                        item.getSubtotal(),
                        item.getProduct().getStockQuantity()
                ))
                .collect(Collectors.toList());

        BigDecimal totalAmount = itemResponses.stream()
                .map(CartItemResponse::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        int totalItems = itemResponses.stream()
                .mapToInt(CartItemResponse::getQuantity)
                .sum();

        return new CartResponse(cart.getId(), itemResponses, totalAmount, totalItems);
    }
}
