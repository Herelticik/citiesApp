package lib.exceptions;

/**
 * Класс ошибки отправки сообщения юзеру
 */
public class MailException extends RuntimeException {
    public MailException(String message) {
        super(message);
    }
}
