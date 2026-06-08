import { Routes } from '@angular/router';
import { CreateComponent } from './create/create.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { LoginComponent } from './login/login.component';
import { OrderComponent } from './order/order.component';
import { ProductComponent } from './product/product.component';
import { ViewComponent } from './view/view.component';
import { UpdateComponent } from './update/update.component';

export const routes: Routes = [
    { path: 'create', component: CreateComponent },
    { path: 'home', component: DashboardComponent },
    { path: 'login', component: LoginComponent },
    { path: 'order', component: OrderComponent },
    { path: 'products', component: ProductComponent },
    { path: 'view/:productId', component: ViewComponent },
    { path: 'update/:productId', component: UpdateComponent },
    { path: '', redirectTo: 'products', pathMatch: 'full' }
];
