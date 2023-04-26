/* Script for admin-home.html */

var socket = null;

/*
 * Communicates with the server that the admin has logged out.
 */
function initialize()
{
    fetch("../movie-info.json")
        .then(response => response.json())
        .then(data => displayMovies(data));
    
    const button = document.getElementById("logoutBtn");
    button.addEventListener("click", (event) => {
        event.preventDefault();
        logoutAdmin();
    });

    const viewusers = document.getElementById("viewusers");
    viewusers.addEventListener("click", (e) => {
        e.preventDefault();
        getUsers();
    });
}

/*
 * Takes the movie-info and displays it on the homepage.
 */
function displayMovies(movie_data)
{
    // Iterates through each movie in JSON file
    for (let i = 0; i < movie_data.length; i++) {
        // Creates new item-col div
        let newdiv = document.createElement("div");
        newdiv.setAttribute("class", "item-col");

        // Adding elements to be displayed on the homepage
        let img = document.createElement("img");
        img.src = `../${movie_data[i].trailerPicture}`;
        img.alt = `${movie_data[i].title}`;
        img.width = 400;
        img.height = 600;
        let header = document.createElement("h4");
        header.innerHTML = `${movie_data[i].title}`;
        let synopsis = document.createElement("p");
        synopsis.innerHTML = `${movie_data[i].synopsis}`;

         // Append items to item-col div
         newdiv.appendChild(img)
         newdiv.appendChild(header);
 
         // Appending to "Now Playing" or "Coming Soon" div
         if (movie_data[i].display === "Now Playing") {
            newdiv.appendChild(synopsis);
            let trailerbtn = document.createElement("a");
            trailerbtn.href = `${movie_data[i].trailerVideo}`;
            trailerbtn.setAttribute("type", "button");
            trailerbtn.class = "trailerbtn";
            trailerbtn.innerHTML = "Trailer";
            newdiv.appendChild(trailerbtn);
            document.getElementById("nowplaying").appendChild(newdiv);
        } else {
            let display = document.createElement("h5");
            display.innerHTML = `${movie_data[i].display}`;
            newdiv.appendChild(display);
            newdiv.appendChild(synopsis);
            let trailerbtn = document.createElement("a");
            trailerbtn.href = `${movie_data[i].trailerVideo}`;
            trailerbtn.setAttribute("type", "button");
            trailerbtn.class = "trailerbtn"
            trailerbtn.innerHTML = "Trailer";
            newdiv.appendChild(trailerbtn);
            document.getElementById("comingsoon").appendChild(newdiv);
        }
    }
}

/*
 * Sends LOGOUT message to server. Server will clear admin's data
 * from JSON file and redirect to the default homepage.
 */
function logoutAdmin()
{
    socket = new WebSocket("ws://127.0.0.1:8888");
    socket.onopen = () => {
        console.log("Connection to server established.");
        socket.send("LOGOUT");
    }

    socket.onmessage = (event) => {
        console.log(event.data);
        if (event.data === "SUCCESS") {

            // Replace the current URL with a new one, and clears admin-home.html from
            // browser history, preventing access to the page. Page should only be
            // accessible by logging in with an admin account., which this solves.
            history.replaceState(null, null, "../homepage.html");
            window.onpopstate = () => {
                history.go(1);
            };
        }
        socket.close();
    }

    socket.onclose = (event) => {
        console.log("WebSocket connection closed with code:", event.code);
    }
}

/*
 * Gets users before leaving page.
 */
function getUsers()
{
    socket = new WebSocket("ws://127.0.0.1:8888");
    socket.onopen = () => {
        socket.send("GETUSERS")
    }
    socket.onmessage = (e) => {
        console.log(e.data);
        if (e.data === "SUCCESS") {
            window.location.href = "manage_users.html";
        }
        socket.close();
    }
}

window.onload = initialize;