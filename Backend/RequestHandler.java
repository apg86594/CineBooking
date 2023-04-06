import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.*;

public class RequestHandler {
    final String secretKey = "ylwqc";
    SendEmail email = new SendEmail();

    public String handleRequest(String message, Connection connection) throws IOException, SQLException {
        String[] inputs = message.split(",", -2);
        String command = inputs[0];
        if (command.equals("REGISTER")) {
            registerUser register = new registerUser();
            message = register.registerUserEx(inputs, connection);
        } else if (command.equals("LOGIN")) {
            loginUser login = new loginUser();
            message = login.loginUserEx(inputs, connection);
        } else if (command.equals("EDIT")) {
            editUser edit = new editUser();
            message = edit.editUserEx(inputs, connection);
        } else if (command.equals("CONFIRM")) {
            confirmation confirm = new confirmation();
            message = confirm.confirmationEx(inputs, connection);
        } else if (command.equals("REQUESTFORGOTPW")) {
            requestForgotPW request = new requestForgotPW();
            message = request.requestForgotPWEx(inputs, connection);
        } else if (command.equals("SUBMITFORGOTPW")) {
            submitForgotPW submit = new submitForgotPW();
            message = submit.submitForgotPWEx(inputs, connection);
        } else if (command.equals("LOGOUT")) {
            logout logout = new logout();
            message = logout.logoutEx();
        } else if (command.equals("SCHEDULEMOVIE")) {
            scheduleMovie schedule = new scheduleMovie();
            message = schedule.scheduleMovieEx(inputs, connection);
        } else if (command.equals("ADDMOVIE")) {
            addMovie add = new addMovie();
            message = add.addMovieEx(inputs, connection);
        }
        return message;
    } // handleRequest


} // RequestHandler

