/* Script for homepage.html */

/*
 * Is called when window is loaded. Grabs user info to check if a
 * user is logged in or not.
 */
function initialize()
{
    fetch("login-user-info.json")
        .then(response => response.json())
        .then(data => isLoggedIn(data));
}

/*
 * Checks if the user is logged in by checking user info. Changes
 * login button properties based on whether or not the user is logged
 * in or logged out. If logged in, user sees a "Logout" button. If user
 * is not logged in, they will see a "Login" button.
 */
function isLoggedIn(user_data)
{
    const button = document.getElementById("loginBtn");
    if (user_data.email !== "") {
        button.innerHTML = "Logout";
        const regBtn = document.getElementById("registerBtn");
        regBtn.style.display = "none";
    }
    button.addEventListener("click", (event) => {
        event.preventDefault();
        if (button.innerHTML === "Login") {
            window.location.href = "login.html";
        } else {
            logoutUser();
        }
    })
}

/*
 * Establishes a connection with the server to inform the server
 * that the current user has logged out.
 */
function logoutUser()
{
    const socket = new WebSocket("ws://127.0.0.1:8888");
    socket.onopen = () => {
        console.log("Connection to server established.");
        socket.send("LOGOUT");
    }
    socket.onmessage = (event) => {
        console.log(event.data);
        socket.close();
    }
    socket.onclose = () => {
        console.log("Connection to server closed");
    }
}

window.onload = initialize;