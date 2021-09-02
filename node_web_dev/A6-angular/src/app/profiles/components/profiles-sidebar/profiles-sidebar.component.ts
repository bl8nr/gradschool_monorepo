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

  handleAddProfile() {
    // direct user to the connect an account page
    this.router.navigate([`/profiles/connect`]);
  }

  handleProfileClick(profileId) {
    // direct user to the profiles' posts queue
    this.router.navigate([`/profiles/${profileId}/queue`]);
  }

  ngOnInit() {
  }

}
