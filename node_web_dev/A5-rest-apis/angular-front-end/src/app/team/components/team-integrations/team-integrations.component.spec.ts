import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TeamIntegrationsComponent } from './team-integrations.component';

describe('TeamIntegrationsComponent', () => {
  let component: TeamIntegrationsComponent;
  let fixture: ComponentFixture<TeamIntegrationsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TeamIntegrationsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TeamIntegrationsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
