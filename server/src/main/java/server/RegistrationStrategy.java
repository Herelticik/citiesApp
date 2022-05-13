package server;

import lib.Answer;
import lib.AnswerType;
import lib.Request;
import lib.exceptions.AuthorizationException;
import lib.exceptions.MailException;
import server.db.UserDatabaseCommander;
import server.org.Server;

import java.sql.SQLException;
import java.util.logging.Logger;

public class RegistrationStrategy implements RequestHandlerStrategy {
    private final Logger log = Logger.getLogger(Server.class.getName());

    @Override
    public Answer handle(Request request) throws SQLException {
        log.info("Регистрация пользователя: " + request.getUserData().getLogin());
        return executeRequest(request);
    }

    private Answer executeRequest(Request request) throws SQLException {
        Answer answer;
        try {
            EmailMessageSender.getInstance().send(request.getUserData().getEmail(),"Почта подтверждена!");
            UserDatabaseCommander.getInstance().insertElement(request.getUserData());
            answer = new Answer(AnswerType.SUCCESSFULLY, "");
        } catch (MailException | AuthorizationException e) {
            answer = new Answer(AnswerType.UNSUCCESSFULLY, e.getMessage());
        }
        return answer;
    }
}
