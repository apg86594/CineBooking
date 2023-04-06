import java.sql.*;
import java.io.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class sendPromotion {
    private ResultSet results;
    public String sendPromotionEx(String[] inputs, Connection connection) {
        try {
            String userEmail;
            String searchUser = "select * from cinemabookingsystem.user where 1 = enabledPromotion";
            String getLength = "select count(*) from cinemabookingsystem.user where 1 = enabledPromotion";
            PreparedStatement getLengthStmt = connection.prepareStatement(getLength);
            PreparedStatement searchUserStmt = connection.prepareStatement(searchUser);
            results = searchUserStmt.executeQuery();
            ResultSet tempResults = getLengthStmt.executeQuery();
            tempResults.next();
            int len = tempResults.getInt("COUNT(*)");
            for (int i = 0; i < len; i++) {
                results.next();
                userEmail = results.getString("email");
                SendEmail sendConfirmation = new SendEmail();
                String message = "Use code " + inputs[1] + " to receive " + inputs[2] + "% off your order.";
                sendConfirmation.sendEmail(userEmail, message,"New Promotion");
            }
        }
           catch(SQLException | IOException e) { 
                e.printStackTrace();
                return "failure";
            }
        return "SUCCESS";
    }
}