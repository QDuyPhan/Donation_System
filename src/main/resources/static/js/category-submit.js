$(document).ready(function () {
    $("#btn-SaveAdminCategory").on("click", function (event) {
        alert("Thêm tài khoản thành công");
        event.preventDefault();
        var pathS = window.location.pathname.split("/");
        window.location.replace("/" + pathS[1] + "/admin/category");

    })

})