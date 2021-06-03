initializeButtons();
fetchProducts();
fetchCartSize();
showTotal();

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

async function addToCart(id) {
    await fetch(`cart/add?id=${id}`, {
        method: "GET"
    })
    fetchProducts();
    fetchCartSize();
    showTotal();
}

function showTotal() {
    const prices = document.querySelectorAll(".lead");
    const total = document.querySelector(".total");
    const amounts = document.querySelectorAll("input");
    let totalPrice = 0;
    if (total != null) {
        total.innerHTML = "";
        for (let i = 0; i < prices.length; i++) {
            totalPrice += parseFloat(prices[i].innerText.split(" ")[0]) * amounts[i].value;
        }
        let h2 = document.createElement("h2");
        h2.innerText = "Total price: " + totalPrice.toFixed(1) + " USD";
        total.appendChild(h2);
    }
}

function fetchProducts() {
    fetch("fetchitems/name")
        .then((response) => response.json())
        .then((data) => {
            const productList = document.querySelector(".cartitems");
            if (productList != null) {
                productList.innerHTML = "";
                for (let i = 0; i < data.length; i++) {
                    const option = document.createElement("li");
                    option.innerText = data[i];
                    productList.appendChild(option);
                }
            }
        })
}

function fetchCartSize() {
    fetch("fetchitems/size")
        .then((response) => response.json())
        .then(data => {
            const size = document.querySelector("#cart");
            if (size != null) size.innerText = "Cart (" + data + ")";
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
    showTotal();
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
    showTotal();
}

function removeProduct(id) {
    fetch(`cart/remove?id=${id}`, {
        method: "GET"
    })
    location.reload();
    showTotal();
}