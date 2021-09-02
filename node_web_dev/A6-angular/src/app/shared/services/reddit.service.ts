/**
 * this service is responsible for non-authenticated reqs stright to public reddit
 * endpoints, we can save a ton and bandwidth by having the client hit reddit directly
 * when available, although we may have to proxy the reqs in the future
 */

import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map';



@Injectable()
export class RedditService {

  constructor(
    private httpClient: HttpClient
  ) { }

  // fetch all of a reddit users posts, by reddit username
  getUsersPostsByUsername(redditUsername) {
    return this.httpClient.get(`https://www.reddit.com/user/${redditUsername}/submitted.json?limit=1000&sr_detail=true`).map((response: any) => {
      // the actual array of posts lives in data.children
      return response.data.children;
    });
  }

  // fetch one post, by subreddit name and post ('thing') id
  getPostBySubredditAndId(subreddit, id) {
    return this.httpClient.get(`https://www.reddit.com/r/${subreddit}/comments/${id}.json?limit=1`).map((response: any) => {
      // the actual array of posts lives in response[0].data.children[0]
      return response[0].data.children[0];
    });
  }

  // fetch the details of a subreddit, but subreddit name
  getSubredditInfo(subreddit) {
    return this.httpClient.get(`https://www.reddit.com/r/${subreddit}/about.json`).map((response: any) => {
      return response;
    });
  }

}
