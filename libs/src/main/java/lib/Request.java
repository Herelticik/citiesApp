package lib;

import lib.interaction.Command;

import java.io.Serializable;

public class Request implements Serializable {
    private RequestType requestType;
    private Command command;
    private UserData userData;

    public Request(RequestType requestType, Command command) {
        this.requestType = requestType;
        this.command = command;
    }

    public Request(RequestType requestType) {
        this.requestType = requestType;

    }


    public void setUserData(UserData userData) {
        this.userData = userData;
    }

    public UserData getUserData() {
        return userData;
    }

    public Command getCommand() {
        return command;
    }

    public RequestType getRequestType() {
        return requestType;
    }

    @Override
    public String toString() {
        return "Request{" +
                "requestType=" + requestType +
                ", command=" + command +
                ", userData=" + userData +
                '}';
    }
}
