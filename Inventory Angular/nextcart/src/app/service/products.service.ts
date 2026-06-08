import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { PageResponse, PaginationRequestDTO, ProductResponseDTO } from '../model/products';
import { Observable } from 'rxjs';
import { BASE_URL, EXTENSIONS } from '../model/constant';

@Injectable({
  providedIn: 'root'
})
export class ProductsService {

  private http = inject(HttpClient);

  create(data: FormData): Observable<ProductResponseDTO> {
    return this.http.post<ProductResponseDTO>(`${BASE_URL}${EXTENSIONS.CREATE}`, data);
  }

  update(productId: number, data: FormData): Observable<ProductResponseDTO> {
    return this.http.put<ProductResponseDTO>(`${BASE_URL}${EXTENSIONS.UPDATE}/${productId}`, data);
  }

  display(data: PaginationRequestDTO): Observable<PageResponse<ProductResponseDTO>> {
    return this.http.post<PageResponse<ProductResponseDTO>>(`${BASE_URL}${EXTENSIONS.DISPLAY}`, data);
  }

  stock(productId: number, data: number): Observable<void> {
    return this.http.put<void>(`${BASE_URL}${EXTENSIONS.STOCK}/${productId}`, data);
  }

  status(): Observable<string[]> {
    return this.http.get<string[]>(`${BASE_URL}${EXTENSIONS.STATUS}`);
  }

  view(productId: number): Observable<ProductResponseDTO> {
    return this.http.get<ProductResponseDTO>(`${BASE_URL}${EXTENSIONS.SEARCH}/${productId}`);
  };
}
