import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';

import { TeamService, NewTeamRequestBody } from '../../shared/services/team-service.service';
import { UserService } from '../../shared/services/user.service';

@Component({
  selector: 'app-team-new',
  templateUrl: './team-new.component.html',
  styleUrls: ['./team-new.component.scss']
})
export class TeamNewComponent implements OnInit {
  newTeamForm: FormGroup;
  currentStep: number;
  isSavingTeam: Boolean;
  isIntegratedWithReddit: Boolean;
  isIntegratedWithQuora: Boolean;
  isIntegratedWithStackExchange: Boolean;

  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private teamService: TeamService,
    private userService: UserService
  ) { }

  /* *********** */
  /* BEGIN STEPS */
  /* *********** */
  /**
   * simply increment to step 2
   */
  handleStepOneFinished() {
    this.currentStep = 2;
  }

  /**
   * create the team on the server since we have the data we need at step 2
   * we also need the team to exist in order to add members and integrations to
   * it in step 3
   */
  handleStepTwoFinished() {
    this.teamService.createTeam(this.newTeamForm.value).subscribe((team) => {
      this.currentStep = 3;
      this.isSavingTeam = false;
      console.log(this.teamService.team);
    });
  }

  /**
   * this is just a placeholder for now until i find a efficient way to test
   * adding members by id
   */
  handleStepThreeFinished() {
    this.currentStep = 4;
  }

  /**
   * redirect the user to that teams dashboard once the whoe wizard is complete
   */
  handleStepFourFinished() {
    this.router.navigate([`/teams/${this.teamService.team._id}`]);
  }

  /* ********* */
  /* END STEPS */
  /* ********* */

  /* ****************** */
  /* BEGIN INTEGRATIONS */
  /* ****************** */
  /**
   * start login with reddit process
   */
  handleIntegrateRedditClicked() {
    this.isIntegratedWithReddit = true;
    console.log('Integrate with reddit placeholder');
  }

  /**
   * start login with quora process
   */
  handleIntegrateQuoraClicked() {
    this.isIntegratedWithQuora = true;
    console.log('Integrate with Quora placeholder');
  }

  /**
   * start login with stack exchange process
   */
  handleIntegrateStackExchangeClicked() {
    this.isIntegratedWithStackExchange = true;
    console.log('Integrate with Stack Exchange Placeholder');
  }
  /* **************** */
  /* END INTEGRATIONS */
  /* **************** */

  /* ******* */
  /* UTILITY */
  /* ******* */
  handleGoHomeClicked() {
    this.router.navigate(['/home']);
  }

  /**
   * programatically go back to the last step
   * saftey on steps after 3
   */
  handleGoBackClicked() {
    this.currentStep > 1 && this.currentStep < 3 ? this.currentStep = this.currentStep - 1 : this.currentStep = this.currentStep;
  }
  /* *********** */
  /* END UTILITY */
  /* *********** */

  ngOnInit() {
    this.currentStep = 1;

    this.isIntegratedWithQuora = false;
    this.isIntegratedWithReddit = false;
    this.isIntegratedWithStackExchange = false;
    
    this.newTeamForm = this.formBuilder.group({
      teamName: ['', Validators.required],
      adminEmail: [ this.userService.user.profile.email, Validators.required],
      teamDescription: ['', Validators.required]
    })
  }

}
