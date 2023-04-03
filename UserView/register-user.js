/* Script for register.html */

var socket = null;

/*
 * Connects to the server when the page is initially loaded and
 * adds an event listener to the Sign Up button.
 */
function initialize()
{
    socket = new WebSocket("ws://127.0.0.1:8888");
    socket.onopen = () => {
        console.log("Connection to server established.");
        const button = document.getElementById("signupbtn");
        button.addEventListener("click", (event) => {
            event.preventDefault();
            registerUser();
        });
    }
}

/*
 * Sends the information submitted by the user to the server through
 * the WebSocket.
 */
function registerUser()
{
    const psw           = document.getElementById("psw").value;
    const first         = document.getElementById("firstName").value;
    const last          = document.getElementById("lastName").value;
    const email         = document.getElementById("Email").value;
    const USERTYPE      = 1;
    const billingZIP    = document.getElementById("billingZIP").value;
    const cardnum       = document.getElementById("cardnum").value;
    const cvv           = document.getElementById("cvv").value;
    const month         = document.getElementById("Month").value;
    const year          = document.getElementById("Year").value;

    const _month = convertMonthToInt(month);
    const _year  = year.slice(-2);

    const message = `REGISTER,${psw},${first},${last},${email},${USERTYPE},${billingZIP},${cardnum},${cvv},${_month},${_year}`;
        
    socket.send(message);
        
    socket.addEventListener("message", (event) => {
        console.log("Message received from server:", event.data);
        if (event.data === "SUCCESS") { 
            window.location.href = "register_suc.html";
            socket.close();
        }
    });
        
    socket.addEventListener("close", (event) => {
        console.log("WebSocket connection closed with code:", event.code);
    });
        
    socket.addEventListener("error", (event) => {
        console.error("WebSocket connection error:", event);
    });
}

/*
 * Converts str_month to its numerical value.
 */
function convertMonthToInt(str_month)
{
    var m = "";
    switch (str_month)
    {
        case "january":
            m = "01";
            break;
        case "february":
            m = "02";
            break;
        case "march":
            m = "03";
            break;
        case "april":
            m = "04";
            break;
        case "may":
            m = "05";
            break;
        case "june":
            m = "06";
            break;
        case "july":
            m = "07";
            break;
        case "august":
            m = "08";
            break;
        case "september":
            m = "09";
            break;
        case "october":
            m = "10";
            break; 
        case "november":
            m = "11";
            break;
        case "december":
            m = "12";
            break;   
    }
    return m;
}

window.onload = initialize;