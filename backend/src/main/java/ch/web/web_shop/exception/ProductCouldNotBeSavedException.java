package ch.web.web_shop.exception;

/**
 * This Exception is thrown when there is a problem with saving a
 * {@link Question} object.
 * 
 * @author Patrick Meier
 *
 */
public class ProductCouldNotBeSavedException extends RuntimeException {
	public ProductCouldNotBeSavedException(String title) {
		super("The product with Title'" + title + "' could not be saved.");
	}
}
