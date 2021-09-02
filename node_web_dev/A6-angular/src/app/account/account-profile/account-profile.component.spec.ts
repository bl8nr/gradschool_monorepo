import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';

import { AccountProfileComponent } from './account-profile.component';
import { UserService } from '../../shared/services/user.service';

describe('AccountProfileComponent', () => {
  let component: AccountProfileComponent;
  let fixture: ComponentFixture<AccountProfileComponent>;

  class MockUserService {
    user = {
      profile: {
        firstName: 'first name',
        lastName: 'last name',
        displayName: 'display name',
        email: 'test@test.com',
        bio: 'biodsfsdf',
        company: 'test company'
      }
    };
  }

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AccountProfileComponent ],
      schemas: [ CUSTOM_ELEMENTS_SCHEMA ],
      imports: [ReactiveFormsModule, FormsModule],
      providers: [ {provide: UserService, useClass: MockUserService} ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AccountProfileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
