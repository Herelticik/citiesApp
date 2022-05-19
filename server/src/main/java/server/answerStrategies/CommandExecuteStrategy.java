package server.answerStrategies;

import lib.Answer;
import lib.AnswerType;
import lib.Request;
import lib.UserData;
import lib.exceptions.*;
import lib.interaction.Command;
import server.commands.ActionInvoker;
import server.db.UserDatabaseCommander;
import server.org.Server;

import java.util.logging.Logger;

public class CommandExecuteStrategy implements RequestHandlerStrategy {
    private final Logger log = Logger.getLogger(Server.class.getName());

    @Override
    public Answer handle(Request request) {
        checkAuthorization(request.getUserData());
        Command command = request.getCommand();
        return executeCommand(command);
    }

    private Answer executeCommand(Command command) {
        Answer answer;
        log.info("Обработка команды: " + command.getName());
        try {
            answer = ActionInvoker.getInstance().execute(command);
        } catch (IncorrectCommandException | IncorrectArgumentException | DatabaseElementException |
                 ExecuteScriptException e) {
            answer = new Answer(AnswerType.RET_MESSAGE, e.getMessage());
            log.warning("Команда " + command.getName() + " не выполнена. Ошибка: " + e.getMessage() + " Аргумент: " + command.getParameter());
        } catch (IllegalArgumentException e) {
            answer = new Answer(AnswerType.RET_MESSAGE, "Аргумент команды некорректен!");
            log.warning("Аргумент команды " + command.getName() + " некорректен. Введено: " + command.getParameter());
        }
        return answer;
    }

    private void checkAuthorization(UserData userData) {
        UserDatabaseCommander.getInstance().checkUserPassword(userData);
    }
}
