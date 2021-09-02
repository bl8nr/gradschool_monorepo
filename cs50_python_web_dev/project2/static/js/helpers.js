/**
 * Handlebar Helpers
 * All handlebar helpers used in this app are in this file, that way the main chat.js file is easier to read
 * and that way these handlebar helpers are easier to manage.
 */

/**
 * This handlebar helper will convert an epoch timestamp into a human readable date
 */
Handlebars.registerHelper("prettifyDate", function (timestamp) {
    return (new Date(timestamp).toLocaleDateString() + ' ' + new Date(timestamp).toLocaleTimeString());
});

/**
 * This handlebar helper will replace a user ID with the displayName associated with that user ID
 */
Handlebars.registerHelper("getUserDisplayNameById", function (userId) {
    return (users.find((user) => user.id === userId).displayName);
});

/**
 * This handlebar helper is specific to the chat bubbles and will right justify and element if the
 * user id passed in is that of the current user
 */
Handlebars.registerHelper('if_current_user', function (senderId) {
    if (currentUserId === senderId) {
        return 'text-right';
    }

    // if the senderId isn't the currentUserId, then dont't return a css class name
    return null;
});