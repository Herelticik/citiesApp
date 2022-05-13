package lib;

import java.io.Serializable;

public enum RequestType implements Serializable {
    REGISTRATION_REQUEST,
    AUTHORIZATION_REQUEST,
    PASSWORD_RECOVER_REQUEST,
    COMMAND_EXECUTION

}
