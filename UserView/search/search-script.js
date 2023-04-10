function initialize()
{
    fetch("../movie-info.json")
      .then(response => response.json())
      .then(data => displayMovies(data));
}

function displayMovies(movie_data)
{
    const table = document.getElementById("myTable");
    for (let i = 0; i < movie_data.length; i++)
    {
        const newdiv = document.createElement("tr");
        const td_img = document.createElement("td");
        const img = document.createElement("img");
        img.src = `../${movie_data[i].trailerPicture}`;
        img.alt = `${movie_data[i].title}`;
        img.width = 100;
        img.height = 150;
        td_img.appendChild(img);
        const td_title = document.createElement("td");
        td_title.innerHTML = `${movie_data[i].title}`;
        const td_genre = document.createElement("td");
        td_genre.innerHTML = `${movie_data[i].genre}`.replace(/:/g, ', ');
        const td_rating = document.createElement("td");
        td_rating.innerHTML = `${movie_data[i].ratingCode}`;
        newdiv.appendChild(td_img);
        newdiv.appendChild(td_title);
        newdiv.appendChild(td_genre);
        newdiv.appendChild(td_rating);
        newdiv.addEventListener("click", (event) => {
            event.preventDefault();
            window.location.href = `../mv-details.html?id=${movie_data[i].movieID}`;
        })
        table.appendChild(newdiv);
    }
}



function myFunction() {
    // Declare variables
    var input, filter, table, tr, td, i, txtValue;
    console.log("Function invoked");
    input = document.getElementById("myInput");
    filter = input.value.toUpperCase();
    table = document.getElementById("myTable");
    tr = table.getElementsByTagName("tr");
    console.log("tr value: " + tr);

    // Loop through all table rows, and hide those who don't match the search query
    for (i = 0; i < tr.length; i++) {
      td = tr[i].getElementsByTagName("td")[1];
      if (td) {
        txtValue = td.textContent || td.innerText;
        console.log("textValue is " + txtValue);
        if (txtValue.toUpperCase().indexOf(filter) > -1) {
          tr[i].style.display = "";
        } else {
          tr[i].style.display = "none";
        }
      }
    }
}

function searchForText() {
  var input, filter, table, tr, td, i, txtValue;
  console.log("Function invoked");
  input = document.getElementById("textSearch");
  filter = input.value.toUpperCase();
  table = document.getElementById("myTable");
  tr = table.getElementsByTagName("tr");
  console.log("searches: " + document.getElementById("searches").value);

  if(document.getElementById("searches").value == "title") {
    myFunctionTitle();
  }
  else if(document.getElementById("searches").value == "genre") {
    myFunctionGenre();
  }
  else if(document.getElementById("searches").value == "rating") {
    myFunctionRating();
  }

}

function myFunctionTitle() {
    // Declare variables
    var input, filter, table, tr, td, i, txtValue;
    console.log("Function invoked");
    input = document.getElementById("textSearch");
    filter = input.value.toUpperCase();
    table = document.getElementById("myTable");
    tr = table.getElementsByTagName("tr");
    console.log("tr value: " + tr);

    // Loop through all table rows, and hide those who don't match the search query
    for (i = 0; i < tr.length; i++) {
      td = tr[i].getElementsByTagName("td")[1];
      if (td) {
        txtValue = td.textContent || td.innerText;
        console.log("textValue is " + txtValue);
        if (txtValue.toUpperCase().indexOf(filter) > -1) {
          tr[i].style.display = "";
        } else {
          tr[i].style.display = "none";
        }
      }
    }
}

function myFunctionGenre() {
    // Declare variables
    var input, filter, table, tr, td, i, txtValue;
    console.log("Function invoked");
    input = document.getElementById("textSearch");
    filter = input.value.toUpperCase();
    table = document.getElementById("myTable");
    tr = table.getElementsByTagName("tr");
    console.log("tr value: " + tr);

    // Loop through all table rows, and hide those who don't match the search query
    for (i = 0; i < tr.length; i++) {
      td = tr[i].getElementsByTagName("td")[2];
      if (td) {
        txtValue = td.textContent || td.innerText;
        console.log("textValue is " + txtValue);
        if (txtValue.toUpperCase().indexOf(filter) > -1) {
          tr[i].style.display = "";
        } else {
          tr[i].style.display = "none";
        }
      }
    }
}

function myFunctionRating() {
    // Declare variables
    var input, filter, table, tr, td, i, txtValue;
    console.log("Function invoked");
    input = document.getElementById("textSearch");
    filter = input.value.toUpperCase();
    table = document.getElementById("myTable");
    tr = table.getElementsByTagName("tr");
    console.log("tr value: " + tr);

    // Loop through all table rows, and hide those who don't match the search query
    for (i = 0; i < tr.length; i++) {
      td = tr[i].getElementsByTagName("td")[3];
      if (td) {
        txtValue = td.textContent || td.innerText;
        console.log("textValue is " + txtValue);
        if (txtValue.toUpperCase().indexOf(filter) > -1) {
          tr[i].style.display = "";
        } else {
          tr[i].style.display = "none";
        }
      }
    }
}

window.onload = initialize;