import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {PhoneDataModel} from "../../models/phone-data.model";
import {ActivatedRoute, Router} from "@angular/router";
import {AddressService} from "../../services/address.service";
import {AddressDataModel} from "../../models/address-data.model";

@Component({
  selector: 'app-address-from',
  templateUrl: './address-from.component.html',
  styleUrls: ['./address-from.component.css']
})
export class AddressFromComponent implements OnInit {

  contactId!: number;

  addressForm: FormGroup;
  // loggedInUser!: AuthenticatedUserModel;
  existingAddressData!: PhoneDataModel;

  constructor(private addressService: AddressService,
              private formBuilder: FormBuilder,
              // private accountService: AccountService,
              private router: Router,
              private activatedRoute: ActivatedRoute) {
    this.addressForm = this.formBuilder.group({
      zipCode: [''],
      city: ['', Validators.required],
      street: ['', Validators.required],
      houseNumber: ['', Validators.required],
      note: ['']
    });
  }

  ngOnInit(): void {
    this.activatedRoute.paramMap.subscribe({
      next: param => {
        this.contactId = Number(param.get('id'));
        console.log("contactId: " + this.contactId);
      }
    })
  }

  submitData() {
    const addressData: AddressDataModel = this.addressForm.value;
    addressData.addressOwner = this.contactId;
    this.addressService.sendAddressRegistration(addressData).subscribe(
      {
        next: () => {
        },
        error: (err) => {
          console.log(err)
        },
        complete: () => {
          console.log("New address saved successfully.");
          this.router.navigate(['contactDetails', this.contactId]);
        }
      });
  }

  cancelForm() {
    this.router.navigate(['contactDetails', this.contactId]);
  }
}
