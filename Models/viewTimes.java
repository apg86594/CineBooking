import java.util.Arrays;
import java.sql.*;
import java.io.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class viewTimes {

    // this assumes inputs[1] is movieID of movie selected 
    private ResultSet results;
    public String timeAndPlace(String[] inputs, Connection connection) { 
        String output = "failure";
    try {
        String searchMov = "select * from cinemabookingsystem.movieshow where ? = movieID";
        String getLength = "select count(*) from cinemabookingsystem.movieshow where ? = movieID";
        PreparedStatement searchMovStmt = connection.prepareStatement(searchMov);
        PreparedStatement getLengthStmt = connection.prepareStatement(getLength);
        searchMovStmt.setString(1, inputs[1]);
        results = searchMovStmt.executeQuery();
        getLengthStmt.setString(1, inputs[1]);
        ResultSet tempResults = getLengthStmt.executeQuery();
        tempResults.next();
        int len = tempResults.getInt("COUNT(*)");
        int i = 0;
        JSONObject jsonObject[] = new JSONObject[len];
        JSONArray jsonArray = new JSONArray();
        while(results.next()) {
            jsonObject[i] = new JSONObject();
            jsonObject[i].put("movieShowID", results.getString("movieShowID"));
            jsonObject[i].put("showID", results.getString("showID"));
            jsonObject[i].put("movieID", results.getString("movieID"));
            jsonObject[i].put("auditoriumID", results.getString("auditoriumID"));
            jsonObject[i].put("availableSeats", results.getString("availableSeats"));
            jsonObject[i].put("showStart", results.getString("showStart"));
            jsonObject[i].put("timeFilled", results.getString("timeFilled"));
            jsonArray.add(jsonObject[i]);
        }
        FileWriter file = new FileWriter("./UserView/movieShow-info.json");
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
