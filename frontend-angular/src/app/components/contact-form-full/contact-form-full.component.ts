import {Component, OnDestroy} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {AuthenticatedUserModel} from "../../models/authenticated-user.model";
import {ContactDetailsDataModel} from "../../models/contact-details-data.model";
import {ContactsService} from "../../services/contacts.service";
import {ActivatedRoute, Router} from "@angular/router";
import {AccountService} from "../../services/account.service";
import {validationHandler} from "../../utils/validationHandler";
import {ContactFormFullService} from "../../services/contact-form-full.service";
import {PhoneDataModel} from "../../models/phone-data.model";
import {AddressDataModel} from "../../models/address-data.model";
import {ContactDataModel} from "../../models/contact-data.model";

@Component({
  selector: 'app-contact-form-full',
  templateUrl: './contact-form-full.component.html',
  styleUrls: ['./contact-form-full.component.css']
})
export class ContactFormFullComponent implements OnDestroy {


  contactDetails!: ContactDetailsDataModel;
  contactPhones!: PhoneDataModel[];
  contactAddresses!: AddressDataModel[];

  contactForm: FormGroup;
  loggedInUser!: AuthenticatedUserModel;


  constructor(private contactService: ContactsService,
              private formBuilder: FormBuilder,
              private router: Router,
              private route: ActivatedRoute,
              private accountService: AccountService,
              private contactFormFullService: ContactFormFullService
  ) {
    this.contactForm = this.formBuilder.group({
      firstName: ['', [Validators.required, Validators.minLength(3)]],
      lastName: [''],
      email: ['', [Validators.required, Validators.minLength(3), Validators.email]],
      birthDate: [''],
      mothersName: [''],
      ssId: [''],
      taxId: ['']
    });
  }

  ngOnInit(): void {
    this.accountService.loggedInUser.subscribe({
      next: value => {
        this.loggedInUser = value;
        console.log("User ID from behav. subject: " + value.userId);
      }
    });

    this.contactFormFullService.contactFormDetails.subscribe({
      next: value => {
        this.contactDetails = value;
      }
    });

    this.contactFormFullService.phoneList.subscribe({
      next: value => {
        this.contactPhones = value;
        console.log("Contact Phones length from subject: " + this.contactPhones.length)
      }
    });

    this.contactFormFullService.addressList.subscribe({
      next: value => {
        this.contactAddresses = value;
      }
    });

    this.route.paramMap.subscribe({
      next: param => {
        const idFromParamMap = param.get('id');
        console.log("idFromParamMap: " + idFromParamMap);
        if (idFromParamMap) {
          this.contactService.getFullContactById(Number(idFromParamMap)).subscribe({
            next:
              (data) => {
                this.contactDetails = data.contactBasicData;
                this.contactPhones = data.phoneList;
                this.contactAddresses = data.addressList;
                this.contactFormFullService.contactFormDetails.next(this.contactDetails);
                this.contactFormFullService.phoneList.next(this.contactPhones);
                this.contactFormFullService.addressList.next(this.contactAddresses);
                this.fillContactForm();
              },
            error: () => {
            },
            complete: () => {
            }
          });
        } else {
          //route did not contain contact id as path variable, so creating a new contact,
          // or this is the case when returning back from phone/address form
          console.log("Route without contact Id..")
          this.fillContactForm();
        }
      }
    })
  }

  private fillContactForm() {
    this.contactForm.patchValue({
      'firstName': this.contactDetails.firstName,
      'lastName': this.contactDetails.lastName,
      'email': this.contactDetails.email,
      'birthDate': this.contactDetails.birthDate,
      'mothersName': this.contactDetails.mothersName,
      'ssId': this.contactDetails.ssId,
      'taxId': this.contactDetails.taxId
    });
  }

  submitData() {
    let contactData: ContactDetailsDataModel = this.contactForm.value;
    contactData.userId = this.loggedInUser.userId;
    let contactFullData: ContactDataModel = {
      contactBasicData: contactData,
      phoneList: this.contactPhones,
      addressList: this.contactAddresses
    }
    console.log("Logged in User ID in submitData : " + this.loggedInUser.userId);
    //If contactDetails has a valid id, then this is a contact to be updated
    if (this.contactDetails.id) {
      contactData.id = this.contactDetails.id;
      this.contactService.sendContactFullUpdate(contactFullData).subscribe({
        next: () => {
          this.clearContactSubjects();
          this.router.navigate(['contacts']);
        },
        error: (err) => {
          validationHandler(err, this.contactForm);
          console.log(err);
        },
        complete: () => {
          console.log("Contact updated successfully.");
        }
      });
    } else
      //If contactDetails has no id, then this is a new contact to be registered
    {
      this.contactService.sendContactFullRegistration(contactFullData).subscribe({
        next: () => {
          this.clearContactSubjects();
          this.router.navigate(['contacts']);
        },
        error: (err) => {
          validationHandler(err, this.contactForm);
          console.log(err);
        },
        complete: () => {
          console.log("New contact saved successfully.");
        }
      });
    }
  }

  cancelForm() {
    this.clearContactSubjects();
    this.router.navigate(['contacts']);
  }

  ngOnDestroy(): void {
    this.clearContactSubjects();
  }

  saveContactDetailsToSubject() {
    let contactData: ContactDetailsDataModel = this.contactForm.value;
    contactData.id = this.contactFormFullService.contactFormDetails.getValue().id;
    contactData.userId = this.contactFormFullService.contactFormDetails.getValue().userId;
    this.contactFormFullService.contactFormDetails.next(contactData);
  }

  clearContactSubjects() {
    this.contactFormFullService.contactFormDetails.next(this.contactFormFullService.INITIAL_CONTACT_DETAILS);
    this.contactFormFullService.phoneList.next(this.contactFormFullService.INITIAL_PHONE_LIST);
    this.contactFormFullService.phoneList.next(new Array<PhoneDataModel>());
    this.contactFormFullService.addressList.next(new Array<AddressDataModel>());
  }
}
