package server.commands;

import lib.Answer;
import lib.interaction.Command;
import server.db.CollectionDatabaseCommander;
import lib.element.City;

/**
 * Класс события удаления всех элементов больших прочитанного
 */
public class RemoveGreaterAction extends UnaryAction {
    @Override
    public Answer execute(Command command) {
        City element = command.getElement();
        if (element == null) {
            return requireElement();
        }
        CollectionDatabaseCommander.getInstance().removeGreater(element.getArea(),command.getSender());
        manager.removeGreater(element);
        return notifyAboutResult("");
    }
}
