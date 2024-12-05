$(document).ready(function () {
    $("#btn-Search").on("click", function () {
        var id = $("#filter-id").val();
        var name = $("#filter-name").val();
        var baseUrl = window.location.origin; // Lấy ra đường dẫn gốc của trang
        var pathS = window.location.pathname.split("/");
        var newPath = baseUrl + "/" + pathS[1] + "/admin/category?id=" + id + "&name=" + name;
        window.location.href = newPath;
    });
    $("#btn-Add").on("click", function () {
        var pathS = window.location.pathname.split("/");
        window.location.replace("/" + pathS[1] + "/admin/category/add");
    })
    $(".btn-edit").on("click", function () {
        const pathS = window.location.pathname.split("/");
        const categoryId = $(this).closest("tr").find(".category-id").text();
        const newPath=`/${pathS[1]}/admin/category/edit?id=${categoryId}`;
        window.location.replace(newPath);
    });
    $(".btn-deleteF").on("click", function () {
        const foundationId = $(this).closest('tr').find('.foundation-id').text(); // Lấy ID Foundation từ hàng hiện tại

        if (confirm("Are you sure you want to delete foundation with ID: " + foundationId + "?")) {
            const foundationData = { "foundation-id": foundationId }; // Tạo object JSON chứa ID

            $.ajax({
                url: "/Donations/admin/foundation/delete",
                method: "POST",
                contentType: "application/json", // Gửi dữ liệu dưới dạng JSON
                data: JSON.stringify(foundationData), // Chuyển object sang JSON
                success: function (response) {
                    alert("Foundation deleted successfully!"); // Hiển thị thông báo thành công
                    location.reload(); // Tải lại trang để cập nhật dữ liệu
                },
                error: function (xhr) {
                    alert("Error deleting foundation: " + xhr.responseText); // Hiển thị lỗi nếu xảy ra
                }
            });
        }
    });
    $("#btn-SelectedDelete").on("click", function () {
        const selectedIdList = [];
        $("#tb-category input:checked").each(function () {
            selectedIdList.push($(this).parent().siblings(".category-id").text());
        });
        if (selectedIdList.length == 0) {
            return;
        }
        if (confirm("Are you sure to delete those items?")) {
            var pathS = window.location.pathname.split("/");
            var url = "/" + pathS[1] + "/admin/category/?action=multipledelete";
            var inputs = '';
            selectedIdList.forEach((categoryId) => {

                inputs += '<input type="text" name="category-id" value="' + categoryId + '" />';
            });
            var form = $('<form action="' + url + '" method="post">' +
                inputs +
                '</form>');
            $('body').append(form);
            form.submit();

            // window.location.replace("/"+pathS[1]+"/admin/foundation/?action=delete&id="+foundationId);
        }
    });
    $("#btn-Save").on("click", function (e) {
        e.preventDefault(); // Ngăn hành động mặc định (submit form)

        // Thu thập dữ liệu từ form
        const categoryDate = {
            name: $("#category-name").val(),
            description: $("#category-description").val(),
            status: $("#category-status").val()
        };

        // Gửi dữ liệu qua AJAX
        $.ajax({
            url: "/Donations/admin/category/add", // Đường dẫn API
            method: "POST",
            contentType: "application/json",
            data: JSON.stringify(foundationData), // Chuyển dữ liệu thành JSON
            success: function (response) {
                // Xử lý khi lưu thành công
                alert(" added successfully!");
                window.location.href = "/Donations/admin/category"; // Điều hướng sau khi lưu
            },
            error: function (xhr, status, error) {
                // Xử lý lỗi
                console.error("Error:", error);
                alert("Error saving: " + xhr.responseText);
            }
        });
    });
    $("#btn-Save-Update").on("click", function (e) {
        e.preventDefault(); // Ngăn submit mặc định của form

        const categoryData = {
            id: $("#category-id").val(),
            name: $("#category-name").val(),
            description: $("#category-description").val(),
            status: $("#category-status").val()
        };

        $.ajax({
            url: "/Donations/admin/category/edit",
            method: "POST",
            contentType: "application/json", // Nếu server mong đợi JSON
            data: JSON.stringify(categoryData), // Chuyển dữ liệu sang JSON
            success: function (response) {
                alert("Cập nhật Danh mục thành công!");
                window.location.href = "/Donations/admin/category";
            },
            error: function (xhr, status, error) {
                alert("Lỗi Cập nhật Danh mục không thành công: " + xhr.responseText);
            }
        });
    });
});