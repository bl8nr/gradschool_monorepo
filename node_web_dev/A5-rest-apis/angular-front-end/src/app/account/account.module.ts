import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SharedModule } from '../shared/shared.module';
import { RouterModule, Router } from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

/* import account/settings related components */
import { AccountProfileComponent } from './account-profile/account-profile.component';
import { AccountSidebarComponent } from './account-sidebar/account-sidebar.component';
import { AccountTeamsComponent } from './account-teams/account-teams.component';

@NgModule({
  imports: [
    CommonModule,
    SharedModule,
    RouterModule,
    FormsModule,
    ReactiveFormsModule
  ],
  exports: [
  ],
  declarations: [
    AccountProfileComponent,
    AccountSidebarComponent,
    AccountTeamsComponent
  ]
})
export class AccountModule { }
