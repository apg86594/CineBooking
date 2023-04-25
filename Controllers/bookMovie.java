import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.io.*;

public class bookMovie {
    private ResultSet results;
    SendEmail email = new SendEmail();
    
    public String bookMovieEx(String[] inputs, Connection connection) throws IOException {
        try{
            String sql = "insert into booking (userID, movieShowID, noChildTickets, noAdultTickets, noSeniorTickets, totalPrice, promoID, seatIDs)" +
                    "values (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStmt = connection.prepareStatement(sql);
        
                preparedStmt.setString(1, inputs[1]); //userID
                preparedStmt.setString(2, inputs[2]); //movieShowID
                preparedStmt.setString(3, inputs[3]); //noChildTickets
                preparedStmt.setString(4, inputs[4]); //noAdultTickets
                preparedStmt.setString(5, inputs[5]); //noSeniorTickets
                preparedStmt.setString(6, inputs[6]); //totalPrice
                preparedStmt.setString(7, inputs[7]); //promoID
                preparedStmt.setString(8, inputs[8]); //seatIDs
             
                preparedStmt.executeUpdate();

                /* Set each seat in movieshow seats to unavailable and decrement the number of available seats from movieshow */
                String[] seatIDs = inputs[8].split(":", -2);
                String getSeatID = "UPDATE movieshowseats SET available=0 WHERE ? = seatID and ? = movieShowID";
                PreparedStatement getSeatIDStmt = connection.prepareStatement(getSeatID);
                for (String seatID : seatIDs) {
                    getSeatIDStmt.setString(1,seatID);
                    getSeatIDStmt.setString(2,inputs[2]);
                    getSeatIDStmt.executeUpdate();
                }
                String getAvailableSeats = "select * from cinemabookingsystem.movieshow where ? = movieShowID";
                PreparedStatement getAvailableStmt = connection.prepareStatement(getAvailableSeats);
                getAvailableStmt.setString(1, inputs[2]);
                results = getAvailableStmt.executeQuery();
                results.next();
                int availableSeats = results.getInt("availableSeats"); 
                String decreaseAvailableSeats = "UPDATE movieshow SET availableSeats=? WHERE ? = movieShowID";
                PreparedStatement decPreparedStatement = connection.prepareStatement(decreaseAvailableSeats);
                int newAvailableSeats = availableSeats - (Integer.parseInt(inputs[3]) + Integer.parseInt(inputs[4]) + Integer.parseInt(inputs[5]));
                decPreparedStatement.setInt(1, newAvailableSeats);
                decPreparedStatement.setString(2,inputs[2]);
                decPreparedStatement.executeUpdate();
                /* ----------------------------------------------------------------------------------------------------------- */


            String getMovieShowInfo = "select * from cinemabookingsystem.movieshow where ? = movieShowID";
            PreparedStatement getMovieShowInfoStmt = connection.prepareStatement(getMovieShowInfo);
            getMovieShowInfoStmt.setString(1, inputs[2]);
            results = getMovieShowInfoStmt.executeQuery();
            results.next();
            String showID = results.getString("showID");
            String movieID = results.getString("movieID");
            String auditoriumID = results.getString("auditoriumID");
            String showStart = results.getString("showStart");
            String getMovieTitle = "select * from cinemabookingsystem.movie where ? = movieID";
            String getAuditoriumName = "select * from cinemabookingsystem.auditorium where ? = audID";
            String getShowTime = "select * from cinemabookingsystem.showtimes where ? = showID";
            String getUserEmail = "select * from cinemabookingsystem.user where ? = userID";
            PreparedStatement getMovieTitleStmt = connection.prepareStatement(getMovieTitle);
            getMovieTitleStmt.setString(1,movieID);
            PreparedStatement getAuditoriumNameStmt = connection.prepareStatement(getAuditoriumName);
            getAuditoriumNameStmt.setString(1,auditoriumID);
            PreparedStatement getShowTimeStmt = connection.prepareStatement(getShowTime);
            getShowTimeStmt.setString(1, showID);
            PreparedStatement getUserEmailStmt = connection.prepareStatement(getUserEmail);
            getUserEmailStmt.setString(1, inputs[1]);
            results = getUserEmailStmt.executeQuery();
            results.next();
            String userEmail = results.getString("email");
            results = getMovieTitleStmt.executeQuery();
            results.next();
            String movieTitle = results.getString("title");
            results = getAuditoriumNameStmt.executeQuery();
            results.next();
            String auditoriumName = results.getString("audName");
            results = getShowTimeStmt.executeQuery();
            results.next();
            String showTime = results.getString("timeStamp");
            int numTickets = Integer.parseInt(inputs[3]) + Integer.parseInt(inputs[4]) + Integer.parseInt(inputs[5]); 
                SendEmail sendConfirmation = new SendEmail();
                String message = "This is a message to confirm your order for " + numTickets + " tickets to see " +
                movieTitle + ". Your show will be on " + 
                showStart + " at " + showTime + ". Your show is located in " + auditoriumName + ".";
                sendConfirmation.sendEmail(userEmail, message,"Order confirmation");
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return "FAILURE";
            } // try
            
        return "SUCCESS";
    }
}
