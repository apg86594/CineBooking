import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class searchByInput {
    private ResultSet results;
    final String secretKey = "ylwqc";

    public String searchByInputExString(String[] inputs, Connection connection) { 
        String defaultResult = "Sorry, no results found matching what you were looking for.";
        String movieIDs = "";
        try  {
        String searchMov = "select * from cinemabookingsystem.movie where title like CONCAT('%',?,'%')";
        String getLength = "select count(*) from cinemabookingsystem.movie where title like CONCAT('%',?,'%')";
        PreparedStatement searchMovStmt = connection.prepareStatement(searchMov);
        PreparedStatement getLengthStmt = connection.prepareStatement(getLength);
        searchMovStmt.setString(1, inputs[1]);
        results = searchMovStmt.executeQuery();
        getLengthStmt.setString(1, inputs[1]);
        ResultSet tempResults = getLengthStmt.executeQuery();
        tempResults.next();
        int len = tempResults.getInt("COUNT(*)");
        System.out.println(len);
        for (int i = 0; i < len; i++) {
            results.next();
            movieIDs += results.getString("movieID");
            movieIDs += ",";
        }
        if (movieIDs.equals("")) {
            return defaultResult;
        }
        }
        catch(SQLException e) { 
            e.printStackTrace();
            return "failure";
        }
        return movieIDs.substring(0,movieIDs.length() - 1);
        
    }

}
