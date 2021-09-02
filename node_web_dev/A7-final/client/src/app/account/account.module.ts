import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SharedModule } from '../shared/shared.module';
import { RouterModule, Router } from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

/* import account/settings related components */
import { AccountComponent } from './account.component';
import { EmailComponent } from './email/email.component';
import { AccountSidebarComponent } from './components/account-sidebar/account-sidebar.component';
import { SecurityComponent } from './security/security.component';
import { LeaveComponent } from './leave/leave.component';

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
    AccountComponent,
    EmailComponent,
    AccountSidebarComponent,
    SecurityComponent,
    LeaveComponent
  ]
})
export class AccountModule { }
