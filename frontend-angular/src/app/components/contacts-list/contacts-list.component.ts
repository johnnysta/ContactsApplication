import {Component, OnInit} from '@angular/core';
import {ContactListItemModel} from "../../models/contact-list-item.model";
import {ContactsService} from "../../services/contacts.service";
import {AuthenticatedUserModel} from "../../models/authenticated-user.model";
import {Router} from "@angular/router";
import {AccountService} from "../../services/account.service";


@Component({
  selector: 'app-contacts-list',
  templateUrl: './contacts-list.component.html',
  styleUrls: ['./contacts-list.component.css']
})
export class ContactsListComponent implements OnInit {

  loggedInUser!: AuthenticatedUserModel;

  contacts!: ContactListItemModel[];

  currentContactId!: number;
  currentContactFirst!: string;
  currentContactLast!: string;

  constructor(private contactsService: ContactsService,
              private router: Router,
              private accountService: AccountService) {
  }

  ngOnInit(): void {
    this.accountService.loggedInUser.subscribe({
      next: value => {
        this.loggedInUser = value;
        this.loadContacts();
      }
    });
  }

  loadContacts() {
    if (this.loggedInUser && this.loggedInUser.userId != 0) {
      this.contactsService.getContactsByUserId(this.loggedInUser.userId).subscribe({
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

