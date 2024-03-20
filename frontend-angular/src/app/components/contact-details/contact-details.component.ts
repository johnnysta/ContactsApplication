import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Route} from "@angular/router";

@Component({
  selector: 'app-contact-details',
  templateUrl: './contact-details.component.html',
  styleUrls: ['./contact-details.component.css']
})
export class ContactDetailsComponent implements OnInit {

  contactId!: number;
  fullName!: string;

  constructor(private activatedRoute: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.activatedRoute.paramMap.subscribe({
      next: param => {
        this.contactId = Number(param.get('id'));
        this.fullName = param.get('fullName') ?? "...";
      }
    })
  }

}
