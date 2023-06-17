
package ch.web.web_shop.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

	@Override
	public ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
																	   HttpHeaders headers, HttpStatus status, WebRequest request) {
		return createDefaultErrorResponse(ex.getMessage());
	}

	@Override
	public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
															   HttpHeaders headers, HttpStatus status, WebRequest request) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach(error -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});

		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(CategoryLoadException.class)
	public ResponseEntity<Object> handleCategoryLoadException(CategoryLoadException ex, WebRequest request) {
		return createDefaultErrorResponse(ex.getMessage());
	}

	@ExceptionHandler(CategoryNotFoundException.class)
	public ResponseEntity<Object> handleCategoryNotFoundException(CategoryNotFoundException ex, WebRequest request) {
		return createDefaultErrorResponse(ex.getMessage());
	}

	@ExceptionHandler(ProductLoadException.class)
	public ResponseEntity<Object> handleProductLoadException(ProductLoadException ex, WebRequest request) {
		return createDefaultErrorResponse(ex.getMessage());
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<Object> handleProductNotFoundException(ResourceNotFoundException ex, WebRequest request) {
		return createDefaultErrorResponse(ex.getMessage());
	}

	@ExceptionHandler(ProductCouldNotBeSavedException.class)
	public ResponseEntity<Object> handleQuestionCouldNotBeSavedException(ProductCouldNotBeSavedException ex,
			WebRequest request) {
		return createDefaultErrorResponse(ex.getMessage());
	}

	private ResponseEntity<Object> createDefaultErrorResponse(String exceptionMessage) {
		Map<String, String> errors = new HashMap<>();
		errors.put("error", exceptionMessage);

		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}



}
