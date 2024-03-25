import {Component} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {LoginDataModel} from "../../models/login-data.model";
import {AccountService} from "../../services/account.service";
import {Router} from "@angular/router";
import {UserRegistrationDataModel} from "../../models/user-registration-data.model";

@Component({
  selector: 'app-user-registration',
  templateUrl: './user-registration.component.html',
  styleUrls: ['./user-registration.component.css']
})
export class UserRegistrationComponent {


  userRegData!: UserRegistrationDataModel;
  userRegForm: FormGroup;

  constructor(private accountService: AccountService,
              private formBuilder: FormBuilder,
              private router: Router
  ) {
    this.userRegForm = this.formBuilder.group({
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      email: ['', Validators.required],
      password: ['', Validators.required],
      role: ['', Validators.required]
    });
  }

  submitData() {
    this.userRegData = this.userRegForm.value;
    this.accountService.registerUser(this.userRegData).subscribe(
      {
        next: () => {
        },
        error: (err) => {
          console.log(err)
        },
        complete: () => {
          this.router.navigate(['home']);
        }
      });
  }


  cancelForm() {
    this.router.navigate(["home"]);
  }
}
