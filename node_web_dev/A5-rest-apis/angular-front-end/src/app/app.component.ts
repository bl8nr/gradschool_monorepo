import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { UserService, User } from './shared/services/user.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit{
  isLoading = true;

  constructor(
    private router: Router,
    private userService: UserService
  ) { }

  ngOnInit() {
    this.userService.checkForSessionAndLoadUser().subscribe((user: User) => {
      console.info(`Session Validated. UserId ${user.id}`);
      this.isLoading = false;
    }, (err) => {
      if (err.status === 401) {
        console.info('Session Invalid. Redirecting to Login...');
        this.isLoading = false;
        this.router.navigate(['/login']);
      };
    })
  }
}
