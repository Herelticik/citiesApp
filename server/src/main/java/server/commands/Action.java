package server.commands;

import lib.Answer;
import lib.AnswerType;
import lib.interaction.Command;
import server.CollectionManager;
import lib.element.City;

/**
 * Общий класс для всех событий,
 * запускаемых командами
 */
public abstract class Action {
    protected CollectionManager manager;
    /**
     *Содержит количество слов в команде
     */
    protected int numOfWords;

    public Answer requireElement(){
        return new Answer(AnswerType.NEED_ELEMENT,"");
    }

    public Answer notifyAboutResult(String message){
        return new Answer(AnswerType.RET_MESSAGE,message);
    }

    public abstract Answer execute(Command command);

    public void setManager(CollectionManager manager) {
        this.manager = manager;
    }


    public int getNumOfWords() {
        return numOfWords;
    }

}
