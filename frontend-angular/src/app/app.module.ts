import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {NavbarComponent} from './components/navbar/navbar.component';
import {ContactsListComponent} from './components/contacts-list/contacts-list.component';
import {LoginComponent} from './components/login/login.component';
import {ReactiveFormsModule} from "@angular/forms";
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {UserRegistrationComponent} from './components/user-registration/user-registration.component';
import {AuthInterceptor} from "./utils/auth-interceptor.service";
import {HomeComponent} from './components/home/home.component';
import {ContactFormFullComponent} from './components/contact-form-full/contact-form-full.component';
import {PhonesListSubComponent} from './components/phones-list-sub/phones-list-sub.component';
import {PhoneFormLocalComponent} from './components/phone-form-local/phone-form-local.component';
import {AddressesListSubComponent} from './components/addresses-list-sub/addresses-list-sub.component';
import {AddressFormLocalComponent} from './components/address-form-local/address-form-local.component';
import { UserChangePwComponent } from './components/user-change-pw/user-change-pw.component';

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    ContactsListComponent,
    LoginComponent,
    UserRegistrationComponent,
    HomeComponent,
    ContactFormFullComponent,
    PhonesListSubComponent,
    PhoneFormLocalComponent,
    AddressesListSubComponent,
    AddressFormLocalComponent,
    UserChangePwComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule
  ],
  providers: [{provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true}],
  bootstrap: [AppComponent]
})
export class AppModule {
}
