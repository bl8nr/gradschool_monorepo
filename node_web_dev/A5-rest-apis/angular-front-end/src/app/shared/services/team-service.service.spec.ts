import { TestBed, inject } from '@angular/core/testing';

import { TeamServiceService } from './team-service.service';

describe('TeamServiceService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [TeamServiceService]
    });
  });

  it('should be created', inject([TeamServiceService], (service: TeamServiceService) => {
    expect(service).toBeTruthy();
  }));
});
