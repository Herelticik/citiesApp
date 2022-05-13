package server;

import lib.Answer;
import lib.Request;

import java.sql.SQLException;

public interface RequestHandlerStrategy {

    Answer handle(Request request) throws SQLException;

}
