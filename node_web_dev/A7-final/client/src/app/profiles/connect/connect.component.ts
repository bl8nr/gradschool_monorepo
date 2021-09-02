import { Component, OnInit } from '@angular/core';

import { UserService } from '../../shared/services/user.service';
import { OauthService } from '../../shared/services/oauth.service';
import { User } from '../../shared/models/user';

@Component({
  selector: 'app-connect',
  templateUrl: './connect.component.html',
  styleUrls: ['./connect.component.scss']
})
export class ConnectComponent implements OnInit {
  user: User = this.userService.user;

  constructor(
    private userService: UserService,
    private oauthService: OauthService
  ) { }

  /**
   * redirect the user to reddit to allow
   * them to OAuth their reddit account
   */
  handleLoginWithReddit(userId) {
    this.oauthService.authorizeRedditRedirect(userId);
  }

  ngOnInit() {
  }

}
