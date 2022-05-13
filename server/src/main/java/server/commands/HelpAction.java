package server.commands;

import lib.Answer;
import lib.interaction.Command;

/**
 * Класс события вывода всех команд
 */
public class HelpAction extends UnaryAction {
    @Override
    public Answer execute(Command command) {
        return notifyAboutResult(manager.help());
    }
}
