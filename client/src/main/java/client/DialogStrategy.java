package client;

import lib.Answer;
import lib.Request;
import lib.RequestType;
import lib.element.City;
import lib.exceptions.ExitException;
import lib.interaction.Command;

import java.io.IOException;

public class DialogStrategy implements InteractionStrategy {
    @Override
    public StateConfiguration handleAnswer(ConsoleReader reader, InteractionClient client) throws IOException, ClassNotFoundException {
        Command command = reader.readCommand();
        Request request=new Request(RequestType.COMMAND_EXECUTION,command);
        client.sendRequest(request);
        Answer answer = client.getAnswer();
        switch (answer.getType()) {
            case EXIT:
                throw new ExitException("Exit");
            case RET_MESSAGE:
                if (!answer.getMessage().equals("")) {
                    return new StateConfiguration(answer.getMessage(),new DialogStrategy());
                }
                break;
            case NEED_ELEMENT:
                City city = reader.readElement();
                command.setElement(city);
                request=new Request(RequestType.COMMAND_EXECUTION,command);
                client.sendRequest(request);
                answer = client.getAnswer();
                return new StateConfiguration(answer.getMessage(),new DialogStrategy());
            case GETTER_STATE:
                return new StateConfiguration("",new ReceiverState());
        }
        return new StateConfiguration(answer.getMessage(),new DialogStrategy());
    }
}
