import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {ContactListItemModel} from "../models/contact-list-item.model";
import {ContactDetailsDataModel} from "../models/contact-details-data.model";
import {environment} from "../environments/environment";
import {PhoneDataModel} from "../models/phone-data.model";
import {AddressDataModel} from "../models/address-data.model";

const BASE_URL: string = environment.serverUrl + '/api/contacts/addresses';

@Injectable({
  providedIn: 'root'
})
export class AddressService {

  constructor(private http: HttpClient) {
  }


  getAddressesByContactId(contactId: number) {
    return this.http.get<AddressDataModel[]>(BASE_URL + "/" + contactId);
  }

  deleteAddressById(id: number) {
    return this.http.delete(BASE_URL + "/" + id);

  }
}
