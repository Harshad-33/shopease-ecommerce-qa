import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable, tap } from 'rxjs';
import { AuthResponse, LoginRequest, RegisterRequest, User } from '../models/user.model';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://localhost:8080/api/auth';
  private currentUserSubject: BehaviorSubject<User | null>;
  public currentUser$: Observable<User | null>;

  constructor(private http: HttpClient, private router: Router) {
    const savedUser = localStorage.getItem('shopease_user');
    this.currentUserSubject = new BehaviorSubject<User | null>(savedUser ? JSON.parse(savedUser) : null);
    this.currentUser$ = this.currentUserSubject.asObservable();
  }

  public get currentUserValue(): User | null {
    return this.currentUserSubject.value;
  }

  public get isLoggedIn(): boolean {
    return !!this.getToken() && !!this.currentUserValue;
  }

  public get isAdmin(): boolean {
    return this.currentUserValue?.role === 'ADMIN';
  }

  getToken(): string | null {
    return localStorage.getItem('shopease_token');
  }

  login(credentials: LoginRequest): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${this.apiUrl}/login`, credentials).pipe(
      tap(response => {
        localStorage.setItem('shopease_token', response.token);
        const user: User = {
          id: response.id,
          fullName: response.fullName,
          email: response.email,
          phone: response.phone,
          role: response.role
        };
        localStorage.setItem('shopease_user', JSON.stringify(user));
        this.currentUserSubject.next(user);
      })
    );
  }

  register(data: RegisterRequest): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${this.apiUrl}/register`, data).pipe(
      tap(response => {
        localStorage.setItem('shopease_token', response.token);
        const user: User = {
          id: response.id,
          fullName: response.fullName,
          email: response.email,
          phone: response.phone,
          role: response.role
        };
        localStorage.setItem('shopease_user', JSON.stringify(user));
        this.currentUserSubject.next(user);
      })
    );
  }

  logout() {
    localStorage.removeItem('shopease_token');
    localStorage.removeItem('shopease_user');
    this.currentUserSubject.next(null);
    this.router.navigate(['/login']);
  }
}
