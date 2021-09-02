import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map';

@Injectable()
export class OauthService {
  redditClientId = 'BkCcgaXRY5izkQ';
  redditRedirectUri = 'https://staging.replyas.com/redditcb';
  redditScopeString = 'identity edit read submit privatemessages';
  redditStateCode: string = null;
  redditDuration = 'permanent';
  redditResponseType = 'code';

  constructor(
    private httpClient: HttpClient
  ) { }

  authorizeRedditRedirect(userId) {
    this.redditStateCode = userId;
    const redditUrl = `https://ssl.reddit.com/api/v1/authorize?state=${this.redditStateCode}&duration=${this.redditDuration}&response_type=${this.redditResponseType}&scope=${this.redditScopeString}&client_id=${this.redditClientId}&redirect_uri=${this.redditRedirectUri}`;
    window.location.href = redditUrl;
  }

  saveRedditIntegration(userId, code): Observable<any> {
    const requestBody = {
      originalCode: code,
      originalState: userId
    };

    return this.httpClient.post(`/api/profiles`, requestBody);
  }

  deleteRedditIntegration(teamId, integrationId) {
    return this.httpClient.delete(`/api/teams/${teamId}/reddits/${integrationId}`);
  }

  authorizeStackExchangeRedirect() {
    console.log('stackoverflow');
  }

  authorizeStackExchangeCallback() {

  }

}
