import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Category, Product } from '../models/product.model';

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  private baseUrl = 'http://localhost:8080/api';

  constructor(private http: HttpClient) {}

  getCategories(): Observable<Category[]> {
    return this.http.get<Category[]>(`${this.baseUrl}/categories`);
  }

  getProducts(search?: string, categoryId?: number, sortBy?: string, inStockOnly?: boolean): Observable<Product[]> {
    let params = new HttpParams();
    if (search && search.trim()) {
      params = params.set('search', search.trim());
    }
    if (categoryId && categoryId > 0) {
      params = params.set('categoryId', categoryId.toString());
    }
    if (sortBy) {
      params = params.set('sortBy', sortBy);
    }
    if (inStockOnly !== undefined) {
      params = params.set('inStockOnly', inStockOnly.toString());
    }

    return this.http.get<Product[]>(`${this.baseUrl}/products`, { params });
  }

  getProductById(id: number): Observable<Product> {
    return this.http.get<Product>(`${this.baseUrl}/products/${id}`);
  }
}
