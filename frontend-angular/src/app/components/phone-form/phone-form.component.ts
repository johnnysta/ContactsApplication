import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {ContactDetailsDataModel} from "../../models/contact-details-data.model";
import {ContactsService} from "../../services/contacts.service";
import {ActivatedRoute, Router} from "@angular/router";
import {PhoneDataModel} from "../../models/phone-data.model";
import {PhonesService} from "../../services/phones.service";

@Component({
  selector: 'app-phone-form',
  templateUrl: './phone-form.component.html',
  styleUrls: ['./phone-form.component.css']
})
export class PhoneFormComponent implements OnInit {

  contactId!: number;

  phoneForm: FormGroup;
  // loggedInUser!: AuthenticatedUserModel;
  existingPhoneData!: PhoneDataModel;

  constructor(private phoneService: PhonesService,
              private formBuilder: FormBuilder,
              // private accountService: AccountService,
              private router: Router,
              private activatedRoute: ActivatedRoute) {
    this.phoneForm = this.formBuilder.group({
      phoneUseType: ['', Validators.required],
      phoneNumber: ['', Validators.required],
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
    const phoneData: PhoneDataModel = this.phoneForm.value;
    phoneData.phoneNumberOwner = this.contactId;
    this.phoneService.sendPhoneRegistration(phoneData).subscribe(
      {
        next: () => {
        },
        error: (err) => {
          console.log(err)
        },
        complete: () => {
          console.log("New phone saved successfully.");
          this.router.navigate(['contactDetails', this.contactId]);
        }
      });
  }


}
