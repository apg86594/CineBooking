/* Script for order_hist.html */

var socket = null;

// Grab movieID and dispay already-existing information
const queryString = window.location.search;
const urlParams = new URLSearchParams(queryString);
const userId = urlParams.get('id');

function initialize()
{
    fetch("../orderHistory-info.json")
    .then(res => res.json())
    .then(data => displayOrderHistory(data));
}

async function displayOrderHistory(data)
{
    var mvshowid = [];
    var mvid, aud, showtime, seats, price;

    for (let i = 0; i < data.length; i++) {
        mvshowid.push(data[i].movieShowID);
    }
    for (let i = 0; i < data.length; i++) {
        const row = document.createElement("tr");
        row.setAttribute("class", "header");

        /*
        // Get date of movie
        const td_date = document.createElement("td");
        td_date.style.width = '20%';
        const movieshowResponse = await fetch("../movieShow-info.json");
        const movieshow_data = await movieshowResponse.json();
        for (let j = 0; j < movieshow_data.length; j++) {
            if (movieshow_data[j].movieShowID === mvshowid[i]) {
                td_date.innerHTML = `${movieshow_data[j].showStart}`;
                mvid = movieshow_data[j].movieID;
            }
        }
        row.appendChild(td_date);

        // Get movie name
        const td_movie = document.createElement("td");
        td_movie.style.width = '20%';
        const movieResponse = await fetch("../movie-info.json");
        const movie_data = await movieResponse.json();
        for (let j = 0; j < movie_data.length; i++) {
            if (movie_data[j].movieID === mvid) {
                td_movie.innerHTML = movie_data[j].title;
            }
        }
        row.appendChild(td_movie);
        

        document.getElementById("myTable").appendChild(row);
        */
    }
}

window.onload = initialize;