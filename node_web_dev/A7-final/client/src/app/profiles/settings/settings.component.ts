import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';

import { ProfileService } from '../../shared/services/profile.service';
import { Profile } from '../../shared/models/profile';

@Component({
  selector: 'app-settings',
  templateUrl: './settings.component.html',
  styleUrls: ['./settings.component.scss']
})
export class SettingsComponent implements OnInit {
  isLoading: boolean;
  profile: Profile;
  deleteConfirm: string;
  profileIsBeingDeleted: boolean;
  profileHasBeenDeleted: boolean;

  constructor(
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private profileService: ProfileService
  ) { }

  /**
   * delete a profile by id if confirm is correct, then
   * enable the deleted success button to appear
   */
  handleDeleteProfile(profileId) {
    if (this.deleteConfirm === this.profile.accountInfo.name) {
      this.profileIsBeingDeleted = true;
      this.profileService.deleteProfileById(profileId).subscribe(() => {
        this.profileHasBeenDeleted = true;
      });
    }
  }

  /**
   * redirect the user to reload the page, sending them back
   * to the dashboard and triggering a refresh of their profiles
   */
  handleDeletedSuccessClicked() {
    window.location.href = '/';
  }

  ngOnInit() {
    this.isLoading = true;
    this.profileHasBeenDeleted = false;
    this.profileIsBeingDeleted = false;

    /**
     * load the profile into the component then reveal
     * the component
     */
    this.activatedRoute.params
      .flatMap((params) => {
        return this.profileService.getProfileById(params['profileId']);
      })
      .subscribe((profile: Profile) => {
        this.profile = profile;
        this.isLoading = false;
      });
  }

}
