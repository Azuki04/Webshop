package ch.web.web_shop.payload.response;

// MessageResponse is used to map the JSON response body to a Java object.
public class MessageResponse {
    private String message;

    public MessageResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}