/* Script for booking.html */

/* 
IGNORE: NOTE TO SELF
    - insert showtimes to display showtimes
    - might need to use movieshow table, unsure yet
    - work on functions for checking out (& checkout btn)
    - fix tickets to where there are only 3 types:
        Child
        Adult
        Senior
    - do something with the seating. reduce to at most 40 seats per auditorium
*/

var socket = null;

// Grab movieID and dispay already-existing information
const queryString = window.location.search;
const urlParams = new URLSearchParams(queryString);
const movieId = urlParams.get('id');

function initialize()
{
    fetch("movie-info.json")
        .then(res => res.json())
        .then(movie_data => displayMovie(movie_data));

    fetch("auditorium-info.json")
        .then(res => res.json())
        .then(aud_data => {
            // Checks if auditoriums exist
            if (aud_data == "") {
                socket = new WebSocket("ws://127.0.0.1:8888");
                socket.onopen = () => {
                    console.log("Connected to server. Grabbing auditoriums...");
                    socket.send("GETAUDITORIUMS");
                }
                socket.onmessage = (event) => {
                    console.log(`GETAUDITORIUMS: ${event.data}`);
                    socket.close();
                }
                socket.onclose = () => {
                    console.log("Connection closed.");
                }
            }
            displayAuditoriums(aud_data);
        });

    /*
    fetch("showtime-info.json")
        .then(res => res.json())
        .then(showtime_data => displayShowtimes(showtime_data));
    */
}

/*
 * Displays the selected movie for checkout.
 */
function displayMovie(data)
{
    let newdiv = document.createElement("div");
    newdiv.setAttribute("class", "shop-item");
    let img = document.createElement("img");
    img.src = `${data[movieId - 1].trailerPicture}`;
    img.alt = `${data[movieId - 1].title}`;
    img.width = 400;
    img.height = 600;
    newdiv.appendChild(img);
    document.getElementById("mv_display").appendChild(newdiv);
}

/*
 * Displays available auditoriums.
 */
function displayAuditoriums(data)
{
    let newdiv = document.createElement("div");
    newdiv.setAttribute("class", "auditoriums");
    let header = document.createElement("h2");
    header.innerHTML = "Select an auditorium";
    newdiv.appendChild(header);
    for (let i = 0; i < data.length; i++) {
        let aud_div = document.createElement("div");
        let checkbox = document.createElement("input");
        checkbox.setAttribute("class", "single-checkbox");
        checkbox.setAttribute("type", "checkbox");
        checkbox.setAttribute("value", "yes");
        checkbox.addEventListener('change', function() {
          if (this.checked) {
            // loop through all other checkboxes and uncheck them
            let checkboxes = document.querySelectorAll('.single-checkbox');
            checkboxes.forEach(function(otherCheckbox) {
              if (otherCheckbox !== checkbox) {
                otherCheckbox.checked = false;
              }
            });
          }
        });
        let label = document.createElement("label");
        label.innerHTML = `${data[i].audName}`;
        aud_div.appendChild(checkbox);
        aud_div.appendChild(label);
        newdiv.appendChild(aud_div);
    }
    document.getElementById("aud_display").appendChild(newdiv);
}

function ready() {
    var removeCartItemButtons = document.getElementsByClassName('btn-danger')
    for (var i = 0; i < removeCartItemButtons.length; i++) {
        var button = removeCartItemButtons[i]
        button.addEventListener('click', removeCartItem)
    }

    var quantityInputs = document.getElementsByClassName('cart-quantity-input')
    for (var i = 0; i < quantityInputs.length; i++) {
        var input = quantityInputs[i]
        input.addEventListener('change', quantityChanged)
    }

    var addToCartButtons = document.getElementsByClassName('shop-item-button')
    for (var i = 0; i < addToCartButtons.length; i++) {
        var button = addToCartButtons[i]
        button.addEventListener('click', addToCartClicked)
        button.addEventListener('click', addTimeToCartClicked)
    }

    document.getElementsByClassName('btn-purchase')[0].addEventListener('click', purchaseClicked)
}

function readyTime() {
    var removeCartTimeButtons = document.getElementsByClassName('btn-danger')
    for (var i = 0; i < removeCartTimeButtons.length; i++) {
        var button = removeCartTimeButtons[i]
        button.addEventListener('click', removeCartTime)
    }

    var addTimeToCartButtons = document.getElementsByClassName('shop-item-button')
    for (var i = 0; i < addTimeToCartButtons.length; i++) {
        var button = addTimeToCartButtons[i]
        button.addEventListener('click', addTimeToCartClicked)
    }

    document.getElementsByClassName('btn-purchase')[0].addEventListener('click', purchaseClicked)
}

