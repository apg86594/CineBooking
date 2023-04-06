import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class addPromotion {

    final String secretKey = "ylwqc";

    // This function allows the admin to add a new movie to the database
    // The input is an array of strings that contain the movie information with a certain order
    public String addPromotionEx(String[] inputs, Connection connection) {
        
        //Connects to the database
        try {

            //Creates the SQL statement
            String sql = "INSERT INTO promotions (promotionCode, percentOff) VALUES (?, ?)";
            
            PreparedStatement preparedStmt = connection.prepareStatement(sql);

            try {
                //Sets the values of "question marks" in the SQL statement
                preparedStmt.setString(1, inputs[1]); // promotionCode
                preparedStmt.setString(2, inputs[2]); // percentOff
            
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
