import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { CartDto } from '../core/models/cart.model';
import { CartService } from '../core/services/cart.service';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.scss']
})
export class CartComponent implements OnInit {

  cartId = '1'; // replace with real id or from route
  cart?: CartDto;
  discountCode = '';
  errorMessage = '';
  hasDiscount = false;
  isChangingCode = false;

  constructor(private cartService: CartService) { }

  ngOnInit(): void {
    this.loadCart();
  }

  loadCart(): void {
    this.cartService.getCart(this.cartId).subscribe({
      next: (data) => {
        this.cart = data;
        this.hasDiscount = !!data.discountCode;
        this.isChangingCode = false;
      },
      error: () => (this.errorMessage = 'Failed to load cart'),
    });
  }

  applyDiscount(): void {
    if (!this.discountCode.trim()) return;
    this.cartService.applyDiscount(this.cartId, this.discountCode).subscribe({
      next: (data) => {
        this.cart = data;
        this.hasDiscount = true;
        this.isChangingCode = false;
        this.errorMessage = '';
      },
      error: () => (this.errorMessage = 'Invalid or expired discount code'),
    });
  }

  changeDiscountCode(): void {
    // Allows the user to re-enter a new code
    this.isChangingCode = true;
    this.hasDiscount = false;
    this.discountCode = '';
    // Optional: reset prices visually
    if (this.cart) {
      this.cart.totalAfterDiscount = undefined as any;
    }
  }
}