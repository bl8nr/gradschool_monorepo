import { Injectable } from '@angular/core';

import { Http, Response } from '@angular/http'
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/mergeMap';
import 'rxjs/add/observable/throw';

@Injectable()
export class UserService {
  // userId is stored after login or register and should never change
  // NEVER mutate userId, its our only natural ref to the logged in user
  userId: String;

  // create and empty user object
  user: User = {
    id: null,
    username: null,
    profile: {
      firstName: null,
      lastName: null,
      displayName: null,
      email: null,
      company: null,
      bio: null,
      profilePicture: null
    }
  };

  constructor(
    private http: Http
  ) { }

  /**
   *  create a new account on the server which also creates a session and returns a userId 
   *  then use that userId to get and load the user account data into memory
   */
  register(formValues: RegistrationFormValues): Observable<any> {

    const requestBody: User = {
      username: formValues.username,
      password: formValues.password,
      profile: {
        firstName: formValues.firstName,
        lastName: formValues.lastName,
        displayName: `${formValues.firstName} ${formValues.lastName}`,
        email: formValues.email,
        bio: '',
        company: '',
        profilePicture: ''
      }
    };

    return this.http.post(`/api/accounts`, requestBody).flatMap((response: Response) => {
      this.userId = response.json().userId;
      return this.loadUser(this.userId)
    }).map(() => {
      return this.user;
    })
    .catch((err) => {
      throw err;
    })
  }

  /**
   *  authenticate user, creating a session on the server and recieved the userId
   *  then load the users account data into memory via their userId
   */
  login(formValues: LoginFormValues): Observable<any> {
    const requestBody = {
      username: formValues.username,
      password: formValues.password,
      rememberMe: formValues.rememberMe
    };

    return this.http.post(`/api/accounts/login`, requestBody).flatMap((response: Response) => {
      this.userId = response.json().userId;
      return this.loadUser(this.userId);
    })
    .map(() => {
      return this.user;
    })
    .catch((err) => {
      throw err;
    })
  }

  /**
   * logout user, destroying the session on the server
   */
  logout() {
    return this.http.get(`/api/accounts/logout`).map((response: Response) => {
      if (response.status === 200) {
        this.userId = null;
      };
    });
  }

  /**
   *  get and sotre the user account data from the server, found via userId 
   *  you must exist in a session of the user whose data your requesting
   */
  loadUser(userId: String) {
    return this.http.get(`/api/accounts/${userId}`).map((response: Response) => {
      const userFromResponse = response.json();

      this.user.id = userFromResponse._id;
      this.user.username = userFromResponse.username;
      this.user.profile = userFromResponse.profile;
    });

  }

  updateUserProfile(userProfile: UserProfile) {
    const requestBody = this.user;
    requestBody.profile = userProfile;

    // update the user profile and get a returned user data objects which we sotre in memory
    return this.http.put(`/api/accounts/${this.userId}`, requestBody).map((response: Response) => {
      this.user.profile = response.json().profile;
    })
  }

  /**
   * ping server to see if a valid session exists and get userId if so
   * then get and load user account info in the memeory
   */
  checkForSessionAndLoadUser() {
    return this.http.get(`/api/accounts/session`).flatMap((response: Response) => {
      this.userId = response.json().userId;
      return this.loadUser(this.userId);
    })
    .map(() => {
      return this.user;
    })
    .catch((err) => {
      throw err;
    })
  }

}

export interface User {
  id?: String;
  username: String;
  profile: UserProfile,
  password?: String;
}

export interface UserProfile {
  firstName: String;
    lastName: String;
    displayName: String;
    email: String;
    bio: String;
    company: String;
    profilePicture: String;
}

export interface RegistrationFormValues {
  username: String;
  firstName: String;
  lastName: String;
  email: String;
  password: String;
}

interface LoginFormValues {
  username: String;
  password: String;
  rememberMe: Boolean;
}


