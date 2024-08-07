import {Injectable} from '@angular/core';
import {Observable} from "rxjs";
import {PhoneDataModel} from "../models/phone-data.model";
import {environment} from "../environments/environment";
import {HttpClient} from "@angular/common/http";
import {PhoneRegistrationInitDataModel} from "../models/phone-registration-init-data.model";

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

  getPhoneRegistrationInitData(): Observable<PhoneRegistrationInitDataModel> {
    return this.http.get<PhoneRegistrationInitDataModel>(BASE_URL + "/init-data");
  }

  sendPhonesData(contactPhones: PhoneDataModel[], id: number) {
    return this.http.post<PhoneDataModel[]>(BASE_URL + "/phones-list/" + id, contactPhones);
  }
}
