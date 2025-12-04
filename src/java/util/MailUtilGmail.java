package util;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class MailUtilGmail {

    public static void sendMail(String to, String from, String subject, String body, boolean bodyIsHTML) throws MessagingException {
        
        // 1 - get a mail session
        Properties props = new Properties();
        props.put("mail.transport.protocol", "smtp"); 

        props.put("mail.smtp.host", "smtp.gmail.com");

        // QUAN TRỌNG: Đổi port 465 -> 587
        props.put("mail.smtp.port", 587); 

        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.quitwait", "false");

        // QUAN TRỌNG: Bắt buộc bật STARTTLS cho port 587
        props.put("mail.smtp.starttls.enable", "true"); 

        // Vẫn giữ dòng này để tránh lỗi SSL Handshake như lúc nãy
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");
        Session session = Session.getDefaultInstance(props);
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
        // Lưu ý: Thay thế email và password thực tế của bạn ở dưới
        transport.connect("nguyendoantruongvi11@gmail.com", "khgn pvmm ldma bnbu"); 
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
    }
}