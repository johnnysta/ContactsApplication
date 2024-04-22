import {Component} from '@angular/core';
import {LoginDataModel} from "../../models/login-data.model";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {AccountService} from "../../services/account.service";
import {validationHandler} from "../../utils/validationHandler";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  loginData!: LoginDataModel;
  loginForm: FormGroup;
  otherServerError!: string;


  constructor(private accountService: AccountService,
              private formBuilder: FormBuilder,
              private router: Router
  ) {
    this.loginForm = this.formBuilder.group({
      email: ['', [Validators.required, Validators.minLength(3), Validators.email]],
      password: ['', [Validators.required, Validators.minLength(3)]]
    });

  }

  submitData() {
    console.log("Submitting login data..");
    this.loginData = this.loginForm.value;
    this.accountService.login(this.loginData).subscribe({
      next: value => {
        console.log("E-mail back: " + value.email);
        console.log("Role back: " + value.role);
        value.isLoggedIn = true;
        this.accountService.setLoggedIn(value);
        this.router.navigate(['home']);
      },
      error: err => {
        console.log(err)
        this.accountService.setLoggedOut();
        console.error("****User Login Err ****" + err.error);
        this.otherServerError = err.error;
        validationHandler(err, this.loginForm);
      },
      complete: () => {

      }
    });
  }

  cancel() {
    this.router.navigate(['home'])
  }
}
