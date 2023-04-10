/* Script for edit-movies.html */

var socket = null;

// Grab movieID and dispay already-existing information
const queryString = window.location.search;
const urlParams = new URLSearchParams(queryString);
const movieId = urlParams.get('id');

/*
 * Called upon loading the webpage.
 */
function initialize()
{
    fetch('../movie-info.json')
        .then(response => response.json())
        .then(data => {
            document.getElementById('imageUrl').value           = data[movieId - 1].trailerPicture;
            document.getElementById('trailerUrl').value         = data[movieId - 1].trailerVideo;
            document.getElementById('mvName').value             = data[movieId - 1].title;
            document.getElementById('genre').value              = data[movieId - 1].genre;
            document.getElementById('syn').value                = data[movieId - 1].synopsis;
            document.getElementById('reviews').value            = data[movieId - 1].review;
            document.getElementById('duration').value           = data[movieId - 1].duration;
            document.getElementById('cast').value               = data[movieId - 1].casting;
            document.getElementById('rating-select-box').value  = data[movieId - 1].rating;
            document.getElementById('director').value           = data[movieId - 1].director;
            document.getElementById('prod').value               = data[movieId - 1].producer;
            document.getElementById('release').value            = data[movieId - 1].display;
        });
    
    socket = new WebSocket("ws://127.0.0.1:8888");
    socket.onopen = () => {
        console.log("Connection to server established.");
        const savebtn = document.getElementById('saveEditsBtn');
        savebtn.addEventListener("click", (event) => {
            event.preventDefault();
            editMovie();
        });
    }
}

/*
 * Grabs values and sends message to server.
 */
function editMovie()
{
    const imageUrl      = document.getElementById('imageUrl').value
    const trailerUrl    = document.getElementById('trailerUrl').value
    const title         = document.getElementById('mvName').value
    const genre         = document.getElementById('genre').value
    const newgenre      = genre.replace(/,/g, ':');
    const synopsis      = document.getElementById('syn').value
    const newsyn        = synopsis.replace(/,/g, ':');
    const reviews       = document.getElementById('reviews').value
    const duration      = document.getElementById('duration').value
    const casting       = document.getElementById('cast').value
    const newcasting    = casting.replace(/,/g, ':');
    const rating        = document.getElementById('rating-select-box').value
    const director      = document.getElementById('director').value
    const producer      = document.getElementById('prod').value
    const display       = document.getElementById('release').value

    socket.send(`EDITMOVIE,${title},${newcasting},${newgenre},${director},${producer},${duration},${newsyn},${display},${imageUrl},${trailerUrl},${reviews},${rating},${movieId}`);
    
    socket.onmessage = (event) => {
        console.log(event.data);
        if (event.data === "SUCCESS") {
            socket.send("GETMOVIES");
            window.location.href = "manage_movies.html";
        }
        socket.close();
    }

    socket.onclose = () => {
        console.log("Connection to server has ended.");
    }
}

window.onload = initialize;