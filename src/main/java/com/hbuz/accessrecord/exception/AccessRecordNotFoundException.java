package com.hbuz.accessrecord.exception;

public class AccessRecordNotFoundException extends RuntimeException {

    public AccessRecordNotFoundException(String id) {
        super("Access record " +id+ " not found");
    }
}
