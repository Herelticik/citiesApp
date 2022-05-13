package server;

import server.db.UserDatabaseCommander;
import lib.Answer;
import lib.Request;

import java.sql.SQLException;
import java.util.concurrent.ForkJoinPool;

public class ServerRequestHandler {

    ForkJoinPool pool = new ForkJoinPool(10);

    public ServerRequestHandler() throws SQLException {
        UserDatabaseCommander.getInstance().createTableIfNotExist();
    }

    public void handle() {
        InteractionServer server = new InteractionServer();
        Request request = server.getRequest();
        try {
            server.sendAnswer(getAnswer(request));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Answer getAnswer(Request request) throws SQLException {
        RequestHandlerStrategy answerHandler = null;
        switch (request.getRequestType()) {
            case COMMAND_EXECUTION:
                answerHandler = new CommandExecuteStrategy();
                break;
            case REGISTRATION_REQUEST:
                answerHandler = new RegistrationStrategy();
                break;
            case AUTHORIZATION_REQUEST:
                answerHandler = new AuthorizationStrategy();
                break;
            case PASSWORD_RECOVER_REQUEST:
                answerHandler = new RecoverPasswordStrategy();
                break;
        }
        return answerHandler.handle(request);
    }
}
