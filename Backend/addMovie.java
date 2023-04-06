import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class addMovie {

    private ResultSet results;
    final String secretKey = "ylwqc";
    SendEmail email = new SendEmail();

    // This function allows the admin to add a new movie to the database
    // The input is an array of strings that contain the movie information with a certain order
    public String addMovieEx(String[] inputs, Connection connection) {
        
        //Connects to the database
        try {

            //Creates the SQL statement
            String sql = "INSERT INTO movie (title, casting, genre, producer, duration, trailerPicture, trailerVideo, review, ratingID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            
            PreparedStatement preparedStmt = connection.prepareStatement(sql);

            try {
                //Sets the values of "question marks" in the SQL statement
                preparedStmt.setString(1, inputs[1]); // title
                preparedStmt.setString(2, inputs[2]); // casting
                preparedStmt.setString(3, inputs[3]); // genre
                preparedStmt.setString(4, inputs[4]); // producer
                preparedStmt.setString(5, inputs[5]); // duration
                preparedStmt.setString(6, inputs[6]); // trailerPicture
                preparedStmt.setString(7, inputs[7]); // trailerVideo
                preparedStmt.setString(8, inputs[8]); // review
                preparedStmt.setString(9, inputs[9]); // ratingID
                

                //Executes the SQL statement (updates the database table)
                preparedStmt.execute();
            } catch (SQLException e) {
                e.printStackTrace();
                return "FAILURE";
            } // try
        } catch (SQLException e) {
            e.printStackTrace();
            return "FAILURE";
        } // try
        return "SUCCESS";

    }
    
}