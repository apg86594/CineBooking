/* Script for login.html */

var socket = null;

/*
 * Connects to the server when the page is initially loaded and
 * adds an event listener to the Login button.
 */
function initialize()
{
    socket = new WebSocket("ws://127.0.0.1:8888");
    socket.onopen = () => {
        console.log("Connection to server established.");
        var button = document.getElementById("loginbtn");
        button.addEventListener("click", loginUser);
    }
}

/*
 * Sends login data to the server. If successful, we'll store
 * their information in a JSON file for later use.
 */
function loginUser()
{
    const email     = document.getElementById("email_login").value;
    const password  = document.getElementById("psw_login").value;
    const message   = `LOGIN,${email},${password}`;
    socket.send(message);

    socket.onmessage = (event) => {
        var message = event.data;
        console.log("Message received from server:", message); // debug
        if (message.split(",")[0] !== "SUCCESS") {
            handleLoginError(message);
        } else {
            const user_data = {
                userID:         message.split(",")[1],
                password:       message.split(",")[2],
                firstName:      message.split(",")[3],
                lastName:       message.split(",")[4],
                email:          message.split(",")[5],
                userType:       message.split(",")[6],
                billingAddress: message.split(",")[7],
                ACTIVE:         message.split(",")[8],
                cardnum:        message.split(",")[9],
                securitynum:    message.split(",")[10],
                expmonth:       message.split(",")[11],
                expdate:        message.split(",")[12]
            }
            const json_data = JSON.stringify(user_data);
            fs.writeFile("login-user-info.json", json_data, (err) => {
                if (err) throw err;
                console.log("Data written to file.");
            });
        }
        socket.close();
    }

    socket.onclose = (event) => {
        console.log("WebSocket connection closed with code:", event.code);
    }

    socket.addEventListener("error", (event) => {
        console.error("WebSocket connection error:", event);
    });
}

/*
 * Handles login errors.
 */
function handleLoginError(message)
{
    switch (message)
    {
        case "BADUSER":
            // user does not exist, handle here
            break;
        case "BADPASSWORD":
            // notify user of incorrect password
            break;
        case "NOTACTIVE":
            // notify user to confirm registration first
            break;
    }
}

window.onload = initialize;