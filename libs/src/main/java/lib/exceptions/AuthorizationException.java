package lib.exceptions;

/**
 * Класс ошибки при авторизации(неправильный пароль, несуществующий аккаунт)
 */

public class AuthorizationException extends RuntimeException {
    public AuthorizationException(String message) {
        super(message);
    }
}
