package server.commands;


import lib.interaction.Command;
import server.db.CollectionDatabaseCommander;

import lib.Answer;
import lib.element.City;


/**
 * Класс события обновления элемента по id
 */
public class UpdateAction extends BinaryAction {


    @Override
    public Answer execute(Command command) {
        int id = getId(command);
        City element = command.getElement();
        CollectionDatabaseCommander.getInstance().checkElementAccess(id, command.getSender());
        if (element == null) {
            return requireElement();
        }
        CollectionDatabaseCommander.getInstance().updateElement(id, element);
        manager.update(id, element);
        return notifyAboutResult("");
    }


    private int getId(Command command) {
        return Integer.parseInt(command.getParameter());
    }
}
