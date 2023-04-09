/* Script for homepage.html */

var socket = null;

/*
 * Is called when window is loaded. Grabs user info to check if a
 * user is logged in or not.
 */
function initialize()
{
    fetch("movie-info.json")
        .then(response => response.json())
        .then(data => displayMovies(data));

    fetch("login-user-info.json")
        .then(response => response.json())
        .then(data => isLoggedIn(data));
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
        img.src = `${movie_data[i].trailerPicture}`;
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
            let bookbtn = document.createElement("a");
            bookbtn.href = "booking.html";
            bookbtn.setAttribute("type", "button");
            bookbtn.class = "bookmovie";
            bookbtn.innerHTML = "Book Tickets";
            newdiv.appendChild(trailerbtn);
            newdiv.appendChild(bookbtn);
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
 * Checks if the user is logged in by checking user info. Changes
 * login button properties based on whether or not the user is logged
 * in or logged out. If logged in, user sees a "Logout" button. If user
 * is not logged in, they will see a "Login" button.
 */
function isLoggedIn(user_data)
{
    const button = document.getElementById("loginBtn");
    if (user_data.email !== "") {
        button.innerHTML = "Logout";
        const regBtn = document.getElementById("registerBtn");
        regBtn.style.display = "none";
    }
    button.addEventListener("click", (event) => {
        event.preventDefault();
        if (button.innerHTML === "Login") {
            window.location.href = "login.html";
        } else {
            logoutUser();
        }
    });
}

/*
 * Establishes a connection with the server to inform the server
 * that the current user has logged out.
 */
function logoutUser()
{
    const socket = new WebSocket("ws://127.0.0.1:8888");
    socket.onopen = () => {
        console.log("Connection to server established.");
        socket.send("LOGOUT");
    }
    socket.onmessage = (event) => {
        console.log(event.data);
        socket.close();
    }
    socket.onclose = () => {
        console.log("Connection to server closed");
    }
}

window.onload = initialize;