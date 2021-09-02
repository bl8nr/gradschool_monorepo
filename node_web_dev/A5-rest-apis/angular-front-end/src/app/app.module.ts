import { BrowserModule } from '@angular/platform-browser';
import { NgModule, Injectable } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { HttpModule } from '@angular/http';

import { RouterModule, Routes, RouteReuseStrategy } from '@angular/router';


import { AppComponent } from './app.component';
import { SharedModule } from './shared/shared.module';
import { AccountModule } from './account/account.module';
import { AppRoutingModule } from './app-routing.module';
import { HomeModule } from './home/home.module';
import { TeamService } from './shared/services/team-service.service';
import { TeamModule } from './team/team.module';
import { MemberService } from './shared/services/member.service';
import { UserModule } from './user/user.module';
import { UserService } from './shared/services/user.service';

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    HttpModule,
    AppRoutingModule,
    RouterModule,
    SharedModule,
    AccountModule,
    HomeModule,
    TeamModule,
    UserModule
  ],
  providers: [TeamService, MemberService, UserService, HttpModule],
  bootstrap: [AppComponent]
})
export class AppModule { }