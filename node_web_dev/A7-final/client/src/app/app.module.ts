import { BrowserModule } from '@angular/platform-browser';
import { NgModule, Injectable } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { RouterModule, Routes, RouteReuseStrategy } from '@angular/router';
import { AppComponent } from './app.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

/**
 * import application modules
 */
import { AccountModule } from './account/account.module';
import { SharedModule } from './shared/shared.module';
import { UserModule } from './user/user.module';
import { CallbacksModule } from './callbacks/callbacks.module';
import { ProfilesModule } from './profiles/profiles.module';

/**
 * import app routing module
 * all of the app routing is done in AppRoutingModule
 */
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
    ProfilesModule,
    NgbModule.forRoot()
  ],
  providers: [ ],
  bootstrap: [AppComponent]
})

export class AppModule { }
