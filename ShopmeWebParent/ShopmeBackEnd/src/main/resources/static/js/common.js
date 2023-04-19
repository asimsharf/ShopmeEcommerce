$(document).ready(function () {

    $("#logoutLink").click(function (event) {
        console.log("Logout clicked");
        event.preventDefault();
        $("#logoutForm").submit();
        console.log("Logout form submitted");
    });

    $("#theBtnCancel").click(function () {
        window.location.href = "/ShopmeAdmin/users";
    });

    $("#fileImage").change(function () {
        fileSize = this.files[0].size;
        if (fileSize > 2097152) {
            $("#modalTitle").text("Warning");
            $("#modalBody").text("The file size must not exceed 2MB.");
            $("#modalDialog").modal();
            this.value = "";
            this.reportValidity();
            return false;
        }
        showThumbnail(this, "#thumbnail");
    });

    $(".link-delete").on("click", function (e) {
        e.preventDefault();
        link = $(this);
        userId = link.attr("userId");
        $("#yesButton").attr("href", link.attr("href"));
        $("#confirmText").text("Are you sure you want to delete this user with ID " + userId + "?");
        $("#confirmModal").modal();
    });

    $("#keyword").on("keyup", function (e) {
        if (e.keyCode == 13) {
            searchUsers();
        }
    });

});

function showThumbnail(fileInput, img) {
    if (fileInput.files && fileInput.files[0]) {
        var reader = new FileReader();
        reader.onload = function (e) {
            $(img).attr('src', e.target.result);
        }
        reader.readAsDataURL(fileInput.files[0]);
    }
}

function checkEmailUnique(form) {
    url = "/ShopmeAdmin/api/users/check_email";
    userEmail = $("[name='email']").val();
    csrfToken = $("input[name='_csrf']").val();
    theUserId = $("[name='id']").val();
    params = {
        id: theUserId,
        email: userEmail,
        _csrf: csrfToken
    };
    $.post(url, params, function (res) {
        if (res == "OK") {
            form.submit();
        } else if (res == "Duplicated") {
            showModalDialog("Warning", "The email is already in use " + userEmail);
        } else {
            showModalDialog("Error", "Unknown response from server: ");
        }
    }).fail(function (xhr, status, error) {
        showModalDialog("Error", "Could not connect to server: " + error);
    });
    return false;
}

function showModalDialog(title, message) {
    $("#email").focus();
    $("#email").css("border-color", "#dc3545");
    $("#modalTitle").text(title);
    $("#modalBody").text(message);
    $("#modalDialog").modal();
}

function clearFilter() {
    window.location.href = "/ShopmeAdmin/users";
}

function searchUsers() {
    var keyword = $("#keyword").val();
    window.location.href = "/ShopmeAdmin/users/search?keyword=" + keyword;
}