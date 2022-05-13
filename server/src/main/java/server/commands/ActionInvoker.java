package server.commands;

import lib.Answer;
import lib.UserData;
import lib.exceptions.IncorrectArgumentException;
import server.CollectionManager;
import lib.exceptions.IncorrectCommandException;
import lib.interaction.Command;

import java.util.ArrayDeque;
import java.util.HashMap;

/**
 * Класс запуска события
 */
public class ActionInvoker {

    private final HashMap<String, Action> commands = new HashMap<>();
    public static ActionInvoker invoker;
    private CollectionManager manager;


    private ActionInvoker() {
    }


    public static ActionInvoker getInstance() {
        if (invoker == null) {
            invoker = new ActionInvoker();
            invoker.setManager(new CollectionManager());
        }
        return invoker;
    }

    public void setManager(CollectionManager manager) {
        this.manager = manager;
        invoker.addCommand("help", new HelpAction());
        invoker.addCommand("add", new AddAction());
        invoker.addCommand("show", new ShowAction());
        invoker.addCommand("add_if_max", new AddIfMaxAction());
        invoker.addCommand("clear", new ClearAction());
        invoker.addCommand("info", new InfoAction());
        invoker.addCommand("update", new UpdateAction());
        invoker.addCommand("print_field_descending_climate", new PrintClimateFieldAction());
        invoker.addCommand("remove_all_by_climate", new RemoveAllByClimate());
        invoker.addCommand("remove_by_id", new RemoveByIdAction());
        invoker.addCommand("remove_any_by_meters_above_sea_level", new RemoveBySeaLevelAction());
        invoker.addCommand("remove_greater", new RemoveGreaterAction());
        invoker.addCommand("execute_script", new ExecuteScriptAction());
        invoker.addCommand("exit", new ExitAction());
    }

    public void addCommand(String name, Action action) {
        action.setManager(manager);
        commands.put(name, action);
    }


    public Answer execute(Command command) {
        checkCommandExistence(command);
        String commandName = command.getName();
        Action action = getAction(commandName);
        return action.execute(command);
    }

    public Action getAction(String commandName) {
        return commands.get(commandName);
    }


    public void checkCommandExistence(Command command) {
        String commandName = command.getName();

        if (!commands.containsKey(commandName)) {
            throw new IncorrectCommandException("Команды не существует!");
        }
        if (!isCorrectCommandForm(command)) {
            throw new IncorrectArgumentException("Команда не может содержать такой аргумент!");
        }

    }

    private boolean isCorrectCommandForm(Command command) {
        return isUnaryCommand(command) || isBinaryCommand(command);
    }


    private boolean isUnaryCommand(Command command) {
        return isSecondParameterNull(command) && getNumOfParameters(command) == 1;
    }

    private boolean isBinaryCommand(Command command) {
        return !isSecondParameterNull(command) && getNumOfParameters(command) == 2;
    }


    private int getNumOfParameters(Command command) {
        return getAction(command.getName()).getNumOfWords();
    }

    private boolean isSecondParameterNull(Command command) {
        return command.getParameter() == null;
    }


}
