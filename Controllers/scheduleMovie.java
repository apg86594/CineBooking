import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.function.ToIntFunction;

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
            String findMovieID = "select * from cinemabookingsystem.movie where ? = movieID";
            String movieDuration;
            PreparedStatement findMovieIDStmt = connection.prepareStatement(findMovieID);

            //sets the value of the question mark in the find show statement
            findMovieIDStmt.setString(1, inputs[2]);

            results = findMovieIDStmt.executeQuery();
            //checks to see if that movieID exists
            if(!results.next()) {

                return "BADMOVIEID";
            } else {
                movieDuration = results.getString("duration");
            }
                        //Creates the query statement in sql for the show table
                        String findShowID = "select * from cinemabookingsystem.auditorium where ? = audID";
                        String auditoriumSeats;
                        PreparedStatement findShowIDStmt = connection.prepareStatement(findShowID);
            
                        //sets the value of the question mark in the find show statement
                        findShowIDStmt.setString(1, inputs[3]); // audID
            
                        results = findShowIDStmt.executeQuery();
                        //checks to see if that movieID exists
                        if(!results.next()) {
            
                            return "BADSHOWID";
                        } else {
                            auditoriumSeats = results.getString("noOfSeats");
                        }

            String findDupShow = "select * from cinemabookingsystem.movieshow where ? = showID and ? = showStart and ? = auditoriumID";
            PreparedStatement findDupShowStmt = connection.prepareStatement(findDupShow);
            findDupShowStmt.setString(1, inputs[1]);
            findDupShowStmt.setString(2, inputs[4]);
            findDupShowStmt.setString(3, inputs[3]);

            results = findDupShowStmt.executeQuery();
            if(results.next()) {
                return "TIMEFILLED";
            } 

            //Creates the sql statement
            String sql = "INSERT INTO movieshow (showID, movieID, auditoriumID, availableSeats, showStart, timeFilled) VALUES (?, ?, ?, ?, ?, ?)";

            PreparedStatement preparedStmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            try {
                //sets the values of the "question marks" in the sql statement
                preparedStmt.setString(1, inputs[1]); //showID
                preparedStmt.setString(2, inputs[2]); //movieID
                preparedStmt.setString(3, inputs[3]); //auditoriumID
                preparedStmt.setString(4, auditoriumSeats); //availableSeats
                preparedStmt.setString(5, inputs[4]); //showStart
                preparedStmt.setString(6, movieDuration); //timeFilled

                //executes the sql statement
                preparedStmt.execute();
            } catch (SQLException e) {
                e.printStackTrace();
                return "FAILURE";
            } // try

            /*Get the movie show ID from insertion---------------*/
            String getMovieID = "select * from cinemabookingsystem.movieshow where ? = showID and ? = showStart and ? = auditoriumID";
            PreparedStatement getMovieIDStmt = connection.prepareStatement(getMovieID);
            getMovieIDStmt.setString(1, inputs[1]);
            getMovieIDStmt.setString(2, inputs[4]);
            getMovieIDStmt.setString(3, inputs[3]);
            results = getMovieIDStmt.executeQuery();
            results.next();
            String movieShowID = results.getString("movieShowID");
            /* -------------------------------------------------- */

            /*Get each seatID from the auditorium---------------- */
            String getSeatIDs = "select * from cinemabookingsystem.audseats where ? = audID";
            PreparedStatement getSeatIDsStmt = connection.prepareStatement(getSeatIDs);
            getSeatIDsStmt.setString(1, inputs[3]);
            results = getSeatIDsStmt.executeQuery();
            String[] seatIDs = new String[Integer.parseInt(auditoriumSeats)];
            int index = 0;
            while(results.next()) {
                seatIDs[index] = results.getString("seatID");
                index++;
            }
            /* -------------------------------------------------- */
            String updateSeats = "INSERT INTO movieshowseats (movieShowID, seatID) VALUES (?, ?)";
            PreparedStatement updateSeatsStmt = connection.prepareStatement(updateSeats);
            for(int i = 0; i < Integer.parseInt(auditoriumSeats); i++) {
                updateSeatsStmt.setString(1, movieShowID);
                updateSeatsStmt.setString(2,seatIDs[i]);
                updateSeatsStmt.execute();
            } // for

            /*Set movie to now playing---------------------------- */
            String setMovieNowPlaying = "UPDATE movie SET display='Now Playing' WHERE movieID = ?";
            PreparedStatement setMovieNowPlayingStmt = connection.prepareStatement(setMovieNowPlaying);
            setMovieNowPlayingStmt.setString(1, inputs[2]);
            setMovieNowPlayingStmt.executeUpdate();
            /*---------------------------------------------------- */
            
        } catch (SQLException e) {
            e.printStackTrace();
            return "FAILURE";
        } // try
        return "SUCCESS";

        }
    
}
