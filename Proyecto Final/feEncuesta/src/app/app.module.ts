import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import {MatBottomSheetModule,MatButtonModule, MatToolbarModule, MatIconModule, MatSidenavModule, MatMenuModule, 
  MatDividerModule, MatProgressSpinnerModule, MatCardModule, MatListModule, MatFormFieldModule, 
  MatTableModule, MatPaginatorModule, MatInputModule, MatDialogModule,/* MatPaginatorIntl,*/ MatSelectModule, 
  MatSnackBarModule, MatGridListModule, MatProgressBarModule, MatExpansionModule,MatRadioModule,MAT_RADIO_DEFAULT_OPTIONS}
from '@angular/material';
// import { MatPaginatorImpl } from '../_shared/mat-paginator';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { ErrorComponent } from './error/error.component';
import { HomeComponent } from './home/home.component';
import { LogoutComponent } from './logout/logout.component';
import { PollComponent } from './poll/poll.component';
import { AdminComponent } from './admin/admin.component';
import {TokenInterceptorService} from './services/TokenInterceptorService';
import { EditpollComponent } from './editpoll/editpoll.component';
import { SuccessComponent } from './success/success.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    ErrorComponent,
    HomeComponent,
    LogoutComponent,
    PollComponent,
    AdminComponent,
    EditpollComponent,
    SuccessComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    MatBottomSheetModule,
    MatButtonModule, 
    MatToolbarModule,
    MatIconModule, 
    MatSidenavModule, 
    MatMenuModule, 
    MatDividerModule, 
    MatProgressSpinnerModule, 
    MatCardModule, 
    MatListModule, 
    MatFormFieldModule, 
    MatTableModule, 
    MatPaginatorModule, 
    MatInputModule, 
    MatDialogModule, 
  /*  MatPaginatorIntl, */
    MatSelectModule, 
    MatSnackBarModule, 
    MatGridListModule, 
    MatProgressBarModule, 
    MatExpansionModule,
    MatRadioModule,
    ReactiveFormsModule,
    FormsModule,
    HttpClientModule,
    BrowserAnimationsModule
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptorService,
      multi: true
    },
    {
      provide: MAT_RADIO_DEFAULT_OPTIONS,
      useValue: { color: 'accent' },
  }
  ],
  bootstrap: [AppComponent],
  entryComponents:[
    ErrorComponent,
    EditpollComponent,
    SuccessComponent
  ]
})
export class AppModule { }
