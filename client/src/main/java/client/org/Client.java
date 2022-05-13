package client.org;

import client.*;
import lib.exceptions.ExitException;


public class Client {
    public static void main(String[] args) {
        InteractionClient client = new InteractionClient("62.109.17.62", 5000);
        ConsoleReader reader = new ConsoleReader();
        InteractionManager manager = new InteractionManager(client, reader);
        System.out.println("Вы зарегистрированны?(Y/N/R)");
        while (true) {
            String mode = reader.readLine().toLowerCase();
            if (mode.equals("y")) {
                manager.setStrategy(new AuthorizationState());
                break;
            }
            if (mode.equals("n")) {
                manager.setStrategy(new RegistrationState());
                break;
            }
            if (mode.equals("r")) {
                manager.setStrategy(new RecoverPasswordState());
                break;
            }
            System.out.println("Введено не требуемое значение");
        }

        while (true) {
            try {
                manager.handleAnswer();
            } catch (ExitException e) {
                break;
            }
        }
    }
}
