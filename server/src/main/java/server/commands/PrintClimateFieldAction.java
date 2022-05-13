package server.commands;

import lib.Answer;
import lib.interaction.Command;

/**
 * Класс события вывода поля климата всех файлов в порядке убывания
 */
public class PrintClimateFieldAction extends UnaryAction {
    @Override
    public Answer execute(Command command) {
        return notifyAboutResult(manager.printFieldDescendingClimate());
    }
}
