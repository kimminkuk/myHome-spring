function reserveItemMake() {
    let reserveItemBody = document.querySelector(".reserve-item-make-body");
    if (reserveItemBody.style.display == "none") {
        reserveItemBody.style.display = "block";
    } else {
        reserveItemBody.style.display = "none";
    }
    return;
}