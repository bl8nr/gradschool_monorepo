import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { UserService } from '../../../shared/services/user.service';

import { User } from '../../models/user';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent implements OnInit {
  user: User = this.userService.user;

  constructor(
    private router: Router,
    private userService: UserService
  ) { }

  handleNavbarMenuClicked() {
    // load teams into the dropdown on navbar quick
    // there could be a little delay here but thats alright
  }

  handleTeamClicked(teamId) {
    // navigate the the team dashboard with a url param that is the teamid
    this.router.navigate([`/teams/`, teamId]);
  }

  handleSettingsClicked() {
    console.log('/settings');
  }

  handleProfileClicked() {
    console.log('/profile');
  }

  handleLogoutClicked() {
    this.userService.logout().subscribe(() => {
      this.router.navigate(['/login']);
    });
  }

  ngOnInit() {
    // load teams into the dropdown on navbar load
  }

}
