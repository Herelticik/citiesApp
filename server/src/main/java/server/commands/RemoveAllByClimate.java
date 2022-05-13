package server.commands;

import lib.Answer;
import lib.interaction.Command;
import server.db.CollectionDatabaseCommander;
import lib.element.Climate;

import java.util.Locale;

/**
 * Класс события удаления элементов коллекции с заданным климатом
 */
public class RemoveAllByClimate extends BinaryAction {
    @Override
    public Answer execute(Command command) {
        Climate climate=Climate.valueOf(command.getParameter().toUpperCase(Locale.ROOT));
        CollectionDatabaseCommander.getInstance().removeByClimate(climate,command.getSender());
        manager.removeAllByClimate(climate,command.getSender());
        return notifyAboutResult("");
    }



}
