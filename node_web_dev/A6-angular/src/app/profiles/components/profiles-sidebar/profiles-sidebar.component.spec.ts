import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ProfilesSidebarComponent } from './profiles-sidebar.component';

describe('ProfilesSidebarComponent', () => {
  let component: ProfilesSidebarComponent;
  let fixture: ComponentFixture<ProfilesSidebarComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ProfilesSidebarComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ProfilesSidebarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
