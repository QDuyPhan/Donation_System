$(document).ready(function () {
    $("#btn-SearchAminCategory").on("click", function () {
        var id = $("#filter-id").val();
        var name = $("#filter-name").val();
        var baseUrl = window.location.origin; // Lấy ra đường dẫn gốc của trang
        var pathS = window.location.pathname.split("/");
        var newPath = baseUrl + "/" + pathS[1] + "/admin/category?id=" + id + "&name=" + name;
        window.location.href = newPath;
    });
});