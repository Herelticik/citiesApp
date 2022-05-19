package server.commands;

import lib.Answer;
import lib.element.CityBuilder;
import lib.interaction.Command;
import server.db.CollectionDatabaseCommander;
import lib.element.City;

import java.sql.SQLException;
import java.time.LocalDate;

/**
 * Класс события добавления в случае, если элемент наибольший
 */
public class AddIfMaxAction extends UnaryAction {

    @Override
    public Answer execute(Command command) {
        City element = command.getElement();
        if (element == null) {
            return requireElement();
        }
        if (element.getArea() > CollectionDatabaseCommander.getInstance().getMaxArea()) {
            CollectionDatabaseCommander.getInstance().insertElement(element);
        }
        manager.addIfMax(element);
        return notifyAboutResult("");
    }
}
