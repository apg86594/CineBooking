import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class submitForgotPW {

    private ResultSet results;
    String url = "jdbc:MySQL://localhost:3306/cinemabookingsystem"; // change port if server is on different port
    String username = "root"; // set user name to local server username
    String password = "Test123"; // set password to local server password
    final String secretKey = "ylwqc";
    SendEmail email = new SendEmail();

    public String submitForgotPW(String[] inputs) {
        encryptObject encrypter = new encryptObject();
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String findUser = "select * from cinemabookingsystem.user where ? = email";
            PreparedStatement findUserStmt = connection.prepareStatement(findUser);
            findUserStmt.setString(1, inputs[1]); // email
            results = findUserStmt.executeQuery();
            if(results.next()) { 
                String confirmationCode = results.getString("confirm");
                String sql = "UPDATE user SET password = ? where ? = email";
                PreparedStatement preparedStmt = connection.prepareStatement(sql);
                if(confirmationCode.equals(inputs[3])) { 
                    try {
                        preparedStmt.setString(1, encrypter.encrypt(inputs[2],secretKey)); // password
                        preparedStmt.setString(2,inputs[1]); // email
                        preparedStmt.execute();
                    } catch (SQLException e) {
                        e.printStackTrace();
                        return "FAILURE";
                    } 
                    }
                    else {
                        System.out.println("BADCODE");
                        return "BADCODE";
                    }
                }
        } catch (SQLException e) {
            e.printStackTrace();
            return "FAILURE";
        }
        return "success";
    }



    
}
