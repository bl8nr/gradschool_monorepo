/**
 * -- performance-performance-metrics.component.ts --
 * this component fetches the post performance metrics view from
 * redditService and presents it in a table in the template
 */

import { Component, OnInit, Input } from '@angular/core';

import { RedditService } from '../../../shared/services/reddit.service';

@Component({
  selector: 'app-post-performance-metrics',
  templateUrl: './post-performance-metrics.component.html',
  styleUrls: ['./post-performance-metrics.component.scss']
})
export class PostPerformanceMetricsComponent implements OnInit {
  @Input() postId: string;
  @Input() subredditName: string;
  isLoading: boolean;
  post: any;

  constructor(
    private redditService: RedditService
  ) { }

  ngOnInit() {
    this.isLoading = true;

    /**
     * fetch reddit post data from reddit by subreddit and 'thing' id
     */
    this.redditService.getPostBySubredditAndId(this.subredditName, this.postId).subscribe((post) => {
      this.post = post;
      this.isLoading = false;
    });
  }

}
