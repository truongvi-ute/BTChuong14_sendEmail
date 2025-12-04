package util;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class MailUtilGmail {

    public static void sendMail(String to, String from, String subject, String body, boolean bodyIsHTML) throws MessagingException {
        
        // Lấy thông tin từ biến môi trường (cho Render)
        String gmailEmail = System.getenv("GMAIL_EMAIL");
        String gmailPassword = System.getenv("GMAIL_APP_PASSWORD");
        
        // Fallback cho môi trường local (nếu không có env variables)
        if (gmailEmail == null || gmailEmail.isEmpty()) {
            gmailEmail = "nguyendoantruongvi11@gmail.com";
        }
        if (gmailPassword == null || gmailPassword.isEmpty()) {
            gmailPassword = "khgnpvmmldmabnbu";
        }
        
        // 1 - get a mail session
        Properties props = new Properties();
        props.put("mail.transport.protocol", "smtp"); 
        props.put("mail.smtp.host", "smtp.gmail.com");
        
        // Port 587 với STARTTLS - tương thích tốt hơn với Render
        props.put("mail.smtp.port", 587); 
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.quitwait", "false");
        
        // Bật STARTTLS cho port 587
        props.put("mail.smtp.starttls.enable", "true"); 
        props.put("mail.smtp.starttls.required", "true");
        
        // SSL protocols
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        
        Session session = Session.getInstance(props);
        session.setDebug(true);

        // 2 - create a message
        Message message = new MimeMessage(session);
        message.setSubject(subject);
        
        if (bodyIsHTML) {
            message.setContent(body, "text/html");
        } else {
            message.setText(body);
        }

        // 3 - address the message
        Address fromAddress = new InternetAddress(from);
        Address toAddress = new InternetAddress(to);
        message.setFrom(fromAddress);
        message.setRecipient(Message.RecipientType.TO, toAddress);

        // 4 - send the message
        Transport transport = session.getTransport();
        transport.connect(gmailEmail, gmailPassword); 
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
    }
}