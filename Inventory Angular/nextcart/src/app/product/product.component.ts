import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ProductsService } from '../service/products.service';
import { ProductResponseDTO } from '../model/products';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';
import { EXTENSIONS } from '../model/constant';

@Component({
  selector: 'app-product',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule
  ],
  templateUrl: './product.component.html',
  styleUrl: './product.component.css'
})
export class ProductComponent implements OnInit {

  private service = inject(ProductsService);
  private router = inject(Router);

  Math = Math;
  String = String;

  products: ProductResponseDTO[] = [];
  searchName = '';
  searchBrand = '';
  searchPrice?: number;
  currentPage = 0;
  totalPages = 0;
  loading = false;
  selectedStatus = '';

  statuses: string[] = [];

  ngOnInit(): void {

    this.service.status().subscribe({
      next: (response: string[]) => {
        this.statuses = response;
      },
      error: (err: HttpErrorResponse) => {
        console.log(err);
      }
    });
    this.loadProducts();
  }

  loadProducts(page: number = 0): void {

    this.loading = true;

    const request = {
      page: page,
      size: 4,
      productName: this.searchName,
      brand: this.searchBrand,
      status: this.selectedStatus || null,
      price: this.searchPrice,
      sortBy: 'productName',
      sortDir: 'asc'
    };

    this.service.display(request).subscribe({

      next: (response) => {
        this.products = response.content;
        this.totalPages = response.totalPages;
        this.currentPage = response.number;
        this.loading = false;
      },

      error: (err) => {
        console.error(err);
        this.loading = false;
      }
    });
  }

  click(productId: number): void {
    this.router.navigate(['/view', productId]);
  }

  clearFilters(): void {
    this.searchName = '';
    this.searchBrand = '';
    this.searchPrice = undefined;
    this.selectedStatus = '';

    this.loadProducts(0);
  }

  create(){
    this.router.navigate(['/create']);
  }

}

