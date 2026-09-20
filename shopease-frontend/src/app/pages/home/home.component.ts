import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { ProductService } from '../../services/product.service';
import { CartService } from '../../services/cart.service';
import { AuthService } from '../../services/auth.service';
import { ToastService } from '../../services/toast.service';
import { Category, Product } from '../../models/product.model';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  categories: Category[] = [];
  featuredProducts: Product[] = [];
  loading: boolean = true;
  addingToCartId: number | null = null;

  constructor(
    private productService: ProductService,
    private cartService: CartService,
    public authService: AuthService,
    private toastService: ToastService
  ) {}

  ngOnInit(): void {
    this.loadHomeData();
  }

  loadHomeData(): void {
    this.loading = true;
    this.productService.getCategories().subscribe({
      next: (categories) => {
        this.categories = categories;
      },
      error: () => {
        this.toastService.error('Failed to load categories');
      }
    });

    this.productService.getProducts(undefined, undefined, 'newest').subscribe({
      next: (products) => {
        this.featuredProducts = products.slice(0, 8);
        this.loading = false;
      },
      error: () => {
        this.toastService.error('Failed to load featured products');
        this.loading = false;
      }
    });
  }

  addToCart(product: Product, event: Event): void {
    event.stopPropagation();
    if (!this.authService.isLoggedIn) {
      this.toastService.warning('Please log in to add items to your cart.');
      return;
    }

    if (product.stockQuantity <= 0) {
      this.toastService.error('Sorry, this product is currently out of stock.');
      return;
    }

    this.addingToCartId = product.id;
    this.cartService.addToCart(product.id, 1).subscribe({
      next: () => {
        this.toastService.success(`Added "${product.name}" to your cart!`);
        this.addingToCartId = null;
      },
      error: (err) => {
        this.toastService.error(err.error?.message || 'Failed to add item to cart.');
        this.addingToCartId = null;
      }
    });
  }
}
