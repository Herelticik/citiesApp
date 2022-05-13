package server.commands;

import lib.Answer;
import lib.interaction.Command;

/**
 * Класс события вывода коллекции
 */
public class ShowAction extends UnaryAction {
    @Override
    public Answer execute(Command command) {
        return notifyAboutResult(manager.show());
    }
}
