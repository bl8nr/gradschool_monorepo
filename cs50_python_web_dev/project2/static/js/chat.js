let channels = [];
let users = [];
let socket = null;
let currentChannelId = null;
let currentChannelMessages = null;
let currentUserId = null;

/**
 * Compile handlebars templates
 */
let channelListTemplate = Handlebars.compile(document.getElementById("channelListTemplate").innerHTML);
let userListTemplate = Handlebars.compile(document.getElementById("userListTemplate").innerHTML);
let messageThreadTemplate = Handlebars.compile(document.getElementById("messageThreadTemplate").innerHTML);

/**
 * Assign event handlers and run initialization functions when the page is ready
 */
$(document).ready(() => {

    // when the displayNameModalButton is clicked, show the displayNameModal
    selectors.displayNameModalButton.click(() => {
        selectors.displayNameModal.modal('show');
    });

    // when the displayNameModalForm is submitted, change the display name if the form input is valid
    selectors.displayNameModalForm.submit((event) => {
        const isValid = checkHTMLFormValidation(event, selectors.displayNameModalForm[0], selectors.displayNameModalTextField);
        if (isValid) {
            createOrChangeDisplayName(selectors.displayNameModalTextField.val());
        }
    });

    // when the newChannelModalButton is clicked, reset the modals error msg and show the modal
    selectors.newChannelModalButton.click(() => {
        selectors.channelAlreadyExistsAlert.hide();
        selectors.newChannelModal.modal('show');
    });

    // when the newChannelModalForm is submitted, create a new channel and reset input field if the form input is valid
    selectors.newChannelModalForm.submit((event) => {
        const isValid = checkHTMLFormValidation(event, selectors.newChannelModalForm[0], selectors.newChannelModalTextField);
        if (isValid) {
            createChannel(selectors.newChannelModalTextField.val());
            selectors.newChannelModalTextField.val("");
        }
    });

    // when the messageComposerForm is submitted, send a new message and reset input field if the form input is valid
    selectors.messageComposerForm.submit((event) => {
        const isValid = checkHTMLFormValidation(event, selectors.messageComposerForm[0], selectors.messageComposerTextArea);
        if (isValid) {
            sendMessage(selectors.messageComposerTextArea.val());
            selectors.messageComposerTextArea.val("");
        }
    });

    // initialize the app by loading the users and connecting to the socket
    load_user();
    init_socketio();
});

/**
 * Attempt to load the locally stored user
 */
function load_user() {
    // get user id from local storage
    const localStorageCurrentUserId = localStorage.getItem("currentUserId");

    // make a REST HTTP request for all users using the app
    const request = new XMLHttpRequest();
    request.open('GET', `/users`);
    request.onload = () => {
        const allAppUsers = JSON.parse(request.responseText);

        // if there is a locally stored user and that user is in the servers list of current users...
        if (localStorageCurrentUserId && (allAppUsers.find((user) => user.id === localStorageCurrentUserId))) {

            // store that user as the apps currentUser and update the DOM accordingly
            currentUserId = localStorageCurrentUserId;
            selectors.displayNameModalTextField.val(allAppUsers.find((user) => user.id === localStorageCurrentUserId).displayName);
            selectors.displayNameModal.modal('hide');
        } else {
            // else reveal the display name modal (the locally stored user isn't there or is invalid)
            selectors.displayNameModal.modal('show');
        }
    };
    request.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    request.send();
}

/**
 * Create a new channel by sending a POST request to the API
 * After a new channel is created, the server will emit channel_update to all clients, causing an channel list update
 */
function createChannel(channelName) {
    const request = new XMLHttpRequest();
    request.open('POST', `/channels`);

    request.onreadystatechange = () => {
        if (request.readyState === 4) {
            if (request.status === 200) {
                selectors.channelAlreadyExistsAlert.hide();
                selectors.newChannelModal.modal('hide');
            } else if(request.status === 409) {

                // if a 409 is returned then the channel name is taken, so show an error
                selectors.channelAlreadyExistsAlert.show();
            }
        }
    };

    request.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    request.send(JSON.stringify({name: channelName, timestamp: Date.now()}));
}

/**
 * Select a channel using the channels channel id
 */
