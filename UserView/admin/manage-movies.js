/* Script for manage-movies.html */

var socket = null;

/*
 * Is called when the window opens. Creates event listeners
 * for the buttons on the page.
 */
function initialize()
{
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

function createMovieElement()
{
    // Adds new div to movie grid
    const movieSection = document.getElementsByClassName("now_play_grid");
    const newMovieDisplay = document.createElement("div");
    newMovieDisplay.setAttribute("class", "item-col");

    // Adds new movie elements
    let movieInfo = JSON.parse(localStorage.getItem("newMovie"));
    const imageDisplay = document.createElement("img");
    imageDisplay.setAttribute("src", movieInfo.image);
    imageDisplay.setAttribute("alt", movieInfo.title);
    imageDisplay.setAttribute("width", "400px");
    imageDisplay.setAttribute("height", "600px");
    const titleHeader = document.createElement("h4");
    titleHeader.innerHTML = movieInfo.title;
    const description = document.createElement("p");
    description.innerHTML = movieInfo.synopsis;
    const trailerDisplay = document.createElement("a");
    trailerDisplay.setAttribute("href", movieInfo.trailer);
    trailerDisplay.setAttribute("class", "trailerbtn");
    trailerDisplay.innerHTML = "Trailer";
    const bookTicketDisplay = document.createElement("a");
    bookTicketDisplay.setAttribute("href", "../booking.html");
    bookTicketDisplay.setAttribute("class", "bookmovie");

    // Adds elements to page
    newMovieDisplay.appendChild(imageDisplay);
    newMovieDisplay.appendChild(titleHeader);
    newMovieDisplay.appendChild(description);
    newMovieDisplay.appendChild(trailerDisplay);
    newMovieDisplay.appendChild(bookTicketDisplay);
    movieSection.appendChild(newMovieDisplay);
}

window.onload = initialize;