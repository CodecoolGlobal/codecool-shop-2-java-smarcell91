
let payPalUserName = document.getElementById("payPalUsername");
let payPalPW = document.getElementById("payPalPW");

let cardNumber = document.getElementById("cardNumber");
let cardHolder = document.getElementById("cardHolder");
let expiryDate = document.getElementById("expiryDate");
let cardCode = document.getElementById("cardCode");

let payPal = document.getElementById("payPalCheckbox");
let creditCard = document.getElementById("cardCheckbox");

let creditCardDiv = document.getElementById("creditCard");
let payPalDiv = document.getElementById("paypal")

payPal.onclick = () => {
    if (payPal.checked === true) {
        payPalDiv.style.visibility = "visible";
    } else {
        emptyPayPalInfo();
        payPalDiv.style.visibility = "hidden";
    }
}

creditCard.onclick = () => {
    if (creditCard.checked === true) {
        creditCardDiv.style.visibility = "visible";
    } else {
        emptyCreditCardInfo();
        creditCardDiv.style.visibility = "hidden"
    }
}

function emptyPayPalInfo() {
    payPalUserName.value = "";
    payPalPW.value = "";
}

function emptyCreditCardInfo () {
    cardNumber.value = "";
    cardHolder.value = "";
    expiryDate.value = "";
    cardCode.value = "";
}