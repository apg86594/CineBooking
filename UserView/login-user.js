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
        button.addEventListener("click", (event) => {
            event.preventDefault();
            loginUser();
        });
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
        console.log("Message received from server:", event.data);

        // If login failed, handle server error message
        if (event.data.split(",")[0] !== "SUCCESS") {
            handleLoginError(event.data);

        // Else, login was successful
        // For testing, remove else statement to bypass "NOTACTIVE" status
        } else {

            // Redirects admins to the admin-home.html page
            if (event.data.split(",")[6] === "ADMIN") {
                window.location.href = "admin/admin-home.html";

            // Otherwise, direct user to default homepage
            } else {
                window.location.href = "homepage.html"
            }
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
        // User does not exist; therefore offer option to register
        case "BADUSER":
            const errorElem = document.getElementById("error-message");
            errorElem.innerHTML = "User does not exist. Click the button to go to the registration page.";
            const registerBtn = document.createElement("button");
            registerBtn.innerHTML = "Go to registration page";
            registerBtn.addEventListener("click", () => {
                window.location.href = "register.html";
            });
            errorElem.appendChild(registerBtn);
            break;

        // User exists but used incorrect password
        case "BADPASSWORD":
            const pswbox = document.getElementById("psw_login");
            pswbox.setAttribute("placeholder", "Incorrect password");
            pswbox.value = "";
            pswbox.style.borderColor = "red";
            pswbox.style.borderWidth = "2px";
            break;

        // User has not confirmed account via email confirmation
        case "NOTACTIVE":
            const _errorElem = document.getElementById("error-message");
            _errorElem.innerHTML = "Please confirm registration before logging in. Check your email for a confirmation code.";
            break;
    }
}

window.onload = initialize;