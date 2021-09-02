/**
 * -- post-card.component.ts --
 * this component hold a posts data and presents it in a template
 * it is also responsible for viewing or hiding the 'performance-metrics'
 * component which lives in the card
 */

import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-post-card',
  templateUrl: './post-card.component.html',
  styleUrls: ['./post-card.component.scss']
})
export class PostCardComponent implements OnInit {
  @Input() post: any;
  performanceMetricsHasBeenExpanded: boolean;

  constructor() { }

  /**
   * convert utc posting time to 'days ago' format for template presentation
   */
  getReadableTime(utcTimestamp) {
    let response = null;
    const hoursAgo = Math.floor(((Math.round(new Date().getTime() / 1000)) - utcTimestamp) / 3600);
    hoursAgo < 24 ? response = `${hoursAgo} hours` : response = `${Math.floor(hoursAgo / 24)} days`;
    return response;
  }

  ngOnInit() {

    /**
     * set that the 'performance-metrics' has not been expanded since
     * component just inited, in the child this allows us to fire API
     * reqs to get the metrics only once for efficiency
     */
    this.performanceMetricsHasBeenExpanded = false;
  }

}
