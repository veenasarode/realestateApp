package io.bootify.my_app.exception;

public class RentPersonNotFoundException extends RuntimeException{

    public RentPersonNotFoundException(String message) {
        super(message);
    }
}
