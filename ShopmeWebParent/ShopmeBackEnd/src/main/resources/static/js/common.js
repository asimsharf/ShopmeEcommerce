let MAX_FILE_SIZE = 10485760; // 10MB
let productModuleURL = "[[@{/products}]]";
let brandModuleURL = "[[@{/brands}]]";
let defaultFileImageSrc = "/images/image-thumbnail.png";
let extraImagesCount = 0;

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
            $(img).attr('src', e.target.result).width(200).height(200);
        }
        reader.readAsDataURL(fileInput.files[0]);
    }
}

const showModalDialog = (title, message) => {
    let email = $("#email");
    email.focus().css("border-color", "#dc3545");
    $("#modalTitle").text(title);
    $("#modalBody").text(message);
    $("#modalDialog").modal();
};

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
    $(".navbar .dropdown").hover(function () {
        $(this).find(".dropdown-menu").first().stop(true, true).delay(250).slideDown();
    }, function () {
        $(this).find(".dropdown-menu").first().stop(true, true).delay(100).slideUp();
    });
    $(".dropdown > a").click(function () {
        location.href = this.href;
    });
}

function checkPasswordMath(confirmPassword) {
    if (confirmPassword.value !== $("#password").val()) {
        confirmPassword.setCustomValidity("كلمة المرور غير متطابقة");
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
        checkFileSize(this);
        showThumbnail(this, "#thumbnail");
    });
}

function linkDelete() {
    $(".link-delete").on("click", function (e) {
        e.preventDefault();
        let link = $(this);
        let theID = link.attr("theID");
        $("#yesButton").attr("href", link.attr("href"));
        $("#confirmText").text("هل أنت متأكد أنك تريد حذف السجل رقم " + theID + "?");
        $("#confirmModal").modal();
    });
}

function checkEmailUnique(form) {
    let url = "/ShopmeAdmin/api/users/check_email";
    let userEmail = $("[name='email']").val();
    let csrfToken = $("input[name='_csrf']").val();
    let theUserId = $("[name='id']").val();
    let params = {
        id: theUserId, email: userEmail, _csrf: csrfToken
    };
    $.post(url, params, function (res) {
        if (res === "OK") {
            form.submit();
        } else if (res === "Duplicated") {
            showModalDialog("تحذير", "البريد الإلكتروني " + userEmail + " مستخدم بالفعل ");
        } else {
            showModalDialog("خطأ", "استجابة غير معروفة من الخادم");
        }
    }).fail(function (xhr, status, error) {
        showModalDialog("خطأ", "تعذر الاتصال بالخادم: " + error);
    });
    return false;
}

function checkCategoryAndAliasUnique(form) {
    let url = "/ShopmeAdmin/api/categories/check_unique";
    let categoryName = $("[name='name']").val();
    let aliasName = $("[name='alias']").val();
    let csrfToken = $("input[name='_csrf']").val();
    let theCategoryId = $("[name='id']").val();
    let params = {
        id: theCategoryId, name: categoryName, alias: aliasName, _csrf: csrfToken
    };
    $.post(url, params, function (res) {
        if (res === "OK") {
            form.submit();
        } else if (res === "DuplicateName" || res === "DuplicateAlias") {
            showModalDialog("تحذير", "اسم الفئة " + categoryName + " أو اسم الاسم المستعار " + aliasName + " مستخدم بالفعل ");
        } else {
            showModalDialog("خطأ", "استجابة غير معروفة من الخادم");
        }
    }).fail(function (xhr, status, error) {
        showModalDialog("خطأ", "تعذر الاتصال بالخادم: " + error);
    });
    return false;
}

function checkBrandUnique(form) {
    let url = "/ShopmeAdmin/api/brands/check_unique";
    let brandName = $("[name='name']").val();
    let csrfToken = $("input[name='_csrf']").val();
    let theBrandId = $("[name='id']").val();
    let params = {
        id: theBrandId, name: brandName, _csrf: csrfToken
    };
    $.post(url, params, function (res) {
        if (res === "OK") {
            form.submit();
        } else if (res === "DuplicateName") {
            showModalDialog("تحذير", "اسم العلامة التجارية " + brandName + " مستخدم بالفعل ");
        } else {
            showModalDialog("خطأ", "استجابة غير معروفة من الخادم");
        }
    }).fail(function (xhr, status, error) {
        showModalDialog("خطأ", "تعذر الاتصال بالخادم: " + error);
    });
    return false;
}

