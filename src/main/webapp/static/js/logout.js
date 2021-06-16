initButton()

function initButton() {
    const button = document.querySelector("#logout");
    if (button != null) button.addEventListener("click", logout);
}

function logout() {
    window.location.replace("/logout");
}