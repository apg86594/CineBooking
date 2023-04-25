import java.sql.*;
import java.io.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class getUsers {
    private ResultSet results;
    final String secretKey = "ylwqc";
    public String getUsersEx(Connection connection) {
        try {
            String searchMov = "select * from cinemabookingsystem.user";
            String getLength = "select count(*) from cinemabookingsystem.user";
            PreparedStatement getLengthStmt = connection.prepareStatement(getLength);
            PreparedStatement searchMovStmt = connection.prepareStatement(searchMov);
            results = searchMovStmt.executeQuery();
            ResultSet tempResults = getLengthStmt.executeQuery();
            tempResults.next();
            int len = tempResults.getInt("COUNT(*)");
            JSONObject jsonObject[] = new JSONObject[len];
            JSONArray jsonArray = new JSONArray();
            encryptObject encrypter = new encryptObject();
            for (int i = 0; i < len; i++) {
                results.next();
                jsonObject[i] = new JSONObject();
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
                    jsonObject[i].put("userID",userID);
                    jsonObject[i].put("password",password);
                    jsonObject[i].put("firstName",firstName);
                    jsonObject[i].put("lastName",lastName);
                    jsonObject[i].put("email",email);
                    jsonObject[i].put("userType",userType);
                    jsonObject[i].put("billingAddressLine1",billingAddressLine1);
                    jsonObject[i].put("billingAddressLine2",billingAddressLine2);
                    jsonObject[i].put("billingZip",billingZip);
                    jsonObject[i].put("billingCity",billingCity);
                    jsonObject[i].put("billingState",billingState);
                    jsonObject[i].put("shippingAddressLine1",shippingAddressLine1);
                    jsonObject[i].put("shippingAddressLine2",shippingAddressLine2);
                    jsonObject[i].put("shippingZip",shippingZip);
                    jsonObject[i].put("shippingCity",shippingCity);
                    jsonObject[i].put("shippingState",shippingState);
                    jsonObject[i].put("ACTIVE",ACTIVE);
                    jsonObject[i].put("cardnum",cardnum);
                    jsonObject[i].put("securitynum",securitynum);
                    jsonObject[i].put("expmonth",expmonth);
                    jsonObject[i].put("expdate",expdate);
                    jsonObject[i].put("enabledPromotion",enabledPromotion);
                    jsonArray.add(jsonObject[i]);
            }
            FileWriter file = new FileWriter("./UserView/users-info.json");
                file.write(jsonArray.toString());
                
                file.close();

            }
            catch(SQLException | IOException e) { 
                e.printStackTrace();
                return "failure";
            }
        return "SUCCESS";
    }
}
