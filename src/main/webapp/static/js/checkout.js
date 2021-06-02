

let firstName = document.getElementById('firstName');
let lastName = document.getElementById('lastName');
let email = document.getElementById('email');
let phoneNum = document.getElementById('phonenum');

let shippingCountry = document.getElementById('shippingCountry');
let shippingCity = document.getElementById('shippingCity');
let shippingZipcode = document.getElementById('shippingZipcode');
let shippingAddress = document.getElementById('shippingAddress');

let sameAddress = document.getElementById('sameAddress');

let billingCountry = document.getElementById('billingCountry');
let billingCity = document.getElementById('billingCity');
let billingZipcode = document.getElementById('billingZipcode');
let billingAddress = document.getElementById('billingAddress');

let billingData = document.getElementById('billingData');
let submitBtn = document.getElementById('submitCheckout');

sameAddress.onclick = () => {
    if (sameAddress.checked === true) {
        sameAddressFill();
        billingData.style.visibility = "hidden";
    } else {
        emptyBillingAddress();
        billingData.style.visibility = "visible";
    }
}


function sameAddressFill() {
    billingCountry.value = shippingCountry.value;
    billingCity.value = shippingCity.value;
    billingZipcode.value = shippingZipcode.value;
    billingAddress.value = shippingAddress.value;
}

function emptyBillingAddress() {
    billingCountry.value = "";
    billingCity.value = "";
    billingZipcode.value = "";
    billingAddress.value = "";
}