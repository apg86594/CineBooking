import java.sql.*;
import java.io.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class getAuditoriumSeats {
    private ResultSet results;
    public String getAuditoriumSeatsEx(String[] inputs, Connection connection) {
        try {
            String searchMovSeats = "select * from cinemabookingsystem.movieshowseats where ? = movieShowID";
            String getLength = "select count(*) from cinemabookingsystem.movieshowseats where ? = movieShowID";
            PreparedStatement getLengthStmt = connection.prepareStatement(getLength);
            getLengthStmt.setString(1, inputs[1]); // movieshowID
            PreparedStatement searchMovSeatsStmt = connection.prepareStatement(searchMovSeats);
            searchMovSeatsStmt.setString(1, inputs[1]); // movieshowID
            results = searchMovSeatsStmt.executeQuery();
            ResultSet tempResults = getLengthStmt.executeQuery();
            tempResults.next();
            int len = tempResults.getInt("COUNT(*)");
            JSONObject jsonObject[] = new JSONObject[len];
            JSONArray jsonArray = new JSONArray();
            for (int i = 0; i < len; i++) {
                results.next();
                String getSeatInfo = "select * from cinemabookingsystem.audseats where ? = seatID";
                PreparedStatement getSeatInfoStmt = connection.prepareStatement(getSeatInfo);
                getSeatInfoStmt.setString(1, results.getString("seatID"));
                tempResults = getSeatInfoStmt.executeQuery();
                tempResults.next();
                jsonObject[i] = new JSONObject();
                jsonObject[i].put("seatID",results.getString("seatID"));
                jsonObject[i].put("sectionRow",tempResults.getString("sectionRow"));
                jsonObject[i].put("sectionCol",tempResults.getString("sectionCol"));
                jsonObject[i].put("available",results.getString("available"));
                jsonArray.add(jsonObject[i]);
            }
            FileWriter file = new FileWriter("./UserView/movieShowSeats-info.json");
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
