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

  handleDeleteProfile(profileId) {
    if (this.deleteConfirm === this.profile.accountInfo.name) {
      this.profileIsBeingDeleted = true;
      this.profileService.deleteProfileById(profileId).subscribe(() => {
        this.profileHasBeenDeleted = true;
      });
    }
  }

  ngOnInit() {
    this.isLoading = true;
    this.profileHasBeenDeleted = false;
    this.profileIsBeingDeleted = false;

    // load the profile into component via API
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
