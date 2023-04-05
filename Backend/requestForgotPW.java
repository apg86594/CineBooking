import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.io.*;

public class requestForgotPW {

    private ResultSet results;
    String url = "jdbc:MySQL://localhost:3306/cinemabookingsystem"; // change port if server is on different port
    String username = "root"; // set user name to local server username
    String password = "Test123"; // set password to local server password
    final String secretKey = "ylwqc";
    SendEmail email = new SendEmail();

    public String requestForgotPW(String[] inputs) {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
        Random r = new Random();
        String randomNumber = String.format("%04d", 10000 + r.nextInt(9999));
        String updateUser = "UPDATE user SET confirm = ? WHERE ? = email";
        PreparedStatement updateUserStmt = connection.prepareStatement(updateUser);
        updateUserStmt.setString(1, randomNumber); // confirm
        updateUserStmt.setString(2, inputs[1]); //email
        updateUserStmt.execute();
        SendEmail sendConfirmation = new SendEmail();
        sendConfirmation.sendEmail(inputs[1], (String)randomNumber);
        connection.close();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            return "FAILURE";
        }//try
        return "success";
    }
    
}
