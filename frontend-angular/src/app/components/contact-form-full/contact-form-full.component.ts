import {Component} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {AuthenticatedUserModel} from "../../models/authenticated-user.model";
import {ContactDetailsDataModel} from "../../models/contact-details-data.model";
import {ContactsService} from "../../services/contacts.service";
import {ActivatedRoute, Router} from "@angular/router";
import {AccountService} from "../../services/account.service";
import {validationHandler} from "../../utils/validationHandler";
import {ContactFormFullService} from "../../services/contact-form-full.service";
import {PhonesService} from "../../services/phones.service";
import {AddressService} from "../../services/address.service";
import {PhoneDataModel} from "../../models/phone-data.model";
import {AddressDataModel} from "../../models/address-data.model";

@Component({
  selector: 'app-contact-form-full',
  templateUrl: './contact-form-full.component.html',
  styleUrls: ['./contact-form-full.component.css']
})
export class ContactFormFullComponent {


  contactDetails!: ContactDetailsDataModel;
  contactPhones!: PhoneDataModel[];
  contactAddresses!: AddressDataModel[];

  contactForm: FormGroup;
  loggedInUser!: AuthenticatedUserModel;
  existingContactData!: ContactDetailsDataModel;


  constructor(private contactService: ContactsService,
              private formBuilder: FormBuilder,
              private router: Router,
              private route: ActivatedRoute,
              private accountService: AccountService,
              private contactFormFullService: ContactFormFullService,
              private phonesService: PhonesService,
              private addressService: AddressService) {
    this.contactForm = this.formBuilder.group({
      firstName: ['', [Validators.required, Validators.minLength(3)]],
      lastName: ['',],
      email: ['', [Validators.required, Validators.minLength(3), Validators.email]],
      birthDate: ['',],
      mothersName: ['',],
      ssId: ['',],
      taxId: ['',]
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
          this.contactService.getContactById(Number(idFromParamMap)).subscribe({
            next:
              (data) => {
                // this.existingContactData = data;
                this.contactDetails = data;
                this.contactFormFullService.contactFormDetails.next(this.contactDetails);
                this.fillContactForm();
              },
            error: () => {
            },
            complete: () => {
            }
          });

          this.phonesService.getPhonesByContactId(Number(idFromParamMap)).subscribe({
            next: (data) => {
              this.contactPhones = data;
              this.contactFormFullService.phoneList.next(this.contactPhones);
              // this.fillPhonesList();
            },
            error: () => {
            },
            complete: () => {
            }
          })

          this.addressService.getAddressesByContactId(Number(idFromParamMap)).subscribe({
            next: (data) => {
              this.contactAddresses = data;
              this.contactFormFullService.addressList.next(this.contactAddresses);
              // this.fillAddressesList();
            },
            error: () => {
            },
            complete: () => {
            }
          })
        } else {
          //route did not contain contact id as path variable, so creating a new contact, or returning back from phone/address form
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
    console.log("Logged in User ID in submitData : " + this.loggedInUser.userId);
    if (this.contactDetails.id) {
      contactData.id = this.contactDetails.id;
      this.contactService.sendContactUpdate(contactData).subscribe(
        {
          next: () => {
            console.log("Phones list length: " + this.contactPhones.length);
            console.log("Contact ID: " + this.contactDetails.id);
            this.phonesService.sendPhonesData(this.contactPhones, this.contactDetails.id).subscribe({
              next: () => {
                console.log("Phones added")
              },
              error: (err) => {
                console.log(err)
              }
            });
            //TODO send addresses list to backend contactData.id
            console.log("Contact updated successfully.");
            this.router.navigate(['contacts']);
          },
          error: (err) => {
            validationHandler(err, this.contactForm);
            console.log(err)
          },
          complete: () => {
          }
        });
    } else {
      this.contactService.sendContactRegistration(contactData).subscribe(
        {
          next: (contactIdFromServer: number) => {
            this.phonesService.sendPhonesData(this.contactPhones, contactIdFromServer);
            //TODO send addresses list to backend contactIdFromServer
            console.log("New contact saved successfully.");
            this.router.navigate(['contacts']);
          },
          error: (err) => {
            validationHandler(err, this.contactForm);
            console.log(err)
          },
          complete: () => {
          }
        });
    }
  }

  cancelForm() {
    this.router.navigate(['contacts']);
  }


}
