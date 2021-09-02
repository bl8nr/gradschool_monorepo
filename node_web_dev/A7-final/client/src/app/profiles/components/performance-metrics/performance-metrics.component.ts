/**
 * -- performance-metrics.component.ts --
 * this component controlls which metrics component is currently displayed
 * it's parent to all the metric components and contains a menu which keeps
 * track the the metrics component in view
 */

import { Component, OnInit, Input } from '@angular/core';
import { forkJoin } from 'rxjs/observable/forkJoin';

import { RedditService } from '../../../shared/services/reddit.service';

@Component({
  selector: 'app-performance-metrics',
  templateUrl: './performance-metrics.component.html',
  styleUrls: ['./performance-metrics.component.scss']
})
export class PerformanceMetricsComponent implements OnInit {
  @Input() postId: string;
  @Input() subredditName: string;
  activeSelection = 'post_performance';

  constructor(
    private redditService: RedditService
  ) { }

  /**
   * change which metrics component is in view
   */
  handleChangeActiveSelection(newSelection) {
    this.activeSelection = newSelection;
  }

  ngOnInit() {
  }

}
