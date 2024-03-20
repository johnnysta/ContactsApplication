import {Component, Input} from '@angular/core';
import {ContactListItemModel} from "../../models/contact-list-item.model";
import {ContactsService} from "../../services/contacts.service";
import {Router} from "@angular/router";
import {PhoneDataModel} from "../../models/phone-data.model";
import {PhonesService} from "../../services/phones.service";

@Component({
  selector: 'app-phones-list',
  templateUrl: './phones-list.component.html',
  styleUrls: ['./phones-list.component.css']
})
export class PhonesListComponent {

  // @ts-ignore
  @Input() public contactId: number;

  phones!: PhoneDataModel[];

  currentPhoneId!: number;
  currentPhoneNumber!: string;

  constructor(private phonesService: PhonesService,
              private router: Router) {
  }

  ngOnInit(): void {
    this.loadPhones();
  }

  loadPhones() {
    this.phonesService.getPhonesByContactId(this.contactId).subscribe({
      next: value => {
        this.phones = value;
      },
      error: err => {
        console.log(err);
      },
      complete: () => {
        console.log("Phones for contact retrieved successfully.");
      }
    });
  }


  openDeleteModal(id: number, phoneNumber: string) {
    const modelDiv = document.getElementById('confirmDeleteModal');
    if (modelDiv != null) {
      console.log("Delete Modal opened..")
      modelDiv.style.display = 'block';
      this.currentPhoneId = id;
      this.currentPhoneNumber = phoneNumber;
    }
  }


  closeDeleteModal() {
    const modelDiv = document.getElementById('confirmDeleteModal');
    if (modelDiv != null) {
      modelDiv.style.display = 'none';
    }
  }

  deletePhone() {
    this.phonesService.deletePhoneById(this.currentPhoneId).subscribe({
      next: () => {
      },
      error: (err: any) => {
        console.log(err)
      },
      complete: () => {
        console.log("Phone with id " + this.currentPhoneId + " was deleted successfully.");
        this.loadPhones();
      }
    });
    this.closeDeleteModal();
  }

  addNewPhone() {
    this.router.navigate(["phoneForm", this.contactId]);
  }

  editPhone(id: number) {
  }

}
