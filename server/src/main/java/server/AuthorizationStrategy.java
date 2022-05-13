package server;

import lib.Answer;
import lib.AnswerType;
import lib.Request;
import lib.exceptions.AuthorizationException;
import lib.exceptions.DatabaseElementException;
import server.db.UserDatabaseCommander;
import server.org.Server;

import java.util.logging.Logger;

public class AuthorizationStrategy implements RequestHandlerStrategy {
    private final Logger log = Logger.getLogger(Server.class.getName());

    @Override
    public Answer handle(Request request) {
        log.info("Авторизация пользователя: " + request.getUserData().getLogin());
        return executeRequest(request);
    }

    private Answer executeRequest(Request request) {
        Answer answer;
        try {
            UserDatabaseCommander.getInstance().checkUserPassword(request.getUserData());
            answer = new Answer(AnswerType.SUCCESSFULLY, "");
        } catch (DatabaseElementException | AuthorizationException e) {
            answer = new Answer(AnswerType.UNSUCCESSFULLY, e.getMessage());
        }
        return answer;
    }
}
