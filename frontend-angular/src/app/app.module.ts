import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { ContactsListComponent } from './components/contacts-list/contacts-list.component';
import { LoginComponent } from './components/login/login.component';
import {ReactiveFormsModule} from "@angular/forms";
import {HttpClientModule} from "@angular/common/http";
import { ContactFormComponent } from './components/contact-form/contact-form.component';
import { PhonesListComponent } from './components/phones-list/phones-list.component';
import { AddressesListComponent } from './components/addresses-list/addresses-list.component';
import { ContactDetailsComponent } from './components/contact-details/contact-details.component';
import { PhoneFormComponent } from './components/phone-form/phone-form.component';
import { AddressFromComponent } from './components/address-from/address-from.component';
import { ContactBasicDataComponent } from './components/contact-basic-data/contact-basic-data.component';

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
    ContactBasicDataComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
