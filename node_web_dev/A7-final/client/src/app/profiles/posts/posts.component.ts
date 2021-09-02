import { Component, OnInit } from '@angular/core';
import * as moment from 'moment';

import { ProfileService } from '../../shared/services/profile.service';
import { RedditService } from '../../shared/services/reddit.service';

@Component({
  selector: 'app-posts',
  templateUrl: './posts.component.html',
  styleUrls: ['./posts.component.scss']
})
export class PostsComponent implements OnInit {
  isLoading: boolean;
  redditUsername: string;
  postsByDate = [];

  constructor(
    private profileService: ProfileService,
    private redditService: RedditService
  ) { }

  ngOnInit() {
    this.isLoading = true;

    // load reddit username from the selected profile
    this.redditUsername = this.profileService.selectedProfile.accountInfo.name;

    /**
      * fetch posts by username and sort them into objects by
      * date of posting, then save them in this.postsByDate[]
      */
    this.redditService.getUsersPostsByUsername(this.redditUsername).subscribe((posts: any) => {

      posts.forEach((post) => {
        // round the time to the day posted
        const postDay = moment.unix(post.data.created_utc).startOf('day');

        // see if there is already an entry for that day in this.postsByDate
        const postArray = this.postsByDate.find((postSet) => {
          return postSet.unix === postDay.unix();
        });

        if (postArray) {
          // if that day exists in this.postsByDate, then add the post to it
          postArray.posts.unshift(post);
        } else {
          // if it doesnt, then add the day along with the post
          const postsByDateObject = {
            month: postDay.format('MMMM'),
            day: postDay.format('Do'),
            weekday: postDay.format('dddd'),
            unix: postDay.unix(),
            posts: [post]
          };

          this.postsByDate.push(postsByDateObject);
        }
      });

      this.isLoading = false;
    });
  }
}
