import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-footer',
  standalone: true,
  imports: [CommonModule, RouterModule],
  template: `
    <footer class="bg-dark text-light pt-5 pb-3 mt-auto">
      <div class="container">
        <div class="row g-4 mb-4">
          <!-- Brand Column -->
          <div class="col-lg-4 col-md-6">
            <h5 class="fw-bold text-white d-flex align-items-center mb-3">
              <i class="bi bi-bag-check-fill text-warning me-2 fs-4"></i>
              <span>Shop<span class="text-warning">Ease</span></span>
            </h5>
            <p class="text-secondary small">
              ShopEase is your trusted modern e-commerce platform delivering high-quality products directly to your doorstep with ease, speed, and security.
            </p>
            <div class="d-flex gap-3 text-warning fs-5">
              <i class="bi bi-shield-check" title="Secure Payments"></i>
              <i class="bi bi-truck" title="Fast Delivery"></i>
              <i class="bi bi-arrow-counterclockwise" title="Easy Returns"></i>
              <i class="bi bi-headset" title="24/7 Support"></i>
            </div>
          </div>

          <!-- Quick Links -->
          <div class="col-lg-2 col-md-6">
            <h6 class="text-white fw-bold mb-3 text-uppercase small">Quick Links</h6>
            <ul class="list-unstyled small">
              <li class="mb-2"><a routerLink="/" class="text-secondary text-decoration-none">Home</a></li>
              <li class="mb-2"><a routerLink="/products" class="text-secondary text-decoration-none">All Products</a></li>
              <li class="mb-2"><a routerLink="/cart" class="text-secondary text-decoration-none">My Cart</a></li>
              <li class="mb-2"><a routerLink="/orders" class="text-secondary text-decoration-none">Track Order</a></li>
            </ul>
          </div>

          <!-- Top Categories -->
          <div class="col-lg-3 col-md-6">
            <h6 class="text-white fw-bold mb-3 text-uppercase small">Categories</h6>
            <ul class="list-unstyled small">
              <li class="mb-2"><a [routerLink]="['/products']" [queryParams]="{categoryId: 1}" class="text-secondary text-decoration-none">Electronics</a></li>
              <li class="mb-2"><a [routerLink]="['/products']" [queryParams]="{categoryId: 2}" class="text-secondary text-decoration-none">Clothing & Fashion</a></li>
              <li class="mb-2"><a [routerLink]="['/products']" [queryParams]="{categoryId: 3}" class="text-secondary text-decoration-none">Shoes & Footwear</a></li>
              <li class="mb-2"><a [routerLink]="['/products']" [queryParams]="{categoryId: 6}" class="text-secondary text-decoration-none">Home & Kitchen</a></li>
            </ul>
          </div>

          <!-- Academic & Viva Note -->
          <div class="col-lg-3 col-md-6">
            <h6 class="text-white fw-bold mb-3 text-uppercase small">Academic Project</h6>
            <p class="text-secondary small mb-2">
              <strong>Stack:</strong> Angular 21, Spring Boot 3, Spring Data JPA, Spring Security (JWT), MySQL 8.
            </p>
            <span class="badge bg-secondary">B.Tech Final Year Capstone</span>
          </div>
        </div>

        <hr class="border-secondary opacity-25" />

        <div class="d-flex flex-column flex-sm-row justify-content-between align-items-center text-secondary small">
          <p class="mb-0">&copy; 2026 ShopEase E-Commerce Application. All rights reserved.</p>
          <p class="mb-0">Designed with Clean Architecture &amp; REST APIs</p>
        </div>
      </div>
    </footer>
  `,
  styles: [`
    a.text-secondary:hover {
      color: #ffc107 !important;
      transition: color 0.2s ease-in-out;
    }
  `]
})
export class FooterComponent {}
