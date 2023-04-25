/* Script for booking.html */

/* 
IGNORE: NOTE TO SELF
    - insert showtimes to display showtimes
    - work on functions for checking out (& checkout btn)
    - fix tickets to where there are only 3 types:
        Child
        Adult
        Senior
*/

var socket = null;

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
            displayMovieShowtimes(movieshow_data);
        })
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
async function displayMovieShowtimes(data) {
    var auditoriumid;
  
    // Fetch auditorium info
    const audboxes = document.querySelectorAll(".single-checkbox");
    for (let checkbox of audboxes) {
        console.log("checkboxes: ", checkbox);
        if (checkbox.checked === true) {
            const label = checkbox.nextElementSibling;
            const audResponse = await fetch("auditorium-info.json");
            const aud_data = await audResponse.json();
            for (let i = 0; i < aud_data.length; i++) {
                console.log("aud_data[i].audID: ", aud_data[i].audID)
                if (aud_data[i].audName === label.innerHTML) {
                    auditoriumid = aud_data[i].audID;
                }
            }
        }
    }
  
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
        console.log("showtime: ", showtime);
        console.log("auditoriumid: ", auditoriumid);
        console.log("data[i].auditoriumID: ", data[i].auditoriumID)
        if (showtime !== null && auditoriumid === data[i].auditoriumID) {
            const newdiv = document.createElement("div");
            newdiv.setAttribute("class", "time-selection");
            const show = document.createElement("p");
            show.innerHTML = showtime;
            const btn = document.createElement("button");
            btn.innerHTML = "Add";
            newdiv.appendChild(show);
            newdiv.appendChild(btn);
            document.getElementById("mvshowtimes").appendChild(newdiv);
        }
    }
}

window.onload = initialize;