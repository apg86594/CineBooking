import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.io.*;


public class registerUser {


    private ResultSet results;
    final String secretKey = "ylwqc";
    SendEmail email = new SendEmail();
    
    public String registerUserEx(String[] inputs, Connection connection) throws IOException {
            String findDupUser = "select * from cinemabookingsystem.user where ? = email";
            PreparedStatement findDupUserStmt;
            try {
                findDupUserStmt = connection.prepareStatement(findDupUser);
            encryptObject encrypter = new encryptObject();
            Random r = new Random();
            String randomNumber = String.format("%04d", 10000 + r.nextInt(9999));
            try {
                findDupUserStmt.setString(1, inputs[4]);
                results = findDupUserStmt.executeQuery();
                if (results.next()) {
                    return "User already exists";
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } // try
            String sql = "insert into user (password, firstName, lastName, email, USERTYPE, billingAddressLine1, ACTIVE, confirm, cardnum, securitynum, expmonth, expdate)" +
                    "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStmt = connection.prepareStatement(sql);
        
                preparedStmt.setString(1, encrypter.encrypt(inputs[1],secretKey)); //password
                preparedStmt.setString(2, inputs[2]); //first
                preparedStmt.setString(3, inputs[3]); // last
                preparedStmt.setString(4, inputs[4]); // email
                preparedStmt.setString(5, inputs[5]); // Usertype
                preparedStmt.setString(6, inputs[6]); // billingAddress
                preparedStmt.setString(7, "0"); // active
                preparedStmt.setString(8, randomNumber); // confirm
                preparedStmt.setString(9, encrypter.encrypt(inputs[7],secretKey)); // cardnum
                preparedStmt.setString(10, encrypter.encrypt(inputs[8], secretKey)); // securitynum
                preparedStmt.setString(11, inputs[9]); // expmonth
                preparedStmt.setString(12, inputs[10]); // expdate
                preparedStmt.execute();
                SendEmail sendConfirmation = new SendEmail();
                sendConfirmation.sendEmail(inputs[4], (String)randomNumber);
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return "FAILURE";
            } // try
            //email.sendEmail(inputs[4],randomNumber);
            
        return "SUCCESS";
    }
    
}
