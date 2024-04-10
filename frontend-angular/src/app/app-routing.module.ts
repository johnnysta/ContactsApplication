import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ContactsListComponent} from "./components/contacts-list/contacts-list.component";
import {LoginComponent} from "./components/login/login.component";
import {UserRegistrationComponent} from "./components/user-registration/user-registration.component";
import {HomeComponent} from "./components/home/home.component";
import {ContactFormFullComponent} from "./components/contact-form-full/contact-form-full.component";
import {PhoneFormLocalComponent} from "./components/phone-form-local/phone-form-local.component";
import {AddressFormLocalComponent} from "./components/address-form-local/address-form-local.component";
import {UserChangePwComponent} from "./components/user-change-pw/user-change-pw.component";

const routes: Routes = [
  {path: "login", component: LoginComponent},
  {path: "registerUser", component: UserRegistrationComponent},
  {path: "contacts", component: ContactsListComponent},
  {path: "phoneFormLocal/:id", component: PhoneFormLocalComponent},
  {path: "phoneFormLocal", component: PhoneFormLocalComponent},
  {path: "addressFormLocal", component: AddressFormLocalComponent},
  {path: "addressFormLocal/:id", component: AddressFormLocalComponent},
  {path: "contactsFormFull/:id", component: ContactFormFullComponent},
  {path: "contactsFormFull", component: ContactFormFullComponent},
  {path: "userChangePw", component: UserChangePwComponent},
  {path: "home", component: HomeComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
