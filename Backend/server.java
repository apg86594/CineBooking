import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

class server {
    public static void main(String[] args) {
        String url = "jdbc:MySQL://localhost:3306/cinemabookingsystem";
        String username = "root";
        String password = "Test123";
        System.out.println("Connecting database...");
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            System.out.println("Database connected");
            Statement stmt = connection.createStatement();
            String sql = "insert into user (userID, password, firstName, lastName, email, USERTYPE, billingAddress)" + 
            "values (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStmt = connection.prepareStatement(sql);
            preparedStmt.setString(1, "1");
            preparedStmt.setString(2, "Test");
            preparedStmt.setString(3, "Charlie");
            preparedStmt.setString(4, "Skinner");
            preparedStmt.setString(5, "test");
            preparedStmt.setString(6, "1");
            preparedStmt.setString(7, "test");
            preparedStmt.execute();
            connection.close();
        } catch (SQLException e) {
            throw new IllegalStateException(e.getMessage());
        }

    }
}