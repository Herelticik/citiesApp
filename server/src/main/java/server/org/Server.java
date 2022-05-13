package server.org;

import server.ServerRequestHandler;
import java.sql.SQLException;
import java.util.logging.Logger;


public class Server {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Logger log = Logger.getLogger(Server.class.getName());
        Class.forName("org.postgresql.Driver");
        ServerRequestHandler handler = new ServerRequestHandler();
        System.out.println("Сервер запущен!");
        log.info("Сервер запущен!");
        while (true) {
            handler.handle();
        }
    }
}
