package ua.nure.shoestore.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

public class ErrorUtil {

    private ErrorUtil() {}

    public static ResponseEntity<Object> getErrorsResponseEntity(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>(); // If there are validation errors, return a response with the field errors
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                errors.put(fieldError.getField(), fieldError.getDefaultMessage()); // Map<field, message>
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }
        return null;
    }
}
