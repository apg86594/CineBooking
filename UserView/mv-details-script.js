/* Script for mv-details.html */

// Grab movieID and dispay already-existing information
const queryString = window.location.search;
const urlParams = new URLSearchParams(queryString);
const movieId = urlParams.get('id');

function initialize()
{
    fetch("movie-info.json")
        .then(res => res.json())
        .then(movie_data => {
            document.getElementById("mvName").innerHTML = movie_data[movieId - 1].title;
            document.getElementById("genre").innerHTML = movie_data[movieId - 1].genre.replace(/:/g, ', ');
            document.getElementById("syn").innerHTML = movie_data[movieId - 1].synopsis.replace(/:/g, ', ');
            document.getElementById("reviews").innerHTML = `Movie Rating: ${movie_data[movieId - 1].review}`;
            document.getElementById("rating").innerHTML = movie_data[movieId - 1].ratingCode;
            document.getElementById("casting").innerHTML = movie_data[movieId - 1].casting.replace(/:/g, ', ');
            document.getElementById("director").innerHTML = movie_data[movieId - 1].director;
            document.getElementById("producer").innerHTML = movie_data[movieId - 1].producer;
            document.getElementById("trailerUrl").href = movie_data[movieId - 1].trailerVideo;
            document.getElementById("imageUrl").src = movie_data[movieId - 1].trailerPicture;

            document.getElementById("bookingbtn").addEventListener("click", (event) => {
                event.preventDefault();
                window.location.href = `booking.html?id=${movieId}`;
            })
        });
}

window.onload = initialize;