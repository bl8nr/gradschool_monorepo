import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { FormBuilder, FormGroup, FormControl, Validators } from '@angular/forms';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/debounceTime';
import 'rxjs/add/operator/distinctUntilChanged';
import 'rxjs/add/operator/do';
import 'rxjs/add/operator/switchMap';
import 'rxjs/add/operator/merge';
import { of } from 'rxjs/observable/of';
import { NgbDateStruct } from '@ng-bootstrap/ng-bootstrap';
import * as moment from 'moment';

import { ProfileService } from '../../../shared/services/profile.service';
import { RedditService } from '../../../shared/services/reddit.service';
import { PostService } from '../../../shared/services/post.service';

@Component({
  selector: 'app-new-post-card',
  templateUrl: './new-post-card.component.html',
  styleUrls: ['./new-post-card.component.scss']
})
export class NewPostCardComponent implements OnInit {
  @Output() toggleViewWelcome: EventEmitter<boolean> = new EventEmitter<boolean>();
  @Output() updateQueuedPostsList: EventEmitter<any> = new EventEmitter<any>();
  trendingSubreddits: string[] = [];
  selectedProfile: any = this.profileService.selectedProfile;
  newPostForm: FormGroup;
  now = new Date();
  targetDate: NgbDateStruct = { year: this.now.getFullYear(), month: this.now.getMonth() + 1, day: this.now.getDate() + 1 };
  targetTime: NgbTimeStruct = { hour: 16, minute: 20 };
  isSavingPost = false;
  searching = false;

  /* 'nextbest24' , 'nextbest7d', 'custom' */
  schedulingStrategy = 'custom';

  constructor(
    private formBuilder: FormBuilder,
    private profileService: ProfileService,
    private redditService: RedditService,
    private postSerivce: PostService
  ) { }

  /**
   * convert times appropriatly for the API, then
   * save the new post and reset the new post form
   */
  handleAddToQueuePressed() {
    this.isSavingPost = true;
    this.newPostForm.disable();
    const targetTimeInMoment = moment(`${this.targetDate.year}-${this.targetDate.month}-${this.targetDate.day} ${this.targetTime.hour}:${this.targetTime.minute}`, 'YYYY-MM-DD HH:mm');
    (this.newPostForm.get('targetPostTimeUTC') as FormControl).setValue(targetTimeInMoment.unix());
    (this.newPostForm.get('targetPostTimeReadable') as FormControl).setValue(targetTimeInMoment.format('dddd, MMMM Do YYYY, h:mm:ss a'));

    this.postSerivce.createPost(this.profileService.selectedProfileId, this.newPostForm.value).subscribe((posts) => {
      this.newPostForm.reset();
      this.newPostForm.get('type').setValue('linkPost');
      this.newPostForm.get('status').setValue('new');
      this.newPostForm.enable();
      this.isSavingPost = false;
      this.updateQueuedPostsList.emit(posts);
    });
  }

  /**
   * set subreddit form value on click of auto suggestion
   */
  handleSubredditClicked(subreddit) {
    (this.newPostForm.get('subreddit') as FormControl).setValue(subreddit);
  }

  /**
   * detect change in subreddit form field then
   * run a search against reddit to pop up/auto suggest
   * subreddit names
   */
  search = (text$: Observable<string>) =>
    text$
      .debounceTime(300)
      .distinctUntilChanged()
      .do(() => this.searching = true)
      .switchMap(term =>
        this.redditService.querySubredditName(term)
          .catch(() => {
            return of([]);
          }))
      .do(() => this.searching = false)

  ngOnInit() {

    /**
     * fetch trending subreddits to display
     * on the side of the form
     */
    this.redditService.getTrendingSubreddits().subscribe((trendingSubreddits: string[]) => {
      this.trendingSubreddits = trendingSubreddits;
    });

    /**
     * create reactive form for new post
     */
    this.newPostForm = this.formBuilder.group({
      title: ['', [ Validators.required ]],
      subreddit: ['', [ Validators.required ]],
      link: ['', [ Validators.required ]],
      body: ['', /* validations */ ],
      type: ['linkPost', [ Validators.required ]],
      targetPostTimeUTC: ['', /* validations */ ],
      targetPostTimeReadable: ['', /* validations */ ],
      status: ['new', [ Validators.required ]]
    });

    /**
     * reassign validators in the new post form if the new
     * post type is changed
     */
    this.newPostForm.controls.type.valueChanges.subscribe((newValue) => {
      if (newValue === 'linkPost') {
        this.newPostForm.controls.body.setValidators([ /* not requried */ ]);
        this.newPostForm.controls.body.updateValueAndValidity();
        this.newPostForm.controls.link.setValidators([ Validators.required ]);
        this.newPostForm.controls.link.updateValueAndValidity();
      } else if (newValue === 'textPost') {
        this.newPostForm.controls.body.setValidators([ Validators.required ]);
        this.newPostForm.controls.body.updateValueAndValidity();
        this.newPostForm.controls.link.setValidators([ /* not required */ ]);
        this.newPostForm.controls.link.updateValueAndValidity();
      } else {
        this.newPostForm.controls.body.setValidators([ Validators.required ]);
        this.newPostForm.controls.body.updateValueAndValidity();
        this.newPostForm.controls.link.setValidators([ Validators.required ]);
        this.newPostForm.controls.link.updateValueAndValidity();
      }
    });
  }

}

/**
 * interface/model for NG Time picker
 */
interface NgbTimeStruct {
  hour: number;
  minute: number;
}
