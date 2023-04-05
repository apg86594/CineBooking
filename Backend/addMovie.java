import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;
import java.io.*;
import org.json.simple.JSONObject;

public class addMovie {

    private ResultSet results;
    String url = "jdbc:MySQL://localhost:3306/cinemabookingsystem"; // change port if server is on different port
    String username = "root"; // set user name to local server username
    String password = "Test123"; // set password to local server password
    final String secretKey = "ylwqc";
    SendEmail email = new SendEmail();

    // This function allows the admin to add a new movie to the database
    // The input is an array of strings that contain the movie information with a certain order
    public String addMovie(String[] inputs) {
        
        //Connects to the database
        try (Connection connection = DriverManager.getConnection(url, username, password)) {

            //Creates the SQL statement
            String sql = "INSERT INTO movie (title, casting, genre, producer, duration, trailerPicture, trailerVideo, review, ratingID, movieID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            
            PreparedStatement preparedStmt = connection.prepareStatement(sql);

            try {
                //Sets the values of "question marks" in the SQL statement
                preparedStmt.setString(1, inputs[1]);
                preparedStmt.setString(2, inputs[2]);
                preparedStmt.setString(3, inputs[3]);
                preparedStmt.setString(4, inputs[4]);
                preparedStmt.setString(5, inputs[5]);
                preparedStmt.setString(6, inputs[6]);
                preparedStmt.setString(7, inputs[7]);
                preparedStmt.setString(8, inputs[8]);
                preparedStmt.setString(9, inputs[9]);
                preparedStmt.setString(10, inputs[10]);
                

                //Executes the SQL statement (updates the database table)
                preparedStmt.execute();
            } catch (SQLException e) {
                e.printStackTrace();
                return "FAILURE";
            } // try
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return "FAILURE";
        } // try
        return "SUCCESS";

    }
    
}
