import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, ActivatedRoute, RouterModule } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { ToastService } from '../../services/toast.service';
import { LoginRequest } from '../../models/user.model';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  credentials: LoginRequest = {
    email: '',
    password: ''
  };
  loading: boolean = false;
  errorMessage: string = '';
  returnUrl: string = '/';

  constructor(
    private authService: AuthService,
    private router: Router,
    private route: ActivatedRoute,
    private toastService: ToastService
  ) {}

  ngOnInit(): void {
    this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/';
    if (this.authService.isLoggedIn) {
      this.redirectBasedOnRole();
    }
  }

  onSubmit(): void {
    if (!this.credentials.email || !this.credentials.password) {
      this.errorMessage = 'Please enter both email and password.';
      return;
    }

    this.loading = true;
    this.errorMessage = '';

    this.authService.login(this.credentials).subscribe({
      next: (user) => {
        this.loading = false;
        this.toastService.success(`Welcome back, ${user.fullName}!`);
        this.redirectBasedOnRole();
      },
      error: (err) => {
        this.loading = false;
        this.errorMessage = err.error?.message || 'Invalid email or password. Please try again.';
        this.toastService.error(this.errorMessage);
      }
    });
  }

  fillDemo(type: 'admin' | 'customer'): void {
    if (type === 'admin') {
      this.credentials.email = 'admin@shopease.com';
      this.credentials.password = 'Admin@123';
    } else {
      this.credentials.email = 'rahul@example.com';
      this.credentials.password = 'Customer@123';
    }
    this.errorMessage = '';
  }

  private redirectBasedOnRole(): void {
    if (this.authService.isAdmin) {
      this.router.navigate(['/admin/dashboard']);
    } else {
      this.router.navigateByUrl(this.returnUrl);
    }
  }
}
