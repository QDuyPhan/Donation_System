/**
 *
 */


$(document).ready(function () {
    $(".btn-edit").on("click", function () {
        var pathS = window.location.pathname.split("/");
        var foundationId = $(this).parent().siblings(".fund-id").text();
        window.location.replace("/" + pathS[1] + "/admin/fund/?action=edit&id=" + foundationId);
    });

    $(".btn-Cancel-fund").on("click", function () {
        window.history.back();
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
    document.getElementById("btn-SelectedDelete-fund").addEventListener("click", function () {
        // Lấy tất cả checkbox
        const checkboxes = document.querySelectorAll("tbody input[type='checkbox']");

        // Lọc các checkbox được chọn và lấy giá trị (ID)
        const selectedIds = Array.from(checkboxes)
            .filter(checkbox => checkbox.checked && checkbox.value) // Kiểm tra giá trị hợp lệ
            .map(checkbox => checkbox.value);

        // Kiểm tra nếu không có checkbox nào được chọn
        if (selectedIds.length === 0) {
            alert("Please select at least one fund to delete.");
            return;
        }

        // Tạo URL để gửi request
        redirectToDeleteFunds(selectedIds);
    });

// Hàm để chuyển hướng đến URL xóa
    function redirectToDeleteFunds(ids) {
        const url = `/Donations/admin/fund/deleteFunds?ids=${ids.join(",")}`;
        window.location.href = url;
    }

    // $("#btn-SelectedDelete").on("click", function () {
    //     const selectedIdList = [];
    //     $("#tb-fund input:checked").each(function () {
    //         selectedIdList.push($(this).parent().siblings(".fund-id").text());
    //     });
    //     if (selectedIdList.length == 0) {
    //         alert("Không có phần tử nào được chọn");
    //         return;
    //     }
    //     if (confirm("Are you sure to delete those items?")) {
    //         var pathS = window.location.pathname.split("/");
    //         var url = "/" + pathS[1] + "/admin/fund/?action=multipledelete";
    //         var inputs = '';
    //         selectedIdList.forEach((fundId) => {
    //
    //             inputs += '<input type="text" name="fund-id" value="' + fundId + '" />';
    //         });
    //         var form = $('<form action="' + url + '" method="post">' +
    //             inputs +
    //             '</form>');
    //         $('body').append(form);
    //         form.submit();
    //
    //     }
    // });



})

