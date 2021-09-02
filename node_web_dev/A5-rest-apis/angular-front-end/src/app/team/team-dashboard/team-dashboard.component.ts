import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';

import { TeamService, Team } from '../../shared/services/team-service.service';
import { UserService } from '../../shared/services/user.service';

@Component({
  selector: 'app-team-dashboard',
  templateUrl: './team-dashboard.component.html',
  styleUrls: ['./team-dashboard.component.scss']
})
export class TeamDashboardComponent implements OnInit {
  teamId: String;
  breadcrumbs: any[];
  isLoading = true;
  team: Team;
  currentTab: String = 'settings';

  constructor(
    private teamService: TeamService,
    private userService: UserService,
    private activateRoute: ActivatedRoute,
    private router: Router
  ) {}

  handleChangeTab(newTabName) {
    this.currentTab = newTabName;
  }

  /**
   *  hit the teamService to fetch a team and store its details 
   *  then we access the team via teamService.team
   */
  loadTeam(teamId) {
    this.isLoading = true;

    this.teamService.fetchAndStoreTeam(teamId).subscribe((team) => {

      this.team = this.teamService.team;
      this.teamId = this.teamService.team._id;

      // load the breadcrumbs from the team data
      this.breadcrumbs = [
        { name: 'Home', value: `/home` },
        { name: this.teamService.team.teamName, value: `/teams/${teamId}` },
        { name: 'Dashboard', value: `/teams/${teamId}` }
      ];

      // revaeal all the template stuff that relies on a team being laoded
      this.isLoading = false;
      
    });
  }

  ngOnInit() {
    
    /**
     *  get our route params :teamId and make a fresh team load
     *  that way we decouple this component from depending on
     * any lifecycle but its own
     */
    this.activateRoute.params.subscribe(params => { 
      this.teamId = params['teamId'];
      this.loadTeam(this.teamId);
    });
  }
    
}
