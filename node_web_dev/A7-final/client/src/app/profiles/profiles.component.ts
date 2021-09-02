/**
 * -- profiles.component.ts --
 * this component is the parent to everything on the /profiles route
 * it basically shows a loading screen while it fetches available profiles
 * then renders its children after it has loaded profile data to profileService
 */

import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';

import { ProfileService } from '../shared/services/profile.service';

@Component({
  selector: 'app-profiles',
  templateUrl: './profiles.component.html',
  styleUrls: ['./profiles.component.scss']
})
export class ProfilesComponent implements OnInit {
  isLoading: boolean;

  constructor(
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private profileService: ProfileService
  ) { }

  ngOnInit() {
    this.isLoading = true;

    /**
     * load profiles available to the current user
     * if there is no :profileId (i.e. user went to /profiles)
     * then send the user to their first profiles' dashboard by default
     * otherwise set the selectedProfileId to the URL param
     */
    this.profileService.loadProfiles().subscribe(() => {
      // profiles are cached in the profileService.profiles
      if (this.activatedRoute.firstChild.snapshot.params['profileId']) {
        this.profileService.selectedProfileId = this.activatedRoute.firstChild.snapshot.params['profileId'];
      } else {
        // if profiles exist, auto select the first profile otherwise send user to connect a reddit profile
        if (this.profileService.profiles[0]) {
          this.router.navigate([`/profiles/${this.profileService.profiles[0]._id}/queue`]);
        } else {
          this.router.navigate(['/profiles/connect']);
        }
      }

      this.isLoading = false;
    });

    /**
     * detect if :profileId has changed (i.e. user clicked to view a different profile)
     * and simply change the selectedProfileId. This saves us a hard-er refresh and thus
     * an unneeded additional API fetch for profiles
     */
    this.router.events.subscribe(() => {
      this.profileService.selectedProfileId = this.activatedRoute.firstChild.snapshot.params['profileId'];
    });

  }

}
