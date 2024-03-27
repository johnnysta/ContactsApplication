import {Component} from '@angular/core';
import {AuthenticatedUserModel} from "../../models/authenticated-user.model";
import {AccountService} from "../../services/account.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent {

  loggedInUser!: AuthenticatedUserModel;

  constructor(private accountService: AccountService,
              private router: Router) {
  }

  ngOnInit(): void {
    this.accountService.loggedInUser.subscribe({
      next: value => {
        this.loggedInUser = value;
      }
    });
  }


  logout() {
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
  }
}
