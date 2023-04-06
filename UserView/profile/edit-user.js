/* Script for edit-profile.html */

var socket = null;

/*
 * Connects to the server when the page is initially loaded and
 * adds an event listener to the Save Edits button.
 */
function initialize()
{
    socket = new WebSocket("ws://127.0.0.1:8888");
    socket.onopen = () => {
        console.log("Connection to server established.");
        var button = document.getElementById("save_btn");
        button.addEventListener("click", (event) => {
            event.preventDefault();
            console.log("Clicked!");
            fetch("../login-user-info.json")
                .then(response => response.json())
                .then(data => editUser(data));
        });
    }
}

/*
 * Compares existing user's data from JSON file and compares
 * it to the input boxes on the edit-profile.html page. If any
 * change has been made, send changed data to server to be updated
 * in SQL database.
 */
function editUser(user_data)
{

    // Grabs any elements that may have changed
    const fullname      = document.getElementById("fullName").value.toString().split(" ");
    const firstname     = fullname[0];
    const lastname      = fullname[1];
    const cardnum       = document.getElementById("ccNum").value;
    const expmonth      = document.getElementById("monthExp").value;
    const expdate       = document.getElementById("yearExp").value;
    const cvv           = document.getElementById("cvvNum").value;
    const shippingAddr1 = document.getElementById("streetAdd").value;
    const shippingAddr2 = document.getElementById("streetAddMore").value;
    const shippingZIP   = document.getElementById("zipAdd").value;
    const shippingCity  = document.getElementById("cityAdd").value;
    const shippingState = document.getElementById("stateAdd").value;

    var billingAddr1, billingAddr2, billingZIP, billingCity, billingState;
    if (document.getElementById("sameAdd").value === "yes") {
        billingAddr1  = document.getElementById("streetAdd").value;
        billingAddr2  = document.getElementById("streetAddMore").value;
        billingZIP    = document.getElementById("zipAdd").value;
        billingCity   = document.getElementById("cityAdd").value;
        billingState  = document.getElementById("stateAdd").value;
    } else {
        billingAddr1  = document.getElementById("b_streetAdd").value;
        billingAddr2  = document.getElementById("b_streetAddMore").value;
        billingZIP    = document.getElementById("b_zipAdd").value;
        billingCity   = document.getElementById("b_cityAdd").value;
        billingState  = document.getElementById("b_stateAdd").value;
    }
    
    // Comparison to existing values
    var results = `EDIT,`
    results += user_data.firstName !== firstname && firstname !== "" ? firstname : user_data.firstName;
    results += ",";
    results += user_data.lastName !== lastname && lastname !== "" ? lastname : user_data.lastName;
    results += ",";
    results += user_data.email;
    results += ",";

    switch (user_data.userType)
    {
        case "ADMIN":
            results += "1";
            break;
        case "CUSTOMER":
            results += "2";
            break;
        case "EMPLOYEE":
            results += "3";
            break;
    }

    results += ",";
    results += user_data.billingAddressLine1 !== billingAddr1 && billingAddr1 !== "" ? billingAddr1 : user_data.billingAddressLine1;
    results += ",";
    results += user_data.billingAddressLine2 !== billingAddr2 && billingAddr2 !== "" ? billingAddr2 : user_data.billingAddressLine2;
    results += ",";
    results += user_data.billingZip !== billingZIP && billingZIP !== "" ? billingZIP : user_data.billingZip;
    results += ",";
    results += user_data.billingCity !== billingCity && billingCity !== "" ? billingCity : user_data.billingCity;
    results += ",";
    results += user_data.billingState !== billingState && billingState !== "" ? billingState : user_data.billingState;
    results += ",";
    results += user_data.shippingAddressLine1 !== shippingAddr1 && shippingAddr1 !== "" ? shippingAddr1 : user_data.shippingAddressLine1;
    results += ",";
    results += user_data.shippingAddressLine2 !== shippingAddr2 && shippingAddr2 !== "" ? shippingAddr2 : user_data.shippingAddressLine2;
    results += ",";
    results += user_data.shippingZip !== shippingZIP && shippingZIP !== "" ? shippingZIP : user_data.shippingZip;
    results += ",";
    results += user_data.shippingCity !== shippingCity && shippingCity !== "" ? shippingCity : user_data.shippingCity;
    results += ",";
    results += user_data.shippingState !== shippingState && shippingState !== "" ? shippingState : user_data.shippingState;
    results += ",";
    results += user_data.cardnum !== cardnum && cardnum !== "" ? cardnum : user_data.cardnum;
    results += ",";
    results += user_data.securitynum !== cvv && cvv !== "" ? cvv : user_data.securitynum;
    results += ",";
    results += user_data.expmonth !== expmonth && expmonth !== "" ? expmonth : user_data.expmonth;
    results += ",";
    results += user_data.expdate !== expdate && expdate !== "" ? expdate : user_data.expdate;
    results += ",";
    results += user_data.enabledPromotion;

    const message = results;

    socket.send(message);

    socket.onmessage = (event) => {
        console.log(event.data); // debug
        if (event.data.toString().toUpperCase() === "SUCCESS") {
            window.location.href = "profile.html";
        }
        socket.close();
    }

    socket.onclose = () => {
        console.log("Connection closed.");
    }
}

window.onload = initialize;