import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

@Injectable()
export class OauthService {
  redditScopeString = 'identity edit read submit';
  redditStateCode: string = null;
  redditDuration = 'permanent';
  redditResponseType = 'code';

  constructor(
    private httpClient: HttpClient
  ) { }

  /**
   * redirect the user to Reddit to complete OAuth
   * @param userId users UUID
   */
  authorizeRedditRedirect(userId) {
    this.redditStateCode = userId;
    const redditUrl = `https://ssl.reddit.com/api/v1/authorize?state=${this.redditStateCode}&duration=${this.redditDuration}&response_type=${this.redditResponseType}&scope=${this.redditScopeString}&client_id=${environment.reddit_oauth_client_id}&redirect_uri=${environment.reddit_oauth_redirect_uri}`;
    window.location.href = redditUrl;
  }

  /**
   * create a Reddit profile integration using the Reddit callback response
   * @param userId users UUID
   * @param code Reddit callback response code/token
   */
  saveRedditIntegration(userId, code): Observable<any> {
    const requestBody = {
      originalCode: code,
      originalState: userId
    };

    return this.httpClient.post(`/api/profiles`, requestBody);
  }

}
