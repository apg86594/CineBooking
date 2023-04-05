import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class editUser {

    private ResultSet results;
    String url = "jdbc:MySQL://localhost:3306/cinemabookingsystem"; // change port if server is on different port
    String username = "root"; // set user name to local server username
    String password = "Test123"; // set password to local server password
    final String secretKey = "ylwqc";
    SendEmail email = new SendEmail();

    public String editUser(String[] inputs) { 
        encryptObject encrypter = new encryptObject();
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String findUser = "select * from cinemabookingsystem.user where ? = email";
            PreparedStatement findUserStmt = connection.prepareStatement(findUser);
            findUserStmt.setString(1, inputs[4]);
            results = findUserStmt.executeQuery();
            if(results.next()) { 
                String userEmail = results.getString("email");
                String sql = "UPDATE user SET password = ?, firstName=?, lastName=?, email=?, USERTYPE=?, billingAddress=?, ACTIVE=?, cardnum=?, securitynum=?, expmonth=?,expdate=?" +
                    "WHERE email = ?";
                    PreparedStatement preparedStmt = connection.prepareStatement(sql);
            try {
                preparedStmt.setString(1, encrypter.encrypt(inputs[1],secretKey)); //password
                preparedStmt.setString(2, inputs[2]); //firstName
                preparedStmt.setString(3, inputs[3]); //lastName
                preparedStmt.setString(4, inputs[4]); //email
                preparedStmt.setString(5, inputs[5]); //usertype
                preparedStmt.setString(6, inputs[6]); //billingaddress
                preparedStmt.setString(7, "1"); //active
                preparedStmt.setString(8, encrypter.encrypt(inputs[7],secretKey)); // cardnum
                preparedStmt.setString(9, encrypter.encrypt(inputs[8],secretKey)); // securitynum
                preparedStmt.setString(10,inputs[9]); // expmonth
                preparedStmt.setString(11,inputs[10]); // expdate
                preparedStmt.setString(12, userEmail);
                preparedStmt.execute();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } // try
            }
            else 
            System.out.println("failure");
            connection.close();
    } catch (SQLException e) {
        e.printStackTrace();
        return "FAILURE";
    } 
    return "Success";
    }
    
}
