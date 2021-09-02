import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable } from 'rxjs/observable';
import 'rxjs/add/observable/of';

import { PerformanceMetricsComponent } from './performance-metrics.component';
import { RedditService } from '../../../shared/services/reddit.service';

describe('PerformanceMetricsComponent', () => {
  let component: PerformanceMetricsComponent;
  let fixture: ComponentFixture<PerformanceMetricsComponent>;

  class MockRedditService {
    getPostBySubredditAndId () {
      return Observable.of(null);
    }
  }

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PerformanceMetricsComponent ],
      providers: [
        {provide: RedditService, useClass: MockRedditService}
      ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PerformanceMetricsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  xit('should create', () => {
    expect(component).toBeTruthy();
  });
});
