package server.commands;

import lib.Answer;
import lib.interaction.Command;
import server.db.CollectionDatabaseCommander;

/**
 * Класс события удаления первого элемента с таким уровнем над водой
 */
public class RemoveBySeaLevelAction extends BinaryAction {
    @Override
    public Answer execute(Command command) {
        long seaLevel = Long.parseLong(command.getParameter());
        int id = CollectionDatabaseCommander.getInstance().removeAnyBySeaLevel(seaLevel, command.getSender());
        manager.removeById(id);
        return notifyAboutResult("");
    }
}


