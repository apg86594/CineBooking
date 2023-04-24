/* Script fot sch-movies.html */

var socket = null;

/*
 * Is called when the window is loaded.
 */
function initialize()
{
    // Reading from json
    fetch("../showtime-info.json")
        .then(res => res.json())
        .then(show_data => {
            // Checks if showtimes exist
            if (show_data == "") {
                socket = new WebSocket("ws://127.0.0.1:8888");
                socket.onopen = () => {
                    console.log("Connected to server. Getting showtimes...");
                    socket.send("GETSHOWTIMES");
                }
                socket.onmessage = (event) => {
                    console.log(`GETSHOWTIMES: ${event.data}`);
                    socket.close();
                }
                socket.onclose = () => {
                    console.log("Connection closed.");
                }
            }
            displayShowtimes(show_data);
        });

    // Reading from json
    fetch("../auditorium-info.json")
        .then(res => res.json())
        .then(aud_data => {
            // Checks if auditoriums exist
            if (aud_data == "") {
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
    
    // Listens for schedule button press
    const scheduleBtn = document.getElementById("schedulebtn");
    scheduleBtn.addEventListener("click", (event) => {
        event.preventDefault();
        scheduleMovie();
    });
}

/*
 * Displays showtimes.
 */
function displayShowtimes(data)
{
    let newdiv = document.createElement("div");
    newdiv.setAttribute("class", "showtimes");
    let header = document.createElement("h2");
    header.innerHTML = "Select a showtime";
    newdiv.appendChild(header);
    for (let i = 0; i < data.length; i++) {
        let show_div = document.createElement("div");
        let checkbox = document.createElement("input");
        checkbox.setAttribute("class", "show-checkbox");
        checkbox.setAttribute("type", "checkbox");
        checkbox.setAttribute("value", "yes");
        checkbox.addEventListener('change', function() {
          if (this.checked) {
            // loop through all other checkboxes and uncheck them
            let checkboxes = document.querySelectorAll('.show-checkbox');
            checkboxes.forEach(function(otherCheckbox) {
              if (otherCheckbox !== checkbox) {
                otherCheckbox.checked = false;
              }
            });
          }
        });
        let name = document.createElement("label");
        name.setAttribute("class", "show-label");
        name.innerHTML = `${data[i].timeStamp}`;
        if (parseInt(data[i].timeStamp.toString().split(":")[0]) >= 12) {
            name.innerHTML += " PM";
        } else {
            name.innerHTML += " AM";
        }
        show_div.appendChild(checkbox);
        show_div.appendChild(name);
        newdiv.appendChild(show_div);
    }
    document.getElementById("show-info").appendChild(newdiv);
}

/*
 * Displays auditoriums for the admin to select from when scheduling.
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
        checkbox.setAttribute("class", "aud-checkbox");
        checkbox.setAttribute("type", "checkbox");
        checkbox.setAttribute("value", "yes");
        checkbox.addEventListener('change', function() {
          if (this.checked) {
            // loop through all other checkboxes and uncheck them
            let checkboxes = document.querySelectorAll('.aud-checkbox');
            checkboxes.forEach(function(otherCheckbox) {
              if (otherCheckbox !== checkbox) {
                otherCheckbox.checked = false;
              }
            });
          }
        });
        let name = document.createElement("label");
        name.setAttribute("class", "aud-label")
        name.innerHTML = `${data[i].audName}`;
        aud_div.appendChild(checkbox);
        aud_div.appendChild(name);
        newdiv.appendChild(aud_div);
    }
    document.getElementById("aud-info").appendChild(newdiv);
}

/*
 * Schedules the movie.
 */
async function scheduleMovie() {
    var showid, movieid, auditoriumid, showstart;
  
    // Fetch movie info
    const movieResponse = await fetch("../movie-info.json");
    const mv_data = await movieResponse.json();
    for (let i = 0; i < mv_data.length; i++) {
        if (mv_data[i].title === document.getElementById("movieName").value) {
            movieid = mv_data[i].movieID;
        }
    }
  
    // Fetch showtime info
    const showboxes = document.querySelectorAll(".show-checkbox");
    for (let checkbox of showboxes) {
        if (checkbox.checked === true) {
            const label = checkbox.nextElementSibling;
            const showResponse = await fetch("../showtime-info.json");
            const show_data = await showResponse.json();
            for (let i = 0; i < show_data.length; i++) {
                if (show_data[i].timeStamp === label.innerHTML.split(" ")[0]) {
                    showid = show_data[i].showID;
                }
            }
        }
    }
  
    // Fetch auditorium info
    const audboxes = document.querySelectorAll(".aud-checkbox");
    for (let checkbox of audboxes) {
        if (checkbox.checked === true) {
            const label = checkbox.nextElementSibling;
            const audResponse = await fetch("../auditorium-info.json");
            const aud_data = await audResponse.json();
            for (let i = 0; i < aud_data.length; i++) {
                if (aud_data[i].audName === label.innerHTML) {
                    auditoriumid = aud_data[i].audID;
                }
            }
        }
    }

    // Grab showstart
    const datePicker = document.getElementById('datePicker');
    showstart = datePicker.value;

    // Send message to server
    socket = new WebSocket("ws://127.0.0.1:8888");
    socket.onopen = () => {
        console.log("Connection to server established");
        socket.send(`SCHEDULEMOVIE,${showid},${movieid},${auditoriumid},${showstart}`);
    }
    socket.onmessage = (event) => {
        console.log(event.data);
        if (event.data === "SUCCESS") {
            window.location.href = "manage_movies.html";
        } else {
            handleErrors(event.data);
        }
        socket.close();
    }
    socket.onclose = () => {
        console.log("Connection to server closed.");
    }
}

/*
 * Handles error messages from the server.
 */
function handleErrors(msg)
{
    switch(msg)
    {
        case "TIMEFILLED":
            // movie is already scheduled at this time
            break;
        
        case "BADMOVIEID":
            // movieID does not exist
            break;
        
        case "BADSHOWID":
            // showID does not exist
            break;
        
        case "FAILURE":
            // wrong input
            break;
    }
}
  
window.onload = initialize;