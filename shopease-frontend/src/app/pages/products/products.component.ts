import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, RouterModule, Router } from '@angular/router';
import { ProductService } from '../../services/product.service';
import { CartService } from '../../services/cart.service';
import { AuthService } from '../../services/auth.service';
import { ToastService } from '../../services/toast.service';
import { Category, Product } from '../../models/product.model';

@Component({
  selector: 'app-products',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.css']
})
export class ProductsComponent implements OnInit {
  products: Product[] = [];
  categories: Category[] = [];
  loading: boolean = true;
  addingToCartId: number | null = null;

  // Filter States
  searchQuery: string = '';
  selectedCategoryId: number | null = null;
  sortBy: string = 'newest';
  inStockOnly: boolean = false;

  constructor(
    private productService: ProductService,
    private cartService: CartService,
    public authService: AuthService,
    private toastService: ToastService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.productService.getCategories().subscribe({
      next: (categories) => {
        this.categories = categories;
      }
    });

    this.route.queryParams.subscribe(params => {
      this.searchQuery = params['search'] || '';
      this.selectedCategoryId = params['categoryId'] ? +params['categoryId'] : null;
      this.sortBy = params['sortBy'] || 'newest';
      this.inStockOnly = params['inStockOnly'] === 'true';
      this.fetchProducts();
    });
  }

  fetchProducts(): void {
    this.loading = true;
    this.productService.getProducts(
      this.searchQuery,
      this.selectedCategoryId || undefined,
      this.sortBy,
      this.inStockOnly
    ).subscribe({
      next: (products) => {
        this.products = products;
        this.loading = false;
      },
      error: () => {
        this.toastService.error('Failed to load products.');
        this.loading = false;
      }
    });
  }

  onFilterChange(): void {
    const queryParams: any = {};
    if (this.searchQuery && this.searchQuery.trim()) queryParams.search = this.searchQuery.trim();
    if (this.selectedCategoryId) queryParams.categoryId = this.selectedCategoryId;
    if (this.sortBy !== 'newest') queryParams.sortBy = this.sortBy;
    if (this.inStockOnly) queryParams.inStockOnly = 'true';

    this.router.navigate(['/products'], { queryParams });
  }

  selectCategory(categoryId: number | null): void {
    this.selectedCategoryId = categoryId;
    this.onFilterChange();
  }

  resetFilters(): void {
    this.searchQuery = '';
    this.selectedCategoryId = null;
    this.sortBy = 'newest';
    this.inStockOnly = false;
    this.router.navigate(['/products']);
  }

  addToCart(product: Product, event: Event): void {
    event.stopPropagation();
    if (!this.authService.isLoggedIn) {
      this.toastService.warning('Please log in to add items to your cart.');
      this.router.navigate(['/login'], { queryParams: { returnUrl: '/products' } });
      return;
    }

    if (product.stockQuantity <= 0) {
      this.toastService.error('Sorry, this product is currently out of stock.');
      return;
    }

    this.addingToCartId = product.id;
    this.cartService.addToCart(product.id, 1).subscribe({
      next: () => {
        this.toastService.success(`Added "${product.name}" to cart!`);
        this.addingToCartId = null;
      },
      error: (err) => {
        this.toastService.error(err.error?.message || 'Failed to add to cart.');
        this.addingToCartId = null;
      }
    });
  }
}
