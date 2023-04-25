import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.io.*;

public class bookMovie {
    private ResultSet results;
    final String secretKey = "ylwqc";
    SendEmail email = new SendEmail();
    
    public String bookMovieEx(String[] inputs, Connection connection) throws IOException {
        try{
            String sql = "insert into booking (userID, movieShowID, noChildTickets, noAdultTickets, noSeniorTickets, totalPrice, promoID)" +
                    "values (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStmt = connection.prepareStatement(sql);
        
                preparedStmt.setString(1, inputs[1]); //userID
                preparedStmt.setString(2, inputs[2]); //movieShowID
                preparedStmt.setString(3, inputs[3]); //noChildTickets
                preparedStmt.setString(4, inputs[4]); //noAdultTickets
                preparedStmt.setString(5, inputs[5]); //noSeniorTickets
                preparedStmt.setString(6, inputs[6]); //totalPrice
                preparedStmt.setString(7, inputs[7]); //promoID
             
                preparedStmt.executeUpdate();
                /*SendEmail sendConfirmation = new SendEmail();
                String message = "Your confirmation code is: ";
                sendConfirmation.sendEmail(inputs[4], message,"Confirmation Code");*/
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return "FAILURE";
            } // try
            
        return "SUCCESS";
    }
}
