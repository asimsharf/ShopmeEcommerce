$(document).ready(function () {
    keyword()
    customizeDropdown();
    logoutLink();
    fileImage();
    linkDelete();
    chosenCategories();
    activateTab();
    getCategories();
});

function showThumbnail(fileInput, img) {
    if (fileInput.files && fileInput.files[0]) {
        const reader = new FileReader();
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
        if (e.keyCode === 13) {
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
    if (confirmPassword.value !== $("#password").val()) {
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
        let link = $(this);
        let  theID = link.attr("theID");
        $("#yesButton").attr("href", link.attr("href"));
        $("#confirmText").text("Are you sure you want to delete this user with ID " + theID + "?");
        $("#confirmModal").modal();
    });
}

function checkEmailUnique(form) {
    let url = "/ShopmeAdmin/api/users/check_email";
    let  userEmail = $("[name='email']").val();
    let  csrfToken = $("input[name='_csrf']").val();
    let  theUserId = $("[name='id']").val();
    let params = {
        id: theUserId,
        email: userEmail,
        _csrf: csrfToken
    };
    $.post(url, params, function (res) {
        if (res === "OK") {
            form.submit();
        } else if (res === "Duplicated") {
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
    let url = "/ShopmeAdmin/api/categories/check_unique";
    let categoryName = $("[name='name']").val();
    let aliasName = $("[name='alias']").val();
    let csrfToken = $("input[name='_csrf']").val();
    let  theCategoryId = $("[name='id']").val();
    let  params = {
        id: theCategoryId,
        name: categoryName,
        alias: aliasName,
        _csrf: csrfToken
    };
    $.post(url, params, function (res) {
        if (res === "OK") {
            form.submit();
        } else if (res === "DuplicateName" || res === "DuplicateAlias") {
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
    let url = "/ShopmeAdmin/api/brands/check_unique";
    let  brandName = $("[name='name']").val();
    let csrfToken = $("input[name='_csrf']").val();
    let  theBrandId = $("[name='id']").val();
    let params = {
        id: theBrandId,
        name: brandName,
        _csrf: csrfToken
    };
    $.post(url, params, function (res) {
        if (res === "OK") {
            form.submit();
        } else if (res === "DuplicateName") {
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
    let url = "[[@{/brands}]]";

    $("#categories").change(function(){
        $("#chosenCategories").empty();

        $("#categories").children("option:selected").each(function(){
            let selectedCategory = $(this);
            let  id = selectedCategory.val();
            let  name = selectedCategory.text().replace(/-/g, "");

            $("#chosenCategories").append("<span class='badge badge-secondary m-1'>" + name + "</span>");
        })
    })
}

function activateTab(){
    $('.nav-tabs a').click(function(){
        $(this).tab('show');
    });
}

function getCategories() {
    let dropdownBrand = $('#brand');
    let dropdownCategory = $('#category');

    function fetchCategories(brandId) {
        let url = "/ShopmeAdmin/api/brands/" + brandId + "/categories";
        $.get(url, function(responseJson) {
            dropdownCategory.empty();
            $.each(responseJson, function (index, category) {
                dropdownCategory.append($('<option>').text(category.name).attr('value', category.id));
            });
        });
    }

    dropdownBrand.change(function(){
        let brandId = $(this).val();
        if (brandId) {
            fetchCategories(brandId);
        } else {
            dropdownCategory.empty();
        }
    });

    let initialBrandId = dropdownBrand.val();
    if (initialBrandId) {
        fetchCategories(initialBrandId);
    }
}
