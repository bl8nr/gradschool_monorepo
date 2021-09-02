/**
 * -- subreddit-performance-metrics.component.ts --
 * this component fetches the subreddit performance metrics view from
 * redditService and presents it in a table in the template
 */

import { Component, OnInit, Input } from '@angular/core';

import { RedditService } from '../../../shared/services/reddit.service';

@Component({
  selector: 'app-subreddit-performance-metrics',
  templateUrl: './subreddit-performance-metrics.component.html',
  styleUrls: ['./subreddit-performance-metrics.component.scss']
})
export class SubredditPerformanceMetricsComponent implements OnInit {
  @Input() postId: string;
  @Input() subredditName: string;
  isLoading: boolean;
  subreddit: any;

  constructor(
    private redditService: RedditService
  ) { }

  ngOnInit() {
    this.isLoading = true;

    /**
     * fetch subreddit data from reddit by subreddit name
     */
    this.redditService.getSubredditInfo(this.subredditName).subscribe((subreddit) => {
      this.subreddit = subreddit;
      this.isLoading = false;
    });
  }

}
