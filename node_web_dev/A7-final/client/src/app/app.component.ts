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
    /**
     * check for an active session, if exists
     * then load the users reddit profiles, else
     * send the user to the login page
     */
    this.userService.checkForSessionAndLoadUser()
      .flatMap((user) => {
        return this.profileService.loadProfiles();
      })
      .subscribe((profiles) => {
        this.isLoading = false;
      }, (err) => {
        if (err.status === 401) {
          this.isLoading = false;
          this.router.navigate(['/login']);
        }
      });
  }
}
