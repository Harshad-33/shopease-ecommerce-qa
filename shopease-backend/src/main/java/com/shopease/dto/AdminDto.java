package com.shopease.dto;

import java.math.BigDecimal;

public class AdminDto {

    public static class DashboardStatsResponse {
        private long totalProducts;
        private long totalCustomers;
        private long totalOrders;
        private long pendingOrders;
        private BigDecimal totalRevenue;
        private long lowStockProducts;

        public DashboardStatsResponse() {}

        public DashboardStatsResponse(long totalProducts, long totalCustomers, long totalOrders,
                                      long pendingOrders, BigDecimal totalRevenue, long lowStockProducts) {
            this.totalProducts = totalProducts;
            this.totalCustomers = totalCustomers;
            this.totalOrders = totalOrders;
            this.pendingOrders = pendingOrders;
            this.totalRevenue = totalRevenue != null ? totalRevenue : BigDecimal.ZERO;
            this.lowStockProducts = lowStockProducts;
        }

        public long getTotalProducts() { return totalProducts; }
        public void setTotalProducts(long totalProducts) { this.totalProducts = totalProducts; }
        public long getTotalCustomers() { return totalCustomers; }
        public void setTotalCustomers(long totalCustomers) { this.totalCustomers = totalCustomers; }
        public long getTotalOrders() { return totalOrders; }
        public void setTotalOrders(long totalOrders) { this.totalOrders = totalOrders; }
        public long getPendingOrders() { return pendingOrders; }
        public void setPendingOrders(long pendingOrders) { this.pendingOrders = pendingOrders; }
        public BigDecimal getTotalRevenue() { return totalRevenue; }
        public void setTotalRevenue(BigDecimal totalRevenue) { this.totalRevenue = totalRevenue; }
        public long getLowStockProducts() { return lowStockProducts; }
        public void setLowStockProducts(long lowStockProducts) { this.lowStockProducts = lowStockProducts; }
    }
}
