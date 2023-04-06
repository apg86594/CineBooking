import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class scheduleMovie {

    private ResultSet results;
    final String secretKey = "ylwqc";
    SendEmail email = new SendEmail();

    //This function allows the admin to schedule a time for a movie to be shown
    //The input is an array of strings that will update the MOVIESHOW table

    public String scheduleMovieEx(String[] inputs, Connection connection) {

        //String timeStamp; 

        //connects to the database
        try {

            //Creates the query statement in sql for the show table
            String findShowID = "select * from cinemabookingsystem.showtimes where ? = showID";
            
            PreparedStatement findShowStmt = connection.prepareStatement(findShowID);

            //sets the value of the question mark in the find show statement
            findShowStmt.setString(1, inputs[1]);

           results = findShowStmt.executeQuery();

           //checks to see if that showID exists
            if(!results.next()) {

                return "BADSHOWID";
            } else {

               // timeStamp = results.getString("timeStamp");
            }



            //Creates the query statement in sql for the show table
            String findMovieID = "select * from cinemabookingsystem.movie where ? = movieID";
            
            PreparedStatement findMovieIDStmt = connection.prepareStatement(findMovieID);

            //sets the value of the question mark in the find show statement
            findMovieIDStmt.setString(1, inputs[2]);

            results = findMovieIDStmt.executeQuery();

            //checks to see if that movieID exists
            if(!results.next()) {

                return "BADMOVIEID";
            }

            String findAuditoriumID = "select * from cinemabookingsystem.auditorium where ? = audID";

            PreparedStatement findAuditoriumIDStmt = connection.prepareStatement(findAuditoriumID);

            findAuditoriumIDStmt.setString(1, inputs[3]);

            results = findAuditoriumIDStmt.executeQuery();

            //checks to see if that auditoriumID exists
            if(!results.next()) {

                return "BADAUDITORIUMID";
            }

            //Creates the sql statement
            String sql = "INSERT INTO movieshow (showID, movieID, auditoriumID, availableSeats, showStart, timeFilled) VALUES (?, ?, ?, ?, ?, ?)";

            PreparedStatement preparedStmt = connection.prepareStatement(sql);

            try {
                //sets the values of the "question marks" in the sql statement

                preparedStmt.setString(1, inputs[1]); //showID
                preparedStmt.setString(2, inputs[2]); //movieID
                preparedStmt.setString(3, inputs[3]); //auditoriumID
                preparedStmt.setString(4, inputs[4]); //availableSeats
                preparedStmt.setString(5, inputs[5]); //showStart
                preparedStmt.setString(6, inputs[6]); //timeFilled

                //executes the sql statement
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
