import { Component, inject, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ProductsService } from '../service/products.service';
import { ProductResponseDTO } from '../model/products';
import { HttpErrorResponse } from '@angular/common/http';
import { IMAGE_URL } from '../model/constant';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-view',
  standalone: true,
  imports: [
    CommonModule
  ],
  templateUrl: './view.component.html',
  styleUrl: './view.component.css'
})
export class ViewComponent implements OnInit {

  productId: number | null = null;

  product: ProductResponseDTO = {
    productId: 0,
    productName: '',
    brand: '',
    description:'',
    price: 0,
    stock: 0,
    status: '',
    addedOn: '',
    imageName: ''
  }

  IMAGE_URL = IMAGE_URL;

  private router = inject(Router);
  private service = inject(ProductsService);
  private route = inject(ActivatedRoute);


  ngOnInit(): void {
    this.productId = Number(this.route.snapshot.paramMap.get('productId'));

    this.service.view(this.productId).subscribe({
      next: (response: ProductResponseDTO) => {
        this.product = response;
      },
      error: (err: HttpErrorResponse) => {
        console.log(err);
      }
    });
  }

  update(productId: number) {
    this.router.navigate(['/update', productId]);
  }

  return() {
    this.router.navigate(['/products']);
  }
}
