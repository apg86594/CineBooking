import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.io.*;
import org.json.simple.JSONObject;

public class editUser {

    private ResultSet results;
    final String secretKey = "ylwqc";
    SendEmail email = new SendEmail();

    public String editUserEx(String[] inputs, Connection connection) { 
        encryptObject encrypter = new encryptObject();
        try {
            String findUser = "select * from cinemabookingsystem.user where ? = email";
            PreparedStatement findUserStmt = connection.prepareStatement(findUser);
            findUserStmt.setString(1, inputs[3]);
            results = findUserStmt.executeQuery();
            if(results.next()) { 
                String userEmail = results.getString("email");
                String userPassword = encrypter.decrypt(results.getString("password"),secretKey);
                String userID = results.getString("userID");
                String ACTIVE = results.getString("ACTIVE");
                String privileges = results.getString("USERTYPE");
                String findPrivileges = "select * from cinemabookingsystem.usertype where ? = userTypeID";
                PreparedStatement findPrivilegesStmt = connection.prepareStatement(findPrivileges);
                findPrivilegesStmt.setString(1, privileges);
                ResultSet tempResults = findPrivilegesStmt.executeQuery();
                tempResults.next();
                String userType = tempResults.getString("UserTypeName");

                String sql = "UPDATE user SET firstName=?, lastName=?, email=?, USERTYPE=?, billingAddressLine1=?, " + 
                "billingAddressLine2=?, billingZip=?, billingCity=?, billingState=?, shippingAddressLine1=?, shippingAddressLine2=?," +
                "shippingZip=?, shippingCity=?, shippingState=?, cardnum=?, securitynum=?, expmonth=?,expdate=?,enabledPromotion=?" +
                    "WHERE email = ?";
                    PreparedStatement preparedStmt = connection.prepareStatement(sql);
            try {
                preparedStmt.setString(1, inputs[1]); //first
                preparedStmt.setString(2, inputs[2]); // last
                preparedStmt.setString(3, inputs[3]); // email
                preparedStmt.setString(4, inputs[4]); // Usertype
                preparedStmt.setString(5, inputs[5]); // billingAddressLine1
                preparedStmt.setString(6, inputs[6]); // billingAddressLine2
                preparedStmt.setString(7, inputs[7]); // billingZip
                preparedStmt.setString(8, inputs[8]); // billingCity
                preparedStmt.setString(9, inputs[9]); // billingState
                preparedStmt.setString(10, inputs[10]); // shippingAddressLine1
                preparedStmt.setString(11, inputs[11]); // shippingAddressLine2
                preparedStmt.setString(12, inputs[12]); // shippingZip
                preparedStmt.setString(13, inputs[13]); // shippingCity
                preparedStmt.setString(14, inputs[14]); // shippingState
                preparedStmt.setString(15, encrypter.encrypt(inputs[15],secretKey)); // cardnum
                preparedStmt.setString(16, encrypter.encrypt(inputs[16], secretKey)); // securitynum
                preparedStmt.setString(17, inputs[17]); // expmonth
                preparedStmt.setString(18, inputs[18]); // expdate
                preparedStmt.setString(19, inputs[19]); // enabledPromotion
                preparedStmt.setString(20, userEmail); // email
                preparedStmt.execute();
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("firstName",inputs[1]);
                jsonObject.put("lastName",inputs[2]);
                jsonObject.put("email",inputs[3]);
                jsonObject.put("userType",inputs[4]);
                jsonObject.put("billingAddressLine1",inputs[5]);
                jsonObject.put("billingAddressLine2",inputs[6]);
                jsonObject.put("billingZip",inputs[7]);
                jsonObject.put("billingCity",inputs[8]);
                jsonObject.put("billingState",inputs[9]);
                jsonObject.put("shippingAddressLine1",inputs[10]);
                jsonObject.put("shippingAddressLine2",inputs[11]);
                jsonObject.put("shippingZip",inputs[12]);
                jsonObject.put("shippingCity",inputs[13]);
                jsonObject.put("shippingState",inputs[14]);;
                jsonObject.put("cardnum",inputs[15]);
                jsonObject.put("securitynum",inputs[16]);
                jsonObject.put("expmonth",inputs[17]);
                jsonObject.put("expdate",inputs[18]);
                jsonObject.put("enabledPromotion",inputs[19]);
                jsonObject.put("userID",userID);
                jsonObject.put("password",userPassword);
                jsonObject.put("ACTIVE",ACTIVE);
                FileWriter file = new FileWriter("./UserView/login-user-info.json");
                file.write(jsonObject.toJSONString());
                file.close();
            } catch (SQLException | IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return "FAILURE";
            } // try
            }
            else 
            System.out.println("failure");
    } catch (SQLException e) {
        e.printStackTrace();
        return "FAILURE";
    } 
    return "Success";
    }
    
}
