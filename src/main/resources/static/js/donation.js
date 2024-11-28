$(document).ready(function () {
    $("#btn-SearchDonation").on("click", function () {
        var id = $("#filter-id").val();
        var username = $("#filter-username").val();
        var fundname = $("#filter-fundname").val();
        var baseUrl = window.location.origin; // Lấy ra đường dẫn gốc của trang
        var pathS = window.location.pathname.split("/");
        var newPath = baseUrl + "/" + pathS[1] + "/admin/donation?id=" + id + "&username=" + username + "&fundname=" + fundname;
        window.location.href = newPath;
    });
})