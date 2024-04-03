import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { ContactsListComponent } from './components/contacts-list/contacts-list.component';
import { LoginComponent } from './components/login/login.component';
import {ReactiveFormsModule} from "@angular/forms";
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import { ContactFormComponent } from './components/contact-form/contact-form.component';
import { PhonesListComponent } from './components/phones-list/phones-list.component';
import { AddressesListComponent } from './components/addresses-list/addresses-list.component';
import { ContactDetailsComponent } from './components/contact-details/contact-details.component';
import { PhoneFormComponent } from './components/phone-form/phone-form.component';
import { AddressFromComponent } from './components/address-from/address-from.component';
import { ContactBasicDataComponent } from './components/contact-basic-data/contact-basic-data.component';
import { UserRegistrationComponent } from './components/user-registration/user-registration.component';
import {AuthInterceptor} from "./utils/auth-interceptor.service";
import { HomeComponent } from './components/home/home.component';
import { ContactFormFullComponent } from './components/contact-form-full/contact-form-full.component';
import { PhonesListSubComponent } from './components/phones-list-sub/phones-list-sub.component';
import { PhoneFormLocalComponent } from './components/phone-form-local/phone-form-local.component';
import { AddressesListSubComponent } from './components/addresses-list-sub/addresses-list-sub.component';
import { AddressFormLocalComponent } from './components/address-form-local/address-form-local.component';

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    ContactsListComponent,
    LoginComponent,
    ContactFormComponent,
    PhonesListComponent,
    AddressesListComponent,
    ContactDetailsComponent,
    PhoneFormComponent,
    AddressFromComponent,
    ContactBasicDataComponent,
    UserRegistrationComponent,
    HomeComponent,
    ContactFormFullComponent,
    PhonesListSubComponent,
    PhoneFormLocalComponent,
    AddressesListSubComponent,
    AddressFormLocalComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule
  ],
  providers: [{ provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true }],
  bootstrap: [AppComponent]
})
export class AppModule { }
