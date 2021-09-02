import os
import uuid
from flask import Flask, render_template, request, jsonify, redirect, abort
from flask_socketio import SocketIO, emit
from flask_assets import Environment, Bundle

app = Flask(__name__)
assets = Environment(app)

# bundle all javascripts into one js file
js = Bundle('js/selectors.js', 'js/chat.js', 'js/helpers.js', filters='jsmin', output='packed.js')
assets.register('js_all', js)

# bundle all scss and precompile it into one css file
scss = Bundle('scss/styles.scss', filters='pyscss', output='all.css')
assets.register('scss_all', scss)

# setup and initialize socketIO
app.config["SECRET_KEY"] = os.getenv("SECRET_KEY")
socketio = SocketIO(app)

# create data store for users, channels, and messages
# Add a system Flakbot and a test default channel
dataStore = {
    "users": [
        {"displayName": "Flakbot", "id": "00000000-0000-0000-0000-000000000000"}
    ],
    "channels": [
        {
            "id": "9ffd70ee-f2a7-11e9-be77-acde48001122",
            "name": "Test Channel"
        }
    ],
    "messages": [
        {
            "channelId": "9ffd70ee-f2a7-11e9-be77-acde48001122",
            "messages": [
                {
                    "sender": "00000000-0000-0000-0000-000000000000",
                    "message": "This is just a test channel",
                    "timestamp": 1572136394648,
                    "channel": "9ffd70ee-f2a7-11e9-be77-acde48001122"
                }
            ]
        }
    ]
}


# HTML ROUTES
# index serves the only html page since this is a SPA
@app.route("/", methods=["GET"])
def index():
    return render_template('index.html')


# catch all redirect to send users to the single page app index
@app.route('/<path:path>')
def redirectToIndex(path):
    return redirect('/', code=301)


# REST API ROUTES
# create a user or get all users
@app.route("/users", methods=["POST", "GET"])
def users():
    # if the method used is POST (client wants to create a user)...
    if request.method == "POST":

        # shape the user object and store it in the users data store
        user = {
            "displayName": request.json["displayName"],
            "id": str(uuid.uuid1())
        }
        dataStore["users"].append(user)

        # let all clients know there's a users update and return the user with ID to the requester
        emit_users_update(True)
        return jsonify(user)
    else:

        # otherwise the method used must be GET, so return all the users to the requester
        return jsonify(dataStore["users"])


# change a users display name
@app.route("/users/<user_id>", methods=["PUT"])
def editUserById(user_id):
    # find that user by id in the users data store and save the new display name
    user = next(x for x in dataStore["users"] if x["id"] == user_id)
    user["displayName"] = request.json["displayName"]

    # let all clients know there's a users update and return the user with new display name to the requester
    emit_users_update(True)
    return jsonify(user)


# create a new channel
@app.route("/channels", methods=["POST"])
def channels():
    # if the channel name already exists, fail early and return a 409
    if any(channel for channel in dataStore["channels"] if channel["name"] == request.json["name"]):
        return abort(409, "Channel name already in use")

    # assign a new uuid to the channel and store it in the channels data store
    print('wefewfwefw')
    channel_id = str(uuid.uuid1())
    dataStore["channels"].append({
        "name": request.json["name"],
        "id": channel_id
    })

    # add a new dict in messages for the new channel to store its messages. add a welcome message.
    dataStore["messages"].append({
        "channelId": channel_id,
        "messages": [
            {"sender": "00000000-0000-0000-0000-000000000000", "message": "Welcome to this new channel!",
             "timestamp": request.json["timestamp"], "channel": channel_id}
        ]
    })

    # let all clients know there's a channels update and return channels to the requester
    emit_channels_update(True)
    return jsonify(dataStore["channels"])


# get the messages for a channel
@app.route("/channels/<channel_id>/messages", methods=["GET"])
def getChannelMessagesByChannelId(channel_id):
    # find the message store for a channel by channel id, jsonify the messages and return them to the requester
    return jsonify(next(x for x in dataStore["messages"] if x["channelId"] == channel_id))


# SOCKET EVENT HANDLERS
# on receiving of a connect event...
@socketio.on('connect')
def connect():
    # emit a response informing the client of current users and a response informing the client of the current channels
    emit_users_update(False)
    emit_channels_update(False)


# on the receiving of a chat_message event
@socketio.on('chat_message')
def chat_message(channelId, userId, timestamp, message):
    # find the message store that the incoming message belongs to
    messageStore = next(x for x in dataStore["messages"] if x["channelId"] == channelId)

    # shape the message so that it can be added to the message storage
    newMessage = {
        "channel": channelId,
        "sender": userId,
        "message": message,
        "timestamp": timestamp
    }

    # if the message store has more than 100 messages, pop the first one off the array
    if len(messageStore["messages"]) >= 100:
        messageStore["messages"].pop(0)

    # store the new message in the message store
    messageStore["messages"].append(newMessage)

    # emit a chat_message event to all clients to let them know another message is coming
    emit('chat_message', newMessage, broadcast=True)


# emit event to let one (if broadcast = False) or all (if broadcast = True) clients know the channel list has changed
def emit_channels_update(broadcast):
    socketio.emit('channel_update', {'channels': dataStore['channels']}, broadcast=broadcast)


# emit event to let one (if broadcast = False) or all (if broadcast = True) clients know the users list has changed
def emit_users_update(broadcast):
    socketio.emit('users_update', {'users': dataStore['users']}, broadcast=broadcast)
