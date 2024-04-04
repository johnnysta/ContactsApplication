import {Component} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {AddressService} from "../../services/address.service";
import {ActivatedRoute, Router} from "@angular/router";
import {AddressDataModel} from "../../models/address-data.model";
import {ContactFormFullService} from "../../services/contact-form-full.service";
import {ContactDetailsDataModel} from "../../models/contact-details-data.model";

@Component({
  selector: 'app-address-form-local',
  templateUrl: './address-form-local.component.html',
  styleUrls: ['./address-form-local.component.css']
})
export class AddressFormLocalComponent {

  addressIndex: number = -1;
  addressForm: FormGroup;
  addressList!: AddressDataModel[];
  contactFormDetails!: ContactDetailsDataModel;

  constructor(private addressService: AddressService,
              private formBuilder: FormBuilder,
              private router: Router,
              private activatedRoute: ActivatedRoute,
              private contactFormFullService: ContactFormFullService) {
    this.addressForm = this.formBuilder.group({
      zipCode: [''],
      city: ['', [Validators.required, Validators.minLength(2)]],
      street: ['', [Validators.required, Validators.minLength(2)]],
      houseNumber: ['', Validators.required],
      note: ['']
    });
  }

  ngOnInit(): void {

    this.contactFormFullService.addressList.subscribe({
      next: value => this.addressList = value
    });

    this.contactFormFullService.contactFormDetails.subscribe({
      next: value => this.contactFormDetails = value
    });


    this.activatedRoute.paramMap.subscribe({
      next: param => {
        this.addressIndex = Number(param.get('id'));
        console.log("addressIndex: " + this.addressIndex);
        if (this.addressIndex != -1) {
          this.fillAddressForm(this.addressIndex);
        } else {
          //Add new phone number clicked
        }

      }
    })
  }

  private fillAddressForm(addressIndex: number) {
    this.addressForm.patchValue({
      'zipCode': this.addressList[addressIndex].zipCode,
      'city': this.addressList[addressIndex].city,
      'street': this.addressList[addressIndex].street,
      'houseNumber': this.addressList[addressIndex].houseNumber,
      'note': this.addressList[addressIndex].note
    });
  }


  submitData() {
    if (this.addressIndex === -1) {
      const addressData: AddressDataModel = this.addressForm.value;
      addressData.addressOwner = this.contactFormDetails.id;
      addressData.id = 0;
      addressData.isDeleted = false;
      this.addressList.push(addressData);
    } else {
      this.addressList[this.addressIndex].zipCode = this.addressForm.value['zipCode'];
      this.addressList[this.addressIndex].city = this.addressForm.value['city'];
      this.addressList[this.addressIndex].street = this.addressForm.value['street'];
      this.addressList[this.addressIndex].houseNumber = this.addressForm.value['houseNumber'];
      this.addressList[this.addressIndex].note = this.addressForm.value['note'];
    }
    this.contactFormFullService.addressList.next(this.addressList);
    this.router.navigate(['contactsFormFull']);
  }

  cancelForm() {
    this.router.navigate(['contactsFormFull']);
  }


}
