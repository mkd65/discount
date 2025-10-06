import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CartDto } from '../models/cart.model';

@Injectable({
  providedIn: 'root'
})
export class CartService {
  private baseUrl = 'http://localhost:8080/api/cart';

  constructor(private http: HttpClient) { }

  getCart(cartId: string): Observable<CartDto> {
    return this.http.get<CartDto>(`${this.baseUrl}/${cartId}`);
  }

  applyDiscount(cartId: string, code: string): Observable<CartDto> {
    return this.http.post<CartDto>(`${this.baseUrl}/${cartId}/apply-discount/${code}`, {});
  }
}
