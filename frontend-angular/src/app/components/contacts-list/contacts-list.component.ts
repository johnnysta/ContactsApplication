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

  sortByField = "firstName";
  ascDesc = 1;   //Ascending: 1, descending: -1
  collator = new Intl.Collator('hu');

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
          this.sortContacts();
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
    // this.router.navigate(['contactDetails', item.id]);
    this.router.navigate(['contactsFormFull', item.id]);
  }

  addNewContact() {
    // this.router.navigate(["contactsForm"]);
    this.router.navigate(["contactsFormFull"]);
  }

  private sortContacts() {
    console.log("Sortbyfield in sortcontacts: " + this.sortByField);
    if (this.sortByField === "firstName") {
      this.contacts.sort((a, b) => {
        if (this.collator.compare(a.firstName, b.firstName) == -1) {
          return -1 * this.ascDesc;
        }
        if (this.collator.compare(a.firstName, b.firstName) == 1) {
          return 1 * this.ascDesc;
        }
        if (this.collator.compare(a.lastName, b.lastName) == -1) {
          return -1 * this.ascDesc;
        }
        if (this.collator.compare(a.lastName, b.lastName) == 1) {
          return 1 * this.ascDesc;
        }
        return 0;
      })
    } else if (this.sortByField === "lastName") {
      this.contacts.sort((a, b) => {
        if (this.collator.compare(a.lastName, b.lastName) == -1) {
          return -1 * this.ascDesc;
        }
        if (this.collator.compare(a.lastName, b.lastName) == 1) {
          return 1 * this.ascDesc;
        }
        if (this.collator.compare(a.firstName, b.firstName) == -1) {
          return -1 * this.ascDesc;
        }
        if (this.collator.compare(a.firstName, b.firstName) == 1) {
          return 1 * this.ascDesc;
        }
        return 0;
      })


    }
  }

  orderBy(sortByField: string) {
    // console.log(sortByField);
    // console.log("currently: " + this.sortByField);
    if (sortByField === this.sortByField) {
      this.ascDesc *= -1;
    } else {
      this.sortByField = sortByField;
      this.ascDesc = 1;
    }
    this.sortContacts();
  }


}

