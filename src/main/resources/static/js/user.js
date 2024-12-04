$(document).ready(function () {
    $("#btn-Search").on("click", function () {
        var username = $("#filter-username").val();
        var phone = $("#filter-sdt").val();
        var email = $("#filter-email").val();
        var role = $("#filter-role option:selected").text();
        if (role == 'Role') role = '';
        var pathS = window.location.pathname.split("/");
        window.location.replace("/" + pathS[1] + "/admin/user?username=" + username + "&phone=" + phone + "&email=" + email + "&role=" + role);
    });

    $("#btn-Add").on("click", function () {
        var pathS = window.location.pathname.split("/");
        window.location.replace("/" + pathS[1] + "/admin/user/add");
    });

    $(".btn-Edit").on("click", function () {
        var pathS = window.location.pathname.split("/");
        var userId = $(this).parent().siblings(".user-id").text();
        window.location.replace("/" + pathS[1] + "/admin/user/?action=edit&id=" + userId);
    });

    $(".btn-Delete").on("click", function () {
        if (confirm("Are you sure to delete this?")) {
            var pathS = window.location.pathname.split("/");
            var userId = $(this).parent().siblings(".user-id").text();
            var url = "/" + pathS[1] + "/admin/user/?action=delete";
            var form = $('<form action="' + url + '" method="post">' +
                '<input type="text" name="user-id" value="' + userId + '" />' +
                '</form>');
            $('body').append(form);
            form.submit();
            // window.location.replace("/"+pathS[1]+"/admin/foundation/?action=delete&id="+foundationId);
        }
    })
})