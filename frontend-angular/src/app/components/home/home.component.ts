import {Component, OnInit} from '@angular/core';
import {AuthenticatedUserModel} from "../../models/authenticated-user.model";
import {AccountService} from "../../services/account.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

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

  login() {
    this.router.navigate(['login']);
  }
}
