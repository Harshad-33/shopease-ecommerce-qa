import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { AdminService } from '../../../services/admin.service';
import { ProductService } from '../../../services/product.service';
import { ToastService } from '../../../services/toast.service';
import { Category, Product, ProductRequest } from '../../../models/product.model';

@Component({
  selector: 'app-admin-products',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './admin-products.component.html',
  styleUrls: ['./admin-products.component.css']
})
export class AdminProductsComponent implements OnInit {
  products: Product[] = [];
  categories: Category[] = [];
  loading: boolean = true;
  saving: boolean = false;

  showModal: boolean = false;
  editingProduct: Product | null = null;

  productForm: ProductRequest = {
    name: '',
    description: '',
    price: 0,
    stockQuantity: 0,
    imageUrl: '',
    categoryId: 1,
    active: true
  };

  constructor(
    private adminService: AdminService,
    private productService: ProductService,
    private toastService: ToastService
  ) {}

  ngOnInit(): void {
    this.loadCategories();
    this.loadProducts();
  }

  loadCategories(): void {
    this.productService.getCategories().subscribe({
      next: (categories) => {
        this.categories = categories;
      }
    });
  }

  loadProducts(): void {
    this.loading = true;
    this.adminService.getAllProducts().subscribe({
      next: (products) => {
        this.products = products;
        this.loading = false;
      },
      error: () => {
        this.toastService.error('Failed to load products for admin.');
        this.loading = false;
      }
    });
  }

  openAddModal(): void {
    this.editingProduct = null;
    this.productForm = {
      name: '',
      description: '',
      price: 0,
      stockQuantity: 10,
      imageUrl: 'https://images.unsplash.com/photo-1526170375885-4d8ecf77b99f?w=600&auto=format&fit=crop&q=60',
      categoryId: this.categories.length > 0 ? this.categories[0].id : 1,
      active: true
    };
    this.showModal = true;
  }

  openEditModal(product: Product): void {
    this.editingProduct = product;
    this.productForm = {
      name: product.name,
      description: product.description,
      price: product.price,
      stockQuantity: product.stockQuantity,
      imageUrl: product.imageUrl,
      categoryId: product.categoryId,
      active: product.active
    };
    this.showModal = true;
  }

  closeModal(): void {
    this.showModal = false;
    this.editingProduct = null;
  }

  saveProduct(): void {
    if (!this.productForm.name || !this.productForm.price || this.productForm.stockQuantity < 0) {
      this.toastService.error('Please fill in valid name, price, and stock quantity.');
      return;
    }

    this.saving = true;

    if (this.editingProduct) {
      this.adminService.updateProduct(this.editingProduct.id, this.productForm).subscribe({
        next: () => {
          this.saving = false;
          this.toastService.success('Product updated successfully!');
          this.closeModal();
          this.loadProducts();
        },
        error: (err) => {
          this.saving = false;
          this.toastService.error(err.error?.message || 'Failed to update product.');
        }
      });
    } else {
      this.adminService.createProduct(this.productForm).subscribe({
        next: () => {
          this.saving = false;
          this.toastService.success('Product added successfully!');
          this.closeModal();
          this.loadProducts();
        },
        error: (err) => {
          this.saving = false;
          this.toastService.error(err.error?.message || 'Failed to create product.');
        }
      });
    }
  }

  deleteProduct(product: Product): void {
    if (confirm(`Are you sure you want to deactivate "${product.name}"?`)) {
      this.adminService.deleteProduct(product.id).subscribe({
        next: () => {
          this.toastService.success('Product deactivated.');
          this.loadProducts();
        },
        error: (err) => {
          this.toastService.error(err.error?.message || 'Failed to deactivate product.');
        }
      });
    }
  }
}
