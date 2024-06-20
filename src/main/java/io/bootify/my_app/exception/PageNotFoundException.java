package io.bootify.my_app.exception;

<<<<<<< HEAD
public class PageNotFoundException extends Throwable {
    public PageNotFoundException(String pageNotFound) {
        super(pageNotFound);
    }
}
=======


public class PageNotFoundException extends RuntimeException {
    public PageNotFoundException(String message) {
        super(message);
    }
}
>>>>>>> origin/master
