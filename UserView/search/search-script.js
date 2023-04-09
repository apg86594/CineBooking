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