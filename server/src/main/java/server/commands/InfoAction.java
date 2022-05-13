package server.commands;

import lib.Answer;
import lib.interaction.Command;

/**
 * Класс события вывода информации о коллекции
 */
public class InfoAction extends UnaryAction {
    @Override
    public Answer execute(Command command) {
        return notifyAboutResult(manager.info());
    }
}
