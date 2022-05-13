package lib.exceptions;


/**
 * Класс ошибки ввода команды
 */
public class IncorrectCommandException extends RuntimeException {
    /**
     * Выброс текста ошибки
     *
     * @param message Текст ошибки
     */
    public IncorrectCommandException(String message) {
        super(message);
    }
}
