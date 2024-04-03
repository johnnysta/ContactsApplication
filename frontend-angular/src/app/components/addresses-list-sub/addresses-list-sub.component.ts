import {Component, Input} from '@angular/core';
import {ContactFormFullService} from "../../services/contact-form-full.service";
import {Router} from "@angular/router";
import {AddressDataModel} from "../../models/address-data.model";

@Component({
  selector: 'app-addresses-list-sub',
  templateUrl: './addresses-list-sub.component.html',
  styleUrls: ['./addresses-list-sub.component.css']
})
export class AddressesListSubComponent {

  // @ts-ignore
  @Input() contactId: number;


  addresses!: AddressDataModel[];

  currentAddressIndex!: number;

  currentAddressStreet!: string;
  currentAddressHouseNumber!: string;

  constructor(private contactFormFullService: ContactFormFullService,
              private router: Router
  ) {
  }

  ngOnInit(): void {
    this.loadAddresses();
  }

  loadAddresses() {
    this.contactFormFullService.addressList.subscribe({
      next: value => {
        this.addresses = value;
        console.log("Addresses list size in addresses list component's service subscription: " + this.addresses.length);
      },
      error: err => {
        console.log(err);
      }
    })
  }


  openDeleteModal(index: number, street: string, houseNumber: string) {
    const modelDiv = document.getElementById('confirmAddressDeleteModal');
    if (modelDiv != null) {
      console.log("Delete Modal opened..")
      modelDiv.style.display = 'block';
      this.currentAddressIndex = index;
      this.currentAddressStreet = street;
      this.currentAddressHouseNumber = houseNumber;
    }
  }


  closeDeleteModal() {
    const modelDiv = document.getElementById('confirmAddressDeleteModal');
    if (modelDiv != null) {
      modelDiv.style.display = 'none';
    }
  }

  deleteAddress() {
    this.addresses[this.currentAddressIndex].isDeleted = true;
    this.contactFormFullService.addressList.next(this.addresses);
    this.closeDeleteModal();
  }

  addNewAddress() {
    this.router.navigate(["addressFormLocal", -1]);
  }

  editAddress(index: number) {
    this.router.navigate(["addressFormLocal", index]);
  }


  undeleteAddress(i: number) {
    this.addresses[i].isDeleted = false;
    this.contactFormFullService.addressList.next(this.addresses);
  }

}
