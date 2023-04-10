import java.sql.*;
import java.io.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class getShowtimes {
    private ResultSet results;
    public String getShowtimesEx(Connection connection) {
        try {
            String searchShowtimes = "select * from cinemabookingsystem.showtimes";
            String getLength = "select count(*) from cinemabookingsystem.showtimes";
            PreparedStatement getLengthStmt = connection.prepareStatement(getLength);
            PreparedStatement searchShowtimesStmt = connection.prepareStatement(searchShowtimes);
            results = searchShowtimesStmt.executeQuery();
            ResultSet tempResults = getLengthStmt.executeQuery();
            tempResults.next();
            int len = tempResults.getInt("COUNT(*)");
            JSONObject jsonObject[] = new JSONObject[len];
            JSONArray jsonArray = new JSONArray();
            for (int i = 0; i < len; i++) {
                results.next();
                jsonObject[i] = new JSONObject();
                jsonObject[i].put("showID",results.getString("showID"));
                jsonObject[i].put("timeStamp",results.getString("timeStamp"));
                jsonArray.add(jsonObject[i]);
            }
            FileWriter file = new FileWriter("./UserView/showtime-info.json");
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