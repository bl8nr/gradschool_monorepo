import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map';



@Injectable()
export class ProfileService {
  profiles: any;
  selectedProfileId: string = null;

  constructor(
    private httpClient: HttpClient
  ) { }

  loadProfiles() {
    return this.httpClient.get(`/api/profiles`).map((profiles) => {
      this.profiles = profiles;
      return this.profiles;
    });
  }

  getProfileById(profileId) {
    return this.httpClient.get(`/api/profiles/${profileId}`);
  }

  deleteProfileById(profileId) {
    return this.httpClient.delete(`/api/profiles/${profileId}`);
  }

  getActivity(profileId) {
    return this.httpClient.get(`/api/profiles/${profileId}/proxy/message/inbox`).map((activity) => {
      return activity;
    });
  }

  get selectedProfile() {
    return this.profiles.find((profile) => {
      return profile._id === this.selectedProfileId;
    });
  }

}
