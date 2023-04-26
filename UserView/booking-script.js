/* Script for booking.html */

var socket = null;

// movieShowID to pass to server when booking
var g_movieShowID = null;

// Number of tickets per category to pass to server when booking.
var numChildTickets;
var numAdultTickets;
var numSeniorTickets;

// Total price to pass to server when booking
var total;

// seatIDs to pass to server when booking.
var selectedSeats = {};

// Grab movieID and dispay already-existing information
const queryString = window.location.search;
const urlParams = new URLSearchParams(queryString);
const movieId = urlParams.get('id');

/*
 * Is called when window is loaded.
 */
function initialize()
{
    fetch("movie-info.json")
        .then(res => res.json())
        .then(movie_data => displayMovie(movie_data));

    fetch("auditorium-info.json")
        .then(res => res.json())
        .then(aud_data => {
            // Checks if auditoriums exist
            if (aud_data === "") {
                socket = new WebSocket("ws://127.0.0.1:8888");
                socket.onopen = () => {
                    console.log("Connected to server. Grabbing auditoriums...");
                    socket.send("GETAUDITORIUMS");
                }
                socket.onmessage = (event) => {
                    console.log(`GETAUDITORIUMS: ${event.data}`);
                    socket.close();
                }
                socket.onclose = () => {
                    console.log("Connection closed.");
                }
            }
            displayAuditoriums(aud_data);
        });

    ticketListener();

    const checkoutbtn = document.getElementById("checkoutbtn");
    checkoutbtn.addEventListener("click", (e) => {
        e.preventDefault();
        bookMovie();
    });
}

/*
 * Displays the selected movie for checkout.
 */
function displayMovie(data)
{
    let newdiv = document.createElement("div");
    newdiv.setAttribute("class", "shop-item");
    let img = document.createElement("img");
    img.src = `${data[movieId - 1].trailerPicture}`;
    img.alt = `${data[movieId - 1].title}`;
    img.width = 400;
    img.height = 600;
    newdiv.appendChild(img);
    document.getElementById("mv_display").appendChild(newdiv);
}

/*
 * Displays available auditoriums.
 */
function displayAuditoriums(data)
{
    let newdiv = document.createElement("div");
    newdiv.setAttribute("class", "auditoriums");
    let header = document.createElement("h2");
    header.innerHTML = "Select an auditorium";
    newdiv.appendChild(header);
    for (let i = 0; i < data.length; i++) {
        let aud_div = document.createElement("div");
        let checkbox = document.createElement("input");
        checkbox.setAttribute("class", "single-checkbox");
        checkbox.setAttribute("type", "checkbox");
        checkbox.setAttribute("value", "yes");
        checkbox.addEventListener('change', function() {
            if (this.checked) {
                // loop through all other checkboxes and uncheck them
                let checkboxes = document.querySelectorAll('.single-checkbox');
                checkboxes.forEach(function(otherCheckbox) {
                    if (otherCheckbox !== checkbox) {
                        otherCheckbox.checked = false;
                    }
                });
                fetch("movieShow-info.json")
                    .then(res => res.json())
                    .then(movieshow_data => {
                        if (movieshow_data === "") {
                            socket = new WebSocket("ws://127.0.0.1:8888");
                            socket.onopen = () => {
                                console.log("Connected to server. Grabbing movie showtimes...");
                                socket.send(`GETMOVIETIMES,${movieId}`);
                            }
                            socket.onmessage = (event) => {
                                console.log(`GETMOVIETIMES: ${event.data}`);
                                socket.close();
                            }
                            socket.onclose = () => {
                                console.log("Connection closed");
                            }
                        }
                        displayMovieShowtimes(movieshow_data, data[i].audID);
                    });
            }
        });
        let label = document.createElement("label");
        label.innerHTML = `${data[i].audName}`;
        aud_div.appendChild(checkbox);
        aud_div.appendChild(label);
        newdiv.appendChild(aud_div);
    }
    document.getElementById("aud_display").appendChild(newdiv);
}

/*
 * Displays scheduled movie times.
 */
async function displayMovieShowtimes(data, audID) 
{
    document.getElementById("mvshowtimes").innerHTML = "";
    const header = document.createElement("h2");
    header.innerHTML = "Select a showtime";
    document.getElementById("mvshowtimes").appendChild(header);

    const showResponse = await fetch("showtime-info.json");
    const show_data = await showResponse.json();
    for (let i = 0; i < data.length; i++) {
        let showtime = null;
        for (let j = 0; j < show_data.length; j++) {
            if (show_data[j].showID === data[i].showID) {
                showtime = show_data[j].timeStamp;
                break;
            }
        }
        if (showtime !== null && audID === data[i].auditoriumID) {
            document.getElementById("mvshowtimes").style.display = 'block';
            const newdiv = document.createElement("div");
            const show = document.createElement("p");
            show.innerHTML = `${showtime} for ${data[i].showStart}`;
            const btn = document.createElement("button");
            btn.setAttribute("class", "addShowtime");
            btn.innerHTML = "Add";
            btn.addEventListener("click", (e) => {
                e.preventDefault();
                g_movieShowID = data[i].movieShowID
                console.log("clicked!")
                handleSeating();
            });
            newdiv.appendChild(show);
            newdiv.appendChild(btn);
            document.getElementById("mvshowtimes").appendChild(newdiv);
        } else {
            const msg = document.createElement("p");
            msg.innerHTML = "No showtimes have been scheduled for this auditorium.";
            document.getElementById("mvshowtimes").appendChild(msg);
            break;
        }
    }
}

