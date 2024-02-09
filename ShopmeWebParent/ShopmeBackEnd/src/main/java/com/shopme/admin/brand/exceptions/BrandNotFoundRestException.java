package com.shopme.admin.brand.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Brand not found")
public class BrandNotFoundRestException extends Exception {

    public BrandNotFoundRestException(String message) {
        super(message);
    }
}
