import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class confirmation {

    private ResultSet results;
    final String secretKey = "ylwqc";
    SendEmail email = new SendEmail();
    
    public String confirmationEx(String[] inputs, Connection connection) { 
        try {
            String findUser = "select * from cinemabookingsystem.user where ? = email";
            PreparedStatement findUserStmt = connection.prepareStatement(findUser);
            findUserStmt.setString(1, inputs[1]);
            results = findUserStmt.executeQuery();
            if(results.next()) { 
                String confirmationCode = results.getString("confirm");
                String sql = "UPDATE user SET ACTIVE = ? where ? = email";
                PreparedStatement preparedStmt = connection.prepareStatement(sql);
                if(confirmationCode.equals(inputs[2])) { 
                    try {
                        preparedStmt.setString(1, "1");
                        preparedStmt.setString(2,inputs[1]);
                        preparedStmt.execute();
                    } catch (SQLException e) {
                        e.printStackTrace();
                        return "FAILURE";
                    } 
                    }
                    else {
                        System.out.println(" Failed to confirm");
                        return "failure";
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return "FAILURE";
            } 
            return "success";
    }

}
