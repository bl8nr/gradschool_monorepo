import { Component, OnInit } from '@angular/core';
import * as moment from 'moment';

import { PostService } from '../../shared/services/post.service';
import { ProfileService } from '../../shared/services/profile.service';

@Component({
  selector: 'app-queue',
  templateUrl: './queue.component.html',
  styleUrls: ['./queue.component.scss']
})
export class QueueComponent implements OnInit {
  isLoading: boolean;
  isNewPostCollapsed: boolean;
  postsByDate = [];

  constructor(
    private postService: PostService,
    private profileService: ProfileService
  ) { }

  // UI - collapse the new post element
  toggleIsNewPostCollapsed() {
    this.isNewPostCollapsed = !this.isNewPostCollapsed;
  }

  /**
   * sort the fetched posts and put them into objects by
   * date of posting, then save them in this.postsByDate[]
   */
  updateQueuedPostsList(posts) {
    this.postsByDate = [];

    posts.forEach((post) => {
      // round the time to the day posted
      const postDay = moment.unix(post.targetPostTimeUTC).startOf('day');

      // see if there is already an entry for that day in this.postsByDate
      const postArray = this.postsByDate.find((postSet) => {
        return postSet.unix === postDay.unix();
      });

      if (postArray) {
        // if that day exists in this.postsByDate, then add the post to it
        postArray.posts.push(post);
      } else {
        // if it doesnt, then add the day along with the post
        const postsByDateObject = {
          month: postDay.format('MMMM'),
          day: postDay.format('Do'),
          weekday: postDay.format('dddd'),
          unix: postDay.unix(),
          posts: [ post ]
        };

        this.postsByDate.unshift(postsByDateObject);
      }

    });

    this.isNewPostCollapsed = true;
    this.isLoading = false;
  }

  ngOnInit() {
    this.isNewPostCollapsed = true;
    this.isLoading = true;

    /**
     * fetch posts for the selected profile, then
     * sort and arrange them by date in this.postsByDate
     */
    this.postService.getPosts(this.profileService.selectedProfileId).subscribe((posts) => {
      this.updateQueuedPostsList(posts);
    });

  }

}
