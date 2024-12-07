/**
 *
 */
$(document).ready(function () {
    $("#btn-Save").on("click", function (event) {
        if (!validate()) {
            event.preventDefault();
        }
        var fundId = $("#fund-id").val();
        var pathS = window.location.pathname.split("/");
        if (fundId == "") {
            $("form").attr("action", "/" + pathS[1] + "/admin/fund/?action=add")
        } else {
            $("form").attr("action", "/" + pathS[1] + "/admin/fund/?action=edit")
        }
    });
    // $("#btn-Cancel").on("click", function () {
    //     var pathS = window.location.pathname.split("/");
    //     window.location.replace("/" + pathS[1] + "/admin/fund/");
    // })

});

function validate() {
    var isValidate = true;
    if ($("#fund-name").val() == "") {
        isValidate = false;
        $("#fund-name").siblings(".form-message").text("This field is required");

    }
    if ($("#fund-expected-result").val() == "") {
        isValidate = false;
        $("#fund-expected-result").siblings(".form-message").text("This field is required");
    }
    if ($("#fund-end-date").val() == "") {
        isValidate = false;
        $("#fund-end-date").siblings(".form-message").text("This field is required");
    }

    if ($("#fund-category option:selected").text() == "Category") {
        isValidate = false;
        alert("ok");
        $("#fund-category").siblings(".form-message").text("This field is required");
    }
    if ($("#fund-foundation option:selected").text() == "Foundation") {
        isValidate = false;
        $("#fund-foundation").siblings(".form-message").text("This field is required");
    }
    return isValidate;
}
