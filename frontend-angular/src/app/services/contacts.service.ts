import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {ContactListItemModel} from "../models/contact-list-item.model";
import {Observable} from "rxjs";
import {environment} from "../environments/environment.dev";
import {ContactDetailsDataModel} from "../models/contact-details-data.model";
import {ContactDataModel} from "../models/contact-data.model";


const BASE_URL: string = environment.serverUrl + '/api/contacts';

@Injectable({
  providedIn: 'root'
})
export class ContactsService {


  constructor(private http: HttpClient) {
  }


  getContactsByUserId(id: number): Observable<ContactListItemModel[]> {
    return this.http.get<ContactListItemModel[]>(BASE_URL + "/" + id);
  }

  deleteContactById(id: number) {
    return this.http.delete(BASE_URL + "/" + id);
  }

  sendContactRegistration(contactData: ContactDetailsDataModel): Observable<number> {
    return this.http.post<number>(BASE_URL + "/contactDetails/", contactData);
  }

  getContactById(contactId: number): Observable<ContactDetailsDataModel> {
    return this.http.get<ContactDetailsDataModel>(BASE_URL + "/contactDetails/" + contactId);
  }

  sendContactUpdate(contactData: ContactDetailsDataModel) {
    return this.http.put<ContactDetailsDataModel>(BASE_URL + "/contactDetails/" + contactData.id, contactData);
  }

  sendContactFullUpdate(contactFullData: ContactDataModel) {
    return this.http.put<ContactDetailsDataModel>(BASE_URL + "/contact/" + contactFullData.contactBasicData.id, contactFullData);
  }


  getFullContactById(contactId: number): Observable<ContactDataModel> {
    return this.http.get<ContactDataModel>(BASE_URL + "/contact/" + contactId);
  }

  sendContactFullRegistration(contactFullData: ContactDataModel): Observable<number> {
    return this.http.post<number>(BASE_URL + "/contact/", contactFullData);
  }


}
