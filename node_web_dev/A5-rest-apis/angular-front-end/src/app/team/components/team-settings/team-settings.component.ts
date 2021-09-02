import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

import { TeamService, Team } from '../../../shared/services/team-service.service';

@Component({
  selector: 'app-team-settings',
  templateUrl: './team-settings.component.html',
  styleUrls: ['./team-settings.component.scss']
})
export class TeamSettingsComponent implements OnInit {
  team: Team;
  deleteConfirm: String;
  isTeamDetailsBeingSaved: Boolean;
  teamIsBeingDeleted: Boolean;
  teamHasBeenDeleted: Boolean;
  teamSettingsForm: FormGroup;

  constructor(
    private teamService: TeamService,
    private formBuilder: FormBuilder,
    private router: Router
  ) { }

  onSaveChangesClicked(formValues) {
    this.isTeamDetailsBeingSaved = true;
    this.teamService.updateTeam(formValues, this.team._id).subscribe((team) => {
      this.team = this.teamService.team;
      
      this.teamSettingsForm.reset({
        teamName: team.teamName,
        teamDescription: team.teamDescription,
        adminEmail: team.adminEmail,
        teamId: team._id
      });
      
      this.isTeamDetailsBeingSaved = false;

    })

  }

  handleDeleteTeam() {
    if (this.deleteConfirm == this.team.teamName) {
      this.teamIsBeingDeleted = true;
      this.teamService.deleteTeam(this.team._id).subscribe(() => {
        this.teamHasBeenDeleted = true;
      });
    }
  }

  ngOnInit() {
    this.team = this.teamService.team;

    this.teamSettingsForm = this.formBuilder.group({
      teamName: [this.team.teamName, Validators.required],
      teamDescription: [this.team.teamDescription, Validators.required],
      adminEmail: [this.team.adminEmail, Validators.required],
      teamId: [{ value: this.team._id, disabled: true }, /* validations */]
    })

    this.teamIsBeingDeleted = false;
    this.teamHasBeenDeleted = false;
    this.isTeamDetailsBeingSaved = false;
  }

}
