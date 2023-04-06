import java.util.Arrays;
import java.sql.*;

public class viewTimes {

    // this assumes inputs[1] is movieID of movie selected 
    private ResultSet results;
    public String timeAndPlace(String[] inputs, Connection connection) { 
        String output = "failure";
    try {
        String searchMov = "select * from cinebookingsystem.movieshow where ? = movieID";
        String getLength = "select count(*) from cinebookingsystem.movieshow where ? = movieID";
        PreparedStatement searchMovStmt = connection.prepareStatement(searchMov);
        PreparedStatement getLengthStmt = connection.prepareStatement(getLength);
        searchMovStmt.setString(1, inputs[1]);
        results = searchMovStmt.executeQuery();
        getLengthStmt.setString(1, inputs[1]);
        ResultSet tempResults = getLengthStmt.executeQuery();
        tempResults.next();
        int len = tempResults.getInt("COUNT(*)");
        int i =0;
        String showtimes[] = new String[len];
        while(results.next()) {
            Time showtime = results.getTime("SHOWSTART");
            String time = showtime.toString();
            showtimes[i] = time;
        }
        output = showtimes.toString();
        }
        catch(SQLException e) { 
            e.printStackTrace();
            return "failure";
        }
        return output;
    }
}
