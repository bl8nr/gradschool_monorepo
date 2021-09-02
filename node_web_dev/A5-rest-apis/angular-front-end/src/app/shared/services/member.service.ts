import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http'
import { Observable } from 'rxjs/Observable';

@Injectable()
export class MemberService {

  constructor(
    private http: HttpClient
  ) { }

  fetchMember(teamId, memberId): Observable<any> {
    return this.http.get(`https://www.multipost.io/teams/${teamId}/members/${memberId}`);
  };

  updateMember(teamId, memberId, data): Observable<any> {
    const body = {
      displayFirstName: data.displayFirstName,
      displayLastName: data.displayLastName,
      jobTitle: data.jobTitle,
      signature: data.signature
    };
    
    return this.http.put(`https://www.multipost.io/teams/${teamId}/members/${memberId}`, body);
  }

}
