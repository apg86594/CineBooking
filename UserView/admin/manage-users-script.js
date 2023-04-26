/* Script for manage_users.html */

var socket = null;

/*
 * Is called when window is loaded.
 */
function initialize()
{
    fetch("../users-info.json")
    .then(res => res.json())
    .then(data => displayUsers(data));
}

/*
 * Displays the users.
 */
function displayUsers(data)
{
    for (let i = 0; i < data.length; i++) {
        const tr = document.createElement("tr");
        tr.setAttribute("class", "header");

        const td_name = document.createElement("td");
        td_name.style.width = '15%';
        td_name.innerHTML = data[i].firstName + " " + data[i].lastName;
        tr.appendChild(td_name);

        const td_email = document.createElement("td");
        td_email.style.width = '20%';
        td_email.innerHTML = data[i].email;
        tr.appendChild(td_email);

        if (data[i].userType === "1") {
            document.getElementById("adminUserTable").appendChild(tr);
        } else {
            document.getElementById("userTable").appendChild(tr);
        }
    }
}

window.onload = initialize;