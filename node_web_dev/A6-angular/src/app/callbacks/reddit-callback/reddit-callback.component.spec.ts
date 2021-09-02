import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';

import { RedditCallbackComponent } from './reddit-callback.component';
import { OauthService } from '../../shared/services/oauth.service';

describe('RedditCallbackComponent', () => {
  let component: RedditCallbackComponent;
  let fixture: ComponentFixture<RedditCallbackComponent>;

  class MockOauthService {
    // empty
  }

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RedditCallbackComponent ],
      imports: [ RouterTestingModule ],
      providers: [
        {provide: OauthService, useClass: MockOauthService}
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RedditCallbackComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
