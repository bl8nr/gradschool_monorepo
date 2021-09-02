/* Good to go for final */
const Profile = require('../models/profile');

class PostService {

  /**
   * return posts of a profile, by profile ID
   */
  static list(profileId) {
    return GetProfileById(profileId).then((Profile) => {
      return Profile.posts;
    })
  };

  /**
   * create a post and return posts of the changed profile[posts]
   */
  static create(profileId, userId, postData) {

    const deconstructedPost = {
      title: postData.title,
      subreddit: postData.subreddit,
      link: postData.link,
      body: postData.body,
      type: postData.type,
      targetPostTimeUTC: postData.targetPostTimeUTC,
      targetPostTimeReadable: postData.targetPostTimeReadable,
      status: 'new',
      createdBy: userId,
      profileId: profileId
    };

    return GetProfileById(profileId)
      .then((Profile) => {
        Profile.posts.push(deconstructedPost);
        return Profile.save();
      })
      .then((updatedProfile) => {
        return updatedProfile.posts;
      });

  }

  /**
   * update a post and return posts of the changed profile[posts]
   */
  static update(profileId, postId, postData) {

    return GetProfileById(profileId)
      .then((Profile) => {
        const post = Profile.posts.id(postId);
        post.title = postData.title;
        post.link = postData.link;
        post.type = postData.type;
        post.body = postData.body;
        return Profile.save()
      })
      .then((updatedProfile) => {
        return updatedProfile.posts;
      });
    
  }

  /**
   * delete a post and return posts of the changed profile[posts]
   */
  static delete(profileId, postId) {

    return GetProfileById(profileId)
      .then((Profile) => {
        Profile.posts.pull(postId);
        return Profile.save();
      })
      .then((updatedProfile) => {
        return updatedProfile.posts;
      });

  }

}

/** PRIVATE get a Profile model by ID
 * we use this all over this service since
 * Posts are children of Profiles
 */
GetProfileById = function(profileId) {
  return Profile.findById(profileId)
    .then((profile) => {
      return profile;
    })
}

module.exports = PostService;