$(document).ready(function () {
    $("#btn-SendDonate").on("click", function (event) {
        if (!validateDonateForm()) {
            event.preventDefault();
        }
    })
});


function validateDonateForm() {
    var isValidate = true;
    var donationAmount = $("#donation-amount").val();
    var donationMessage = $("#donation-message").val();
    var regexNumber = /^\d+$/;
    if (donationAmount == "") {
        isValidate = false;
        $("#donation-amount").parent().siblings(".form-message").text("This field is required!")
    } else if (!regexNumber.test(donationAmount)) {
        isValidate = false;
        $("#donation-amount").parent().siblings(".form-message").text("Donation amount must be number!");
    } else {
        $("#donation-amount").parent().siblings(".form-message").text("");
    }
    return isValidate;

}