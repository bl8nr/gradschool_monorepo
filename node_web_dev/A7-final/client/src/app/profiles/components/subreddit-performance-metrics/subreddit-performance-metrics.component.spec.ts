import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SubredditPerformanceMetricsComponent } from './subreddit-performance-metrics.component';

describe('SubredditPerformanceMetricsComponent', () => {
  let component: SubredditPerformanceMetricsComponent;
  let fixture: ComponentFixture<SubredditPerformanceMetricsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SubredditPerformanceMetricsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SubredditPerformanceMetricsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
