import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';

// import components to share across entire app
import { NavbarComponent } from './components/navbar/navbar.component';
import { FooterComponent } from './components/footer/footer.component';
import { BreadcrumbsComponent } from './components/breadcrumbs/breadcrumbs.component';
import { PermalinkSearchHeroComponent } from './components/permalink-search-hero/permalink-search-hero.component';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule
  ],
  declarations: [NavbarComponent, FooterComponent, BreadcrumbsComponent, PermalinkSearchHeroComponent],
  exports: [
    NavbarComponent,
    FooterComponent,
    BreadcrumbsComponent,
    PermalinkSearchHeroComponent
  ]
})
export class SharedModule { }
