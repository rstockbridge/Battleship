final class ValidationResult {
    static ValidationResult validResult() {
        return new ValidationResult(true, null);
    }

    static ValidationResult invalidResult(String inputMessage) {
        return new ValidationResult(false, inputMessage);
    }

    private boolean isValid = true;
    private String message;

    private ValidationResult(boolean inputValidity, String inputMessage) {
        isValid = inputValidity;
        message = inputMessage;
    }

    boolean isValid() {
        return isValid;
    }

    String getMessage() {
        if (isValid) {
            throw new IllegalArgumentException("Valid results do not have a message.");
        } else {
            return message;
        }
    }
}