package lib;

import java.io.Serializable;

public enum AnswerType implements Serializable {
    SUCCESSFULLY,
    UNSUCCESSFULLY,
    NEED_ELEMENT,
    RET_MESSAGE,
    GETTER_STATE,
    DIALOG_STATE,
    EXIT
}
