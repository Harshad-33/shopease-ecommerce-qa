import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { ToastService } from '../../services/toast.service';
import { RegisterRequest } from '../../models/user.model';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  user: RegisterRequest = {
    fullName: '',
    email: '',
    password: '',
    confirmPassword: '',
    phone: ''
  };
  loading: boolean = false;
  errorMessage: string = '';

  constructor(
    private authService: AuthService,
    private router: Router,
    private toastService: ToastService
  ) {}

  onSubmit(): void {
    if (!this.user.fullName || !this.user.email || !this.user.password || !this.user.confirmPassword || !this.user.phone) {
      this.errorMessage = 'Please fill out all required fields.';
      return;
    }

    if (this.user.password !== this.user.confirmPassword) {
      this.errorMessage = 'Passwords do not match.';
      return;
    }

    if (this.user.password.length < 6) {
      this.errorMessage = 'Password must be at least 6 characters long.';
      return;
    }

    this.loading = true;
    this.errorMessage = '';

    this.authService.register(this.user).subscribe({
      next: (response) => {
        this.loading = false;
        this.toastService.success(`Welcome to ShopEase, ${response.fullName}! Account created.`);
        this.router.navigate(['/']);
      },
      error: (err) => {
        this.loading = false;
        this.errorMessage = err.error?.message || 'Registration failed. Please check your details.';
        this.toastService.error(this.errorMessage);
      }
    });
  }
}
