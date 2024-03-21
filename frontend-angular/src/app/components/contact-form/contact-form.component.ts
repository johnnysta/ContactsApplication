import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";
import {ContactsService} from "../../services/contacts.service";
import {ContactDetailsDataModel} from "../../models/contact-details-data.model";

@Component({
  selector: 'app-contact-form',
  templateUrl: './contact-form.component.html',
  styleUrls: ['./contact-form.component.css']
})
export class ContactFormComponent implements OnInit {

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
      taxId: ['',]
    });
  }

  ngOnInit(): void {
    this.route.paramMap.subscribe({
      next: param => {
        const idFromParamMap = param.get('id');
        console.log("idFromParamMap: " + idFromParamMap);
        if (idFromParamMap) {
          this.contactService.getContactById(Number(idFromParamMap)).subscribe({
            next:
              (data) => {
                this.existingContactData = data;
              },
            error: () => {
            },
            complete: () => {
              this.fillContactForm(this.existingContactData);
            }
          });
        }
      }
    })
  }

  private fillContactForm(existingContactData: ContactDetailsDataModel) {
    this.contactForm.patchValue({
      'firstName': existingContactData.firstName,
      'lastName': existingContactData.lastName,
      'email': existingContactData.email,
      'birthDate': existingContactData.birthDate,
      'mothersName': existingContactData.mothersName,
      'ssId': existingContactData.ssId,
      'taxId': existingContactData.taxId
    });
  }

  submitData() {
    let contactData: ContactDetailsDataModel = this.contactForm.value;
    //Until authentication not works, we use 1 for logged in user id:
    contactData.userId = 1;
    if (this.existingContactData) {
      contactData.id = this.existingContactData.id;
      this.contactService.sendContactUpdate(contactData).subscribe(
        {
          next: () => {
          },
          error: (err) => {
            console.log(err)
          },
          complete: () => {
            this.router.navigate(['contactDetails', contactData.id]);
          }
        });
    } else {
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

  cancelForm() {
    this.router.navigate(['contacts']);
  }
}
