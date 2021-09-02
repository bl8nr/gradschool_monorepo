just some bullets on assignment 5...
- Theres a short description below these bullets of what im aiming to build
- This includes the angular code i've built so far, i figure it could be a good time to glue them both together since the backend is now RESTful
- I built the 'team' object to assignment 5 spec and to be 100% RESTful as much as i understand the concept, which hopefully is pretty well. However, building auth and user accounts with passport to be RESTful was a little more difficult
- I kept sessions although i know sessions arent very RESTful. Auth was much easier to handle with the browser taking care of the cookie based sessions rather than the front end having to manage a token or usernam:password header. Although i understand that a 100% RESTful way to secure an public API would be with a token or username:password header. You'll see in the API code i tired to make session and account conform to RESTful terminology where i could. Sessions are also persisted in mongo, so the API is stateless which is nice and scaleable
- User accounts are also fully functional, you can register, login, and update your user account although thats mostly a product of me building building auth system.
- The only outside code source i used were the express generator, angular cli, bootstrap, jquery, a few fonts, and a bootstrap theme
- there are a very few components in the front end that have placeholders right now but i've limited those to things im literally planning on building over the next few days (like team and profile pictures) so thats why they're there
- Late in the project i relized my naming could use some work. As an example, in my code i noticed 'account' is synonymous with 'user', so thats something im going to go back and refactor before assignment 6
- I used an nginx reverse proxy to configure ssl and access to the angular dist code and the /api and decouple it from the API codebase, this allows me to use the shipit npm app (in the shipitfile.js file) to automatically deploy my API without having to SSH into my server and it allows me to deploy the front or backend individually, i also included the nginx.conf file in this repo
- I removed alot of the app.js cruft dealing with the pug templating engine and sass compiler i had in assignment 4 since the app is angular powered now

i think thats pretty much it, let me know if you have any questions or if you want me to invite you to my angular codebase repo or make it public for you to review if you want to see the commits or anything like that for grading, although i know the criteria for this assignment was mostly on the API side

# Final Project Description: Reply As (staging.replyas.com / app.replyas.com / replyas.com)
The concept is basically to allow one person to provision access to social media accounts to other people so they can contribute to the success of that one account. Imagine the use case of a social media marketing manager for a small SAAS company wanting to have their technical employees be active on reddit or stack overflow to give the company an image of expertise and thought leadership. The tool would basically allow them all to post under one company named account (without sharing passwords and junk) and would ensure that all the upvotes and credibility go to and stay with the companys social accounts even after employees leave, it would also allow employees to team up on reddit or stack threads in 'draft' states for faster or more thoughtful responses. The marketing manager at the company im at now experienced problems similiar to these, so i thought it would be cool to make a tool for it and it seemed like a project that could keep my attention after the course ends, for practice and to keep my coding skillz sharp.

# REST API Documentation

## team
#### GET /teams
###### Auth: Any user
Returns an array of teams the user has access to.

    Response Body
    [
        {
            adminEmail: String,
            createdAt: Date,
            members: Member[],
            ownerId: String (uuid),
            teamDescription: String,
            teamName: String
        },
        ...
    ]

#### POST /teams
###### Auth: Any user
Creates a new team and sets the ownerId to the _id of the user creating the team.
    
    Req Body
    {
        teamName: String* ,
        organizationName: String* ,
        adminEmail: String* ,
        teamDescription: String*
    }
#
    Response Body
    {
        adminEmail: String,
        createdAt: Date,
        members: Member[],
        ownerId: String (uuid),
        teamDescription: String,
        teamName: String
    }

#### GET /teams/:teamId
###### Auth: Any member, admin, or owner of the team
Returns a single team.

#### PUT /teams/:teamsId
###### Auth: Any admin or owner of the team.
Updates a team and returns an updated team. However, the arrays of members and integrations are not mutated.

    Request Body
    {
        teamName: String* ,
        organizationName: String* ,
        adminEmail: String* ,
        teamDescription: String
    }
    
#
    Response Body
    {
        adminEmail: String,
        createdAt: Date,
        members: Member[],
        ownerId: String (uuid),
        teamDescription: String,
        teamName: String
    }
    
#### DELETE /teams/:teamId
###### Auth: Session must be of user that owns the team
Deletes an entire team including the teams member arrays.


