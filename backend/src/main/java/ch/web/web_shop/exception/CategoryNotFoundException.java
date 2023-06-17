
package ch.web.web_shop.exception;

public class CategoryNotFoundException extends RuntimeException {
	public CategoryNotFoundException(String categoryNotFound) {
		super("The category with id  could not be found.");
	}
}
