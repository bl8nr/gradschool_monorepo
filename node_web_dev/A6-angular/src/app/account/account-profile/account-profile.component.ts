import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';

import { UserService } from '../../shared/services/user.service';
import { User, UserProfile } from '../../shared/models/user';

@Component({
  selector: 'app-account-profile',
  templateUrl: './account-profile.component.html',
  styleUrls: ['./account-profile.component.scss']
})
export class AccountProfileComponent implements OnInit {
  profileForm: FormGroup;
  isSavingChanges: Boolean = false;
  user: User = this.userService.user;

  constructor(
    public userService: UserService,
    private formBuilder: FormBuilder
  ) { }

  handleUpdateProfileClicked(formValues: UserProfile) {
    this.isSavingChanges = true;
    this.userService.saveUser().subscribe(() => {
      this.isSavingChanges = false;
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

    this.profileForm.valueChanges.subscribe((newFormValue) => {
      this.user.profile.firstName = newFormValue.firstName;
      this.user.profile.lastName = newFormValue.lastName;
      this.user.profile.displayName = newFormValue.displayName;
      this.user.profile.email = newFormValue.email;
      this.user.profile.bio = newFormValue.bio;
      this.user.profile.company = newFormValue.company;
    });

  }

}
