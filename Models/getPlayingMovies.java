import java.sql.*;
import java.io.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class getPlayingMovies {
    private ResultSet results;
    public String getPlayingMoviesEx(Connection connection) {
        try {
            String searchMov = "select * from cinemabookingsystem.movie where 'Now Playing' = display";
            String getLength = "select count(*) from cinemabookingsystem.movie where 'Now Playing' = display";
            PreparedStatement getLengthStmt = connection.prepareStatement(getLength);
            PreparedStatement searchMovStmt = connection.prepareStatement(searchMov);
            results = searchMovStmt.executeQuery();
            ResultSet tempResults = getLengthStmt.executeQuery();
            tempResults.next();
            int len = tempResults.getInt("COUNT(*)");
            JSONObject jsonObject[] = new JSONObject[len];
            JSONArray jsonArray = new JSONArray();
            for (int i = 0; i < len; i++) {
                results.next();
                jsonObject[i] = new JSONObject();
                jsonObject[i].put("movieID",results.getString("movieID"));
                jsonObject[i].put("title",results.getString("title"));
                jsonObject[i].put("casting",results.getString("casting"));
                jsonObject[i].put("genre",results.getString("genre"));
                jsonObject[i].put("director",results.getString("director"));
                jsonObject[i].put("producer",results.getString("producer"));
                jsonObject[i].put("duration",results.getString("duration"));
                jsonObject[i].put("synopsis",results.getString("synopsis"));
                jsonObject[i].put("display",results.getString("display"));
                jsonObject[i].put("trailerPicture",results.getString("trailerPicture").replace("\\", ""));
                jsonObject[i].put("trailerVideo",results.getString("trailerVideo").replace("\\", ""));
                jsonObject[i].put("review", results.getString("review"));
                String getRating = "select * from cinemabookingsystem.usrating where ? = ratingID";
                PreparedStatement getRatingStmt = connection.prepareStatement(getRating);
                getRatingStmt.setString(1, results.getString("ratingID"));
                tempResults = getRatingStmt.executeQuery();
                tempResults.next();
                jsonObject[i].put("ratingCode",tempResults.getString("ratingCode"));
                jsonArray.add(jsonObject[i]);
            }
            FileWriter file = new FileWriter("./UserView/playing-movie.json");
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