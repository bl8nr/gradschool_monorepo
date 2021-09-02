import { Component, OnInit, Input } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-profiles-sidebar',
  templateUrl: './profiles-sidebar.component.html',
  styleUrls: ['./profiles-sidebar.component.scss']
})
export class ProfilesSidebarComponent implements OnInit {
  @Input() profiles: any;
  @Input() selectedProfileId: string;

  constructor(
    private router: Router,
  ) { }

  /**
   * redirect user to where they can add a reddit
   * account via OAuth link
   */
  handleAddProfile() {
    this.router.navigate([`/profiles/connect`]);
  }

  /**
   * redirect user to the queue page of the profile
   * they clicked
   */
  handleProfileClick(profileId) {
    this.router.navigate([`/profiles/${profileId}/queue`]);
  }

  ngOnInit() {
  }

}
