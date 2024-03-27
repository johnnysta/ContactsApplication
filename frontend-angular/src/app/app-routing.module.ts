import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ContactsListComponent} from "./components/contacts-list/contacts-list.component";
import {LoginComponent} from "./components/login/login.component";
import {ContactFormComponent} from "./components/contact-form/contact-form.component";
import {ContactDetailsComponent} from "./components/contact-details/contact-details.component";
import {PhoneFormComponent} from "./components/phone-form/phone-form.component";
import {AddressFromComponent} from "./components/address-from/address-from.component";
import {UserRegistrationComponent} from "./components/user-registration/user-registration.component";
import {HomeComponent} from "./components/home/home.component";

const routes: Routes = [
  {path: "login", component: LoginComponent},
  {path: "registerUser", component: UserRegistrationComponent},
  {path: "contacts", component: ContactsListComponent},
  {path: "contactsForm", component: ContactFormComponent},
  {path: "contactsForm/:id", component: ContactFormComponent},
  {path: "contactDetails/:id", component: ContactDetailsComponent},
  {path: "phoneForm/:id", component: PhoneFormComponent},
  {path: "addressForm/:id", component: AddressFromComponent},
  {path: "home", component: HomeComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
