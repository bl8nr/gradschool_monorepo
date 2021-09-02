import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './home/home/home.component';
import { AccountProfileComponent } from './account/account-profile/account-profile.component';
import { AccountTeamsComponent } from './account/account-teams/account-teams.component';
import { TeamDashboardComponent } from './team/team-dashboard/team-dashboard.component';
import { LoginComponent } from './user/login/login.component';
import { RegisterComponent } from './user/register/register.component';
import { TeamNewComponent } from './team/team-new/team-new.component';

const appRoutes: Routes = [
    { path: 'login', component: LoginComponent },
    { path: 'register', component: RegisterComponent },
    { path: 'home', component: HomeComponent },
    { path: 'account', redirectTo: 'account/profile', pathMatch: 'full'},
    { path: 'account/profile', component: AccountProfileComponent},
    { path: 'account/teams', component: AccountTeamsComponent },
    { path: 'teams/create', component: TeamNewComponent },
    { path: 'teams/:teamId', component: TeamDashboardComponent },
    { path: '', redirectTo: '/home', pathMatch: 'full' },
    { path: '**', redirectTo: '/home', pathMatch: 'full' }
];

@NgModule({
    imports: [
        RouterModule.forRoot(appRoutes)
    ],
    exports: []
})

export class AppRoutingModule {}