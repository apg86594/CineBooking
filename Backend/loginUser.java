import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.io.*;
import org.json.simple.JSONObject;

public class loginUser {

    private ResultSet results;
    String url = "jdbc:MySQL://localhost:3306/cinemabookingsystem"; // change port if server is on different port
    String username = "root"; // set user name to local server username
    String password = "password123"; // set password to local server password
    final String secretKey = "ylwqc";
    SendEmail email = new SendEmail();

    public String loginUserEx(String[] inputs, Connection connection) {
            String findUser = "select * from cinemabookingsystem.user where ? = email and ? = password";
            encryptObject encrypter = new encryptObject();
            try {
                PreparedStatement findUserStmt = connection.prepareStatement(findUser);
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
                    String billingAddressLine1 = results.getString("billingAddressLine1");
                    String billingAddressLine2 = results.getString("billingAddressLine2");
                    String billingZip = results.getString("billingZip");
                    String billingCity = results.getString("billingCity");
                    String billingState = results.getString("billingState");
                    String shippingAddressLine1 = results.getString("shippingAddressLine1");
                    String shippingAddressLine2 = results.getString("shippingAddressLine2");
                    String shippingZip = results.getString("shippingZip");
                    String shippingCity = results.getString("shippingCity");
                    String shippingState = results.getString("shippingState");
                    String ACTIVE = results.getString("ACTIVE");
                    String cardnum = encrypter.decrypt(results.getString("cardnum"),secretKey);
                    String securitynum = encrypter.decrypt(results.getString("securitynum"),secretKey);
                    String expmonth = results.getString("expmonth");
                    String expdate = results.getString("expdate");
                    String enabledPromotion = results.getString("enabledPromotion");
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("userID",userID);
                    jsonObject.put("password",password);
                    jsonObject.put("firstName",firstName);
                    jsonObject.put("lastName",lastName);
                    jsonObject.put("email",email);
                    jsonObject.put("userType",userType);
                    jsonObject.put("billingAddressLine1",billingAddressLine1);
                    jsonObject.put("billingAddressLine2",billingAddressLine2);
                    jsonObject.put("billingZip",billingZip);
                    jsonObject.put("billingCity",billingCity);
                    jsonObject.put("billingState",billingState);
                    jsonObject.put("shippingAddressLine1",shippingAddressLine1);
                    jsonObject.put("shippingAddressLine2",shippingAddressLine2);
                    jsonObject.put("shippingZip",shippingZip);
                    jsonObject.put("shippingCity",shippingCity);
                    jsonObject.put("shippingState",shippingState);
                    jsonObject.put("ACTIVE",ACTIVE);
                    jsonObject.put("cardnum",cardnum);
                    jsonObject.put("securitynum",securitynum);
                    jsonObject.put("expmonth",expmonth);
                    jsonObject.put("expdate",expdate);
                    jsonObject.put("enabledPromotion",enabledPromotion);
                    FileWriter file = new FileWriter("./UserView/login-user-info.json");
                    file.write(jsonObject.toJSONString());
                    file.close();
                    return "SUCCESS " + userType;

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
        }
        return "BADUSER";
    }
}
