import java.sql.*;
import java.io.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class getOrderHistory {
    private ResultSet results;
    
    public String getOrdersEx(String[] inputs, Connection connection) { 
        try { 
            String searchUsr = "select * from cinemabookingsystem.booking where ? = userID";
            String getLength = "select count(*) from cinemabookingsystem.booking where ? = userID";
            PreparedStatement getLengthStmt = connection.prepareStatement(getLength);
            PreparedStatement searchUsrStmt = connection.prepareStatement(searchUsr);
            getLengthStmt.setString(1,inputs[1]);
            searchUsrStmt.setString(1,inputs[1]);
            results = searchUsrStmt.executeQuery();
            ResultSet tempResults = getLengthStmt.executeQuery();
            tempResults.next();
            int len = tempResults.getInt("COUNT(*)");
            JSONObject jsonObject[] = new JSONObject[len];
            JSONArray jsonArray = new JSONArray();
            for(int i = 0; i < len; i ++) { 
                results.next();
                jsonObject[i] = new JSONObject();
                jsonObject[i].put("bookingID", results.getString("bookingID"));
                jsonObject[i].put("movieShowID", results.getString("movieShowID"));
                jsonObject[i].put("noTickets", results.getString("noTickets"));
                jsonObject[i].put("ticketID", results.getString("ticketID"));
                jsonObject[i].put("totalPrice", results.getString("totalPrice"));
                jsonObject[i].put("promoID", results.getString("promoID"));
                jsonArray.add(jsonObject[i]);
            }
            FileWriter file = new FileWriter("./UserView/orderHistory-info.json");
            file.write(jsonArray.toString());
            
            file.close();
        }
        catch(SQLException | IOException e) { 
            e.printStackTrace();
            return "failure";
        }
        return "success";
    }

    
}
