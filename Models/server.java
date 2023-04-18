
import org.java_websocket.server.WebSocketServer;
import java.io.*;


class server {
    public static void main(String[] args) throws IOException, InterruptedException {

        while(true) {
            WebSocketServer socket = new webserver();
            socket.run();
        } // while
    } // main

}