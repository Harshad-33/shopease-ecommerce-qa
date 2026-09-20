import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable, map, tap } from 'rxjs';
import { AddToCartRequest, Cart, UpdateCartItemRequest } from '../models/cart.model';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class CartService {
  private apiUrl = 'http://localhost:8080/api/cart';
  private cartSubject = new BehaviorSubject<Cart | null>(null);
  public cart$ = this.cartSubject.asObservable();

  public cartItemCount$: Observable<number> = this.cart$.pipe(
    map(cart => cart ? cart.totalItems : 0)
  );

  constructor(private http: HttpClient, private authService: AuthService) {
    this.authService.currentUser$.subscribe(user => {
      if (user) {
        this.loadCart().subscribe();
      } else {
        this.cartSubject.next(null);
      }
    });
  }

  loadCart(): Observable<Cart> {
    return this.http.get<Cart>(this.apiUrl).pipe(
      tap(cart => this.cartSubject.next(cart))
    );
  }

  addToCart(productId: number, quantity: number = 1): Observable<Cart> {
    const request: AddToCartRequest = { productId, quantity };
    return this.http.post<Cart>(`${this.apiUrl}/items`, request).pipe(
      tap(cart => this.cartSubject.next(cart))
    );
  }

  updateQuantity(itemId: number, quantity: number): Observable<Cart> {
    const request: UpdateCartItemRequest = { quantity };
    return this.http.put<Cart>(`${this.apiUrl}/items/${itemId}`, request).pipe(
      tap(cart => this.cartSubject.next(cart))
    );
  }

  removeItem(itemId: number): Observable<Cart> {
    return this.http.delete<Cart>(`${this.apiUrl}/items/${itemId}`).pipe(
      tap(cart => this.cartSubject.next(cart))
    );
  }

  clearCart(): Observable<Cart> {
    return this.http.delete<Cart>(`${this.apiUrl}/clear`).pipe(
      tap(cart => this.cartSubject.next(cart))
    );
  }

  get currentCart(): Cart | null {
    return this.cartSubject.value;
  }
}
