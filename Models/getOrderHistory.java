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
            String searchMovieShow = "select * from cinemabookingsystem.movieshow where ? = movieShowID";
            String searchMovie = "select * from cinemabookingsystem.movie where ? = movieID";
            String searchAud = "select * from cinemabookingsystem.auditorium where ? = audID";
            String searchShowTime = "select * from cinemabookingsystem.showtimes where ? = showID";
            PreparedStatement searchMovieShowStmt = connection.prepareStatement(searchMovieShow);
            PreparedStatement searchMovieStmt = connection.prepareStatement(searchMovie);
            PreparedStatement searchAudStmt = connection.prepareStatement(searchAud);
            PreparedStatement searchShowStmt = connection.prepareStatement(searchShowTime);
            for(int i = 0; i < len; i ++) { 
                results.next();
                searchMovieShowStmt.setString(1,results.getString("movieShowID"));
                tempResults = searchMovieShowStmt.executeQuery();
                tempResults.next();
                String showDate = tempResults.getString("showStart");
                searchMovieStmt.setString(1, tempResults.getString("movieID"));
                searchAudStmt.setString(1, tempResults.getString("auditoriumID"));
                searchShowStmt.setString(1, tempResults.getString("showID"));
                tempResults = searchMovieStmt.executeQuery();
                tempResults.next();
                String movieTitle = tempResults.getString("title");
                tempResults = searchAudStmt.executeQuery();
                tempResults.next();
                String audName = tempResults.getString("audName");
                tempResults = searchShowStmt.executeQuery();
                tempResults.next();
                String showTime = tempResults.getString("timeStamp");
                jsonObject[i] = new JSONObject();
                jsonObject[i].put("bookingID", results.getString("bookingID"));
                jsonObject[i].put("userID", results.getString("userID"));
                jsonObject[i].put("movieShowID", results.getString("movieShowID"));
                jsonObject[i].put("noChildTickets", results.getString("noChildTickets"));
                jsonObject[i].put("noAdultTickets", results.getString("noAdultTickets"));
                jsonObject[i].put("noSeniorTickets", results.getString("noSeniorTickets"));
                jsonObject[i].put("seatIDs", results.getString("seatIDs"));
                jsonObject[i].put("totalPrice", results.getString("totalPrice"));
                jsonObject[i].put("promoID", results.getString("promoID"));
                jsonObject[i].put("showDate",showDate);
                jsonObject[i].put("movieTitle",movieTitle);
                jsonObject[i].put("audName",audName);
                jsonObject[i].put("showTime",showTime);
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
