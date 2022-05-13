package lib.interaction;

import lib.element.City;

import java.io.Serializable;

public class Command implements Serializable {
    private String name;
    private String parameter;
    private City element;
    private String sender;

    public Command(String name, String parameter) {
        this.name = name;
        this.parameter = parameter;
    }

    public void setElement(City element) {
        this.element = element;
    }

    public City getElement() {
        return element;
    }

    public String getName() {
        return name;
    }

    public String getParameter() {
        return parameter;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getSender() {
        return sender;
    }

    @Override
    public String toString() {
        if (parameter != null) {
            return name + " " + parameter;
        }
        return name;
    }
}
