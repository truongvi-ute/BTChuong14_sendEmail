package util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class MailUtilBrevo {

    // URL API gửi mail của Brevo
    private static final String API_URL = "https://api.brevo.com/v3/smtp/email";
    
    // Lấy API Key từ biến môi trường (Cấu hình trên Render)
    // Nếu chạy local mà biến này null, bạn có thể điền tạm key vào để test (nhưng nhớ xóa khi push git)
    private static final String API_KEY = System.getenv("BREVO_API_KEY"); 
    // Email người gửi (Phải trùng với email bạn dùng đăng ký Brevo)
    private static final String SENDER_EMAIL = "nguyendoantruongvi11@gmail.com"; 
    private static final String SENDER_NAME = "Truong Vi";

    public static void sendEmail(String toEmail, String subject, String bodyHtml) {
        
        try {
            URL url = new URL(API_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            
            conn.setRequestMethod("POST");
            conn.setRequestProperty("accept", "application/json");
            conn.setRequestProperty("api-key", API_KEY); // Đây là chìa khóa để Brevo cho phép gửi
            conn.setRequestProperty("content-type", "application/json");
            conn.setDoOutput(true);

            // Xử lý chuỗi JSON an toàn (tránh lỗi khi bodyHtml có ký tự đặc biệt)
            String safeBody = bodyHtml
                    .replace("\"", "\\\"")  // Escape dấu ngoặc kép
                    .replace("\n", "")      // Xóa xuống dòng
                    .replace("\r", "");

            // Tạo chuỗi JSON thủ công
            String jsonInputString = "{"
                    + "\"sender\":{\"name\":\"" + SENDER_NAME + "\",\"email\":\"" + SENDER_EMAIL + "\"},"
                    + "\"to\":[{\"email\":\"" + toEmail + "\"}],"
                    + "\"subject\":\"" + subject + "\","
                    + "\"htmlContent\":\"" + safeBody + "\""
                    + "}";

            // Gửi request
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            // Kiểm tra kết quả
            int responseCode = conn.getResponseCode();
            if (responseCode == 201 || responseCode == 200) {
                System.out.println("✅ Email sent successfully via Brevo API!");
            } else {
                System.out.println("❌ Failed to send email. Response Code: " + responseCode);
                // Đọc lỗi chi tiết từ server nếu có
                try(BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getErrorStream(), "utf-8"))) {
                    StringBuilder response = new StringBuilder();
                    String responseLine = null;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                    System.out.println("Error details: " + response.toString());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("❌ Error sending email: " + e.getMessage());
        }
    }
}