import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { TeamService } from '../../services/team-service.service';
import { UserService } from '../../../shared/services/user.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent implements OnInit {
  teams: any[];
  user = this.userService.user;

  constructor(
    private teamService: TeamService,
    private router: Router,
    private userService: UserService
  ) { }

  handleNavbarMenuClicked() {
    // load teams into the dropdown on navbar quick
    // there could be a little delay here but thats alright
    this.loadTeams();
  }

  handleTeamClicked(teamId) {
    // navigate the the team dashboard with a url param that is the teamid
    console.debug(`Navigate to /teams/${teamId}`);
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
  
  loadTeams() {
    this.teamService.fetchTeams().subscribe((teams) => {
      console.debug('Fetching teams for navigation links...');
      this.teams = teams;
    });
  }

  ngOnInit() {
    // load teams into the dropdown on navbar load
    this.loadTeams();
  }

}
