import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';

// import shared services
import { UserService } from './services/user.service';
import { OauthService } from './services/oauth.service';
import { RedditService } from './services/reddit.service';
import { ProfileService } from './services/profile.service';

// import shared components
import { NavbarComponent } from './components/navbar/navbar.component';
import { FooterComponent } from './components/footer/footer.component';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule
  ],
  declarations: [NavbarComponent, FooterComponent ],
  exports: [
    NavbarComponent,
    FooterComponent
  ],
  providers: [ UserService, OauthService, RedditService, ProfileService ]
})
export class SharedModule { }
