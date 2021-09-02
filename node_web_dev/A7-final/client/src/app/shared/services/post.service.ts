import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map';

/**
 * POST SERVICE (CRUD HTTP Service)
 * this service is responsible for all requests dealing with posts.
 * posts are items in the queue, posts that will be or have already
 * been progrmatically posted by our API. This service is stateless,
 * so IDs are required of just about every req.
 *
 * DO NOT CACHE POSTS OR IDS IN THIS SERVICE!!!
 */

@Injectable()
export class PostService {

  constructor(
    private httpClient: HttpClient
  ) { }

  /**
   * get all of the posts associated to reddit profile
   * requires authed access to the reddit profile
   * @param profileId uuid of a reddit OAuth relationship in the API
   */
  getPosts(profileId: string): Observable<any> {
    return this.httpClient.get(`/api/profiles/${profileId}/posts`).map((posts) => {
      return posts;
    });
  }

  /**
   * get one post by id that is associated to a reddit profile
   * @param profileId uuid of a reddit OAuth relationship in the API
   * @param postId uuid of a post that belongs to (profileId)
   */
  getPost(profileId, postId) {
    return this.httpClient.get(`/api/profiles/${profileId}/posts/${postId}`).map((post) => {
      return post;
    });
  }

  /**
   * create one post under the profileID of a reddit profile
   * @param profileId uuid of a reddit OAuth relationship in the API
   */
  createPost(profileId, postBody) {
    return this.httpClient.post(`/api/profiles/${profileId}/posts`, postBody).map((posts) => {
      return posts;
    });
  }

  /**
   * update one post under the profileId of a reddit profile
   * @param profileId uuid of a reddit OAuth relationship in the API
   * @param postId uuid of a post that belongs to (profileId)
   * @param postBody post object matching the API model spec
   */
  updatePost(profileId, postId, postBody) {
    return this.httpClient.put(`/api/profiles/${profileId}/posts/${postId}`, postBody).map((posts) => {
      return posts;
    });
  }

  /**
   * delete one post by postId under the profileId of a reddit profile
   * @param profileId uuid of a reddit OAuth relationship in the API
   * @param postId uuid of a post that belongs to (profileId)
   */
  deletePost(profileId, postId) {
    return this.httpClient.delete(`/api/profiles/${profileId}/posts/${postId}`).map((posts) => {
      return posts;
    });
  }

}
