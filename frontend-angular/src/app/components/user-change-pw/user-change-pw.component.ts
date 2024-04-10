import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {AbstractControl, FormBuilder, FormGroup, Validators} from "@angular/forms";
import {AccountService} from "../../services/account.service";
import {UserChangePasswordModel} from "../../models/user-change-pw.model";
import {AuthenticatedUserModel} from "../../models/authenticated-user.model";

@Component({
  selector: 'app-user-change-pw',
  templateUrl: './user-change-pw.component.html',
  styleUrls: ['./user-change-pw.component.css']
})
export class UserChangePwComponent implements OnInit {

  isNewUser!: boolean;
  changePwForm!: FormGroup;
  loggedInUser!: AuthenticatedUserModel;

  constructor(private route: ActivatedRoute,
              private router: Router,
              private formBuilder: FormBuilder,
              private accountService: AccountService) {
    this.changePwForm = this.formBuilder.group({
      oldPassword: ['', [Validators.required, Validators.minLength(3)]],
      newPassword: ['', [Validators.required, Validators.minLength(3)]],
      confirmNewPassword: ['', [Validators.required, Validators.minLength(3)]]
    }, {
      validators: this.passwordMatchValidator,
    });
  }

  ngOnInit(): void {
    this.accountService.loggedInUser.subscribe({
      next: value => {
        this.loggedInUser = value;
        this.isNewUser = (this.loggedInUser.role === "NEW_USER" || this.loggedInUser.role === "NEW_ADMIN");
      }
    });
  }


  passwordMatchValidator(control: AbstractControl): { [key: string]: string } | null {
    const password = control.get('newPassword');
    const confirmPassword = control.get('confirmNewPassword');
    if (password?.value !== null && confirmPassword?.value != null && password?.value !== confirmPassword.value) {
      return {'passwordMismatch': 'Password and password confirmartion do not match.'};
    }
    return null;
  }


  submitData() {
    const userChangePwData: UserChangePasswordModel = this.changePwForm.value;
    this.accountService.changePassword(userChangePwData).subscribe({
      next: () => {
        console.log("Change PW is successful.");
        if (this.isNewUser) {
          console.log("Logging out user..")
          this.accountService.logout().subscribe({
              next: () => {
                console.log("Logged out successfully");
                this.router.navigate(['home'])
              },
              error: err => console.log("Logout error: " + err),
              complete: () => {
              }
            }
          );
        } else {
          this.router.navigate(["home"]);
        }
      },
      error: (err) => {
        console.log(err);
      },
      complete: () => {
      }
    });
  }

  cancelForm() {
    this.router.navigate(["home"]);
  }
}
