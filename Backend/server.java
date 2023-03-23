import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import org.java_websocket.server.WebSocketServer;

import java.sql.ResultSet;
import java.io.*;
import java.net.*;

class server {
    public static void main(String[] args) throws IOException {

        while(true) {
            WebSocketServer socket = new webserver();
            socket.run();
        } // while

    } // main

}