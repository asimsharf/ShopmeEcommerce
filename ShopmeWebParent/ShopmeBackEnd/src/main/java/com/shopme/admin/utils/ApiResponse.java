package com.shopme.admin.utils;

public record ApiResponse(boolean success, String message, Object data) {

}
