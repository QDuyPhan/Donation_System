$(document).ready(function () {
    $("#btn-Search").on("click", function () {
        var id = $("#filter-id").val();
        var name = $("#filter-name").val();
        var baseUrl = window.location.origin; // Lấy ra đường dẫn gốc của trang
        var pathS = window.location.pathname.split("/");
        var newPath = baseUrl + "/" + pathS[1] + "/admin/foundation?id=" + id + "&name=" + name;
        window.location.href = newPath;
    });
    $("#btn-1").on("click", function () {
        var pathS = window.location.pathname.split("/");
        window.location.replace("/" + pathS[1] + "/admin/foundation?action=add");
    })
    $(".btn-edi").on("click", function () {
        var pathS = window.location.pathname.split("/");
        var foundationId = $(this).parent().siblings(".foundation-id").text();
        window.location.replace("/" + pathS[1] + "/admin/foundation?action=edit&id=" + foundationId);
    });
    $(".btn-delete-Foundation").on("click", function () {
        if (confirm("Are you sure to delete this?")) {
            var pathS = window.location.pathname.split("/");
            var foundationId = $(this).parent().siblings(".foundation-id").text();
            var url = "/" + pathS[1] + "/admin/foundation?action=delete";
            var form = $('<form action="' + url + '" method="post">' +
                '<input type="text" name="foundation-id" value="' + foundationId + '" />' +
                '</form>');
            $('body').append(form);
            form.submit();
        }
    });
    $("#btn-SelectedDelete").on("click", function () {
        const selectedIdList = [];
        $("#tb-foundation input:checked").each(function () {
            selectedIdList.push($(this).parent().siblings(".foundation-id").text());
        });
        if (selectedIdList.length == 0) {
            return;
        }
        if (confirm("Are you sure to delete those items?")) {
            var pathS = window.location.pathname.split("/");
            var url = "/" + pathS[1] + "/admin/Foundation/?action=multipledelete";
            var inputs = '';
            selectedIdList.forEach((foundationId) => {

                inputs += '<input type="text" name="foundation-id" value="' + foundationId + '" />';
            });
            var form = $('<form action="' + url + '" method="post">' +
                inputs +
                '</form>');
            $('body').append(form);
            form.submit();

            // window.location.replace("/"+pathS[1]+"/admin/foundation/?action=delete&id="+foundationId);
        }
    });

    document.getElementById('btn-CancelFoundation').addEventListener('click', function () {
        var baseUrl = window.location.origin; // Lấy ra đường dẫn gốc của trang
        var pathS = window.location.pathname.split("/");
        var newPath = baseUrl + "/" + pathS[1] + "/admin/foundation";
        window.location.href = newPath;
    });
});