package server.commands;

import lib.Answer;
import lib.interaction.Command;
import server.db.CollectionDatabaseCommander;
import lib.element.City;

import java.sql.SQLException;
import java.time.LocalDate;

/**
 * Класс события добавления
 */
public class AddAction extends UnaryAction {
    @Override
    public Answer execute(Command command) {
        City element = command.getElement();
        if (element == null) {
            return requireElement();
        }
        element = CollectionDatabaseCommander.getInstance().insertElement(element);
        manager.add(element);
        return notifyAboutResult("");
    }
}
