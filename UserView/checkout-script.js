/* Script for checkout.html */

function initialize()
{
    fetch("login-user-info.json")
    .then(res => res.json())
    .then(data => populate(data));
}

function populate(data)
{
    const checkbox = document.getElementById("usercard");
    checkbox.addEventListener("change", () => {
        if (this.checked) {
            document.getElementById("firstName").placeholder = `${data.firstName}`;
            document.getElementById("lastName").setAttribute("value", data.lastName);
            document.getElementById("cardnum").setAttribute("value", data.cardnum);
        }
    });
}

window.onload = initialize;