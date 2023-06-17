package ch.web.web_shop.controller;

import ch.web.web_shop.exception.*;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ControllerAdvisorTest {

    @Test
    void handleMissingServletRequestParameter_ReturnsErrorResponse() {
        ControllerAdvisor advisor = new ControllerAdvisor();
        MissingServletRequestParameterException ex = new MissingServletRequestParameterException("param", "String");
        HttpHeaders headers = new HttpHeaders();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        WebRequest request = new ServletWebRequest(new MockHttpServletRequest());
        ResponseEntity<Object> response = advisor.handleMissingServletRequestParameter(ex, headers, status, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Map<String, String> expectedError = new HashMap<>();
        expectedError.put("error", "Required request parameter 'param' for method parameter type String is not present");
        assertEquals(expectedError, response.getBody());
    }

    @Test
    void handleCategoryLoadException_ReturnsErrorResponse() {
        ControllerAdvisor advisor = new ControllerAdvisor();
        CategoryLoadException ex = new CategoryLoadException("Category load failed");
        WebRequest request = new ServletWebRequest(new MockHttpServletRequest());
        ResponseEntity<Object> response = advisor.handleCategoryLoadException(ex, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Map<String, String> expectedError = new HashMap<>();
        expectedError.put("error", "Categories could not be loaded.");
        assertEquals(expectedError, response.getBody());
    }

    @Test
    void handleCategoryNotFoundException_ReturnsErrorResponse() {
        ControllerAdvisor advisor = new ControllerAdvisor();
        CategoryNotFoundException ex = new CategoryNotFoundException("Category not found");
        WebRequest request = new ServletWebRequest(new MockHttpServletRequest());
        ResponseEntity<Object> response = advisor.handleCategoryNotFoundException(ex, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Map<String, String> expectedError = new HashMap<>();
        expectedError.put("error", "The category with id  could not be found.");
        assertEquals(expectedError, response.getBody());
    }

    @Test
    void handleProductLoadException_ReturnsErrorResponse() {
        ControllerAdvisor advisor = new ControllerAdvisor();
        ProductLoadException ex = new ProductLoadException("Product load failed");
        WebRequest request = new ServletWebRequest(new MockHttpServletRequest());
        ResponseEntity<Object> response = advisor.handleProductLoadException(ex, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Map<String, String> expectedError = new HashMap<>();
        expectedError.put("error", "Products could not be loaded.");
        assertEquals(expectedError, response.getBody());
    }

    @Test
    void handleProductNotFoundException_ReturnsErrorResponse() {
        ControllerAdvisor advisor = new ControllerAdvisor();
        ResourceNotFoundException ex = new ResourceNotFoundException("Product not found");
        WebRequest request = new ServletWebRequest(new MockHttpServletRequest());
        ResponseEntity<Object> response = advisor.handleProductNotFoundException(ex, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Map<String, String> expectedError = new HashMap<>();
        expectedError.put("error", "Product not found");
        assertEquals(expectedError, response.getBody());
    }

    @Test
    void handleProductCouldNotBeSavedException_ReturnsErrorResponse() {
        ControllerAdvisor advisor = new ControllerAdvisor();
        ProductCouldNotBeSavedException ex = new ProductCouldNotBeSavedException("Product could not be saved");
        WebRequest request = new ServletWebRequest(new MockHttpServletRequest());
        ResponseEntity<Object> response = advisor.handleQuestionCouldNotBeSavedException(ex, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Map<String, String> expectedError = new HashMap<>();
        expectedError.put("error", "The product with Title'Product could not be saved' could not be saved.");
        assertEquals(expectedError, response.getBody());
    }
}
