import { Component, inject, OnInit } from '@angular/core';
import { ProductsService } from '../service/products.service';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { ProductResponseDTO } from '../model/products';
import { HttpErrorResponse } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { IMAGE_URL } from '../model/constant';

@Component({
  selector: 'app-update',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule
  ],
  templateUrl: './update.component.html',
  styleUrl: './update.component.css'
})
export class UpdateComponent implements OnInit {

  productId: number | null = null;
  selectedFile: File | null = null;
  previewUrl: string | ArrayBuffer | null = null;

  IMAGE_URL = IMAGE_URL;
  
  product: ProductResponseDTO = {
    productId: 0,
    productName: '',
    brand: '',
    price: 0,
    stock: 0,
    status: '',
    addedOn: '',
    imageName: ''
  }

  private service = inject(ProductsService);
  private router = inject(Router);
  private toastr = inject(ToastrService);
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

  update() {

    const formData = new FormData();
    formData.append('product', JSON.stringify(this.product));

    if (this.selectedFile) {
      formData.append('image', this.selectedFile);
    }
    if (this.productId != null) {
      this.service.update(this.productId, formData).subscribe({
        next: (response: ProductResponseDTO) => {
          this.product = response;
          this.toastr.success('Product details updated successfully');
          this.router.navigate(['/products']);
        },
        error: (err: HttpErrorResponse) => {
          console.log(err);
          this.toastr.error('Something went wrong');
        }
      });
    }
  }

  reset() {

    this.service.view(this.productId!).subscribe({
      next: (response) => {

        this.product = response;

        this.previewUrl = null;

        this.selectedFile = undefined as any;
      }
    });
  }

  onFileSelected(event: Event) {

    const input = event.target as HTMLInputElement;

    if (input.files && input.files.length > 0) {

      this.selectedFile = input.files[0];

      const reader = new FileReader();

      reader.onload = () => {
        this.previewUrl = reader.result;
      };

      reader.readAsDataURL(this.selectedFile);
    }
  }

  removeImage(event: Event) {

    event.stopPropagation();

    this.selectedFile = undefined as any;

    this.previewUrl = null;
  }

  cancel() {
    this.router.navigate(['/products']);
  }
}
