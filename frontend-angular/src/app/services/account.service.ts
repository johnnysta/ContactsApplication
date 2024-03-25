import {Injectable} from '@angular/core';
import {LoginDataModel} from "../models/login-data.model";
import {Observable, tap} from "rxjs";
import {AuthenticatedUserModel} from "../models/authenticated-user.model";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {environment} from "../environments/environment";
import {UserRegistrationDataModel} from "../models/user-registration-data.model";

const BASE_URL: string = environment.serverUrl + '/api/users';

@Injectable({
  providedIn: 'root'
})
export class AccountService {

  constructor(private http: HttpClient) {
  }

  basicLogin(loginData: LoginDataModel): Observable<AuthenticatedUserModel> {
    const headers = new HttpHeaders(loginData ? {
      Authorization: 'Basic ' + btoa(loginData.email + ':' + loginData.password),
    } : {});
    return this.http.get<AuthenticatedUserModel>(BASE_URL + '/me', {headers: headers})
      .pipe(
        tap((user) => {
          // this.handleUserData(user);
          // this.basicLoginSubject.next(user);
        }),
      );
  }

  registerUser(userRegData: UserRegistrationDataModel) {
    return this.http.post<UserRegistrationDataModel>(BASE_URL, userRegData);
  }
}
