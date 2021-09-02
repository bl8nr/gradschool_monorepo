import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';

import { OauthService } from '../../shared/services/oauth.service';

@Component({
  selector: 'app-reddit-callback',
  templateUrl: './reddit-callback.component.html',
  styleUrls: ['./reddit-callback.component.scss']
})
export class RedditCallbackComponent implements OnInit {
  teamId: string;
  code: string;
  isLoading: boolean;
  isErrored: boolean;

  constructor(
    private router: Router,
    private activatedRoute: ActivatedRoute,
    private oauthService: OauthService
  ) { }

  ngOnInit() {
    this.isLoading = true;
    this.isErrored = false;
    // load the team based on the route param
    this.activatedRoute.queryParams.flatMap((params) => {
      this.code = params['code'];
      this.teamId = params['state'];
      return this.oauthService.saveRedditIntegration(this.teamId, this.code);
    })
    .subscribe((result) => {
      this.isLoading = false;
    }, (err) => {
      this.isLoading = false;
      this.isErrored = true;
    });
  }

}
