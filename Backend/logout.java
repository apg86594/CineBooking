import java.sql.ResultSet;
import java.io.*;
import org.json.simple.JSONObject;

public class logout {

    private ResultSet results;
    String url = "jdbc:MySQL://localhost:3306/cinemabookingsystem"; // change port if server is on different port
    String username = "root"; // set user name to local server username
    String password = "Test123"; // set password to local server password
    final String secretKey = "ylwqc";
    SendEmail email = new SendEmail();

    public String logoutEx() {
        JSONObject jsonObject = new JSONObject();
                    jsonObject.put("userID","");
                    jsonObject.put("password","");
                    jsonObject.put("firstName","");
                    jsonObject.put("lastName","");
                    jsonObject.put("email","");
                    jsonObject.put("userType","");
                    jsonObject.put("billingAddress", "");
                    jsonObject.put("ACTIVE", "");
                    jsonObject.put("cardnum", "");
                    jsonObject.put("securitynum", "");
                    jsonObject.put("expmonth", "");
                    jsonObject.put("expdate", "");
                    FileWriter file;
                    try {
                        file = new FileWriter("./UserView/login-user-info.json");
                        file.write(jsonObject.toJSONString());
                        file.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        return "FAILURE";
                    }
                    return "SUCCESS";
                    
    }

    public String logout() {
        return null;
    }
    
}
