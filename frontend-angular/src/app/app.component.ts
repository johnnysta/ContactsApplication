import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {AccountService} from "./services/account.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'frontend-angular';

  constructor(private router: Router,
              private accountService: AccountService
  ) {
  }

  ngOnInit(): void {

    console.log("AppComponent's OnInit called...");

    this.accountService.getUserInfo().subscribe({
      next: value => {
        if (!value) {
          this.router.navigate(['home']);
        }
        else {
          value.isLoggedIn = true;
          this.accountService.loggedInUser.next(value);
          console.log("From server based on session, id:" + this.accountService.loggedInUser.getValue().userId)
          console.log("From server based on session, loggedin:" + this.accountService.loggedInUser.getValue().isLoggedIn)
          console.log("From server based on session, role:" + this.accountService.loggedInUser.getValue().role)
          console.log("From server based on session, email:" + this.accountService.loggedInUser.getValue().email)
          console.log("From server based on session, first:" + this.accountService.loggedInUser.getValue().firstName)
          this.router.navigate(['home']);
        }
      },
      error: err => {
        console.log("User not logged in, session expired or other..")
        this.accountService.setLoggedOut();
        this.router.navigate(['home']);
      }
    });

  }


}
