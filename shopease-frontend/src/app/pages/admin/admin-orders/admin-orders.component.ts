import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { AdminService } from '../../../services/admin.service';
import { ToastService } from '../../../services/toast.service';
import { Order, OrderStatus } from '../../../models/order.model';

@Component({
  selector: 'app-admin-orders',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './admin-orders.component.html',
  styleUrls: ['./admin-orders.component.css']
})
export class AdminOrdersComponent implements OnInit {
  orders: Order[] = [];
  loading: boolean = true;
  updatingOrderId: number | null = null;

  statuses: OrderStatus[] = ['PLACED', 'PROCESSING', 'SHIPPED', 'DELIVERED', 'CANCELLED'];

  constructor(
    private adminService: AdminService,
    private toastService: ToastService
  ) {}

  ngOnInit(): void {
    this.loadOrders();
  }

  loadOrders(): void {
    this.loading = true;
    this.adminService.getAllOrders().subscribe({
      next: (orders) => {
        this.orders = orders;
        this.loading = false;
      },
      error: () => {
        this.toastService.error('Failed to load orders for admin.');
        this.loading = false;
      }
    });
  }

  onStatusChange(order: Order, newStatus: OrderStatus): void {
    this.updatingOrderId = order.id;
    this.adminService.updateOrderStatus(order.id, newStatus).subscribe({
      next: (updated) => {
        order.status = updated.status;
        this.updatingOrderId = null;
        this.toastService.success(`Order #${order.orderNumber} status updated to ${newStatus}`);
      },
      error: (err) => {
        this.updatingOrderId = null;
        this.toastService.error(err.error?.message || 'Failed to update order status.');
      }
    });
  }
}
