import {Component, Input, OnInit} from '@angular/core';
import {ContactListItemModel} from "../../models/contact-list-item.model";
import {ContactsService} from "../../services/contacts.service";
import {AuthenticatedUserModel} from "../../models/authenticated-user.model";
import {Router} from "@angular/router";


@Component({
  selector: 'app-contacts-list',
  templateUrl: './contacts-list.component.html',
  styleUrls: ['./contacts-list.component.css']
})
export class ContactsListComponent implements OnInit {

  // @Input() loggedInUser: AuthenticatedUserModel;

  contacts!: ContactListItemModel[];

  currentContactId!: number;
  currentContactFirst!: string;
  currentContactLast!: string;

  constructor(private contactsService: ContactsService,
              private router: Router) {
  }

  ngOnInit(): void {
    this.loadContacts();
  }

  loadContacts() {
    this.contactsService.getContactsByUserId(1).subscribe({
      next: value => {
        this.contacts = value;
      },
      error: err => {
        console.log(err);
      },
      complete: () => {
        console.log("Contacts for user retrieved successfully");
      }
    });
  }


  openDeleteModal(id: number, firstName: string, lastName: string) {
    const modelDiv = document.getElementById('confirmDeleteModal');
    if (modelDiv != null) {
      console.log("Delete Modal opened..")
      modelDiv.style.display = 'block';
      this.currentContactId = id;
      this.currentContactFirst = firstName;
      this.currentContactLast = lastName;
    }
  }


  closeDeleteModal() {
    const modelDiv = document.getElementById('confirmDeleteModal');
    if (modelDiv != null) {
      modelDiv.style.display = 'none';
    }
  }

  deleteContact() {
    this.contactsService.deleteContactById(this.currentContactId).subscribe({
      next: () => {
      },
      error: (err: any) => {
        console.log(err)
      },
      complete: () => {
        console.log("Contact with id " + this.currentContactId + " was deleted successfully.");
        this.loadContacts();
      }
    });
    this.closeDeleteModal();
  }

  showDetails(item: ContactListItemModel) {
    this.router.navigate(['contactDetails', item.id]);
  }

  addNewContact() {
    this.router.navigate(["contactsForm"]);
  }
}

