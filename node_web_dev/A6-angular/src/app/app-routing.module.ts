import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AccountProfileComponent } from './account/account-profile/account-profile.component';

/* user and login components */
import { LoginComponent } from './user/login/login.component';
import { RegisterComponent } from './user/register/register.component';
import { RedditCallbackComponent } from './callbacks/reddit-callback/reddit-callback.component';

/* profiles components */
import { ProfilesComponent } from './profiles/profiles.component';
import { ConnectComponent } from './profiles/connect/connect.component';
import { SettingsComponent } from './profiles/settings/settings.component';
import { QueueComponent } from './profiles/queue/queue.component';
import { PostsComponent } from './profiles/posts/posts.component';

/**
 * configure all of the application routes
 * includes, login, register, cb, profiles, and account
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
  { path: 'account', redirectTo: 'account/profile', pathMatch: 'full' },
  { path: 'account/profile', component: AccountProfileComponent },
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
