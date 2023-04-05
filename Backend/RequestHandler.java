import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;
import java.io.*;
import org.json.simple.JSONObject;

public class RequestHandler {
    private ResultSet results;
    String url = "jdbc:MySQL://localhost:3306/cinemabookingsystem"; // change port if server is on different port
    String username = "root"; // set user name to local server username
    String password = "Test123"; // set password to local server password
    final String secretKey = "ylwqc";
    SendEmail email = new SendEmail();

    public String handleRequest(String message) throws IOException {
        String[] inputs = message.split(",", -2);
        String command = inputs[0];
        if (command.equals("REGISTER")) {
            registerUser register = new registerUser();
            message = register.registerUser(inputs);
        } else if (command.equals("LOGIN")) {
            loginUser login = new loginUser();
            message = login.loginUser(inputs);
        } else if (command.equals("EDIT")) {
            editUser edit = new editUser();
            message = edit.editUser(inputs);
        } else if (command.equals("CONFIRM")) {
            confirmation confirm = new confirmation();
            message = confirm.confirmation(inputs);
        } else if (command.equals("REQUESTFORGOTPW")) {
            requestForgotPW request = new requestForgotPW();
            message = request.requestForgotPW(inputs);
        } else if (command.equals("SUBMITFORGOTPW")) {
            submitForgotPW submit = new submitForgotPW();
            message = submit.submitForgotPW(inputs);
        } else if (command.equals("LOGOUT")) {
            logout logout = new logout();
            message = logout.logout();
        } else if (command.equals("SCHEDULEMOVIE")) {
            scheduleMovie schedule = new scheduleMovie();
            message = schedule.scheduleMovie(inputs);
        } else if (command.equals("ADDMOVIE")) {
            addMovie add = new addMovie();
            message = add.addMovie(inputs);
        }
        return message;
    } // handleRequest


} // RequestHandler

