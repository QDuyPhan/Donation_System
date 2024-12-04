/**
 *
 */
$(document).ready(function () {
    $(".btn-edit").on("click", function () {
        var pathS = window.location.pathname.split("/");
        var foundationId = $(this).parent().siblings(".fund-id").text();
        window.location.replace("/" + pathS[1] + "/admin/fund/?action=edit&id=" + foundationId);
    });
    $(".btn-delete").on("click", function () {
        if (confirm("Are you sure to delete this?")) {
            var pathS = window.location.pathname.split("/");
            var fundId = $(this).parent().siblings(".fund-id").text();
            var url = "/" + pathS[1] + "/admin/fund/?action=delete";
            var form = $('<form action="' + url + '" method="post">' +
                '<input type="text" name="fund-id" value="' + fundId + '" />' +
                '</form>');
            $('body').append(form);
            form.submit();
            // window.location.replace("/"+pathS[1]+"/admin/foundation/?action=delete&id="+foundationId);
        }

    })
    $("#btn-Add").on("click", function () {
        var pathS = window.location.pathname.split("/");
        window.location.replace("/" + pathS[1] + "/admin/fund/add");
    })

    $("#btn-Search").on("click", function () {
        var id = $("#filter-id").val();
        var name = $("#filter-name").val();
        var foundation = $("#filter-foundation").val();
        var category = $("#filter-category option:selected").text();
        if (category == "Category") category_name = "";
        var pathS = window.location.pathname.split("/");
        window.location.replace("/" + pathS[1] + "/admin/fund?id=" + id + "&name=" + name + "&foundation=" + foundation + "&category=" + category);
    });

    $("#btn-SelectedDelete").on("click", function () {
        const selectedIdList = [];
        $("#tb-fund input:checked").each(function () {
            selectedIdList.push($(this).parent().siblings(".fund-id").text());
        });
        if (selectedIdList.length == 0) {
            alert("Không có phần tử nào được chọn");
            return;
        }
        if (confirm("Are you sure to delete those items?")) {
            var pathS = window.location.pathname.split("/");
            var url = "/" + pathS[1] + "/admin/fund/?action=multipledelete";
            var inputs = '';
            selectedIdList.forEach((fundId) => {

                inputs += '<input type="text" name="fund-id" value="' + fundId + '" />';
            });
            var form = $('<form action="' + url + '" method="post">' +
                inputs +
                '</form>');
            $('body').append(form);
            form.submit();

        }
    });

})