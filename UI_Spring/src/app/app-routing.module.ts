import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {CertificationDetailComponent} from "./components/certification-detail/certification-detail.component";
import {HomeComponent} from "./components/home/home.component";
import {InventoryComponent} from "./components/inventory/inventory.component";
import {CreatecategoryComponent} from "./components/admin/category/createcategory/createcategory.component";
import {MyaccountComponent} from "./components/myaccount/myaccount.component";
import {LoginComponent} from "./components/login/login.component";
import {RegisterComponent} from "./components/register/register.component";
import {NotfoundComponent} from "./shared/notfound/notfound.component";
import {CartComponent} from "./shared/cart/cart.component";
import {AuthGuard} from "./guards/auth.guard";
import {OrdersComponent} from "./components/orders/orders.component";
import {CertificationsComponent} from "./components/admin/certifications/certifications.component";
import {CategoryComponent} from "./components/admin/category/category.component";
import {AddmodifyComponent} from "./components/admin/users/addmodify/addmodify.component";
import {UsersComponent} from "./components/admin/users/users.component";
import {
  CreatecertificationComponent
} from "./components/admin/certifications/createcertification/createcertification.component";

const routes: Routes = [
  {path:'',redirectTo:'/home',pathMatch:'full'},
  {path:'home',component:HomeComponent},
  {path:'certifications/:id',component:CertificationDetailComponent},
  {path:'inventory/:username'/*,canActivate:[AuthGuard]*/,component:InventoryComponent},

  //admin routes
  {path:'certifications/create/certification'/*,canActivate:[AuthGuard]*/,component:CreatecertificationComponent},
  {path:'certifications/modify/certification/:gameId'/*,canActivate:[AuthGuard]*/,component:CreatecertificationComponent},

  {path:'categories/create/category'/*,canActivate:[AuthGuard]*/,component:CreatecategoryComponent},
  {path:'categories/modify/category/:categoryId'/*,canActivate:[AuthGuard]*/,component:CreatecategoryComponent},

  {path:'certifications'/*,canActivate:[AuthGuard]*/,component:CertificationsComponent},
  {path:'categories'/*,canActivate:[AuthGuard]*/,component:CategoryComponent},

  {path:'users/modify/user/:userId'/*,canActivate:[AuthGuard]*/,component:AddmodifyComponent},
  {path:'users/create/user'/*,canActivate:[AuthGuard]*/,component:AddmodifyComponent},
  {path:'users'/*,canActivate:[AuthGuard]*/,component:UsersComponent},
  //admin routes


  {path:'user/:username'/*,canActivate:[AuthGuard]*/,component:MyaccountComponent},
  {path:'cart'/*,canActivate:[AuthGuard]*/,component:CartComponent},
  {path:'orders/:username'/*,canActivate:[AuthGuard]*/,component:OrdersComponent},

  {path:'login',component:LoginComponent},
  {path:'register',component:RegisterComponent},
  {path:'**',component:NotfoundComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
