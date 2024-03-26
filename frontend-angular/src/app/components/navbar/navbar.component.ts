import {Component} from '@angular/core';
import {AuthenticatedUserModel} from "../../models/authenticated-user.model";
import {AccountService} from "../../services/account.service";

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent {

  loggedInUser!: AuthenticatedUserModel;

  constructor(private accountService: AccountService) {
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
        next: _ => {
          this.accountService.setLoggedOut()
          console.log("Logged out successfully")
        },
        error: err => console.log("Logout error: " + err)
      }
    );
  }
}
