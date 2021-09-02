import { Injectable } from '@angular/core';

import { Http, Response } from '@angular/http'
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/mergeMap';
import 'rxjs/add/observable/throw';
import { FormBuilder } from '@angular/forms';

@Injectable()
export class TeamService {
  team: Team;

  constructor(
    private http: Http
  ) { }

  /**
   * request a array of teams the user has access to
   * this is not stored its just used for real time feedback like in navbar
   */
  fetchTeams(): Observable<Team[]> {
    return this.http.get(`/api/teams`).map((response: Response) => {
      // response: array of teams
      return response.json();
    });
  };

  /**
   *  request a specific team by id and store it in memory 
   *  a team is only returned in the user is a member of the team
   */
  fetchAndStoreTeam(teamId): Observable<Team> {
    // featch team and load it into the team service
    return this.http.get(`/api/teams/${teamId}`).map((response: Response) => {
      // response: one team
      this.team = response.json();
      return this.team;
    });
  }

  /**
   *  create a team and store it in memory 
   *  the current user is set as the owner and added as a member
   *  when creating a team the UI focus is on said team, 
   *  so its okay to store it at 'the' team in memory even though it wasnt explicitly navigated to
   */
  createTeam(formValues): Observable<Team> {
    const requestBody: NewTeamRequestBody = {
      teamName: formValues.teamName,
      teamDescription: formValues.teamDescription,
      adminEmail: formValues.adminEmail
    };

    return this.http.post(`/api/teams`, requestBody).map((response: Response) => {
      // response: one team just created
      this.team = response.json();
      return this.team;
    })
  }

  updateTeam(formValues: Team, teamId): Observable<Team> {
    const requestBody = {
      teamName: formValues.teamName,
      teamDescription: formValues.teamDescription,
      adminEmail: formValues.adminEmail
    };

    return this.http.put(`/api/teams/${teamId}`, requestBody).map((response: Response) => {
      // response: one updated team
      this.team = response.json();
      return this.team;
    })
  }

  /**
   * delete a team and remove it from memory
   * this current session must be of the owner of the team
   */
  deleteTeam(teamId): Observable<any> {
    
    return this.http.delete(`/api/teams/${teamId}`).map((response: Response) => {
      // null
    })
  }

}

export interface NewTeamRequestBody {
  teamName: String;
  teamDescription: String;
  adminEmail: String;
}

export interface Team {
  _id: String;
  teamName: String;
  organizationName: String;
  adminEmail: String;
  ownerId: String;
  teamDescription: String;
  members: any[];
}