/*
 * Gets available seating. Handles seat selection.
 */
function handleSeating()
{
    if (g_movieShowID !== null) {
        fetch("movieShowSeats-info.json")
        .then(res => res.json())
        .then(data => {
            if (data.length === 0) {
                socket = new WebSocket("ws://127.0.0.1:8888");
                socket.onopen = () => {
                    console.log("Grabbing seats...");
                    socket.send(`GETAUDITORIUMSEATS,${g_movieShowID}`);
                }
                socket.onmessage = (e) => {
                    console.log(e.data);
                    socket.close();
                }
            }
        });
        const seatSelection = document.querySelector('.seat-selection');

        // Listen for changes in the checkbox state
        seatSelection.addEventListener('change', (event) => {
            const checkbox = event.target;
            const seatId = checkbox.id;
            const seatLabel = checkbox.nextElementSibling.innerHTML;

            if (checkbox.checked) {
                // Add the selected seat to the object
                selectedSeats[seatId] = seatLabel;
            } else {
                // Remove the deselected seat from the object
                delete selectedSeats[seatId];
            }

        });
    }
}

/*
 * Listens to the ticket selection.
 */
function ticketListener()
{
    // Get references to the input fields and total price element
    const numChild = document.getElementById("numChild");
    const numAdult = document.getElementById("numAdult");
    const numSenior = document.getElementById("numSenior");
    const totalPrice = document.getElementById("total");

    // Add event listeners to each input field
    numChild.addEventListener("input", updateTotalPrice);
    numAdult.addEventListener("input", updateTotalPrice);
    numSenior.addEventListener("input", updateTotalPrice);

    // Define a function to update the total price
    function updateTotalPrice() {
        // Get the number of tickets selected for each age group
        numChildTickets = parseInt(numChild.value) || 0;
        numAdultTickets = parseInt(numAdult.value) || 0;
        numSeniorTickets = parseInt(numSenior.value) || 0;

        // Calculate the new total price
        total = (numChildTickets * 10) + (numAdultTickets * 15) + (numSeniorTickets * 12);

        // Update the HTML of the total price element
        if (document.getElementById("applyPromo").checked) {
            total = total * 0.85;
        }
        totalPrice.textContent = "$" + total.toFixed(2);
    }

}

/*
 * Books the movie. Sends message to the server.
 */
async function bookMovie()
{
    // Grabs userID
    var userid;
    const userResp = await fetch("login-user-info.json");
    const user_data = await userResp.json();
    userid = user_data.userID;

    // Format seatIDs
    var keys = [];
    for (var key in selectedSeats) {
        if (selectedSeats.hasOwnProperty(key)) {
            keys.push(key);
        }
    }
    var seatIDs = "";
    for (let i = 0; i < keys.length; i++) {
        if (keys[i] === "1A") {
            seatIDs += "1";
        } else if (keys[i] === "2A") {
            seatIDs += "2";
        } else if (keys[i] === "3A") {
            seatIDs += "3";
        } else if (keys[i] === "4A") {
            seatIDs += "4";
        } else if (keys[i] === "5A") {
            seatIDs += "5";
        } else if (keys[i] === "1B") {
            seatIDs += "6";
        } else if (keys[i] === "2B") {
            seatIDs += "7";
        } else if (keys[i] === "3B") {
            seatIDs += "8";
        } else if (keys[i] === "4B") {
            seatIDs += "9";
        } else if (keys[i] === "5B") {
            seatIDs += "10";
        }
        if (i !== keys.length - 1)
            seatIDs += ":";
    }

    socket = new WebSocket("ws://127.0.0.1:8888");
    socket.onopen = () => {
        console.log("Booking movie...");
        socket.send(`BOOKMOVIE,${userid},${g_movieShowID},${numChildTickets},${numAdultTickets},${numSeniorTickets},${total},2,${seatIDs}`);
    }
    socket.onmessage = (e) => {
        console.log(e.data);
        if (e.data === "SUCCESS") {
            window.location.href = "checkout.html";
        }
        socket.close();
    }
}

window.onload = initialize;