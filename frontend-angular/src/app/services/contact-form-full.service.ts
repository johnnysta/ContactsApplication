import {Injectable} from '@angular/core';
import {BehaviorSubject} from "rxjs";
import {PhoneDataModel} from "../models/phone-data.model";
import {AddressDataModel} from "../models/address-data.model";
import {ContactDetailsDataModel} from "../models/contact-details-data.model";

@Injectable({
  providedIn: 'root'
})
export class ContactFormFullService {

  public contactFormDetails: BehaviorSubject<ContactDetailsDataModel>;
  public phoneList: BehaviorSubject<PhoneDataModel[]>;
  public addressList: BehaviorSubject<AddressDataModel[]>;


  readonly INITIAL_CONTACT_DETAILS: ContactDetailsDataModel = {
    id: 0,
    firstName: '',
    lastName: '',
    birthDate: '',
    mothersName: '',
    ssId: '',
    taxId: '',
    email: '',
    userId: 0
  };
  readonly INITIAL_PHONE_LIST: PhoneDataModel[] = new Array<PhoneDataModel>();
  readonly INITIAL_ADDRESS_LIST: AddressDataModel[] = new Array<AddressDataModel>();


  constructor() {
    this.contactFormDetails = new BehaviorSubject<ContactDetailsDataModel>(this.INITIAL_CONTACT_DETAILS);
    this.phoneList = new BehaviorSubject<PhoneDataModel[]>(this.INITIAL_PHONE_LIST);
    this.addressList = new BehaviorSubject<AddressDataModel[]>(this.INITIAL_ADDRESS_LIST);
  }
}
