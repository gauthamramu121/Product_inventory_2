export interface LoginData {
    username: string;
    password: string;
}

export interface ProductRequestDTO {
    productName: string;
    brand: string;
    price: number | null;
    stock: number | null;
}

export interface ProductResponseDTO {
    productId: number;
    productName: string;
    brand: string;
    price: number;
    stock: number;
    status: string;
    addedOn: string;
    imageName: string;
}

export interface PaginationRequestDTO {
    page: number;
    size: number;
    productId?: number;
    productName?: string;
    brand?: string;
    price?: number;
    stock?: number;
    status?: string | null;
    addedOn?: string;
    sortBy?: string;
    sortDir?: string;
}

export interface PageResponse<T> {
    content: T[];
    totalPages: number;
    totalElements: number;
    size: number;
    number: number;
    first: boolean;
    last: boolean;
}

export interface TokenResponse {
    access_token: string;
    refresh_token: string;
}
