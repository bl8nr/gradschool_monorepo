import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/mergeMap';
import 'rxjs/add/observable/throw';

// import models
import { User, UserProfile } from '../../shared/models/user';

@Injectable()
export class UserService {
  // store the user object in the service
  user: User;

  constructor(
    private httpClient: HttpClient
  ) { }

  // login a user then get the user data and store the user in the service
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
        // user the login response userId to get the user data
        return this.getUser(loginResponse.userId);
      });
  }

  // logout the user then clear the user service of the user object
  logout() {
    return this.httpClient.get(`/api/accounts/logout`).map((responseMessage: String) => {
      if (responseMessage === 'success') {
        this.user = null;
      }
    });
  }

  // get the user by userId (id must be of the session users id) and create a new User and store it in memory
  getUser(userId: String): Observable<void> {
    return this.httpClient.get(`/api/accounts/${userId}`).map((userData: User) => {
      const newUserData = new User(userData);
      this.user = newUserData;
    });
  }

  // save changes to the user profile, then update the stored user object to what is returned
  saveUser() {
    const requestBody = this.user;

    return this.httpClient.put(`/api/accounts/${this.user.id}`, requestBody).map((userData: User) => {
      this.user.profile = userData.profile;
    });
  }

  // register a new user, get the new userId then use that to load the new User into memory
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

  // check for an existing session and if one exists then load the user into memory
  checkForSessionAndLoadUser(): Observable<void> {

    interface SessionResponse {
      userId: String;
    }

    return this.httpClient.get(`/api/accounts/session`).flatMap((sessionResponse: SessionResponse) => {
      return this.getUser(sessionResponse.userId);
    });
  }

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


