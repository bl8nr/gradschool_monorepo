/**
 * Element Selectors
 * All of the JQuery HTML element selectors are located in this file in the selectors object.
 * I found it much easier to organize my selectors this way and it also helped make the main
 * JS file (chat.js) more readable. All selectors in this app are ID based.
 */

const selectors  = {
    newChannelModal: $("#newChannelModal"),
    newChannelModalForm: $("#newChannelModalForm"),
    newChannelModalButton: $("#newChannelModalButton"),
    newChannelModalTextField: $("#newChannelModalTextField"),
    displayNameModal: $("#displayNameModal"),
    displayNameModalForm: $("#displayNameModalForm"),
    displayNameModalButton: $("#displayNameModalButton"),
    displayNameModalTextField: $("#displayNameModalTextField"),
    channelList: $("#channelList"),
    userList: $("#userList"),
    channelNameHeader: $("#channelNameHeader"),
    channelAlreadyExistsAlert: $("#channelAlreadyExistsAlert"),
    messageThreadContainer: $("#messageThreadContainer"),
    messageThread: $("#messageThread"),
    messageComposerForm: $("#messageComposerForm"),
    messageComposerTextArea: $("#messageComposerTextArea")
};