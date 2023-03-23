import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.*;

public class RequestHandler {
    private ResultSet results;
    String url = "jdbc:MySQL://localhost:3306/cinemabookingsystem";
    String username = "root";
    String password = "Test123";

    public String handleRequest(String message) {
        String[] inputs = message.split(",", -2);
        String command = inputs[0];
        if (command.equals("REGISTER")) {
            message = registerUser(inputs);
        } else if (command.equals("LOGIN")) {
            message = loginUser(inputs);
        } else if (command.equals("EDIT")) {
            return "NOT IMPLEMENTED YET";
        } else if (command.equals("CONFIRM")) {
            return "NOT IMPLEMENTED YET";
        }
        return message;
    } // handleRequest

    public String registerUser(String[] inputs) {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String findDupUser = "select * from cinemabookingsystem.user where ? = email";
            PreparedStatement findDupUserStmt = connection.prepareStatement(findDupUser);
            try {
                findDupUserStmt.setString(1, inputs[4]);
                results = findDupUserStmt.executeQuery();
                if (results.next()) {
                    return "User already exists";
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } // try
            String sql = "insert into user (password, firstName, lastName, email, USERTYPE, billingAddress, ACTIVE)" +
                    "values (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStmt = connection.prepareStatement(sql);
            try {
                preparedStmt.setString(1, inputs[1]);
                preparedStmt.setString(2, inputs[2]);
                preparedStmt.setString(3, inputs[3]);
                preparedStmt.setString(4, inputs[4]);
                preparedStmt.setString(5, inputs[5]);
                preparedStmt.setString(6, inputs[6]);
                preparedStmt.setString(7, "0");
                preparedStmt.execute();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } // try
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return "FAILURE";
        } // try
        return "SUCCESS";
    }

    public String loginUser(String[] inputs) {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String findUser = "select * from cinemabookingsystem.user where ? = email and ? = password";
            PreparedStatement findUserStmt = connection.prepareStatement(findUser);
            try {
                findUserStmt.setString(1, inputs[1]);
                findUserStmt.setString(2, inputs[2]);
                results = findUserStmt.executeQuery();
                if (results.next()) {
                    String activationStatus = results.getString("ACTIVE");
                    String findActivation = "select * from cinemabookingsystem.active where ? = ActiveID";
                    PreparedStatement findActivationStmt = connection.prepareStatement(findActivation);
                    findActivationStmt.setString(1, activationStatus);
                    ResultSet tempResults = findActivationStmt.executeQuery();
                    tempResults.next();
                    String activationType = tempResults.getString("ActiveStatus");
                    if (!activationType.equals("ACTIVE")) {
                        return "NOTACTIVE";
                    }

                    String privileges = results.getString("USERTYPE");
                    String findPrivileges = "select * from cinemabookingsystem.usertype where ? = userTypeID";
                    PreparedStatement findPrivilegesStmt = connection.prepareStatement(findPrivileges);

                    findPrivilegesStmt.setString(1, privileges);
                    results = findPrivilegesStmt.executeQuery();
                    results.next();
                    String userType = results.getString("UserTypeName");
                    return ("SUCCESS," + userType);

                } else {
                    String findUserEmail = "select * from cinemabookingsystem.user where ? = email";
                    PreparedStatement findUserEmailStmt = connection.prepareStatement(findUserEmail);

                    findUserEmailStmt.setString(1, inputs[1]);
                    results = findUserEmailStmt.executeQuery();
                    if (results.next()) {
                        return "BADPASSWORD";
                    }

                }
            } catch (SQLException e) {
                e.printStackTrace();
            } // try
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "BADUSER";
    }
    public String editUser(String[] inputs) { 
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String findUser = "select * from cinemabookingsystem.user where ? = email";
            PreparedStatement findUserStmt = connection.prepareStatement(findUser);
            findUserStmt.setString(1, inputs[4]);
            results = findUserStmt.executeQuery();
            if(results.next()) { 
                String userEmail = results.getString("email");
                String sql = "UPDATE user SET password = ?, firstName=?, lastName=?, email=?, USERTYPE=?, billingAddress=?, ACTIVE)" +
                    "WHERE email = " + userEmail;
                    PreparedStatement preparedStmt = connection.prepareStatement(sql);
            try {
                preparedStmt.setString(1, inputs[1]);
                preparedStmt.setString(2, inputs[2]);
                preparedStmt.setString(3, inputs[3]);
                preparedStmt.setString(4, inputs[4]);
                preparedStmt.setString(5, inputs[5]);
                preparedStmt.setString(6, inputs[6]);
                preparedStmt.setString(7, "1");
                preparedStmt.execute();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } // try
            }
            else 
            System.out.println("failure");
            connection.close();
    } catch (SQLException e) {
        e.printStackTrace();
        return "FAILURE";
    } 
    return "Success";
    }

} // RequestHandler

