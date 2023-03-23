import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.util.HashSet;
import java.util.Set;

public class webserver extends WebSocketServer {

    private static int TCP_PORT = 8888;

    private Set<WebSocket> conns;

    private String request;
    RequestHandler requestHandler = new RequestHandler();

    public webserver() {
        super(new InetSocketAddress("127.0.0.1",TCP_PORT));
        conns = new HashSet<>();
        String request = "";
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
        message = requestHandler.handleRequest(request);
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
        conn.close();
    }

    @Override
    public void onStart() {
        // TODO Auto-generated method stub
        System.out.println("Socket open on " + this.getAddress());
    }
}