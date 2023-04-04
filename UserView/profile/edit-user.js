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
        button.addEventListener("click", () => {
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

    // Grabbing existing elements to compare with changed elements
    const firstname       = user_data.firstName;
    const lastname        = user_data.lastName;
    const card_num        = user_data.cardnum;
    const card_month      = user_data.expmonth;
    const card_year       = user_data.expdate;
    const card_cvv        = user_data.securitynum;
    const billing_addr    = user_data.billingAddress;

    // Grabs any elements that have changed
    const fullname          = document.getElementById("fullName").value.toString().split(" ")
    const _firstname        = fullname[0];
    const _lastname         = fullname[1];
    const _card_num         = document.getElementById("ccNum").value;
    const _card_month       = document.getElementById("monthExp").value;
    const _card_year        = document.getElementById("yearExp").value;
    const _card_cvv         = document.getElementById("cvvNum").value;
    const _billing_addr     = document.getElementById("b_zipAdd").value;
    
    // Comparison
    var results = `EDIT,${user_data.password},`
    results += firstname != _firstname && _firstname !== "" ? _firstname : firstname;
    results += ",";
    results += lastname != _lastname && _lastname !== "" ? _lastname : lastname;
    results += ",";
    results += user_data.email;
    results += ",";
    results += user_data.userType;
    results += ",";
    results += billing_addr != _billing_addr && _billing_addr !== "" ? _billing_addr : billing_addr;
    results += ",";
    results += user_data.ACTIVE;
    results += ","
    results += card_num != _card_num && _card_num !== "" ? _card_num : card_num;
    results += ",";
    results += card_cvv != _card_cvv && _card_cvv !== "" ? _card_cvv : card_cvv;
    results += ",";
    results += card_month != _card_month && _card_month !== "" ? _card_month : card_month;
    results += ",";
    results += card_year != _card_year && _card_year !== "" ? _card_year : card_year;
    results += ",";

    const message = results;

    socket.send(message);

    socket.onmessage = (event) => {
        console.log(event.data); // debug
        if (event.data === "SUCCESS") {
            window.location.href = "profile.html";
        }
        socket.close();
    }

    socket.onclose = () => {
        console.log("Connection closed.");
    }
}

window.onload = initialize;