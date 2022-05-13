package lib.exceptions;

/**
 * Класс ошибки ввода аргумента команды
 */
public class IncorrectArgumentException extends RuntimeException {
    public IncorrectArgumentException(String message) {
        super(message);
    }
}
