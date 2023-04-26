// using SendGrid's Java Library
// https://github.com/sendgrid/sendgrid-java
import com.sendgrid.*;
import java.io.IOException;

public class SendEmail {
    public void sendEmail(String email, String message, String parSubject) throws IOException {
        Email from = new Email("cinemabookingB2@gmail.com");
        String subject = parSubject;
        Email to = new Email(email);
        Content content = new Content("text/plain", message);
        Mail mail = new Mail(from, subject, to, content);
    
        SendGrid sg = new SendGrid("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");
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