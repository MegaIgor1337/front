package forwarding.agent.api.exceptions;

public class ContactsDoNotExistException extends RuntimeException {
    public ContactsDoNotExistException(String message) {
        super(message);
    }
}
