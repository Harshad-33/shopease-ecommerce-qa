import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, RouterModule, Router } from '@angular/router';
import { ProductService } from '../../services/product.service';
import { CartService } from '../../services/cart.service';
import { AuthService } from '../../services/auth.service';
import { ToastService } from '../../services/toast.service';
import { Product } from '../../models/product.model';

@Component({
  selector: 'app-product-details',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './product-details.component.html',
  styleUrls: ['./product-details.component.css']
})
export class ProductDetailsComponent implements OnInit {
  product: Product | null = null;
  loading: boolean = true;
  quantity: number = 1;
  addingToCart: boolean = false;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private productService: ProductService,
    private cartService: CartService,
    public authService: AuthService,
    private toastService: ToastService
  ) {}

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.loadProduct(+id);
    } else {
      this.router.navigate(['/products']);
    }
  }

  loadProduct(id: number): void {
    this.loading = true;
    this.productService.getProductById(id).subscribe({
      next: (product) => {
        this.product = product;
        this.loading = false;
        this.quantity = 1;
      },
      error: () => {
        this.toastService.error('Product not found.');
        this.loading = false;
        this.router.navigate(['/products']);
      }
    });
  }

  increment(): void {
    if (this.product && this.quantity < this.product.stockQuantity) {
      this.quantity++;
    }
  }

  decrement(): void {
    if (this.quantity > 1) {
      this.quantity--;
    }
  }

  addToCart(): void {
    if (!this.product) return;

    if (!this.authService.isLoggedIn) {
      this.toastService.warning('Please log in to add items to your cart.');
      this.router.navigate(['/login'], { queryParams: { returnUrl: `/products/${this.product.id}` } });
      return;
    }

    if (this.quantity > this.product.stockQuantity) {
      this.toastService.error(`Only ${this.product.stockQuantity} items in stock.`);
      return;
    }

    this.addingToCart = true;
    this.cartService.addToCart(this.product.id, this.quantity).subscribe({
      next: () => {
        this.addingToCart = false;
        this.toastService.success(`Added ${this.quantity} item(s) to your cart!`);
      },
      error: (err) => {
        this.addingToCart = false;
        this.toastService.error(err.error?.message || 'Failed to add item to cart.');
      }
    });
  }
}
