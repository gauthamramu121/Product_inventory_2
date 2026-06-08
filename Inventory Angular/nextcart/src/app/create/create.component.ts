import { Component, inject, OnInit } from '@angular/core';
import { ProductRequestDTO, ProductResponseDTO } from '../model/products';
import { Router } from '@angular/router';
import { ProductsService } from '../service/products.service';
import { ToastrService } from 'ngx-toastr';
import { HttpErrorResponse } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-create',
  standalone: true,
  imports: [
    FormsModule,
    CommonModule
  ],
  templateUrl: './create.component.html',
  styleUrl: './create.component.css'
})
export class CreateComponent implements OnInit {

  availability: string[] = [];
  product: ProductRequestDTO = {
    productName: '',
    brand: '',
    price: null,
    stock: null,
  };

  selectedFile!: File;

  private router = inject(Router);
  private service = inject(ProductsService);
  private toastr = inject(ToastrService);

  ngOnInit(): void {
  }

  create() {

    const formData = new FormData();
    formData.append('product', JSON.stringify(this.product));

    formData.append('image', this.selectedFile);

    this.service.create(formData).subscribe({

      next: (response: ProductResponseDTO) => {
        this.router.navigate(['/products']);
        this.toastr.success('Product added successfully');
      },

      error: (err: HttpErrorResponse) => {
        this.toastr.error('Something went wrong');
        console.log(err);
      }
    });
  }

  cancel() {
    this.router.navigate(['/products']);
  }

  previewUrl: string | null = null;

  onFileSelected(event: any) {
    const file = event.target.files[0];
    if (file) {
      this.selectedFile = file;
      // Create preview URL
      const reader = new FileReader();
      reader.onload = (e: any) => {
        this.previewUrl = e.target.result;
      };
      reader.readAsDataURL(file);
    }
  }

  removeImage(event: Event) {
    event.stopPropagation();
    this.selectedFile = null as any;
    this.previewUrl = null;
    // Clear file input value
    const fileInput = document.querySelector('input[type="file"]') as HTMLInputElement;
    if (fileInput) fileInput.value = '';
  }

  reset(){
    this.product = {
      productName:'',
      brand:'',
      price:null,
      stock:null
    }
  }
}
