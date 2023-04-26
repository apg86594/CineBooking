import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import java.sql.Connection;
import java.sql.DriverManager;

public class webserver extends WebSocketServer {

    private static int TCP_PORT = 8888;

    private Set<WebSocket> conns;

    private String request;
    RequestHandler requestHandler = new RequestHandler();
    Connection connection;
    String url = "jdbc:MySQL://localhost:3306/cinemabookingsystem"; // change port if server is on different port
    String username = "root"; // set user name to local server username
    String password = "password123"; // set password to local server password

    public webserver() {
        super(new InetSocketAddress("127.0.0.1",TCP_PORT));
        conns = new HashSet<>();
        request = "";
        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void setRequest(String newRequest) {
        this.request = newRequest;
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        conns.add(conn);
        System.out.println("New connection from " + conn.getRemoteSocketAddress().getAddress().getHostAddress());
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        conns.remove(conn);
        System.out.println("Closed connection to " + conn.getRemoteSocketAddress().getAddress().getHostAddress());
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        System.out.println("Message from client: " + message);
        setRequest(message);
        try {
            message = requestHandler.handleRequest(request, connection);
        } catch (IOException | SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        conn.send(message);

    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        //ex.printStackTrace();
        if (conn != null) {
            conns.remove(conn);
            // do some thing if required
        }
        System.out.println("ERROR from " + conn.getRemoteSocketAddress().getAddress().getHostAddress());
        ex.printStackTrace();
        try {
            connection.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        conn.close();
    }

    @Override
    public void onStart() {
        // TODO Auto-generated method stub
        System.out.println("Socket open on " + this.getAddress());
    }
}
