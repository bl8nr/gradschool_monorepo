import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-account-teams',
  templateUrl: './account-teams.component.html',
  styleUrls: ['./account-teams.component.scss']
})
export class AccountTeamsComponent implements OnInit {

  breadcrumbs = [ 
    { name: 'Home', value: '/home' },
    { name: 'Account', value: '/account' },
    { name: 'Teams', value: '/account/teams' }
  ];
  
  constructor() { }

  ngOnInit() {
  }

}
