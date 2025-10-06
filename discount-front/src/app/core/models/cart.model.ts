export interface DiscountCodeDto {
    code: string;
    type: string;
    value: number;
    validFrom: string;
    validTo: string;
    applicableProductIds: string[];
}

export interface ProductDto {
    id: string;
    name: string;
    price: number;
}

export interface CartItemDto {
    product: ProductDto;
    quantity: number;
    totalPrice: number;
}


export interface CartDto {
    items: CartItemDto[];
    discountCode?: DiscountCodeDto;
    totalBeforeDiscount: number;
    totalAfterDiscount: number;
}