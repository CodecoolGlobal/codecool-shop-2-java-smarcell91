initButton()

function initButton() {
    const button = document.querySelector("#billing");
    button.addEventListener("click", billingPage);
}

function billingPage() {
    window.location.replace("/billing");
}