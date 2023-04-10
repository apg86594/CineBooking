import java.sql.*;


public class deleteMovie {
    public String deleteMovieEx(String[] inputs, Connection connection) {
        try {
            String deleteMov = "delete from cinemabookingsystem.movie where movieID = ?";
            PreparedStatement deleteMovStmt = connection.prepareStatement(deleteMov);
            deleteMovStmt.setString(1,inputs[1]);
            deleteMovStmt.execute();
            }
            catch(SQLException e) { 
                e.printStackTrace();
                return "failure";
            }
        return "SUCCESS";
    }
}