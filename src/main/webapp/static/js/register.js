initButton()

function initButton() {
    const button = document.querySelector("#register");
    if (button != null) button.addEventListener("click", openRegister);
}

function openRegister() {
    const div = document.querySelector(".user");
    if (document.querySelector("#registerForm") == null && document.querySelector("#loginForm") == null) {
        const form = document.createElement("form");
        const email = document.createElement("input");
        const pw = document.createElement("input");
        const pw2 = document.createElement("input");
        const submit = document.createElement("input");
        const firstName = document.createElement("input");
        const lastName = document.createElement("input");
        const newDiv = document.createElement("div");
        newDiv.setAttribute("class", "userHandle");
        submit.setAttribute("type", "submit");
        submit.setAttribute("value", "Register");
        submit.setAttribute("id", "registerButton")
        submit.addEventListener("click", verifyPasswords);
        form.setAttribute("action", "/register");
        form.setAttribute("method", "POST");
        form.setAttribute("id", "registerForm");
        firstName.setAttribute("type", "text");
        firstName.setAttribute("name", "firstName");
        firstName.setAttribute("required", true);
        firstName.setAttribute("placeholder", "First name");
        lastName.setAttribute("type", "text");
        lastName.setAttribute("name", "lastName");
        lastName.setAttribute("required", true);
        lastName.setAttribute("placeholder", "Last name");
        email.setAttribute("type", "email");
        email.setAttribute("id", "email");
        email.setAttribute("name", "email");
        email.setAttribute("required", true);
        email.setAttribute("placeholder", "E-mail");
        pw.setAttribute("type", "password");
        pw.setAttribute("name", "password");
        pw.setAttribute("id", "pw1")
        pw.setAttribute("required", true);
        pw.setAttribute("placeholder", "Password");
        pw2.setAttribute("type", "password");
        pw2.setAttribute("id", "pw2")
        pw2.setAttribute("required", true);
        pw2.setAttribute("placeholder", "Password again");
        form.appendChild(firstName);
        form.appendChild(lastName);
        form.appendChild(email);
        form.appendChild(pw);
        form.appendChild(pw2);
        form.appendChild(submit);
        newDiv.appendChild(form);
        div.appendChild(newDiv);
    } else {
        const userHandle = document.querySelector(".userHandle");
        div.removeChild(userHandle);
    }
}

function verifyPasswords() {
    const pw = document.getElementById("pw1");
    const pw2 = document.getElementById("pw2");
    const btn = document.getElementById("registerButton");
    if (pw.value != pw2.value) {
        btn.setCustomValidity("Passwords don't match");
    } else if (pw.value.length < 8) {
        btn.setCustomValidity("Password should be at least 8 characters long")
    } else {
        btn.setCustomValidity("");
    }
}