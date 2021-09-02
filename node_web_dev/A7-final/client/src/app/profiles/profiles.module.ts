import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

import { SharedModule } from '../shared/shared.module';

/**
 * import profiles page components
 */
import { ProfilesComponent } from './profiles.component';
import { ConnectComponent } from './connect/connect.component';
import { SettingsComponent } from './settings/settings.component';
import { QueueComponent } from './queue/queue.component';
import { PostsComponent } from './posts/posts.component';

/**
 * import profiles shared components
 */
import { MidMenuComponent } from './components/mid-menu/mid-menu.component';
import { PostCardComponent } from './components/post-card/post-card.component';
import { PerformanceMetricsComponent } from './components/performance-metrics/performance-metrics.component';
import { ProfilesSidebarComponent } from './components/profiles-sidebar/profiles-sidebar.component';
import { PostPerformanceMetricsComponent } from './components/post-performance-metrics/post-performance-metrics.component';
import { SubredditPerformanceMetricsComponent } from './components/subreddit-performance-metrics/subreddit-performance-metrics.component';
import { NewPostCardComponent } from './components/new-post-card/new-post-card.component';
import { QueuedPostCardComponent } from './components/queued-post-card/queued-post-card.component';

@NgModule({
  imports: [
    CommonModule,
    SharedModule,
    RouterModule,
    FormsModule,
    ReactiveFormsModule,
    NgbModule
  ],
  declarations: [
    ProfilesComponent,
    ConnectComponent,
    SettingsComponent,
    MidMenuComponent,
    QueueComponent,
    PostsComponent,
    PerformanceMetricsComponent,
    PostCardComponent,
    ProfilesSidebarComponent,
    PostPerformanceMetricsComponent,
    SubredditPerformanceMetricsComponent,
    NewPostCardComponent,
    QueuedPostCardComponent
  ]
})
export class ProfilesModule { }
