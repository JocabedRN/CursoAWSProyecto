import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {LoginComponent} from './login/login.component';
import {LogoutComponent} from './logout/logout.component';
import { HomeComponent } from './home/home.component';
import { AdminComponent } from './admin/admin.component';
import { PollComponent } from './poll/poll.component';


const routes: Routes = [{path:'login', component:LoginComponent},
  {path:'logout',component:LogoutComponent},
  {path:'home',component:HomeComponent, children:[
    {path:'admin', component:AdminComponent},
    {path:'poll',component:PollComponent}
  ]},
  {path:'**',redirectTo:'login',pathMatch:'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
