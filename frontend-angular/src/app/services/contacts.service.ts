import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {ContactListItemModel} from "../models/contact-list-item.model";
import {Observable} from "rxjs";
import {environment} from "../environments/environment";
import {ContactDetailsDataModel} from "../models/contact-details-data.model";


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
    return this.http.post<number>(BASE_URL, contactData);
  }

  getContactById(contactId: number): Observable<ContactDetailsDataModel> {
    return this.http.get<ContactDetailsDataModel>(BASE_URL + "/contact/" + contactId);
  }

  sendContactUpdate(contactData: ContactDetailsDataModel) {
    return this.http.put<ContactDetailsDataModel>(BASE_URL + "/" + contactData.id, contactData);
  }


}
