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
    const cast          = document.getElementById("cast").value;
    const genre         = document.getElementById("genre").value;
    const producer      = document.getElementById("prod").value;
    const duration      = document.getElementById("duration").value;
    const imageUrl      = document.getElementById("imageUrl").value;
    const trailerUrl    = document.getElementById("trailerUrl").value;
    const reviews       = document.getElementById("reviews").value;

    var rating;
    switch(document.getElementById("rating").value)
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
    

    let movieInfo = {
        title: `${movieName}`, image: `${imageUrl}`, trailer: `${trailerUrl}`, synopsis: `${document.getElementById("syn").value}`
    };
    localStorage.setItem("newMovie", JSON.stringify(movieInfo));

    const message = `ADDMOVIE,${movieName},${cast},${genre},${producer},${duration},${imageUrl},${trailerUrl},${reviews},${rating}`;
    socket.send(message);

    socket.onmessage = (event) => {
        console.log(event.data);
        if (event.data === "SUCCESS") {
            window.location.href = "manage-movies.html";
        }
        socket.close();
    }

    socket.onclose = () => {
        console.log("Connection ended.");
    }
}

window.onload = initialize;