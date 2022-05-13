package client;

import lib.*;

import java.io.IOException;

public class RegistrationState implements InteractionStrategy {
    @Override
    public StateConfiguration handleAnswer(ConsoleReader reader, InteractionClient client) throws IOException, ClassNotFoundException {
        String login;
        String password;
        String email;
        System.out.println("Регистрация:");
        System.out.println("Введите логин:");
        while (true) {
            login = reader.readLine();
            break;
        }
        System.out.println("Введите пароль:");
        while (true) {
            password = reader.readLine();
            break;
        }
        System.out.println("Введите email:");
        while (true) {
            email = reader.readLine();
            break;
        }
        UserData userData = new UserData(login, password);
        userData.setEmail(email);
        client.setUserData(userData);
        Request request = new Request(RequestType.REGISTRATION_REQUEST);
        client.sendRequest(request);
        Answer answer=client.getAnswer();
        if (answer.getType()== AnswerType.SUCCESSFULLY){
            return new StateConfiguration("",new DialogStrategy());
        }
        else{
            return new StateConfiguration(answer.getMessage(),new RegistrationState());
        }
    }
}
