import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.*;

public class RequestHandler{
        String url = "jdbc:MySQL://localhost:3306/cinemabookingsystem";
        String username = "root";
        String password = "Test123";

        public String handleRequest(String message) {
            String[] inputs = message.split(",",-2);
            String command = inputs[0];
            if(command == "REGISTER") {
                message = registerUser(inputs);
            }
            return message;
        } // handleRequest

        public String registerUser(String[] inputs) {
            try(Connection connection = DriverManager.getConnection(url, username, password)) {
            Statement stmt = connection.createStatement();
            String sql = "insert into user (password, firstName, lastName, email, USERTYPE, billingAddress)" + 
            "values (?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStmt = connection.prepareStatement(sql);
            try {
                preparedStmt.setString(1, inputs[1]);
            
            preparedStmt.setString(2, inputs[2]);
            preparedStmt.setString(3, inputs[3]);
            preparedStmt.setString(4, inputs[4]);
            preparedStmt.setString(5, inputs[5]);
            preparedStmt.setString(6, inputs[6]);
            preparedStmt.execute();
            } catch (SQLException e) {
             // TODO Auto-generated catch block
                e.printStackTrace();
            } // try
            connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } // try
            return "SUCCESS";
        }
} // RequestHandler