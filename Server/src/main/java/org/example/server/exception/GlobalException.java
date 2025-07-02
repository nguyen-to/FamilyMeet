package org.example.server.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalException {
    @ExceptionHandler(FamilyException.class)
    public ResponseEntity<String> handleFamilyException(FamilyException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("FamilyException: " + e.getMessage());
    }
    @ExceptionHandler(GroupException.class)
    public ResponseEntity<String> handleGroupException(GroupException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("GroupException: " + e.getMessage());
    }
    @ExceptionHandler(OwnershipException.class)
    public ResponseEntity<String> handleOwnershipException(OwnershipException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("OwnershipException: " + e.getMessage());
    }
}
