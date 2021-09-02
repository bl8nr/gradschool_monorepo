import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map';

/**
 * PROFILE SERVICE (CRUD HTTP Service)
 * this service is responsible for all requests dealing with profiles.
 * A profile represents an OAuthed Reddit account whose scheduled posts
 * are stored in our database.
 */

@Injectable()
export class ProfileService {
  profiles: any;
  selectedProfileId: string = null;

  constructor(
    private httpClient: HttpClient
  ) { }

  /**
   * get a list of all the reddit profiles a user has access to
   * API usses sessions to fetch sessions accesible by only this user
   */
  loadProfiles() {
    return this.httpClient.get(`/api/profiles`).map((profiles) => {
      this.profiles = profiles;
      return this.profiles;
    });
  }

  /**
   * get one profile by id. Client must have active session whose
   * user ID is the same as the profile.ownerId
   * @param profileId UUID of a profile
   */
  getProfileById(profileId) {
    return this.httpClient.get(`/api/profiles/${profileId}`);
  }

  /**
   * delete one profile by id. Client must have active session whose
   * user ID is the same as the profile.ownerId
   * @param profileId UUID of a profile
   */
  deleteProfileById(profileId) {
    return this.httpClient.delete(`/api/profiles/${profileId}`);
  }

  /**
   * return the profile 'selected', whose profile id
   * matches this.selectedProfileId
   */
  get selectedProfile() {
    return this.profiles.find((profile) => {
      return profile._id === this.selectedProfileId;
    });
  }

}
