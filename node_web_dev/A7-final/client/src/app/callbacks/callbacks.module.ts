import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

import { RedditCallbackComponent } from './reddit-callback/reddit-callback.component';

@NgModule({
  imports: [
    CommonModule,
    RouterModule
  ],
  declarations: [RedditCallbackComponent]
})
export class CallbacksModule { }
