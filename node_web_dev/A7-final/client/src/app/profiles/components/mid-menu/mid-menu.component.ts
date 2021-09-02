import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-mid-menu',
  templateUrl: './mid-menu.component.html',
  styleUrls: ['./mid-menu.component.scss']
})
export class MidMenuComponent implements OnInit {
  profileId: string;
  currentViewName: string;

  constructor(
    private router: Router,
    private activatedRoute: ActivatedRoute
  ) { }

  ngOnInit() {

    /**
     * UI - assign the currentViewName based on the url
     * so the nav bar displays the right current location
     */
    this.activatedRoute.params
      .subscribe((params) => {
        this.profileId = params['profileId'];

        if (this.router.url === `/profiles/${this.profileId}/queue`) {
          this.currentViewName = 'queue';
        } else if (this.router.url === `/profiles/${this.profileId}/posts`) {
          this.currentViewName = 'posts';
        } else {
          // if (this.router.url === `/profiles/${this.profileId}/settings`)
          this.currentViewName = 'settings';
        }

      });
  }

}
