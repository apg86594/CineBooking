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

        const shipCheckBox = document.getElementById("sameAsShip");
        shipCheckBox.addEventListener("change", (event) => {
            if (event.target.checked) {
                document.getElementById("billing-info").style.display = "none";
            } else {
                document.getElementById("billing-info").style.display = "block";
            }
        })

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
    // Personal info
    const psw           = document.getElementById("psw").value;
    const first         = document.getElementById("firstName").value;
    const last          = document.getElementById("lastName").value;
    const email         = document.getElementById("Email").value;
    const USERTYPE      = 2;

    // Shipping info
    const shippingAddr1 = document.getElementById("shippingAddr1").value;
    const shippingAddr2 = document.getElementById("shippingAddr2").value;
    const shippingCity  = document.getElementById("shippingCity").value;
    const shippingState = document.getElementById("shippingState").value;
    const shippingZIP   = document.getElementById("shippingZIP").value;

    // Billing info
    var billingAddr1, billingAddr2, billingCity, billingState, billingZIP;
    if (document.getElementById("sameAsShip").value === "yes") {
        billingAddr1  = document.getElementById("shippingAddr1").value;
        billingAddr2  = document.getElementById("shippingAddr2").value;
        billingCity   = document.getElementById("shippingCity").value;
        billingState  = document.getElementById("shippingState").value;
        billingZIP    = document.getElementById("shippingZIP").value;
    } else {
        billingAddr1  = document.getElementById("billingAddr1").value;
        billingAddr2  = document.getElementById("billingAddr2").value;
        billingCity   = document.getElementById("billingCity").value;
        billingState  = document.getElementById("billingState").value;
        billingZIP    = document.getElementById("billingZIP").value;
    }

    // Card info
    const cardnum       = document.getElementById("cardnum").value;
    const cvv           = document.getElementById("cvv").value;
    const month         = document.getElementById("Month").value;
    const year          = document.getElementById("Year").value;

    const _month = convertMonthToInt(month);
    const _year  = year.slice(-2);
    
    // Promo info
    var promo;
    if (document.getElementById("promotions").value === "yes") {
        promo = "1";
    } else {
        promo = "0";
    }

    var message = `REGISTER,${psw},${first},${last},${email},${USERTYPE},${billingAddr1},${billingAddr2},${billingZIP},`;
    message    += `${billingCity},${billingState},${shippingAddr1},${shippingAddr2},${shippingZIP},${shippingCity},`;
    message    += `${shippingState},${cardnum},${cvv},${_month},${_year},${promo}`;
        
    socket.send(message);
        
    socket.addEventListener("message", (event) => {
        console.log("Message received from server:", event.data);

        // Registration success
        if (event.data === "SUCCESS") {
            window.location.href = "confirmation.html";
        
        // Registration failure
        } else if (event.data === "FAILURE") {
            console.log("Registration failed. Try again.");

        // User already exists
        } else if (event.data === "User already exists") {
            const errorElem = document.getElementById("error-message");
            errorElem.innerHTML = "User already exists. Click the button below to go to the login page.";
            const loginBtn = document.createElement("button");
            loginBtn.innerHTML = "Go to login page";
            loginBtn.addEventListener("click", () => {
                window.location.href = "login.html";
            });
            errorElem.appendChild(loginBtn);
        }
        socket.close();    
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