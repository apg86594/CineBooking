import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;
import java.io.*;

public class RequestHandler {
    private ResultSet results;
    String url = "jdbc:MySQL://localhost:3306/cinemabookingsystem"; // change port if server is on different port
    String username = "root"; // set user name to local server username
    String password = "Mala0905hello"; // set password to local server password
    final String secretKey = "ylwqc";
    SendEmail email = new SendEmail();

    public String handleRequest(String message) throws IOException {
        String[] inputs = message.split(",", -2);
        String command = inputs[0];
        if (command.equals("REGISTER")) {
            message = registerUser(inputs);
        } else if (command.equals("LOGIN")) {
            message = loginUser(inputs);
        } else if (command.equals("EDIT")) {
            message = editUser(inputs);
        } else if (command.equals("CONFIRM")) {
            message = confirmation(inputs);
        }
        return message;
    } // handleRequest

    public String registerUser(String[] inputs) throws IOException {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String findDupUser = "select * from cinemabookingsystem.user where ? = email";
            PreparedStatement findDupUserStmt = connection.prepareStatement(findDupUser);
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
            String sql = "insert into user (password, firstName, lastName, email, USERTYPE, billingAddress, ACTIVE, confirm, cardnum, securitynum, expmonth, expdate)" +
                    "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStmt = connection.prepareStatement(sql);
            try {
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
            } // try
            //email.sendEmail(inputs[4],randomNumber);
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
            encryptObject encrypter = new encryptObject();
            try {
                findUserStmt.setString(1, inputs[1]);
                findUserStmt.setString(2, encrypter.encrypt(inputs[2],secretKey));
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
                    tempResults = findPrivilegesStmt.executeQuery();
                    tempResults.next();
                    String userType = tempResults.getString("UserTypeName");
                    String email = results.getString("email");
                    String userID = results.getString("userID");
                    String password = encrypter.decrypt(results.getString("password"),secretKey);
                    String firstName = results.getString("firstName");
                    String lastName = results.getString("lastName");
                    String billingAddress = results.getString("billingAddress");
                    String ACTIVE = results.getString("ACTIVE");
                    String cardnum = encrypter.decrypt(results.getString("cardnum"),secretKey);
                    String securitynum = encrypter.decrypt(results.getString("securitynum"),secretKey);
                    String expmonth = results.getString("expmonth");
                    String expdate = results.getString("expdate");

                    return ("SUCCESS," + userID + "," + password + "," + firstName + "," + lastName + "," + email +"," + userType + "," + 
                    billingAddress + "," + ACTIVE + "," + cardnum + "," + securitynum + "," + expmonth + "," + expdate);

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
        encryptObject encrypter = new encryptObject();
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String findUser = "select * from cinemabookingsystem.user where ? = email";
            PreparedStatement findUserStmt = connection.prepareStatement(findUser);
            findUserStmt.setString(1, inputs[4]);
            results = findUserStmt.executeQuery();
            if(results.next()) { 
                String userEmail = results.getString("email");
                String sql = "UPDATE user SET password = ?, firstName=?, lastName=?, email=?, USERTYPE=?, billingAddress=?, ACTIVE=?, cardnum=?, securitynum=?, expmonth=?,expdate=?" +
                    "WHERE email = ?";
                    PreparedStatement preparedStmt = connection.prepareStatement(sql);
            try {
                preparedStmt.setString(1, encrypter.encrypt(inputs[1],secretKey)); //password
                preparedStmt.setString(2, inputs[2]); //firstName
                preparedStmt.setString(3, inputs[3]); //lastName
                preparedStmt.setString(4, inputs[4]); //email
                preparedStmt.setString(5, inputs[5]); //usertype
                preparedStmt.setString(6, inputs[6]); //billingaddress
                preparedStmt.setString(7, "1"); //active
                preparedStmt.setString(8, encrypter.encrypt(inputs[7],secretKey)); // cardnum
                preparedStmt.setString(9, encrypter.encrypt(inputs[8],secretKey)); // securitynum
                preparedStmt.setString(10,inputs[9]); // expmonth
                preparedStmt.setString(11,inputs[10]); // expdate
                preparedStmt.setString(12, userEmail);
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

    public String confirmation(String[] inputs) { 
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
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
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
                return "FAILURE";
            } 
            return "success";
    }


} // RequestHandler

