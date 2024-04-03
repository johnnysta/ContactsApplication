import {Component, Input} from '@angular/core';
import {PhoneDataModel} from "../../models/phone-data.model";
import {Router} from "@angular/router";
import {ContactFormFullService} from "../../services/contact-form-full.service";

@Component({
  selector: 'app-phones-list-sub',
  templateUrl: './phones-list-sub.component.html',
  styleUrls: ['./phones-list-sub.component.css']
})
export class PhonesListSubComponent {

  // @ts-ignore
  @Input() contactId: number;


  phones!: PhoneDataModel[];
  currentPhoneIndex!: number;
  currentPhoneNumber!: string;

  constructor(private contactFormFullService: ContactFormFullService,
              private router: Router
  ) {
  }

  ngOnInit(): void {
    this.loadPhones();
  }

  loadPhones() {
    this.contactFormFullService.phoneList.subscribe({
      next: value => {
        this.phones = value;
        console.log("Phones list size in phones list component's service subscription: " + this.phones.length);
      },
      error: err => {
        console.log(err);
      }
    })
  }


  openDeleteModal(index: number, phoneNumber: string) {
    const modelDiv = document.getElementById('confirmPhoneDeleteModal');
    if (modelDiv != null) {
      console.log("Delete Modal opened..")
      modelDiv.style.display = 'block';
      this.currentPhoneIndex = index;
      this.currentPhoneNumber = phoneNumber;
    }
  }


  closeDeleteModal() {
    const modelDiv = document.getElementById('confirmPhoneDeleteModal');
    if (modelDiv != null) {
      modelDiv.style.display = 'none';
    }
  }

  deletePhone() {
    this.phones[this.currentPhoneIndex].isDeleted = true;
    this.contactFormFullService.phoneList.next(this.phones);
    this.closeDeleteModal();
  }

  addNewPhone() {
    this.router.navigate(["phoneFormLocal", -1]);
  }

  editPhone(index: number) {
    this.router.navigate(["phoneFormLocal", index]);
  }


  undeletePhone(i: number) {
    this.phones[i].isDeleted = false;
    this.contactFormFullService.phoneList.next(this.phones);
  }
}
