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
    const children = document.getElementById(`${id} amount`).childNodes;
    let counter = 0;
    for (let i = 0; i < children.length; i++) {
        if (children[i].nodeName == "INPUT") {
            counter = i;
        }
    }
    document.getElementById(`${id} amount`).innerHTML = `<input type="text" value="${parseInt(children[counter].value) + 1}">`
}

function decrementAmount(id) {
    fetch(`cart/decrement?id=${id}`, {
        method: "GET"
    })
    const children = document.getElementById(`${id} amount`).childNodes;
    let counter = 0;
    for (let i = 0; i < children.length; i++) {
        if (children[i].nodeName == "INPUT") {
            counter = i;
        }
    }
    if (parseInt(children[counter].value) > 1) {
    document.getElementById(`${id} amount`).innerHTML = `<input type="text" value="${parseInt(children[counter].value) - 1}">`} else {removeProduct(id)}
}

function removeProduct(id) {
    fetch(`cart/remove?id=${id}`, {
        method: "GET"
    })
    location.reload();
}