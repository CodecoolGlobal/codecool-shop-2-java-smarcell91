initializeButtons();

function initializeButtons() {
    const addButtons = document.querySelectorAll(".add-to-cart");
    const incrementButtons = document.querySelectorAll(".increment");
    const decrementButtons = document.querySelectorAll(".decrement");
    const removeButtons = document.querySelectorAll(".remove");
    if (addButtons != null) {
        addButtons.forEach(button => {
            button.addEventListener("click", () => addToCart(button.id));
        })
    }
    if (incrementButtons != null) {
        incrementButtons.forEach(button => {
            button.addEventListener("click", () => incrementAmount(button.id));
        })
        decrementButtons.forEach(button => {
            button.addEventListener("click", () => decrementAmount(button.id));
        })
        removeButtons.forEach(button => {
            button.addEventListener("click", () => removeProduct(button.id));
        })
    }
}

function addToCart(id) {
    fetch(`cart/add?id=${id}`, {
        method: "GET"
    })
}

function incrementAmount(id) {
    fetch(`cart/add?id=${id}`, {
        method: "GET"
    })
    document.querySelector(".amount").innerHTML = `<h3> ${parseInt(document.querySelector(".amount").innerText) + 1} </h3>`
}

function decrementAmount(id) {
    fetch(`cart/decrement?id=${id}`, {
        method: "GET"
    })
    if (parseInt(document.querySelector(".amount").innerText) > 1) {
    document.querySelector(".amount").innerHTML = `<h3> ${parseInt(document.querySelector(".amount").innerText) - 1} </h3>`} else {removeProduct(id)}
}

function removeProduct(id) {
    fetch(`cart/remove?id=${id}`, {
        method: "GET"
    })
    location.reload();
}