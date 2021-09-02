import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

/**
 * register and login related components
 */
import { LoginComponent } from './user/login/login.component';
import { RegisterComponent } from './user/register/register.component';
import { RedditCallbackComponent } from './callbacks/reddit-callback/reddit-callback.component';

/**
 * Profile related components
 */
import { ProfilesComponent } from './profiles/profiles.component';
import { ConnectComponent } from './profiles/connect/connect.component';
import { SettingsComponent } from './profiles/settings/settings.component';
import { QueueComponent } from './profiles/queue/queue.component';
import { PostsComponent } from './profiles/posts/posts.component';

/**
 * user account and settings related components
 */
import { AccountComponent } from './account/account.component';
import { EmailComponent } from './account/email/email.component';
import { SecurityComponent } from './account/security/security.component';
import { LeaveComponent } from './account/leave/leave.component';

/**
 * configure all of the application routes including children.
 * bascially everything fallsback to /profiles (dashboard) except
 * in the event that no active session exists
 */
const appRoutes: Routes = [
  { path: 'redditcb', component: RedditCallbackComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  {
    path: 'profiles', component: ProfilesComponent, children: [
      {
        path: '',
        component: QueueComponent
      },
      {
        path: 'connect',
        component: ConnectComponent
      },
      {
        path: ':profileId/queue',
        component: QueueComponent
      },
      {
        path: ':profileId/posts',
        component: PostsComponent
      },
      {
        path: ':profileId/settings',
        component: SettingsComponent
      }
    ]
  },
  {
    path: 'account', component: AccountComponent, children: [
      {
        path: 'email',
        component: EmailComponent
      },
      {
        path: 'security',
        component: SecurityComponent
      },
      { path: 'leave',
        component: LeaveComponent
      }
    ]
  },
  { path: '', redirectTo: '/profiles', pathMatch: 'full' },
  { path: '**', redirectTo: '/profiles', pathMatch: 'full' }
];

@NgModule({
  imports: [
    RouterModule.forRoot(appRoutes)
  ],
  exports: []
})

export class AppRoutingModule { }
