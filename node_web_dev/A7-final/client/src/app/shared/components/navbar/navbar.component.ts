import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { UserService } from '../../../shared/services/user.service';
import { User } from '../../models/user';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent implements OnInit {
  user: User = this.userService.user;

  constructor(
    private router: Router,
    private userService: UserService
  ) { }

  handleLogoutClicked() {
    this.userService.logout().subscribe(() => {
      this.router.navigate(['/login']);
    });
  }

  ngOnInit() {
  }

}
