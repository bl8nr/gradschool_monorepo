import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';

/**
 * import shared services
 */
import { UserService } from './services/user.service';
import { OauthService } from './services/oauth.service';
import { RedditService } from './services/reddit.service';
import { ProfileService } from './services/profile.service';
import { PostService } from './services/post.service';

/**
 * import shared components
 */
import { NavbarComponent } from './components/navbar/navbar.component';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule
  ],
  declarations: [ NavbarComponent ],
  exports: [ NavbarComponent ],
  providers: [ UserService, OauthService, RedditService, ProfileService, PostService ]
})
export class SharedModule { }
