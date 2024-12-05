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
    $(".btn-delete").on("click", function () {
        const foundationId = $(this).data("id"); // Lấy ID từ nút xóa
        const row = $(this).closest("tr"); // Lấy dòng hiện tại

        if (confirm(`Are you sure you want to delete foundation with ID: ${foundationId}?`)) {
            $.ajax({
                url: `/Donations/admin/foundation/delete/${foundationId}`,
                method: "DELETE", // Gửi request DELETE
                success: function (response) {
                    alert(response); // Hiển thị thông báo thành công
                    row.remove(); // Xóa dòng khỏi bảng
                },
                error: function (xhr) {
                    alert(xhr.responseText || "An error occurred!"); // Hiển thị lỗi
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


    $("#btn-SaveF-Update").on("click", function (e) {
        e.preventDefault(); // Ngăn submit mặc định của form

        const foundationData = {
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
                alert("Cập nhật nhà tổ chức thành công!");
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