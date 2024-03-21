import {Component, Input, OnInit} from '@angular/core';
import {ContactDetailsDataModel} from "../../models/contact-details-data.model";
import {ContactsService} from "../../services/contacts.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-contact-basic-data',
  templateUrl: './contact-basic-data.component.html',
  styleUrls: ['./contact-basic-data.component.css']
})
export class ContactBasicDataComponent implements OnInit {

  @Input() contactId!: number;

  contactDetails!: ContactDetailsDataModel;

  constructor(private contactsService: ContactsService,
              private router: Router) {
  }

  ngOnInit(): void {
    this.contactsService.getContactById(this.contactId).subscribe({
      next: (data) => {
        this.contactDetails = data
      },
      error: (err: any) => {
        console.log(err)
      },
      complete: () => {
      }
    });
  }


  editContactDetails(contactId: number) {
    this.router.navigate(["contactsForm", contactId]);
  }

  backToMyContacts() {
    this.router.navigate(["contacts"])
  }
}
