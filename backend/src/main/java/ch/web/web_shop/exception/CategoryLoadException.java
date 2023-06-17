
package ch.web.web_shop.exception;

public class CategoryLoadException extends RuntimeException {
	public CategoryLoadException(String categoryLoadFailed) {
		super("Categories could not be loaded.");
	}
}
