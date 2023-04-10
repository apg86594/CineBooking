import java.sql.*;
import java.io.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class getAuditoriums {
    private ResultSet results;
    public String getAuditoriumsEx(Connection connection) {
        try {
            String searchAuditorium = "select * from cinemabookingsystem.auditorium";
            String getLength = "select count(*) from cinemabookingsystem.auditorium";
            PreparedStatement getLengthStmt = connection.prepareStatement(getLength);
            PreparedStatement searchAudStmt = connection.prepareStatement(searchAuditorium);
            results = searchAudStmt.executeQuery();
            ResultSet tempResults = getLengthStmt.executeQuery();
            tempResults.next();
            int len = tempResults.getInt("COUNT(*)");
            JSONObject jsonObject[] = new JSONObject[len];
            JSONArray jsonArray = new JSONArray();
            for (int i = 0; i < len; i++) {
                results.next();
                jsonObject[i] = new JSONObject();
                jsonObject[i].put("audID",results.getString("audID"));
                jsonObject[i].put("audName",results.getString("audName"));
                jsonObject[i].put("noOfSeats", results.getObject("noOfSeats"));
                jsonArray.add(jsonObject[i]);
            }
            FileWriter file = new FileWriter("./UserView/auditorium-info.json");
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
