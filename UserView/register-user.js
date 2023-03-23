/*
 * Registers the user with the server.
 */
const WebSocket = require("ws");
const fs        = require("fs");
const {JSDOM}   = require("jsdom");

const html      = fs.readFileSync("UserView/register.html");
const dom       = new JSDOM(html);
const document  = dom.window.document;

function register_user()
{
    const socket = new WebSocket("ws://127.0.0.1:8888");
    console.log("YOU MADE IT HERE");
    // Required fields for registering a user
    const password      = document.getElementById("psw");
    const firstname     = document.getElementById("firstname");
    const lastname      = document.getElementById("lastname");
    const email         = document.getElementById("email");
    const USERTYPE      = 1;
    const billingZIP    = document.getElementById("billingZIP");
    const cardnum       = document.getElementById("cardnum");
    const cvv           = document.getElementById("cvv");
    const month         = document.getElementById("month");
    const year          = document.getElementById("year");

    socket.onopen = function(event) {
        console.log("Connected to server");
        socket.send(`REGISTER,
                ${password}, 
                ${firstname}, 
                ${lastname}, 
                ${email}, 
                ${USERTYPE}, 
                ${billingZIP},
                ${cardnum},
                ${cvv},
                ${month},
                ${year}`);
    };

    socket.onmessage = function(event) {
        console.log(`Received message.\n[MESSAGE]: ${event.data}\nClosing connection.`);
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
    };
      
    socket.onerror = function(error) {
        console.log(`[ERROR]: ${error}`);
    };
}

const btn = document.getElementById("signupbtn");
if (btn) { btn.addEventListener("click", register_user); }