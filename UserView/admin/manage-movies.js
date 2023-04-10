/* Script for manage-movies.html */

var socket = null;

/*
 * Is called when the window opens. Creates event listeners
 * for the buttons on the page.
 */
function initialize()
{
    fetch("../movie-info.json")
        .then(response => response.json())
        .then(data => displayMovies(data));

    socket = new WebSocket("ws://127.0.0.1:8888");
    socket.onopen = () => {
        console.log("Connection to server established.");

        const logoutBtn = document.getElementById("logoutBtn");
        logoutBtn.addEventListener("click", (event) => {
            event.preventDefault();
            logoutAdmin();
        });

        const addBtn = document.getElementById("addBtn");
        addBtn.addEventListener("click", (event) => {
            event.preventDefault();
            window.location.href = "add-movies.html";
        });

        const editBtn = document.getElementById("editBtn");
        editBtn.addEventListener("click", (event) => {
            event.preventDefault();
            window.location.href = "edit-movies.html";
        });

        const delBtn = document.getElementById("delBtn");
        delBtn.addEventListener("click", (event) => {
            event.preventDefault();
            deleteMovie();
        });

        if (localStorage.length > 0) {
            //createMovieElement();
            localStorage.clear();
        }
    }
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
            let editbtn = document.createElement("a");
            editbtn.href = "edit-movies.html";
            editbtn.setAttribute("type", "button");
            editbtn.class = "editmovie";
            editbtn.innerHTML = "Edit";
            let delbtn = document.createElement("a");
            delbtn.href = "#";
            delbtn.setAttribute("type", "button");
            delbtn.class = "delmovie";
            delbtn.innerHTML = "Delete";
            delbtn.addEventListener("click", (event) => {
                event.preventDefault();
                deleteMovie();
            });
            newdiv.appendChild(trailerbtn);
            newdiv.appendChild(editbtn);
            newdiv.appendChild(delbtn);
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
            let editbtn = document.createElement("a");
            editbtn.href = "edit-movies.html";
            editbtn.setAttribute("type", "button");
            editbtn.class = "editmovie";
            editbtn.innerHTML = "Edit";
            let delbtn = document.createElement("a");
            delbtn.href = "#";
            delbtn.setAttribute("type", "button");
            delbtn.class = "delmovie";
            delbtn.innerHTML = "Delete";
            delbtn.addEventListener("click", (event) => {
                event.preventDefault();
                deleteMovie();
            });
            newdiv.appendChild(trailerbtn);
            newdiv.appendChild(editbtn);
            newdiv.appendChild(delbtn);
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
    socket.send("LOGOUT");

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
 *
 */
function deleteMovie()
{
    // todo
}

window.onload = initialize;