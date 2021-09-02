import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { UserService } from './shared/services/user.service';
import { ProfileService } from './shared/services/profile.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  isLoading = true;

  constructor(
    private router: Router,
    private userService: UserService,
    private profileService: ProfileService
  ) { }

  ngOnInit() {
    // on app init, check for an active user session (and load account info)
    this.userService.checkForSessionAndLoadUser()
      // if user session exists, then load the users social profiles
      .flatMap((user) => {
        return this.profileService.loadProfiles();
      })
      .subscribe((profiles) => {
        this.isLoading = false;
      }, (err) => {
        // if no session exists, then route user to the login page
        if (err.status === 401) {
          this.isLoading = false;
          this.router.navigate(['/login']);
        }
      });
  }
}
