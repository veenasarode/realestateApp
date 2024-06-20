package io.bootify.my_app.exception;

public class PageNotFoundException extends Throwable {
    public PageNotFoundException(String pageNotFound) {
        super(pageNotFound);
    }
}
