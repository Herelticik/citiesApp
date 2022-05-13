package lib.exceptions;


/**
 * Класс ошибки, вылетающей при завершении работы клиентского приложения
 */
public class ExitException extends RuntimeException {
    public ExitException(String message) {
        super(message);
    }
}
