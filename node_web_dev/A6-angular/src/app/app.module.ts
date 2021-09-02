import { BrowserModule } from '@angular/platform-browser';
import { NgModule, Injectable } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { RouterModule, Routes, RouteReuseStrategy } from '@angular/router';
import { AppComponent } from './app.component';

// import application modules
import { AccountModule } from './account/account.module';
import { SharedModule } from './shared/shared.module';
import { UserModule } from './user/user.module';
import { CallbacksModule } from './callbacks/callbacks.module';
import { ProfilesModule } from './profiles/profiles.module';

// routing is located in its own module
import { AppRoutingModule } from './app-routing.module';

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    RouterModule,
    SharedModule,
    AccountModule,
    UserModule,
    CallbacksModule,
    ProfilesModule
  ],
  providers: [ ],
  bootstrap: [AppComponent]
})

export class AppModule { }
