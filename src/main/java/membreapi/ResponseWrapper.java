package membreapi;

public class ResponseWrapper {
    private boolean success;
    private String message;
    private Object data;

    public ResponseWrapper(boolean success, String message, Object data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    // Getters and setters
}
