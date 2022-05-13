package lib.exceptions;

/**
 * Класс ошибки работы с элементом из базы данных(например, id элемента не найден)
 */

public class DatabaseElementException extends RuntimeException {
    public DatabaseElementException(String message) {
        super(message);
    }
}
