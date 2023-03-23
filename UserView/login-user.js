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
    }

    socket.onmessage = function(event) {
      const s_message = event.data;
      const user_data = {
          user_id:      s_message.split(",")[1],
          password:     s_message.split(",")[2],
          firstname:    s_message.split(",")[3],
          lastname:     s_message.split(",")[4],
          email:        s_message.split(",")[5],
          user_type:    s_message.split(",")[6],
          billing_addr: s_message.split(",")[7],
          ACTIVE:       s_message.split(",")[8],
          card_num:     s_message.split(",")[9],
          ccv_num:      s_message.split(",")[10],
          month_exp:    s_message.split(",")[11],
          year_exp:     s_message.split(",")[12]
      }

      // Writes data into JSON file
      const json_data = JSON.stringify(user_data);
      fs.writeFile("login-user-info.json", json_data, (err) => {
          if (err) throw err;
          console.log("[SUCCESS] Data written to file.");
      });

      // closes socket, might need to move elsewhere
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