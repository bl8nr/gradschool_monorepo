import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/mergeMap';
import 'rxjs/add/observable/throw';

import { User, UserProfile } from '../../shared/models/user';

@Injectable()
export class UserService {
  // store the user object in the service
  user: User;

  constructor(
    private httpClient: HttpClient
  ) { }

  /**
   * login user, then fetch the user data and store it in this.user
   * @param formValues form value object containing login details
   */
  login(formValues: LoginFormValues): Observable<void> {
    const requestBody = {
      username: formValues.username,
      password: formValues.password,
      rememberMe: formValues.rememberMe
    };

    interface LoginResponse {
      userId: string;
    }

    return this.httpClient.post(`/api/accounts/login`, requestBody)
      .flatMap((loginResponse: LoginResponse) => {
        // use the login responses' userId to fetch/store the user data
        return this.getUser(loginResponse.userId);
      });
  }

  /**
   * logout the current user, then clear the this.user object
   */
  logout() {
    return this.httpClient.get(`/api/accounts/logout`).map((responseMessage: String) => {
      if (responseMessage === 'success') {
        this.user = null;
      }
    });
  }

  /**
   * get user info by user id (id must be of the session users id), store it as this.user
   * @param userId users UUID
   */
  getUser(userId: String): Observable<void> {
    return this.httpClient.get(`/api/accounts/${userId}`).map((userData: User) => {
      const newUserData = new User(userData);
      this.user = newUserData;
    });
  }

  /**
   * save changes to a user (id must be of the session users id), store the new user as this.user
   */
  saveUser() {
    const requestBody = this.user;

    return this.httpClient.put(`/api/accounts/${this.user.id}`, requestBody).map((userData: User) => {
      this.user.profile = userData.profile;
    });
  }

  /**
   * register a new user, get the new userId the load and store the user in this.user
   * @param formValues registration form values
   */
  register(formValues: RegistrationFormValues): Observable<void> {

    const requestBody = {
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

    interface RegisterResponse {
      userId: string;
    }

    return this.httpClient.post(`/api/accounts`, requestBody).flatMap((registerResponse: RegisterResponse) => {
      return this.getUser(registerResponse.userId);
    });
  }

  /**
   * check for an existing session, if one exists then load the user into this.user
   */
  checkForSessionAndLoadUser(): Observable<void> {

    interface SessionResponse {
      userId: String;
    }

    return this.httpClient.get(`/api/accounts/session`).flatMap((sessionResponse: SessionResponse) => {
      return this.getUser(sessionResponse.userId);
    });
  }

}

/**
 * registration form type/model
 */
export interface RegistrationFormValues {
  username: String;
  firstName: String;
  lastName: String;
  email: String;
  password: String;
}

/**
 * login form type/model
 */
interface LoginFormValues {
  username: String;
  password: String;
  rememberMe: Boolean;
}
