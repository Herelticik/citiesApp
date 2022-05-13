package server.commands;

import lib.Answer;
import lib.interaction.Command;
import server.db.CollectionDatabaseCommander;

/**
 * Класс события удаления по Id
 */
public class RemoveByIdAction extends BinaryAction {

    @Override
    public Answer execute(Command command) {
        int id=Integer.parseInt(command.getParameter());
        CollectionDatabaseCommander.getInstance().checkElementAccess(id,command.getSender());
        CollectionDatabaseCommander.getInstance().removeById(id);
        manager.removeById(id);
        return notifyAboutResult("");
    }


}
