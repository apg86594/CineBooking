// using SendGrid's Java Library
// https://github.com/sendgrid/sendgrid-java
import com.sendgrid.*;
import java.io.IOException;

public class SendEmail {
    public void sendEmail(String email, String confirmationCode) throws IOException {
        Email from = new Email("cinemabookingB2@gmail.com");
        String subject = "Confirmation Code";
        Email to = new Email(email);
        Content content = new Content("text/plain", "Your confirmation code is: " + confirmationCode);
        Mail mail = new Mail(from, subject, to, content);
    
        SendGrid sg = new SendGrid(System.getenv("SENDGRID_API_KEY"));
        Request request = new Request();
        try {
          request.setMethod(Method.POST);
          request.setEndpoint("mail/send");
          request.setBody(mail.build());
          Response response = sg.api(request);
          System.out.println(response.getStatusCode());
          System.out.println(response.getBody());
          System.out.println(response.getHeaders());
        } catch (IOException ex) {
          throw ex;
        }
      }
}