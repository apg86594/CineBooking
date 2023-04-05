import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;
import java.io.*;
import org.json.simple.JSONObject;

public class loginUser {

    private ResultSet results;
    String url = "jdbc:MySQL://localhost:3306/cinemabookingsystem"; // change port if server is on different port
    String username = "root"; // set user name to local server username
    String password = "Mala0905hello"; // set password to local server password
    final String secretKey = "ylwqc";
    SendEmail email = new SendEmail();

    public String loginUser(String[] inputs) {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String findUser = "select * from cinemabookingsystem.user where ? = email and ? = password";
            PreparedStatement findUserStmt = connection.prepareStatement(findUser);
            encryptObject encrypter = new encryptObject();
            try {
                findUserStmt.setString(1, inputs[1]);
                findUserStmt.setString(2, encrypter.encrypt(inputs[2],secretKey));
                results = findUserStmt.executeQuery();
                if (results.next()) {
                    String activationStatus = results.getString("ACTIVE");
                    String findActivation = "select * from cinemabookingsystem.active where ? = ActiveID";
                    PreparedStatement findActivationStmt = connection.prepareStatement(findActivation);
                    findActivationStmt.setString(1, activationStatus);
                    ResultSet tempResults = findActivationStmt.executeQuery();
                    tempResults.next();
                    String activationType = tempResults.getString("ActiveStatus");
                    if (!activationType.equals("ACTIVE")) {
                        return "NOTACTIVE";
                    }

                    String privileges = results.getString("USERTYPE");
                    String findPrivileges = "select * from cinemabookingsystem.usertype where ? = userTypeID";
                    PreparedStatement findPrivilegesStmt = connection.prepareStatement(findPrivileges);

                    findPrivilegesStmt.setString(1, privileges);
                    tempResults = findPrivilegesStmt.executeQuery();
                    tempResults.next();
                    String userType = tempResults.getString("UserTypeName");
                    String email = results.getString("email");
                    String userID = results.getString("userID");
                    String password = encrypter.decrypt(results.getString("password"),secretKey);
                    String firstName = results.getString("firstName");
                    String lastName = results.getString("lastName");
                    String billingAddress = results.getString("billingAddress");
                    String ACTIVE = results.getString("ACTIVE");
                    String cardnum = encrypter.decrypt(results.getString("cardnum"),secretKey);
                    String securitynum = encrypter.decrypt(results.getString("securitynum"),secretKey);
                    String expmonth = results.getString("expmonth");
                    String expdate = results.getString("expdate");
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("userID",userID);
                    jsonObject.put("password",password);
                    jsonObject.put("firstName",firstName);
                    jsonObject.put("lastName",lastName);
                    jsonObject.put("email",email);
                    jsonObject.put("userType",userType);
                    jsonObject.put("billingAddress",billingAddress);
                    jsonObject.put("ACTIVE",ACTIVE);
                    jsonObject.put("cardnum",cardnum);
                    jsonObject.put("securitynum",securitynum);
                    jsonObject.put("expmonth",expmonth);
                    jsonObject.put("expdate",expdate);
                    FileWriter file = new FileWriter("./UserView/login-user-info.json");
                    file.write(jsonObject.toJSONString());
                    file.close();
                    return ("SUCCESS," + userID + "," + password + "," + firstName + "," + lastName + "," + email +"," + userType + "," + 
                    billingAddress + "," + ACTIVE + "," + cardnum + "," + securitynum + "," + expmonth + "," + expdate);

                } else {
                    String findUserEmail = "select * from cinemabookingsystem.user where ? = email";
                    PreparedStatement findUserEmailStmt = connection.prepareStatement(findUserEmail);

                    findUserEmailStmt.setString(1, inputs[1]);
                    results = findUserEmailStmt.executeQuery();
                    if (results.next()) {
                        return "BADPASSWORD";
                    }

                }
            } catch (SQLException | IOException e) {
                e.printStackTrace();
            } // try
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "BADUSER";
    }
}
