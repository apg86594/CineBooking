import java.sql.*;
import java.io.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class getPromotion {
    private ResultSet results;
    public String getPromotionEx(Connection connection) {
        try {
            String searchPromotion = "select * from cinemabookingsystem.promotions";
            String getLength = "select count(*) from cinemabookingsystem.promotions";
            PreparedStatement getLengthStmt = connection.prepareStatement(getLength);
            PreparedStatement searchPromotionStmt = connection.prepareStatement(searchPromotion);
            results = searchPromotionStmt.executeQuery();
            ResultSet tempResults = getLengthStmt.executeQuery();
            tempResults.next();
            int len = tempResults.getInt("COUNT(*)");
            JSONObject jsonObject[] = new JSONObject[len];
            JSONArray jsonArray = new JSONArray();
            for (int i = 0; i < len; i++) {
                results.next();
                jsonObject[i] = new JSONObject();
                jsonObject[i].put("promotionID",results.getString("promotionID"));
                jsonObject[i].put("promotionCode",results.getString("promotionCode"));
                jsonObject[i].put("percentOff",results.getString("percentOff"));
                jsonArray.add(jsonObject[i]);
            }
            FileWriter file = new FileWriter("./UserView/promotion-info.json");
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
