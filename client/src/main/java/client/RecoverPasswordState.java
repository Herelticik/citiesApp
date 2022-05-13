package client;

import lib.*;

import java.io.IOException;

public class RecoverPasswordState implements InteractionStrategy{
    @Override
    public StateConfiguration handleAnswer(ConsoleReader reader, InteractionClient client) throws IOException, ClassNotFoundException {
        String login;
        System.out.println("Введите логин:");
        while (true) {
            login = reader.readLine();
            break;
        }
        UserData userData=new UserData();
        userData.setLogin(login);
        Request request=new Request(RequestType.PASSWORD_RECOVER_REQUEST);
        request.setUserData(userData);
        client.sendRequest(request);
        Answer answer=client.getAnswer();
        if (answer.getType()== AnswerType.SUCCESSFULLY){
            return new StateConfiguration("",new AuthorizationState());
        }
        else {
            return new StateConfiguration(answer.getMessage(),new RecoverPasswordState());
        }

    }
}
