import java.util.Arrays;
import java.sql.*;

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
        int i =0;
        String showtimes = new String();
        while(results.next()) {
            Timestamp showtime = results.getTimestamp("SHOWSTART");
            System.out.println(showtime);
            String time = showtime.toString();
            showtimes += time;
            showtimes += ",";
        }
        output = showtimes;
        }
        catch(SQLException e) { 
            e.printStackTrace();
            return "failure";
        }
        return output.substring(0,output.length() -1);
        
    }
}
