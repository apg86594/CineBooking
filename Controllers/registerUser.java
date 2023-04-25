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
            String sql = "insert into user (password, firstName, lastName, email, USERTYPE, billingAddressLine1, billingAddressLine2, billingZip, billingCity," + 
            " billingState, shippingAddressLine1, shippingAddressLine2, shippingZip, shippingCity, shippingState, " + 
            "ACTIVE, confirm, cardnum, securitynum, expmonth, expdate, enabledPromotion)" +
                    "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStmt = connection.prepareStatement(sql);
        
                preparedStmt.setString(1, encrypter.encrypt(inputs[1],secretKey)); //password
                preparedStmt.setString(2, inputs[2]); //first
                preparedStmt.setString(3, inputs[3]); // last
                preparedStmt.setString(4, inputs[4]); // email
                preparedStmt.setString(5, inputs[5]); // Usertype
                preparedStmt.setString(6, inputs[6]); // billingAddressLine1
                preparedStmt.setString(7, inputs[7]); // billingAddressLine2
                preparedStmt.setString(8, inputs[8]); // billingZip
                preparedStmt.setString(9, inputs[9]); // billingCity
                preparedStmt.setString(10, inputs[10]); // billingState
                preparedStmt.setString(11, inputs[11]); // shippingAddressLine1
                preparedStmt.setString(12, inputs[12]); // shippingAddressLine2
                preparedStmt.setString(13, inputs[13]); // shippingZip
                preparedStmt.setString(14, inputs[14]); // shippingCity
                preparedStmt.setString(15, inputs[15]); // shippingState
                preparedStmt.setString(16, "0"); // active
                preparedStmt.setString(17, randomNumber); // confirm
                preparedStmt.setString(18, encrypter.encrypt(inputs[16],secretKey)); // cardnum
                preparedStmt.setString(19, encrypter.encrypt(inputs[17], secretKey)); // securitynum
                preparedStmt.setString(20, inputs[18]); // expmonth
                preparedStmt.setString(21, inputs[19]); // expdate
                preparedStmt.setString(22, inputs[20]); // enabledPromotion
                preparedStmt.execute();
                SendEmail sendConfirmation = new SendEmail();
                String message = "Your confirmation code is: " + (String)randomNumber;
                sendConfirmation.sendEmail(inputs[4], message,"Confirmation Code");
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return "FAILURE";
            } // try
            
        return "SUCCESS";
    }
    
}
