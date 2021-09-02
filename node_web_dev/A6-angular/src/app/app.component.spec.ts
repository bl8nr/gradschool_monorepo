import { TestBed, async } from '@angular/core/testing';
import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterTestingModule } from '@angular/router/testing';

import { AppComponent } from './app.component';
import { UserService } from './shared/services/user.service';
import { ProfileService } from './shared/services/profile.service';

describe('AppComponent', () => {

  class MockUserService {
    // empty
  }

  class MockProfileService {
    // empty
  }

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      schemas: [ CUSTOM_ELEMENTS_SCHEMA ],
      declarations: [
        AppComponent
      ],
      imports: [ RouterTestingModule ],
      providers: [
        {provide: UserService, useClass: MockUserService},
        {provide: ProfileService, useClass: MockProfileService}
      ]
    }).compileComponents();
  }));
  it('should create the app', async(() => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.debugElement.componentInstance;
    expect(app).toBeTruthy();
  }));
});