function selectChannel(channelId) {

    // set the current channel to the one provided as an arg, change the DOM channel header, tab bar title and URL
    currentChannelId = channelId;
    const channel = channels.find((channel) => channel.id === currentChannelId);
    selectors.channelNameHeader.html(channel.name);
    document.title = 'Flak App | ' + channel.name;
    history.pushState({}, document.title, `/chat/${channelId}`);

    // make a GET request to the API to get the most recent 100 messages
    const request = new XMLHttpRequest();
    request.open('GET', `/channels/${currentChannelId}/messages`);
    request.onload = () => {

        // parse out the messages and render the handlebars templates to show those messages
        currentChannelMessages = JSON.parse(request.responseText).messages;
        selectors.messageThread.html(messageThreadTemplate(currentChannelMessages));
    };

    request.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    request.send();
}


/**
 * Make a REST request to the API to either create a display name or modify the current display name
 */
function createOrChangeDisplayName(displayName) {

    // if there's a current user, the request is a PUT (update display name) else its a post (create display name)
    requestMethod = currentUserId ? 'PUT' : 'POST';

    // if there's a current user, the path will target that current user by id, else it uses /users
    endpoint = currentUserId ? `/users/${currentUserId}` : '/users';

    const request = new XMLHttpRequest();
    request.open(requestMethod, endpoint);
    request.onload = () => {

        // set the locally stored userId to the user id of the new/updated display name
        localStorage.setItem("currentUserId", JSON.parse(request.responseText).id);

        // reusing the load user function. Load the new/updated user from the user id now stored locally
        load_user();
    };
    request.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    request.send(JSON.stringify({displayName: displayName}));
}

/**
 * Initialize SocketIO web socket connection to the server, triggering initial user and channel updates from server
 * Setup event handlers for channel and user updates and chat message receives
 */
function init_socketio() {

    // on connection, server will emit channel_update and users_update events, making the app load users and channels
    socket = io();

    // process the new chat message once its received from the server
    socket.on('chat_message', (message) => {
        receiveMessage(message)
    });

    // update the locally stored channels once an update is received from the server
    socket.on('channel_update', (response) => {
        updateChannels(response.channels);
    });

    // update the locally stored users once an update is received from the server
    socket.on('users_update', (response) => {
        updateUsers(response.users);
    });
}

/**
 * Update all the users in the app
 */
function updateUsers(updatedUsers) {

    // update the users in memory and update the users in the DOM and the message thread to reflect user name changes
    users = updatedUsers;
    selectors.userList.html(userListTemplate(users));
    selectors.messageThread.html(messageThreadTemplate(currentChannelMessages));
}

/**
 * Update all the Channels in the app
 */
function updateChannels(updatedChannels) {

    // update the channels in memory and update the channels list in the DOM
    channels = updatedChannels;
    selectors.channelList.html(channelListTemplate(channels));

    // if there is no currentChannel (ie, the app just loaded), then set the current channel to the first channel
    if (!currentChannelId) {
        selectChannel(channels[0].id);
    }
}

/**
 * Send a message
 */
function sendMessage(message) {

    // emit a socketio event (with channelId, userId, timestamp and message)
    socket.emit('chat_message', currentChannelId, currentUserId, Date.now(), message);
}

/**
 * Receive a new message from the server and update the browser accordingly
 */
function receiveMessage(message) {

    // if this message belongs to the currently selected channel...
    if (message.channel === currentChannelId) {

        // first delete the oldest message if there are 100 or more msgs stored in browser
        if (currentChannelMessages.length >= 100) {
            currentChannelMessages.shift()
        }

        // then push the new msg into the current channels message array, update the message thread and scroll down
        currentChannelMessages.push(message);
        selectors.messageThread.html(messageThreadTemplate(currentChannelMessages));
        selectors.messageThreadContainer.scrollTop(selectors.messageThreadContainer.height());
    }

}

/**
 * Utility function to still allow the use of HTML native form validation despite using JQuery/JS
 */
function checkHTMLFormValidation(event, formElement, textInputElement) {
    event.preventDefault();
    if (formElement.checkValidity()) {
        return true;
    } else {
        alert(textInputElement.validationMessage);
        return false;
    }
}