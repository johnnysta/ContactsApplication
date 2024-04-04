import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../environments/environment";
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

  sendAddressRegistration(addressData: AddressDataModel) {
    return this.http.post(BASE_URL, addressData);
  }

  sendAddressesData(contactAddresses: AddressDataModel[], id: number) {
    return this.http.post<AddressDataModel[]>(BASE_URL + "/addresses-list/" + id, contactAddresses);
  }

}
