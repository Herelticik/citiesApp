package lib.exceptions;

/**
 * Класс ошибки ввода переменной окружения
 */
public class EnvException extends RuntimeException{
    public EnvException(String message){
        super(message);
    }
}
