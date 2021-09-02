/* Good to go for final */
/**
 * Utilities for dealing with reddit OAuth
 */
const Profile = require('../models/profile');
const requestPromise = require('request-promise');

const oauthUtilities = {};

/**
 * HTTP request the access token from Reddit and
 * return the response body (tokens)
 */
oauthUtilities.getAccessToken = function(code) {
  const auth = new Buffer(process.env.REDDIT_CLIENT_ID + ':' + process.env.REDDIT_CLIENT_SECRET).toString('base64');

  var options = {
    method: 'POST',
    uri: 'https://www.reddit.com/api/v1/access_token',
    form: {
      grant_type: 'authorization_code',
      code: code,
      redirect_uri: process.env.REDDIT_REDIRECT_URI
    },
    headers: {
      Authorization: 'Basic ' + auth,
      'Content-Type': 'application/x-www-form-urlencoded'
    }
  };

  return requestPromise(options)
    .then((responseBody) => {
      if (JSON.parse(responseBody).error ) {
        throw JSON.parse(responseBody).error;
      }
      return JSON.parse(responseBody);
    });

};

/**
 * HTTP request a refreshed AccessToken using the
 * refresh_token passed in, return the response
 * body (refreshed tokens)
 */
oauthUtilities.refreshAccessToken = function(code) {
  const auth = new Buffer(process.env.REDDIT_CLIENT_ID + ':' + process.env.REDDIT_CLIENT_SECRET).toString('base64');

  var options = {
    method: 'POST',
    uri: 'https://www.reddit.com/api/v1/access_token',
    form: {
      grant_type: 'refresh_token',
      refresh_token: code
    },
    headers: {
      Authorization: 'Basic ' + auth,
      'Content-Type': 'application/x-www-form-urlencoded'
    }
  };

  return requestPromise(options)
    .then((responseBody) => {
      if (JSON.parse(responseBody).error ) {
        throw JSON.parse(responseBody).error;
      }
      return JSON.parse(responseBody);
    });

};

/**
 * MIDDLEWARE
 * compare current token age and expiry, if valid proceed
 * to next(), if not, refresh the token and proceed to next()
 */
oauthUtilities.checkAccessTokenExpiration = function(req, res, next) {
  Profile.findById(req.params.profileId)
    .then((profile) => {
      const issuedAt = profile.authorization.issuedAt;
      const expiresIn = profile.authorization.expiresIn;
      const currentTime = new Date().getTime() / 1000;

      if ((currentTime - issuedAt) >= expiresIn) {
        oauthUtilities.refreshAccessToken(profile.authorization.refreshToken)
          .then((authData) => {
            profile.authorization.issuedAt = currentTime;
            profile.authorization.accessToken = authData.access_token;
            profile.authorization.tokenType = authData.token_type;
            profile.authorization.expiresIn = authData.expires_in;
            profile.authorization.scope = authData.scope;
            return profile.save();
          })
          .then(() => {
            next();
          });
      } else if ((currentTime - issuedAt) <= expiresIn) {
        next();
      };

    });

};

/**
 * HTTP request reddit profile/account info by accessToken
 * return response body (reddit profile/account info)
 */
oauthUtilities.getAccountInfo = function(accessToken) {
  const options = {
    uri: 'https://oauth.reddit.com/api/v1/me',
    method: 'GET',
    headers: {
      'User-Agent': 'client'
    },
    auth: {
      'bearer': accessToken
    }
  };

  return requestPromise(options)
    .then((responseBody) => {
      if (JSON.parse(responseBody).error ) {
        throw 'err';
      }
      return JSON.parse(responseBody);
    });
};

module.exports = oauthUtilities;