package server;

import lib.Answer;
import lib.AnswerType;
import lib.Request;
import lib.exceptions.MailException;
import server.db.UserDatabaseCommander;
import server.org.Server;
import java.util.logging.Logger;

public class RecoverPasswordStrategy implements RequestHandlerStrategy {
    private final Logger log = Logger.getLogger(Server.class.getName());

    @Override
    public Answer handle(Request request) {
        Answer answer;
        log.info("Восстановление пароля пользователя: " + request.getUserData().getLogin());
        try {
            String password = generateRandomPassword();
            UserDatabaseCommander.getInstance().updatePassword(request.getUserData(), password);
            EmailMessageSender.getInstance().send(UserDatabaseCommander.getInstance().getEmail(request.getUserData()), password);
            answer = new Answer(AnswerType.SUCCESSFULLY, "");
        } catch (MailException e) {
            answer = new Answer(AnswerType.UNSUCCESSFULLY, e.getMessage());
        }
        return answer;
    }

    private String generateRandomPassword() {
        int PASSWORD_LENGTH = 12;
        byte[] val = new byte[PASSWORD_LENGTH];
        for (int i = 0; i < val.length; i++) {
            val[i] = (byte) (Math.random() * 93 + 33);
        }
        return new String(val);
    }
}
