import {Injectable} from '@angular/core';
import {Observable} from "rxjs";
import {ContactListItemModel} from "../models/contact-list-item.model";
import {PhoneDataModel} from "../models/phone-data.model";
import {environment} from "../environments/environment";
import {HttpClient} from "@angular/common/http";

const BASE_URL: string = environment.serverUrl + '/api/contacts/phones';

@Injectable({
  providedIn: 'root'
})
export class PhonesService {

  constructor(private http: HttpClient) {
  }

  getPhonesByContactId(contactId: number): Observable<PhoneDataModel[]> {
    return this.http.get<PhoneDataModel[]>(BASE_URL + "/" + contactId);
  }

  deletePhoneById(id: number) {
    return this.http.delete(BASE_URL + "/" + id);
  }

  sendPhoneRegistration(phoneData: PhoneDataModel) {
    return this.http.post(BASE_URL, phoneData);
  }
}
