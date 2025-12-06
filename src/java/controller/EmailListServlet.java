//package controller;
//
//import java.io.*;
//import javax.mail.MessagingException;
//import javax.servlet.*;
//import javax.servlet.http.*;
//import model.User;
//import data.UserDB;
//import javax.servlet.annotation.WebServlet;
//import util.*;
//
//@WebServlet(urlPatterns = {"/emailList"})
//public class EmailListServlet extends HttpServlet {
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
//            throws ServletException, IOException {
//        request.setCharacterEncoding("UTF-8");
//        response.setCharacterEncoding("UTF-8");
//        // get current action
//        String action = request.getParameter("action");
//        if (action == null) {
//            action = "join"; // default action
//        }
//
//        // perform action and set URL to appropriate page
//        String url = "/index.jsp"; 
//        
//        if (action.equals("join")) {
//            url = "/index.jsp"; // the "join" page
//        } 
//        else if (action.equals("add")) {
//            // get parameters from the request
//            String firstName = request.getParameter("firstName");
//            String lastName = request.getParameter("lastName");
//            String email = request.getParameter("email");
//
//            // store data in User object
//            User user = new User(firstName, lastName, email);
//            UserDB.insert(user);
//            request.setAttribute("user", user);
//
//            // send email to user
//            String to = email;
//            String from = "nguyendoantruongvi11@gmail.com";
//            String subject = "Welcome to our email list";
//            String body = "Dear " + firstName + ",\n\n" +
//                          "Thanks for joining our email list. We'll make sure to send " +
//                          "you announcements about new products and promotions.\n" +
//                          "Have a great day and thanks again!\n\n" +
//                          "Kelly Slivkoff\n" +
//                          "Mike Murach & Associates";
//            
//            boolean isBodyHTML = false;
//
//            try {
//                // Gửi mail bằng class tiện ích (MailUtilLocal hoặc MailUtilGmail)
//                MailUtilGmail.sendMail(to, from, subject, body, isBodyHTML);
//            } catch (MessagingException e) {
//                String errorMessage = "ERROR: Unable to send email. " +
//                        "Check Tomcat logs for details.<br>" +
//                        "NOTE: You may need to configure your system " +
//                        "as described in chapter 14.<br>" +
//                        "ERROR MESSAGE: " + e.getMessage();
//                e.printStackTrace();
//                request.setAttribute("errorMessage", errorMessage);
//                
//                // Log lỗi vào file log của server
//                this.log("Unable to send email. \n" +
//                         "Here is the email you tried to send: \n" +
//                         "TO: " + email + "\n" +
//                         "FROM: " + from + "\n" +
//                         "SUBJECT: " + subject + "\n" +
//                         "\n" + body + "\n\n");
//            }
//            url = "/thanks.jsp";
//        }
//        
//        // forward request and response to the view
//        getServletContext()
//            .getRequestDispatcher(url)
//            .forward(request, response);
//    }
//}


package controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import model.User;
import data.UserDB;
import javax.servlet.annotation.WebServlet;
import util.MailUtilBrevo; // Import class mới

@WebServlet(urlPatterns = {"/emailList"})
public class EmailListServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        
        String action = request.getParameter("action");
        if (action == null) {
            action = "join"; 
        }

        String url = "/index.jsp"; 
        
        if (action.equals("join")) {
            url = "/index.jsp"; 
        } 
        else if (action.equals("add")) {
            // 1. Lấy thông tin từ form
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String email = request.getParameter("email");

            // 2. Kiểm tra email đã tồn tại chưa
            if (UserDB.emailExists(email)) {
                // Email đã tồn tại, hiển thị thông báo lỗi
                String errorMessage = "Email này đã được đăng ký. Vui lòng sử dụng email khác.";
                request.setAttribute("errorMessage", errorMessage);
                url = "/index.jsp";
            } else {
                // 3. Email chưa tồn tại, lưu vào Database
                User user = new User(firstName, lastName, email);
                UserDB.insert(user);
                request.setAttribute("user", user);

                // 4. Chuẩn bị nội dung Email (Dùng HTML thay vì text thường)
                String subject = "Welcome to our email list";
                
                String body = "<h3>Dear " + firstName + ",</h3>" +
                              "<p>Thanks for joining our email list. We'll make sure to send " +
                              "you announcements about new products and promotions.</p>" +
                              "<p>Have a great day and thanks again!</p>" +
                              "<br>" +
                              "<i>Kelly Slivkoff</i><br>" +
                              "<b>Mike Murach & Associates</b>";
                
                // 5. Gửi email qua Brevo API
                MailUtilBrevo.sendEmail(email, subject, body);
                
                // Chuyển hướng đến trang cảm ơn
                url = "/thanks.jsp";
            }
        }
        
        getServletContext()
            .getRequestDispatcher(url)
            .forward(request, response);
    }
}