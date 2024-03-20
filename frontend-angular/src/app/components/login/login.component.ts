import {Component} from '@angular/core';
import {LoginDataModel} from "../../models/login-data.model";
import {AddressService} from "../../services/address.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";
import {AccountService} from "../../services/account.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  loginData!: LoginDataModel;
  loginForm: FormGroup;

  constructor(private accountService: AccountService,
              private formBuilder: FormBuilder,
              private router: Router,
  ) {
    this.loginForm = this.formBuilder.group({
      email: ['', Validators.required],
      password: ['', Validators.required]
    });

  }

  submitData() {
    this.loginData = this.loginForm.value;
    this.accountService.basicLogin(this.loginData).subscribe();
  }
}
