import {Component} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";
import {ContactsService} from "../../services/contacts.service";
import {ContactDetailsDataModel} from "../../models/contact-details-data.model";

@Component({
  selector: 'app-contact-form',
  templateUrl: './contact-form.component.html',
  styleUrls: ['./contact-form.component.css']
})
export class ContactFormComponent {

  contactForm: FormGroup;
  // loggedInUser!: AuthenticatedUserModel;
  existingContactData!: ContactDetailsDataModel;


  constructor(private contactService: ContactsService,
              private formBuilder: FormBuilder,
              // private accountService: AccountService,
              private router: Router,
              private route: ActivatedRoute) {
    this.contactForm = this.formBuilder.group({
      firstName: ['', Validators.required],
      lastName: ['',],
      email: ['', Validators.required],
      birthDate: ['',],
      mothersName: ['',],
      ssId: ['',],
      taxId: ['',],

    });
  }

  submitData() {
    let contactData: ContactDetailsDataModel = this.contactForm.value;
    contactData.userId = 1;
    this.contactService.sendContactRegistration(contactData).subscribe(
      {
        next: () => {
        },
        error: (err) => {
          console.log(err)
        },
        complete: () => {
          console.log("New contact saved successfully.");
          this.router.navigate(['contacts']);
        }
      });
  }
}
