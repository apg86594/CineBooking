/*
 * Logs in the user.
 */
const WebSocket = require("ws");
const fs        = require("fs");
const {JSDOM}   = require("jsdom");

const html      = fs.readFileSync("UserView/login.html");
const dom       = new JSDOM(html);
const document  = dom.window.document;

function login_user()
{
    const socket = new WebSocket("ws://127.0.0.1:8888");

    const email     = document.getElementById("login_user");
    const password  = document.getElementById("psw_login");

    socket.onopen = function(event) {
        console.log("Connected to server");
        socket.send(`LOGIN, ${email}, ${password}`);
        socket.close();
    }

    socket.onclose = function(event) {
        if (event.wasClean) {
          console.log(`[CLOSE] Connection closed cleanly, code=${event.code} reason=${event.reason}`);
        } else {
          // e.g. server process killed or network down
          // event.code is usually 1006 in this case
          console.log('[CLOSE] Connection died');
        }
    }
      
    socket.onerror = function(error) {
        console.log(`[ERROR]`);
    }
}
login_user();