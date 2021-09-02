import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { PermalinkSearchHeroComponent } from './permalink-search-hero.component';

describe('PermalinkSearchHeroComponent', () => {
  let component: PermalinkSearchHeroComponent;
  let fixture: ComponentFixture<PermalinkSearchHeroComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [
        FormsModule,
        ReactiveFormsModule
    ],
      declarations: [ PermalinkSearchHeroComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PermalinkSearchHeroComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
