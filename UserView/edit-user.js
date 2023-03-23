/*
 * Allows the user to edit their information.
 */
const WebSocket = require("ws");
const fs        = require("fs");
const {JSDOM}   = require("jsdom");

// Accesses profile.html
const html      = fs.readFileSync("UserView/profile/profile.html");
const dom       = new JSDOM(html);
const document  = dom.window.document;

// Accesses edit-profile.html
const html2     = fs.readFileSync("UserView/profile/edit-profile.html");
const dom2      = new JSDOM(html2);
const document2 = dom2.window.document;

// Waits for the save button to be pressed
const btn = document2.getElementById("savebtn");
if (btn) btn.addEventListener("click", compare_values);

/*
 * Sends data that has been changed to the user.
 */
function edit_user(msg)
{
    // Open socket after comparing values
    const socket = new WebSocket("ws://127.0.0.1:8888");

    socket.onopen = function(event) {
        socket.send(msg);
    }
    socket.onmessage = function(event) {
        console.log(`[MESSAGE: ${event.data}`);
        socket.close();
        console.log("Connection closed.");
    }
}

/*
 * Compares existing values with changed values
 */
function compare_values()
{
    // Retrieves information from the JSON file
    const json_data = fs.readFileSync("login-user-info.js");
    const user_data = JSON.parse(json_data);

    // Grabbing existing elements to compare with changed elements
    const firstname       = user_data.firstname;
    const lastname        = user_data.lastname;
    const phone           = document.getElementById("phone_num");       // !!!!! has no value to retrive from JSON
    const card_num        = user_data.card_num;
    const card_month      = user_data.month_exp;
    const card_year       = user_data.year_exp;
    const card_cvv        = user_data.cvv_num;
    const shipping_addr   = document.getElementById("shipping_addr");   // !!!!! has no value to retrieve from JSON
    const billing_addr    = user_data.billing_addr;

    // Grabs any elements that have changed
    const _firstname        = document2.getElementById("fullName").split(" ")[0];
    const _lastname         = document2.getElementById("fullName").split(" ")[1];
    const _card_num         = document2.getElementById("ccNum");
    const _card_month       = document2.getElementById("monthExp");
    const _card_year        = document2.getElementById("yearExp");
    const _card_cvv         = document2.getElementById("cvvNum");
    const _shipping_addr_1  = document2.getElementById("streetAdd");
    const _shipping_addr_2  = document2.getElementById("streetAddMore");
    const _city             = document2.getElementById("cityAdd");
    const _state            = document2.getElementById("stateAdd");
    const _zip              = document2.getElementById("zipAdd");
    const _shipping_addr    = _shipping_addr_1 + " " + _shipping_addr_2 + " " + _city + " " + _state + " " + _zip;
    var   _billing_addr     = "";
    if (billing_addr == "Same as shipping address") {
        _billing_addr = shipping_addr;
    } else {
        _billing_addr  =       document2.getElementById("b_streetAdd")
        _billing_addr += " " + document2.getElementById("b_streetAddMore");
        _billing_addr += " " + document2.getElementById("b_cityAdd");
        _billing_addr += " " + document2.getElementById("b_stateAdd");
        _billing_addr += " " + document2.getElementById("b_zipAdd");
    }
    
    // Comparison
    // format should be "EDIT,psw,fname,lname,ccnum,mExp,yExp,cvvnum,shipping_addr,billing_addr"
    var results = `EDIT,${user_data.password},`
    results += firstname != _firstname ? _firstname : firstname;
    results += ",";
    results += lastname  != _lastname ? _lastname : lastname;
    results += ",";
    results += card_num != _card_num ? _card_num : card_num;
    results += ",";
    results += card_month != _card_month ? _card_month : card_month;
    results += ",";
    results += card_year != _card_year ? _card_year : card_year;
    results += ",";
    results += card_cvv != _card_cvv ? _card_cvv : card_cvv;
    results += ",";
    results += shipping_addr != _shipping_addr ? _shipping_addr : shipping_addr;
    results += ",";
    results += billing_addr != _billing_addr ? _billing_addr : billing_addr;

    edit_user(results);
}