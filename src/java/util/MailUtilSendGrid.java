package util;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

/**
 * Dự phòng: Dùng SendGrid SMTP nếu Gmail không hoạt động trên Render
 * 
 * Setup SendGrid:
 * 1. Đăng ký tại: https://sendgrid.com (Free: 100 emails/day)
 * 2. Tạo API Key tại: Settings > API Keys
 * 3. Set env variables:
 *    - SENDGRID_API_KEY=your_api_key
 *    - SENDGRID_FROM_EMAIL=your_verified_email
 */
public class MailUtilSendGrid {

    public static void sendMail(String to, String from, String subject, String body, boolean bodyIsHTML) throws MessagingException {
        
        // Lấy SendGrid API Key từ environment
        String apiKey = System.getenv("SENDGRID_API_KEY");
        String fromEmail = System.getenv("SENDGRID_FROM_EMAIL");
        
        if (apiKey == null || apiKey.isEmpty()) {
            throw new MessagingException("SENDGRID_API_KEY not configured");
        }
        if (fromEmail == null || fromEmail.isEmpty()) {
            fromEmail = from; // Fallback to provided from address
        }
        
        // SendGrid SMTP configuration
        Properties props = new Properties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.host", "smtp.sendgrid.net");
        props.put("mail.smtp.port", 587);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.starttls.required", "true");
        
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("apikey", apiKey);
            }
        });
        
        session.setDebug(true);

        // Create message
        Message message = new MimeMessage(session);
        message.setSubject(subject);
        
        if (bodyIsHTML) {
            message.setContent(body, "text/html");
        } else {
            message.setText(body);
        }

        // Address the message
        message.setFrom(new InternetAddress(fromEmail));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));

        // Send
        Transport.send(message);
    }
}
