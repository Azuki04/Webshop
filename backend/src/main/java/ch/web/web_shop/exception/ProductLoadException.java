
package ch.web.web_shop.exception;

public class ProductLoadException extends RuntimeException {
	public ProductLoadException(String productLoadFailed) {
		super("Products could not be loaded.");
	}

	public ProductLoadException(Long categoryId) {
		super("Questions for category with id '" + categoryId + "' could not be loaded");
	}
}
