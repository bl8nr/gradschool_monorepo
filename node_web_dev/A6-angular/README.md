# Assignment #6 - Angular

The description of this assignment can be found in Canvas at [Assignment #6](https://canvas.harvard.edu/courses/35096/assignments/207194) (Spring 2018)

# Running this Angular front end locally
There are alot of moving parts between the API and the front end due to the authentication and session storage. But this should get you up and running if you would like to run the app locally, although its live on staging.replyas.com right now.

1. Clone the project (pull in the master branch, i'm currently using the develop branch for further progress on the final proejct)
2. Edit the proxy-conf.js so that the "/api" proxy "target" is 'https://staging.replyas.com'
3. Npm install
4. Npm run start

# Notes regarding Assignment 6 Submission
This is built upon what I had before for what will evolve into my final. The idea has changed slightly, its basically a buffer for reddit to allow a user to schedule reddit post to be posted at a specific time in the future when theyre more likely to get more upvotes. For Assignment 6, i was sure to build the 'profile/:profileId/posts' scaffold to the assignment spec, with generous comments. Heres where you could easily find these parts of the project. I also added comment descriptions to the top of each of the components in this scaffold so its easier to understand what they do.

- ./profiles/profile.module.ts, the module which houses the 'posts' component scaffold. Routed to via the ./app-routing.module.ts file
- ./profiles/posts/posts.component.ts (child of the profiles.component, member of the profile.module). Is responsible for fetching the list of posts data structure via the redditService then organizes them by day posted and iterates through the posts (via ngFor in the template) twice to present them in subsections of posts by day posted and injects them into post-card components
- ./profiles/components/post-card, component which takes in the reddit post data and displays it, also houses the post metrics component
- ./shared/services/redditService, shared service that is reponsible for making requests to the reddit public open API

Then for a little extra, and because they live in the same scaffold sorta structure...
- ./profiles/components/performance-metrics, component which houses the post metrics component and a menu to select which post metrics component is in view
- ./profiles/components/post-performance-metrics, component which houses the post performance metrics component/table and makes a API req via the redditService to get the data it needs
- ./profiles/components/subreddit-performance-metrics, component which houses the subreddit performance metrics component/table and makes an API req via the redditService to get the data it needs

# A few more notes about things i noticed when i was coding
- From working professionally with a small team of angular developers (many of which were new to angualr) i've picked up a few coding style habits that seemed to make my code friendlier and easier for others to read, im currious what your thoughts are on these, some things you may notice....
  - some of my comments where the high level objective of a bit of code isnt obvious, are not in active tone and instead take a more descriptive approach (eg. 'get post' v. 'we fetch the post first here because of caching in xyz file...' ). I've found this to be more helpful when angular projects ballon up to a couple hundred files
  - sectioning off HTML tamplates into sections. via a comment like <!-- header --> or just using returns/line breaks, this seems to make the templates more readable when they get to be 100+ lines, although that is uncommon
- I had to build-prod and push to git with /dist again to deploy to DO, so there may be a ./dist folder in some of my commit history. Which was unfortunate after i figured out that totally ruins the github insights metrics that measure how many lines were written, which otherwise is cool to look at. So im definitly looking forward to learning or teaching myself how to deploy an angular app in a more efficient way!

# Assignment Checklist
- [x] Assignment should use the Angular CLI as a starting point
- [x] There should be at least on component in the app.component
- [x] There should be at least on repeating template element using ngFor
- [x] Clean up the project so that there are no scaffolds/templates/.ts files that are unused
- [x] Coding Standards stuff
- [x] Display data from a data strucutre such as a simulated API response of a list of items
- [x] Only READ operations are required, others can be used
- [x] Hardcoded data req* But use assignment-5 api instead along with an API/data service
- [x] Deploy and hope all the moving parts still work when its graded

## Replyas
This project was generated with [Angular CLI](https://github.com/angular/angular-cli) version 1.6.6.
To get more help on the Angular CLI use `ng help` or go check out the [Angular CLI README](https://github.com/angular/angular-cli/blob/master/README.md).

## Running the Development Server
Run `ng serve` for a dev server. Navigate to `http://localhost:4200/`. The app will automatically reload if you change any of the source files.

## Building for Production
Run `ng build` to build the project. The build artifacts will be stored in the `dist/` directory. Use the `-prod` flag for a production build.

## Running Unit Tests
Run `ng test` to execute the unit tests via [Karma](https://karma-runner.github.io).

## Running E2E Tests
This project has no end ot end tests in place.
