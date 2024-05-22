import {Component, ElementRef, OnInit, QueryList, ViewChildren} from '@angular/core';
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

  @ViewChildren('contactItems') contactItems!: QueryList<ElementRef>;

  loggedInUser!: AuthenticatedUserModel;

  contacts!: ContactListItemModel[];

  sortByField = "firstName";
  ascDesc = 1;   //Ascending: 1, descending: -1
  collator = new Intl.Collator('hu');

  currentContactId!: number;
  currentContactFirstName!: string;
  currentContactLastName!: string;

  contactsIndex: Map<string, number> = new Map<string, number>();


  get contactsIndexArray(): { key: string, value: number }[] {
    return Array.from(this.contactsIndex, ([key, value]) => ({key, value}));
  }

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
      this.currentContactFirstName = firstName;
      this.currentContactLastName = lastName;
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
          return this.ascDesc;
        }
        if (this.collator.compare(a.lastName, b.lastName) == -1) {
          return -1 * this.ascDesc;
        }
        if (this.collator.compare(a.lastName, b.lastName) == 1) {
          return this.ascDesc;
        }
        return 0;
      });
    } else if (this.sortByField === "lastName") {
      this.contacts.sort((a, b) => {
        if (this.collator.compare(a.lastName, b.lastName) == -1) {
          return -1 * this.ascDesc;
        }
        if (this.collator.compare(a.lastName, b.lastName) == 1) {
          return this.ascDesc;
        }
        if (this.collator.compare(a.firstName, b.firstName) == -1) {
          return -1 * this.ascDesc;
        }
        if (this.collator.compare(a.firstName, b.firstName) == 1) {
          return this.ascDesc;
        }
        return 0;
      });
    }
    this.calculateIndexes();
  }

  orderBy(sortByField: string) {
    if (sortByField === this.sortByField) {
      this.ascDesc *= -1;
    } else {
      this.sortByField = sortByField;
      this.ascDesc = 1;
    }
    this.sortContacts();
  }


  private calculateIndexes() {
    console.log("calculate indexes");
    this.contactsIndex = new Map<string, number>();
    for (let i = 0; i < this.contacts.length; i++) {
      const name = (this.contacts[i][(this.sortByField === 'firstName') ? 'firstName' : 'lastName']);
      let startLetter = '';
      if (name.length > 0) {
        startLetter = name.charAt(0).toUpperCase();
      }
      if (!this.contactsIndex.has(startLetter)) {
        this.contactsIndex.set(startLetter, this.contacts[i].id);
      }
    }
  }

  scrollToItemById(id: number) {
    const contactArray = this.contactItems.toArray();
    const targetElement = contactArray.find(contactItem => contactItem.nativeElement.getAttribute('data-id') == id);
    if (targetElement) {
      targetElement.nativeElement.scrollIntoView({behavior: 'smooth', block: 'center'});
    }
  }


}

