import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/observable/of';
import 'rxjs/add/operator/map';
import * as _ from 'lodash';

/**
 * REDDIT SERVICE (HTTP fetch Service)
 * this service is responsible for non-authenticated reqs stright to public reddit
 * endpoints, we can save a ton and bandwidth by having the client hit reddit directly
 * when available, although we may have to proxy the reqs in the future.
 */

@Injectable()
export class RedditService {
  trendingSubredditCache: string[];

  constructor(
    private httpClient: HttpClient
  ) { }

  /**
   * get all of a reddit users posts by reddit username
   */
  getUsersPostsByUsername(redditUsername) {
    return this.httpClient.get(`https://www.reddit.com/user/${redditUsername}/submitted.json?limit=1000&sr_detail=true`).map((response: any) => {
      // the actual array of posts lives in data.children
      return response.data.children;
    });
  }

  /**
   * get one reddit post by subreddit name and post ('thing') id
   */
  getPostBySubredditAndId(subreddit, id) {
    return this.httpClient.get(`https://www.reddit.com/r/${subreddit}/comments/${id}.json?limit=1`).map((response: any) => {
      // the actual post lives in response[0].data.children[0]
      return response[0].data.children[0];
    });
  }

  /**
   * get one subreddits details by subreddit name
   */
  getSubredditInfo(subreddit) {
    return this.httpClient.get(`https://www.reddit.com/r/${subreddit}/about.json`).map((response: any) => {
      return response;
    });
  }

  /**
   * get an array of the top 5 currently trending subreddits
   */
  getTrendingSubreddits() {
    if (this.trendingSubredditCache) {
      return Observable.of(this.trendingSubredditCache);
    } else {
      return this.httpClient.get(`https://www.reddit.com/api/trending_subreddits.json`).map((response: any) => {
        this.trendingSubredditCache = response.subreddit_names;
        return this.trendingSubredditCache;
      });
    }
  }

  /**
   * query for subreddits by name, return the top 5 matches
   */
  querySubredditName(query: string): Observable<string[]> {
    return this.httpClient.get(`https://www.reddit.com/api/search_reddit_names.json?query=${query}`).map((response: any) => {
      return _.take(response.names, 5);
    });
  }

}
