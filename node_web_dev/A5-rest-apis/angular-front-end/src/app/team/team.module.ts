import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SharedModule } from '../shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';

import { TeamDashboardComponent } from './team-dashboard/team-dashboard.component';
import { TeamNewComponent } from './team-new/team-new.component';
import { TeamSettingsComponent } from './components/team-settings/team-settings.component';
import { TeamMembersComponent } from './components/team-members/team-members.component';
import { TeamIntegrationsComponent } from './components/team-integrations/team-integrations.component';

@NgModule({
  imports: [
    CommonModule,
    SharedModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule
  ],
  declarations: [TeamDashboardComponent, TeamNewComponent, TeamSettingsComponent, TeamMembersComponent, TeamIntegrationsComponent ]
})
export class TeamModule { }
