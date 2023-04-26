/* Script for order_hist.html */

var socket = null;

// Grab movieID and dispay already-existing information
const queryString = window.location.search;
const urlParams = new URLSearchParams(queryString);
const userId = urlParams.get('id');

/*
 * Is called when window is loaded.
 */
function initialize()
{
    fetch("../login-user-info.json")
    .then(res => res.json())
    .then(data => displayName(data));

    fetch("../orderHistory-info.json")
    .then(res => res.json())
    .then(data => displayOrderHistory(data));
}

/*
 * Displays the user's name.
 */
function displayName(data)
{
    const header = document.getElementById("name");
    header.innerHTML = data.firstName + " " + data.lastName;
}

/*
 * Displays user's order history in the table.
 */
function displayOrderHistory(data)
{
    for (let i = 0; i < data.length; i++) {
        const row = document.createElement("tr");
        row.setAttribute("class", "header");

        // Get date of movie
        const td_date = document.createElement("td");
        td_date.style.width = '20%';
        td_date.innerHTML = data[i].showDate;
        row.appendChild(td_date);

        // Get movie name
        const td_movie = document.createElement("td");
        td_movie.style.width = '20%';
        td_movie.innerHTML = data[i].movieTitle;
        row.appendChild(td_movie);

        // Get auditorium
        const td_aud = document.createElement("td");
        td_aud.style.width = '20%';
        td_aud.innerHTML = data[i].audName;
        row.appendChild(td_aud);

        // Get showtime
        const td_showtime = document.createElement("td");
        td_showtime.style.width = '15%';
        td_showtime.innerHTML = data[i].showTime;
        row.appendChild(td_showtime);

        // Get seats
        const td_seats = document.createElement("td");
        td_seats.style.width = '15%';
        td_seats.innerHTML = data[i].seatIDs;
        row.appendChild(td_seats);

        // Get price
        const td_price = document.createElement("td");
        td_price.style.width = '10%';
        td_price.innerHTML = "$" + data[i].totalPrice;
        row.appendChild(td_price);
        
        document.getElementById("myTable").appendChild(row);
    }
}

window.onload = initialize;