import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';

import { UserService, UserProfile } from '../../shared/services/user.service';

@Component({
  selector: 'app-account-profile',
  templateUrl: './account-profile.component.html',
  styleUrls: ['./account-profile.component.scss']
})
export class AccountProfileComponent implements OnInit {
  profileForm: FormGroup;
  isSavingChanges: Boolean = false;

  breadcrumbs = [ 
    { name: 'Home', value: '/home' },
    { name: 'Account', value: '/account' },
    { name: 'Profile', value: '/account/profile' }
  ];

  constructor(
    public userService: UserService,
    private formBuilder: FormBuilder
  ) { }

  handleUpdateProfileClicked(formValues: UserProfile) {
    this.isSavingChanges = true;
    this.userService.updateUserProfile(formValues).subscribe(() => {
      this.resetProfileForm();
      this.isSavingChanges = false;
    });
  }

  resetProfileForm() {
    this.profileForm.reset({
      displayName: [ this.userService.user.profile.displayName, /* validations */ ],
      firstName: [ this.userService.user.profile.firstName, /* validations */ ],
      lastName: [ this.userService.user.profile.lastName, /* validations */ ],
      email: [ this.userService.user.profile.email, /* validations */ ],
      company: [ this.userService.user.profile.company, /* validations */],
      bio: [ this.userService.user.profile.bio, /* validations */ ]
    });
  }

  ngOnInit() {
    this.isSavingChanges = false;

    this.profileForm = this.formBuilder.group({
      displayName: [ this.userService.user.profile.displayName, /* validations */ ],
      firstName: [ this.userService.user.profile.firstName, /* validations */ ],
      lastName: [ this.userService.user.profile.lastName, /* validations */ ],
      email: [ this.userService.user.profile.email, /* validations */ ],
      company: [ this.userService.user.profile.company, /* validations */],
      bio: [ this.userService.user.profile.bio, /* validations */ ]
    });

  }

}
