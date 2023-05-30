package ua.nure.shoestore;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ua.nure.shoestore.dao.DBException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DBException.class)
    public ResponseEntity<String> handleDBException(DBException ex) {
        String errorMessage = "Database operation failed: " + ex.getMessage();
        return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

