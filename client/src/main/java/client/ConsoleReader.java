package client;


import lib.interaction.Command;
import lib.interaction.Input;

import lib.element.City;


import java.util.Scanner;

/**
 * Класс получения и анализа данных из командной строки
 */
public class ConsoleReader {

    private Input input;


    /**
     * Установить последний id
     */
    public ConsoleReader() {
        this.input=new ConsoleInput(new Scanner(System.in));
    }

    public String readLine(){
        return input.getReader().nextLine().trim();
    }

    public Command readCommand(){
        return input.readCommand();
    }

    public City readElement() {
        return input.readElement();
    }
}
