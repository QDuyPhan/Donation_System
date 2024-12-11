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
        $("#donation-amount").parent().siblings(".form-message").text("Vui lòng nhập số tiền cần quyên góp!")
    } else if (!regexNumber.test(donationAmount)) {
        isValidate = false;
        $("#donation-amount").parent().siblings(".form-message").text("Chỉ được nhập số ở ô tiền quyên góp!");
    } else {
        $("#donation-amount").parent().siblings(".form-message").text("");
    }
    return isValidate;

}