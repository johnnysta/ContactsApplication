import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ContactsListComponent} from "./components/contacts-list/contacts-list.component";
import {LoginComponent} from "./components/login/login.component";
import {ContactFormComponent} from "./components/contact-form/contact-form.component";
import {ContactDetailsComponent} from "./components/contact-details/contact-details.component";
import {PhoneFormComponent} from "./components/phone-form/phone-form.component";
import {AddressFromComponent} from "./components/address-from/address-from.component";

const routes: Routes = [
  {path: "login", component: LoginComponent},
  {path: "contacts", component: ContactsListComponent},
  {path: "contactsForm", component: ContactFormComponent},
  {path: "contactDetails/:id/:fullName", component: ContactDetailsComponent},
  {path: "phoneForm/:id", component: PhoneFormComponent},
  {path: "addressForm/:id", component: AddressFromComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
