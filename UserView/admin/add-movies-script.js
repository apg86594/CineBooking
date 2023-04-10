/* Script for add-movies.html */

var socket = null;

function initialize()
{
    socket = new WebSocket("ws://127.0.0.1:8888");
    console.log("Connection to server established");
    socket.onopen = () => {
        const saveBtn = document.getElementById("saveEditsBtn");
        saveBtn.addEventListener("click", (event) => {
            event.preventDefault();
            addMovie();
        });
    }
}

function addMovie()
{
    const movieName     = document.getElementById("mvName").value;
    const cast          = document.getElementById("cast").value.toString().replace(",", ":");
    const genre         = document.getElementById("genre").value.toString().replace(",", ":");
    const director      = document.getElementById("director").value;
    const producer      = document.getElementById("prod").value;
    const duration      = document.getElementById("duration").value;
    const synopsis      = document.getElementById("syn").value.toString().replace(",", ":");
    const display       = document.getElementById("release").value.toString().replace(",", ":");
    const imageUrl      = document.getElementById("imageUrl").value;
    const trailerUrl    = document.getElementById("trailerUrl").value;
    const reviews       = document.getElementById("reviews").value;

    var rating;
    switch(document.getElementById("rating-select-box").value)
    {
        case "PG":
            rating = "1";
            break;
        case "PG-13":
            rating = "2";
            break;
        case "R":
            rating = "3";
            break;
    }

    const message = `ADDMOVIE,${movieName},${cast},${genre},${director},${producer},${duration},${synopsis},${display},${imageUrl},${trailerUrl},${reviews},${rating}`;
    socket.send(message);

    socket.onmessage = (event) => {
        console.log(event.data);
        if (event.data === "SUCCESS") {
            socket.send("GETMOVIES");
            window.location.href = "manage_movies.html";
        }
        socket.close();
    }

    socket.onclose = () => {
        console.log("Connection ended.");
    }
}

window.onload = initialize;