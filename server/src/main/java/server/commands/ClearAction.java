package server.commands;

import lib.Answer;
import lib.interaction.Command;
import server.db.CollectionDatabaseCommander;

/**
 * Класс события очистки коллекции
 */
public class ClearAction extends UnaryAction {
    @Override
    public Answer execute(Command command) {
        CollectionDatabaseCommander.getInstance().clear(command.getSender());
        manager.clear(command.getSender());
        return notifyAboutResult("");
    }
}
