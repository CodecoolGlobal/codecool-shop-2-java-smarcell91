initButton()

function initButton() {
    const button = document.getElementById("login");
    button.addEventListener("click", openLogin)
}

function openLogin() {
    if (document.querySelector("#loginForm") == null) {
        const div = document.querySelector(".login");
        const form = document.createElement("form");
        const email = document.createElement("input");
        const pw = document.createElement("input");
        const submit = document.createElement("input");
        submit.setAttribute("type", "submit");
        form.setAttribute("action", "/login");
        form.setAttribute("method", "POST");
        form.setAttribute("id", "loginForm");
        email.setAttribute("type", "email");
        email.setAttribute("name", "email");
        email.setAttribute("required", true);
        email.setAttribute("placeholder", "E-mail");
        pw.setAttribute("type", "password");
        pw.setAttribute("name", "password");
        pw.setAttribute("required", true);
        pw.setAttribute("placeholder", "Password");
        form.appendChild(email);
        form.appendChild(pw);
        form.appendChild(submit);
        div.appendChild(form);
    }
}