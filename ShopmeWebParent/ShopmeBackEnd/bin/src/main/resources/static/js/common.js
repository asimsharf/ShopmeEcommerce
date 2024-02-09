$(document).ready(function () {
    keyword()
    customizeDropdown();
    logoutLink();
    fileImage();
    linkDelete();
    chosenCategories();
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

function showModalDialog(title, message) {
    $("#email").focus();
    $("#email").css("border-color", "#dc3545");
    $("#modalTitle").text(title);
    $("#modalBody").text(message);
    $("#modalDialog").modal();
}

function clearFilter(page) {
    window.location.href = page;
}


function keyword() {
    $("#keyword").on("keyup", function (e) {
        if (e.keyCode == 13) {
            searchUsers();
        }
    });
}

function searchUsers() {
    window.location.href = "/ShopmeAdmin/users/search?keyword=" + $("#keyword").val();
}

function customizeDropdown() {
    $(".navbar .dropdown").hover(
        function () {
            $(this).find(".dropdown-menu").first().stop(true, true).delay(250).slideDown();
        },
        function () {
            $(this).find(".dropdown-menu").first().stop(true, true).delay(100).slideUp();
        }
    );
    $(".dropdown > a").click(function () {
        location.href = this.href;
    });
}

function checkPasswordMath(confirmPassword) {
    if (confirmPassword.value != $("#password").val()) {
        confirmPassword.setCustomValidity("Passwords do not match");
    } else {
        confirmPassword.setCustomValidity("");
    }
}

function theBtnCancel(page) {
    window.location.href = page;
}


function logoutLink() {
    $("#logoutLink").click(function (event) {
        console.log("Logout clicked");
        event.preventDefault();
        $("#logoutForm").submit();
        console.log("Logout form submitted");
    });
}

function fileImage() {
    $("#fileImage").change(function () {
        let fileSize = this.files[0].size;
        if (fileSize > 10485760) {
            $("#modalTitle").text("Warning");
            $("#modalBody").text("The file size must not exceed 10MB.");
            $("#modalDialog").modal();
            this.value = "";
            this.reportValidity();
            return false;
        }
        showThumbnail(this, "#thumbnail");
    });
}

function linkDelete() {
    $(".link-delete").on("click", function (e) {
        e.preventDefault();
        link = $(this);
        theID = link.attr("theID");
        $("#yesButton").attr("href", link.attr("href"));
        $("#confirmText").text("Are you sure you want to delete this user with ID " + theID + "?");
        $("#confirmModal").modal();
    });
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

function checkCategoryAndAliasUnique(form) {
    url = "/ShopmeAdmin/api/categories/check_unique";
    categoryName = $("[name='name']").val();
    aliasName = $("[name='alias']").val();
    csrfToken = $("input[name='_csrf']").val();
    theCategoryId = $("[name='id']").val();
    params = {
        id: theCategoryId,
        name: categoryName,
        alias: aliasName,
        _csrf: csrfToken
    };
    $.post(url, params, function (res) {
        if (res == "OK") {
            form.submit();
        } else if (res == "DuplicateName" || res == "DuplicateAlias") {
            showModalDialog("Warning", "The Category name " + categoryName + " or Alias name " + aliasName + " is already in use ");
        } else {
            showModalDialog("Error", "Unknown response from server: ");
        }
    }).fail(function (xhr, status, error) {
        showModalDialog("Error", "Could not connect to server: " + error);
    });
    return false;
}

function checkBrandUnique(form) {
    url = "/ShopmeAdmin/api/brands/check_unique";
    brandName = $("[name='name']").val();
    csrfToken = $("input[name='_csrf']").val();
    theBrandId = $("[name='id']").val();
    params = {
        id: theBrandId,
        name: brandName,
        _csrf: csrfToken
    };
    $.post(url, params, function (res) {
        if (res == "OK") {
            form.submit();
        } else if (res == "DuplicateName") {
            showModalDialog("Warning", "The Brand name " + brandName + " is already in use ");
        } else {
            showModalDialog("Error", "Unknown response from server");
        }
    }).fail(function (xhr, status, error) {
        showModalDialog("Error", "Could not connect to server: " + error);
    });
    return false;
}


function chosenCategories(){
    url = "[[@{/brands}]]";

    $("#categories").change(function(){
        $("#chosenCategories").empty();

        $("#categories").children("option:selected").each(function(){
            selectedCategory = $(this);
            id = selectedCategory.val();
            name = selectedCategory.text().replace(/-/g, "");

            $("#chosenCategories").append("<span class='badge badge-secondary m-1'>" + name + "</span>");
        })
    })

}
