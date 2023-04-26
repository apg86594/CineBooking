const checkbox = document.getElementById("usercard");
if (checkbox.checked) {
    document.getElementById("paymentcol").style.display = 'none';
} else {
    document.getElementById("paymentcol").style.display = 'grid';
}