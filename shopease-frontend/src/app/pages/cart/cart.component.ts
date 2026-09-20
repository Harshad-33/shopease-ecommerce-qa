import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Router } from '@angular/router';
import { CartService } from '../../services/cart.service';
import { ToastService } from '../../services/toast.service';
import { Cart, CartItem } from '../../models/cart.model';

@Component({
  selector: 'app-cart',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})
export class CartComponent implements OnInit {
  cart: Cart | null = null;
  loading: boolean = true;
  updatingItemId: number | null = null;

  constructor(
    public cartService: CartService,
    private toastService: ToastService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loadCart();
  }

  loadCart(): void {
    this.loading = true;
    this.cartService.loadCart().subscribe({
      next: (cart) => {
        this.cart = cart;
        this.loading = false;
      },
      error: () => {
        this.toastService.error('Failed to load shopping cart.');
        this.loading = false;
      }
    });
  }

  increment(item: CartItem): void {
    if (item.quantity >= item.stockQuantity) {
      this.toastService.warning(`Cannot add more. Only ${item.stockQuantity} items in stock.`);
      return;
    }
    this.updateQuantity(item, item.quantity + 1);
  }

  decrement(item: CartItem): void {
    if (item.quantity <= 1) {
      return;
    }
    this.updateQuantity(item, item.quantity - 1);
  }

  updateQuantity(item: CartItem, newQty: number): void {
    this.updatingItemId = item.id;
    this.cartService.updateQuantity(item.id, newQty).subscribe({
      next: (cart) => {
        this.cart = cart;
        this.updatingItemId = null;
      },
      error: (err) => {
        this.updatingItemId = null;
        this.toastService.error(err.error?.message || 'Failed to update item quantity.');
      }
    });
  }

  removeItem(item: CartItem): void {
    this.cartService.removeItem(item.id).subscribe({
      next: (cart) => {
        this.cart = cart;
        this.toastService.info(`Removed "${item.productName}" from cart.`);
      },
      error: (err) => {
        this.toastService.error(err.error?.message || 'Failed to remove item.');
      }
    });
  }

  clearCart(): void {
    if (confirm('Are you sure you want to clear your shopping cart?')) {
      this.cartService.clearCart().subscribe({
        next: (cart) => {
          this.cart = cart;
          this.toastService.info('Shopping cart cleared.');
        },
        error: (err) => {
          this.toastService.error(err.error?.message || 'Failed to clear cart.');
        }
      });
    }
  }

  proceedToCheckout(): void {
    if (!this.cart || this.cart.items.length === 0) {
      this.toastService.warning('Your cart is empty. Add items before checking out.');
      return;
    }
    this.router.navigate(['/checkout']);
  }
}
