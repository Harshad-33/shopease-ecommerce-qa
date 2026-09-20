import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { CartService } from '../../services/cart.service';
import { OrderService } from '../../services/order.service';
import { AuthService } from '../../services/auth.service';
import { ToastService } from '../../services/toast.service';
import { Cart } from '../../models/cart.model';
import { CreateOrderRequest, PaymentMethod } from '../../models/order.model';

@Component({
  selector: 'app-checkout',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.css']
})
export class CheckoutComponent implements OnInit {
  cart: Cart | null = null;
  loading: boolean = true;
  placingOrder: boolean = false;

  shippingInfo: CreateOrderRequest = {
    shippingFullName: '',
    shippingAddress: '',
    shippingCity: '',
    shippingState: '',
    shippingPincode: '',
    shippingPhone: '',
    paymentMethod: 'CASH_ON_DELIVERY'
  };

  // Dummy Card Fields (never persisted to backend)
  dummyCard = {
    cardNumber: '4111 2222 3333 4444',
    cardHolder: 'John Doe',
    expiry: '12/28',
    cvv: '123'
  };

  constructor(
    private cartService: CartService,
    private orderService: OrderService,
    private authService: AuthService,
    private toastService: ToastService,
    private router: Router
  ) {}

  ngOnInit(): void {
    const user = this.authService.currentUserValue;
    if (user) {
      this.shippingInfo.shippingFullName = user.fullName;
      this.shippingInfo.shippingPhone = user.phone;
    }

    this.cartService.loadCart().subscribe({
      next: (cart) => {
        this.cart = cart;
        this.loading = false;
        if (!cart || cart.items.length === 0) {
          this.toastService.warning('Your shopping cart is empty.');
          this.router.navigate(['/cart']);
        }
      },
      error: () => {
        this.toastService.error('Failed to load cart items.');
        this.loading = false;
        this.router.navigate(['/cart']);
      }
    });
  }

  fillSampleAddress(): void {
    this.shippingInfo.shippingAddress = 'Flat 402, Sunshine Heights, MG Road';
    this.shippingInfo.shippingCity = 'Bangalore';
    this.shippingInfo.shippingState = 'Karnataka';
    this.shippingInfo.shippingPincode = '560001';
  }

  placeOrder(): void {
    if (
      !this.shippingInfo.shippingFullName ||
      !this.shippingInfo.shippingAddress ||
      !this.shippingInfo.shippingCity ||
      !this.shippingInfo.shippingState ||
      !this.shippingInfo.shippingPincode ||
      !this.shippingInfo.shippingPhone
    ) {
      this.toastService.error('Please fill in all shipping details.');
      return;
    }

    if (this.shippingInfo.paymentMethod === 'CARD_PAYMENT') {
      if (!this.dummyCard.cardNumber || !this.dummyCard.expiry || !this.dummyCard.cvv) {
        this.toastService.error('Please enter complete demo card details.');
        return;
      }
    }

    this.placingOrder = true;
    this.orderService.placeOrder(this.shippingInfo).subscribe({
      next: (order) => {
        this.placingOrder = false;
        this.toastService.success('Order placed successfully!');
        this.router.navigate(['/order-confirmation', order.id]);
      },
      error: (err) => {
        this.placingOrder = false;
        this.toastService.error(err.error?.message || 'Failed to place order. Please try again.');
      }
    });
  }
}
