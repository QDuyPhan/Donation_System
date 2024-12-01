$(document).ready(function () {
    $("#btn-SearchF").on("click", function () {
        var id = $("#filter-idF").val();
        var name = $("#filter-nameF").val();
        var baseUrl = window.location.origin; // Lấy ra đường dẫn gốc của trang
        var pathS = window.location.pathname.split("/");
        var newPath = baseUrl + "/" + pathS[1] + "/admin/foundation?id=" + id + "&name=" + name;
        window.location.href = newPath;
    });
    $(".btn-editF").on("click", function () {
        const pathS = window.location.pathname.split("/");
        const foundationId = $(this).closest("tr").find(".foundation-id").text(); // Tìm Foundation ID
        const newPath = `/${pathS[1]}/admin/foundation/edit?id=${foundationId}`;
        window.location.replace(newPath);
    });
    $(".btn-deleteF").on("click", function () {
        var foundationId = $(this).closest('tr').find('.foundation-id').text();

        // Hiển thị hộp thoại xác nhận
        if (confirm("Are you sure you want to delete foundation with ID: " + foundationId + "?")) {
            // Gửi yêu cầu xóa tới controller nếu xác nhận
            $.ajax({
                url: "/Donations/admin/foundation/delete",  // Đảm bảo đường dẫn đúng
                type: "POST",
                data: { "foundation-id": foundationId },  // Gửi ID foundation
                success: function (response) {
                    alert(response);  // Hiển thị thông báo thành công
                    location.reload(); // Tải lại trang sau khi xóa thành công
                },
                error: function (xhr, status, error) {
                    console.error("Error:", error);
                    alert("Error deleting foundation: " + xhr.responseText); // Hiển thị lỗi nếu có
                }
            });
        }
    });

    $("#btn-SelectedDeleteF").on("click", function () {
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
    $("#btn-SaveF").on("click", function (e) {
        e.preventDefault(); // Ngăn hành động mặc định (submit form)

        // Thu thập dữ liệu từ form
        const foundationData = {
            id: $("#foundation-id").val(),
            name: $("#foundation-name").val(),
            email: $("#foundation-email").val(),
            description: $("#foundation-description").val(),
            status: $("#foundation-status").val()
        };

        // Gửi dữ liệu qua AJAX
        $.ajax({
            url: "/Donations/admin/foundation/add", // Đường dẫn API
            method: "POST",
            contentType: "application/json",
            data: JSON.stringify(foundationData), // Chuyển dữ liệu thành JSON
            success: function (response) {
                // Xử lý khi lưu thành công
                alert("Foundation added successfully!");
                window.location.href = "/Donations/admin/foundation"; // Điều hướng sau khi lưu
            },
            error: function (xhr, status, error) {
                // Xử lý lỗi
                console.error("Error:", error);
                alert("Error saving foundation: " + xhr.responseText);
            }
        });
    });

    $("#btn-SaveF-Update").on("click", function (e) {
        e.preventDefault(); // Ngăn submit mặc định của form

        const foundationData = {
            id: $("#foundation-id").val(),
            name: $("#foundation-name").val(),
            email: $("#foundation-email").val(),
            description: $("#foundation-description").val(),
            status: $("#foundation-status").val()
        };

        $.ajax({
            url: "/Donations/admin/foundation/edit",
            method: "POST",
            contentType: "application/json", // Nếu server mong đợi JSON
            data: JSON.stringify(foundationData), // Chuyển dữ liệu sang JSON
            success: function (response) {
                alert("Foundation updated successfully!");
                window.location.href = "/Donations/admin/foundation";
            },
            error: function (xhr, status, error) {
                alert("Error updating foundation: " + xhr.responseText);
            }
        });
    });



    document.getElementById('btn-CancelF').addEventListener('click', function () {
        var baseUrl = window.location.origin; // Lấy ra đường dẫn gốc của trang
        var pathS = window.location.pathname.split("/");
        var newPath = baseUrl + "/" + pathS[1] + "/admin/foundation";
        window.location.href = newPath;
    });
});