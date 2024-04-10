import {Injectable} from '@angular/core';
import {LoginDataModel} from "../models/login-data.model";
import {BehaviorSubject, Observable} from "rxjs";
import {AuthenticatedUserModel} from "../models/authenticated-user.model";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {environment} from "../environments/environment";
import {UserRegistrationDataModel} from "../models/user-registration-data.model";
import {UserChangePasswordModel} from "../models/user-change-pw.model";

const BASE_URL: string = environment.serverUrl + '/api/users';

@Injectable({
  providedIn: 'root'
})
export class AccountService {

  readonly INITIAL_USER_STATE: AuthenticatedUserModel = {
    userId: 0,
    firstName: '',
    lastName: '',
    email: '',
    isLoggedIn: false,
    role: ''
  }

  public loggedInUser: BehaviorSubject<AuthenticatedUserModel>;

  constructor(private http: HttpClient) {
    this.loggedInUser = new BehaviorSubject<AuthenticatedUserModel>(this.INITIAL_USER_STATE);
  }


  login(loginData: LoginDataModel): Observable<AuthenticatedUserModel> {
    console.log("Email: " + loginData.email);
    console.log("PW: " + loginData.password);
    const headers = new HttpHeaders(loginData ? {
      Authorization: 'Basic ' + btoa(loginData.email + ':' + loginData.password),
    } : {});
    return this.http.post<AuthenticatedUserModel>(BASE_URL + '/login', {}, {headers: headers});
  }


  registerUser(userRegData: UserRegistrationDataModel) {
    return this.http.post<UserRegistrationDataModel>(BASE_URL, userRegData);
  }

  setLoggedIn(user: AuthenticatedUserModel) {
    console.log("User ID received back from server: " + user.userId);
    this.loggedInUser.next(user);
  }

  public logout(): Observable<void> {
    this.setLoggedOut();
    return this.http.get<void>(BASE_URL + '/logout');
  };

  setLoggedOut() {
    this.loggedInUser.next(this.INITIAL_USER_STATE);
  }


  getUserInfo() {
    return this.http.get<AuthenticatedUserModel>(BASE_URL + '/userInfo');
  }

  changePassword(userChangePasswordModel: UserChangePasswordModel) {
    return this.http.post<UserChangePasswordModel>(BASE_URL + '/changePw', userChangePasswordModel);
  }

}
