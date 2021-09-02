import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

import { PostService } from '../../../shared/services/post.service';
import { ProfileService } from '../../../shared/services/profile.service';

@Component({
  selector: 'app-queued-post-card',
  templateUrl: './queued-post-card.component.html',
  styleUrls: ['./queued-post-card.component.scss']
})
export class QueuedPostCardComponent implements OnInit {
  @Input() post: any;
  @Output() updateQueuedPostsList: EventEmitter<any> = new EventEmitter<any>();
  profile: any;
  profileId: string;
  hasExpandedDelete = false;
  isDeletingPost = false;
  isEditingPost = false;
  isSavingPost = false;
  editPostForm: FormGroup;
  schedulingStrategy = 'custom';

  constructor(
    private profileService: ProfileService,
    private postService: PostService,
    private formBuilder: FormBuilder
  ) { }

  /**
   * close the edit post dialog
   */
  handleDiscardChanges() {
    this.isEditingPost = false;
  }

  /**
   * update a post, then emit an event to the parent with the
   * updated posts listing/response
   */
  handleSaveChanges() {
    this.isEditingPost = false;
    this.isSavingPost = true;

    /**
     * set timeout is here only to compensate for the CSS
     * transition time, it has no programmatic purpose. only UI
     */
    setTimeout(() => {
      this.postService.updatePost(this.profileId, this.post._id, this.editPostForm.value).subscribe((posts) => {
        this.isSavingPost = false;
        this.updateQueuedPostsList.emit(posts);
      });
    }, 500);
  }

  /**
   * delete a post, then emit an event to the parent with the
   * updated posts listing/response
   */
  deletePost(postId) {
    this.isDeletingPost = true;
    this.postService.deletePost(this.profileId, postId).subscribe((posts) => {
      this.isDeletingPost = false;
      this.updateQueuedPostsList.emit(posts);
    });
  }

  /**
   * initialize/reinitialize validatiors in the edit post
   * form based on postType
   */
  initValidations(postType) {
    if (postType === 'linkPost') {
      this.editPostForm.controls.body.setValidators([ /* not requried */ ]);
      this.editPostForm.controls.body.updateValueAndValidity();
      this.editPostForm.controls.link.setValidators([ Validators.required ]);
      this.editPostForm.controls.link.updateValueAndValidity();
    } else if (postType === 'textPost') {
      this.editPostForm.controls.body.setValidators([ Validators.required ]);
      this.editPostForm.controls.body.updateValueAndValidity();
      this.editPostForm.controls.link.setValidators([ /* not required */ ]);
      this.editPostForm.controls.link.updateValueAndValidity();
    } else {
      this.editPostForm.controls.body.setValidators([ Validators.required ]);
      this.editPostForm.controls.body.updateValueAndValidity();
      this.editPostForm.controls.link.setValidators([ Validators.required ]);
      this.editPostForm.controls.link.updateValueAndValidity();
    }
  }


  ngOnInit() {
    this.profile = this.profileService.selectedProfile;
    this.profileId = this.profileService.selectedProfileId;

    /**
     * create the editPostForm
     */
    this.editPostForm = this.formBuilder.group({
      title: [this.post.title, [ Validators.required ]],
      subreddit: [this.post.subreddit, [ Validators.required ]],
      link: [this.post.link, /* dynamic validations */ ],
      body: [this.post.body, /* dynamic validations */ ],
      type: [this.post.type, [ Validators.required ]],
      targetPostTimeUTC: [this.post.targetPostTimeUTC, /* no validations requried */],
      targetPostTimeReadable: [this.post.targetPostTimeReadable, /* no validation required */ ],
      status: [this.post.status, [ Validators.required] ]
    });

    /**
     * initialize dynamic validations based
     * on the post type
     */
    this.initValidations(this.editPostForm.value.type);

    /**
     * reinitialize the dynamic validations if the
     * post type changes on the form
     */
    this.editPostForm.controls.type.valueChanges.subscribe((newPostTypeValue) => {
      this.initValidations(newPostTypeValue);
    });

  }

}
