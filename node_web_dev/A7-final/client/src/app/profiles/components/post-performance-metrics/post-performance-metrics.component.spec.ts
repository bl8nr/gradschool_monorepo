import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PostPerformanceMetricsComponent } from './post-performance-metrics.component';

describe('PostPerformanceMetricsComponent', () => {
  let component: PostPerformanceMetricsComponent;
  let fixture: ComponentFixture<PostPerformanceMetricsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PostPerformanceMetricsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PostPerformanceMetricsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
