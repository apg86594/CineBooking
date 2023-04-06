/* Script for confirmation.html */

var socket = null;

/* 
 * Called when window is loaded.
 */
function initialize()
{
    socket = new WebSocket("ws://127.0.0.1:8888");
    socket.onopen = () => {
        console.log("Connection to server established.");
        const button = document.getElementById("confirmBtn");
        button.addEventListener("click", (event) => {
            event.preventDefault();
            confirmUser();
        });
    }
}

/*
 * Confirms the user with the server.
 */
function confirmUser()
{
    socket.send(`CONFIRM,${document.getElementById("emailBox").value},${document.getElementById("confirmBox").value}`);

    socket.onmessage = (event) => {
        console.log(event.data);
        if (event.data.toString().toUpperCase() === "SUCCESS") {
            history.replaceState(null, null, "register_suc.html");
            window.onpopstate = () => {
                history.go(1);
            }
            socket.close();
        } else {
            console.log("Confirmation failed.");
            const confirmError = document.getElementById("confirm-error");
            confirmError.innerHTML = "Confirmation failed. Try again.";
            document.getElementById("emailBox").value = "";
            document.getElementById("confirmBox").value = "";
            document.getElementById("emailBox").style.borderColor = "red";
            document.getElementById("emailBox").style.borderWidth = "2px";
            document.getElementById("confirmBox").style.borderColor = "red";
            document.getElementById("confirmBox").style.borderWidth = "2px";
        }
    }

    socket.onclose = () => {
        console.log("Connection with server has ended.");
    }
}

window.onload = initialize;