function checkProductUnique(form) {
    let url = "/ShopmeAdmin/api/products/check_unique";
    let productName = $("[name='name']").val();
    let productAlias = $("[name='alias']").val();
    let csrfToken = $("input[name='_csrf']").val();
    let theProductId = $("[name='id']").val();
    let params = {
        id: theProductId, name: productName, alias: productAlias, _csrf: csrfToken
    };
    $.post(url, params, function (res) {
        if (res === "OK") {
            form.submit();
        } else if (res === "DuplicateName" || res === "DuplicateAlias") {
            showModalDialog("تحذير", "اسم المنتج " + productName + " أو اسم الاسم المستعار " + productAlias + " مستخدم بالفعل ");
        } else {
            showModalDialog("خطأ", "استجابة غير معروفة من الخادم");
        }
    }).fail(function (xhr, status, error) {
        showModalDialog("خطأ", "تعذر الاتصال بالخادم: " + error);
    });
    return false;
}

function chosenCategories() {
    let url = "[[@{/brands}]]";

    $("#categories").change(function () {
        $("#chosenCategories").empty();

        $("#categories").children("option:selected").each(function () {
            let selectedCategory = $(this);
            let id = selectedCategory.val();
            let name = selectedCategory.text().replace(/-/g, "");

            $("#chosenCategories").append("<span class='badge badge-secondary m-1'>" + name + "</span>");
        })
    })
}

function activateTab() {
    $('.nav-tabs a').click(function () {
        $(this).tab('show');
    });
}

function showExtraImageThumbnail(fileInput, index) {
    let file = fileInput.files[0];
    let reader = new FileReader();
    reader.onload = function (e) {
        $("#extraThumbnail" + index).attr("src", e.target.result).width(200).height(200);
    }
    reader.readAsDataURL(file);

    if (index >= extraImagesCount - 1) {
        addNextExtraFileImageSection(index + 1);
    }


}

function addNextExtraFileImageSection(index) {
    let htmlExtraImage = `
        <div class="col border m-3 p-2" id="divExtraImage${index}">
            <div id="extraImageHeader${index}"><label>صورة إضافية للمنتج ${index + 1}:</label></div>
            <div class="m-2">
                <img id="extraThumbnail${index}" src="/ShopmeAdmin/images/image-thumbnail.png" alt="صورة إضافية للمنتج ${index + 1}:" class="img-fluid"  width="200" height="200"/>
            </div>
            <div class="m-2">
                <input type="file" name="extraFileImage" class="form-control" onchange="showExtraImageThumbnail(this, ${index})" accept="image/png, image/jpeg"/>
            </div>
        </div>
    `;

    let htmlRemove = `
        <a class="btn fas fa-times-circle fa-2x icon-dark float-left" href="javascript:removeExtraFileImageSection(${index-1})"></a>
    `;

    $("#divProductImages").append(htmlExtraImage);
    $("#extraImageHeader" + (index-1)).append(htmlRemove);

    extraImagesCount++;
}

function removeExtraFileImageSection(index) {
    $("#divExtraImage" + index).remove();
}

function getCategories() {
    let dropdownBrand = $('#brand');
    let dropdownCategory = $('#category');

    function fetchCategories(brandId) {
        let url = "/ShopmeAdmin/api/brands/" + brandId + "/categories";
        $.get(url, function (responseJson) {
            dropdownCategory.empty();
            $.each(responseJson, function (index, category) {
                dropdownCategory.append($('<option>').text(category.name).attr('value', category.id));
            });
        });
    }

    $("#shortDescription").richText();
    $("#fullDescription").richText();

    dropdownBrand.change(function () {
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


    $("input[name='extraFileImage']").each(function (index) {
        extraImagesCount++;
        $(this).change(function () {
            checkFileSize(this);
            showExtraImageThumbnail(this, index);
        });
    });

}

function checkFileSize(fileInput) {
    let fileSize = fileInput.files[0].size;
    if (fileSize > MAX_FILE_SIZE) {
        $("#modalTitle").text("تحذير");
        $("#modalBody").text("حجم الملف يجب أن يكون أقل من 10 ميغابايت. الرجاء تحميل ملف أصغر");
        $("#modalDialog").modal();
        fileInput.value = "";
        fileInput.reportValidity();
        return false;
    }
    return true;
}