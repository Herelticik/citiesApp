package lib;

import java.io.Serializable;

public class Answer implements Serializable {
    private AnswerType type;
    String message;

    public Answer(AnswerType type, String message) {
        this.type = type;
        this.message = message;
    }

    public AnswerType getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }


}
