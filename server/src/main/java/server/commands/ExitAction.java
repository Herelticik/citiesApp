package server.commands;

import lib.Answer;
import lib.AnswerType;
import lib.interaction.Command;

/**
 * Класс события выхода из программы
 */
public class ExitAction extends UnaryAction {
    @Override
    public Answer execute(Command command) {
        return new Answer(AnswerType.EXIT,"");
    }
}
