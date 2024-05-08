import {Component} from '@angular/core';
import {AbstractControl, FormBuilder, FormGroup, Validators} from "@angular/forms";
import {AccountService} from "../../services/account.service";
import {Router} from "@angular/router";
import {UserRegistrationDataModel} from "../../models/user-registration-data.model";
import {validationHandler} from "../../utils/validationHandler";

@Component({
  selector: 'app-user-registration',
  templateUrl: './user-registration.component.html',
  styleUrls: ['./user-registration.component.css']
})
export class UserRegistrationComponent {


  userRegData!: UserRegistrationDataModel;
  userRegForm: FormGroup;
  initialRoles = ["NEW_ADMIN", "NEW_USER"];

  constructor(private accountService: AccountService,
              private formBuilder: FormBuilder,
              private router: Router
  ) {
    this.userRegForm = this.formBuilder.group({
      firstName: ['', [Validators.required, Validators.minLength(3)]],
      lastName: ['', [Validators.required, Validators.minLength(3)]],
      email: ['', [Validators.required, Validators.minLength(3), Validators.email ]],
      password: ['', [Validators.required, Validators.minLength(3)]],
      confirmPassword: ['', [Validators.required, Validators.minLength(3)]],
      role: ['', Validators.required]
    }, {
      validators: this.passwordMatchValidator,
    });
  }

  passwordMatchValidator(control: AbstractControl): { [key: string]: string } | null {
    const password = control.get('password');
    const confirmPassword = control.get('confirmPassword');
    if (password?.value !== null && confirmPassword?.value != null && password?.value !== confirmPassword.value) {
      return {'passwordMismatch': 'Passwords do not match'};
    }
    return null;
  }


  submitData() {
    this.userRegData = this.userRegForm.value;
    this.accountService.registerUser(this.userRegData).subscribe(
      {
        next: () => {
        },
        error: (err) => {
          console.log(err);
          validationHandler(err, this.userRegForm);
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
