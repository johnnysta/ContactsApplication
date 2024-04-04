import {Component} from '@angular/core';
import {PhoneRegistrationInitDataModel} from "../../models/phone-registration-init-data.model";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {PhoneDataModel} from "../../models/phone-data.model";
import {PhonesService} from "../../services/phones.service";
import {ActivatedRoute, Router} from "@angular/router";
import {ContactFormFullService} from "../../services/contact-form-full.service";
import {ContactDetailsDataModel} from "../../models/contact-details-data.model";

@Component({
  selector: 'app-phone-form-local',
  templateUrl: './phone-form-local.component.html',
  styleUrls: ['./phone-form-local.component.css']
})
export class PhoneFormLocalComponent {

  phoneIndex: number = -1;
  initData!: PhoneRegistrationInitDataModel;
  phoneForm: FormGroup;
  phoneList!: PhoneDataModel[];

  contactFormDetails!: ContactDetailsDataModel;

  constructor(private phoneService: PhonesService,
              private formBuilder: FormBuilder,
              private router: Router,
              private activatedRoute: ActivatedRoute,
              private contactFormFullService: ContactFormFullService) {
    this.phoneForm = this.formBuilder.group({
      phoneUseType: ['', Validators.required],
      phoneNumber: ['', [Validators.required, Validators.minLength(3)]],
      note: ['']
    });
  }

  ngOnInit(): void {

    this.contactFormFullService.phoneList.subscribe({
      next: value => this.phoneList = value
    });

    this.contactFormFullService.contactFormDetails.subscribe({
      next: value => this.contactFormDetails = value
    });

    this.activatedRoute.paramMap.subscribe({
      next: param => {
        this.phoneIndex = Number(param.get('id'));
        console.log("phoneIndex: " + this.phoneIndex);
        if (this.phoneIndex != -1) {
          this.fillPhoneForm(this.phoneIndex);
        } else {
          //Add new phone number clicked
        }
      }
    })

    this.phoneService.getPhoneRegistrationInitData().subscribe({
      next: value => {
        this.initData = value;
      },
      error: err => {
        console.log(err)
      },
      complete: () => {
        console.log(this.initData)
      }
    })
  }

  private fillPhoneForm(phoneIndex: number) {
    this.phoneForm.patchValue({
      'phoneUseType': this.phoneList[phoneIndex].phoneUseType,
      'phoneNumber': this.phoneList[phoneIndex].phoneNumber,
      'note': this.phoneList[phoneIndex].note
    });
  }

  submitData() {
    if (this.phoneIndex === -1) {
      const phoneData: PhoneDataModel = this.phoneForm.value;
      phoneData.phoneNumberOwner = this.contactFormDetails.id;
      phoneData.id = 0;
      phoneData.isDeleted = false;
      this.phoneList.push(phoneData);
    } else {
      this.phoneList[this.phoneIndex].phoneUseType = this.phoneForm.value['phoneUseType'];
      this.phoneList[this.phoneIndex].phoneNumber = this.phoneForm.value['phoneNumber'];
      this.phoneList[this.phoneIndex].note = this.phoneForm.value['note'];
    }
    this.contactFormFullService.phoneList.next(this.phoneList);
    this.router.navigate(['contactsFormFull']);
  }


  cancelForm() {
    this.router.navigate(['contactsFormFull']);
  }


}