function purchaseClicked() {
    alert('Thank you for your purchase')
    var cartItems = document.getElementsByClassName('cart-items')[0]
    while (cartItems.hasChildNodes()) {
        cartItems.removeChild(cartItems.firstChild)
    }
    updateCartTotal()
}

function removeCartItem(event) {
    var buttonClicked = event.target
    buttonClicked.parentElement.parentElement.remove()
    updateCartTotal()
}

function removeCartTime(event) {
    var buttonClicked = event.target
    buttonClicked.parentElement.parentElement.remove()
    updateCartTimeTotal()
}

function quantityChanged(event) {
    var input = event.target
    if (isNaN(input.value) || input.value <= 0) {
        input.value = 1
    }
    updateCartTotal()
}

function addToCartClicked(event) {
    var button = event.target
    var shopItem = button.parentElement.parentElement
    var title = shopItem.getElementsByClassName('shop-item-title')[0].innerText
    var price = shopItem.getElementsByClassName('shop-item-price')[0].innerText
    var imageSrc = shopItem.getElementsByClassName('shop-item-image')[0].src
    addItemToCart(title, price, imageSrc)
    updateCartTotal()
}

function addTimeToCartClicked(event) {
    var button = event.target
    var shopItem = button.parentElement.parentElement
    var time = shopItem.getElementsByClassName('shop-item-time')[0].innerText
    addTimeToCart(time)
    updateCartTotal()
}

function addItemToCart(title, price, imageSrc) {
    var cartRow = document.createElement('div')
    cartRow.classList.add('cart-row')
    var cartItems = document.getElementsByClassName('cart-items')[0]
    var cartItemNames = cartItems.getElementsByClassName('cart-item-title')
    for (var i = 0; i < cartItemNames.length; i++) {
        if (cartItemNames[i].innerText == title) {
            alert('This item is already added to the cart')
            return
        }
    }
    var cartRowContents = `
        <div class="cart-item cart-column">
            <img class="cart-item-image" src="${imageSrc}" width="200px" height="300px">
        </div>
        <span class="cart-price cart-column">${price}</span>
        <div class="cart-quantity cart-column">
            <input class="cart-quantity-input" type="number" value="1">
            <button class="btn btn-danger" type="button">REMOVE</button>
        </div>`
    cartRow.innerHTML = cartRowContents
    cartItems.append(cartRow)
    cartRow.getElementsByClassName('btn-danger')[0].addEventListener('click', removeCartItem)
    cartRow.getElementsByClassName('cart-quantity-input')[0].addEventListener('change', quantityChanged)
}

function addTimeToCart(time) {
    var cartRow = document.createElement('div')
    cartRow.classList.add('cart-row')
    var cartItems = document.getElementsByClassName('cart-items')[0]
    var cartItemNames = cartItems.getElementsByClassName('cart-item-title')
    for (var i = 0; i < cartItemNames.length; i++) {
        if (cartItemNames[i].innerText == time) {
            alert('This item is already added to the cart')
            return
        }
    }
    var cartRowContents = `
        <span class="cart-time cart-column">${time}</span>`
    cartRow.innerHTML = cartRowContents
    cartItems.append(cartRow)
    cartRow.getElementsByClassName('btn-danger')[0].addEventListener('click', removeCartItem)
    cartRow.getElementsByClassName('cart-quantity-input')[0].addEventListener('change', quantityChanged)
}

function updateCartTotal() {
    var cartItemContainer = document.getElementsByClassName('cart-items')[0]
    var cartRows = cartItemContainer.getElementsByClassName('cart-row')
    var total = 0
    for (var i = 0; i < cartRows.length; i++) {
        var cartRow = cartRows[i]
        var priceElement = cartRow.getElementsByClassName('cart-price')[0]
        var quantityElement = cartRow.getElementsByClassName('cart-quantity-input')[0]
        var price = parseFloat(priceElement.innerText.replace('$', ''))
        var quantity = quantityElement.value
        total = total + (price * quantity)
    }
    total = Math.round(total * 100) / 100
    document.getElementsByClassName('cart-total-price')[0].innerText = '$' + total
    updateCartTimeTotal()
}

function updateCartTimeTotal() {
    var cartItemContainer = document.getElementsByClassName('cart-items')[0]
    var cartRows = cartItemContainer.getElementsByClassName('cart-row')
    for (var i = 0; i < cartRows.length; i++) {
        var cartRow = cartRows[i]
        var timeElement = cartRow.getElementsByClassName('cart-time')[0]
    } 
}

window.onload = initialize;