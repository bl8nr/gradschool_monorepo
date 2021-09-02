import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { QueuedPostCardComponent } from './queued-post-card.component';

describe('QueuedPostCardComponent', () => {
  let component: QueuedPostCardComponent;
  let fixture: ComponentFixture<QueuedPostCardComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ QueuedPostCardComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(QueuedPostCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
