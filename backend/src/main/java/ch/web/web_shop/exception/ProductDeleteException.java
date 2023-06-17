package ch.web.web_shop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ProductDeleteException extends RuntimeException {

    public ProductDeleteException() {
        super("Failed to delete product.");
    }
}
