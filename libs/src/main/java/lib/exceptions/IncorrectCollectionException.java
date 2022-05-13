package lib.exceptions;

/**
 * Класс ошибки представления коллекции
 */
public class IncorrectCollectionException extends RuntimeException {
    public IncorrectCollectionException(String message) {
        super(message);
    }
}
