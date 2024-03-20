import {Component, Input} from '@angular/core';
import {ContactListItemModel} from "../../models/contact-list-item.model";
import {ContactsService} from "../../services/contacts.service";
import {Router} from "@angular/router";
import {PhoneDataModel} from "../../models/phone-data.model";
import {PhonesService} from "../../services/phones.service";
import {AddressDataModel} from "../../models/address-data.model";
import {AddressService} from "../../services/address.service";

@Component({
  selector: 'app-addresses-list',
  templateUrl: './addresses-list.component.html',
  styleUrls: ['./addresses-list.component.css']
})
export class AddressesListComponent {

  // @ts-ignore
  @Input() contactId: number;
  // @ts-ignore
  @Input() fullName: string;

  addresses!: AddressDataModel[];

  currentAddressId!: number;
  currentAddressStreet!: string;
  currentAddressHouseNumber!: string;

  constructor(private addressService: AddressService,
              private router: Router) {
  }

  ngOnInit(): void {
    this.loadAddresses();
  }

  loadAddresses() {
    this.addressService.getAddressesByContactId(this.contactId).subscribe({
      next: value => {
        this.addresses = value;
      },
      error: err => {
        console.log(err);
      },
      complete: () => {
        console.log("Addresses for contact retrieved successfully.");
      }
    });
  }


  openDeleteModal(id: number, street: string, houseNumber: string) {
    const modelDiv = document.getElementById('confirmDeleteModal');
    if (modelDiv != null) {
      console.log("Delete Modal opened..")
      modelDiv.style.display = 'block';
      this.currentAddressId = id;
      this.currentAddressStreet = street;
      this.currentAddressHouseNumber = houseNumber;
    }
  }


  closeDeleteModal() {
    const modelDiv = document.getElementById('confirmDeleteModal');
    if (modelDiv != null) {
      modelDiv.style.display = 'none';
    }
  }

  deleteAddress() {
    this.addressService.deleteAddressById(this.currentAddressId).subscribe({
      next: () => {
      },
      error: (err: any) => {
        console.log(err)
      },
      complete: () => {
        console.log("Address with id " + this.currentAddressId + " was deleted successfully.");
        this.loadAddresses();
      }
    });
    this.closeDeleteModal();
  }

  addNewAddress() {
    this.router.navigate(["addressForm", this.contactId]);
  }

  editAddress(id: number) {
  }


}
