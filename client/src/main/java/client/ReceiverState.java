package client;

import lib.Answer;

import java.io.IOException;

public class ReceiverState implements InteractionStrategy {


    @Override
    public StateConfiguration handleAnswer(ConsoleReader reader, InteractionClient client) throws IOException, ClassNotFoundException {
        Answer answer = client.getAnswer();
        switch (answer.getType()) {
            case DIALOG_STATE:
                return new StateConfiguration(answer.getMessage(), new DialogStrategy());
            case RET_MESSAGE:
                break;

        }
        return new StateConfiguration(answer.getMessage(), new ReceiverState());
    }
}
