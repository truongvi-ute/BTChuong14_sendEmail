//package data;
//
//import java.sql.*;
//
//public class ConnectionPool {
//
//    private static ConnectionPool pool = null;
//
//    // Constructor private để chặn việc tạo đối tượng từ bên ngoài
//    private ConnectionPool() {
//        try {
//            // Load Driver PostgreSQL
//            Class.forName("org.postgresql.Driver");
//        } catch (ClassNotFoundException e) {
//            System.out.println(e);
//        }
//    }
//
//    // Singleton: Đảm bảo chỉ có 1 ConnectionPool duy nhất chạy
//    public static synchronized ConnectionPool getInstance() {
//        if (pool == null) {
//            pool = new ConnectionPool();
//        }
//        return pool;
//    }
//
//    // Hàm lấy kết nối
//    public Connection getConnection() {
//        try {
//            String dbUrl = "jdbc:postgresql://dpg-d4nq9h15pdvs73ac3hb0-a.singapore-postgres.render.com:5432/render_db_fagx";
//            String username = "render_db_fagx_user";
//            String password = "hVrfapv3nbQ2UUTecQDAXpoxDgpr8Mef";
//
//            return DriverManager.getConnection(dbUrl, username, password);
//        } catch (SQLException e) {
//            System.out.println(e);
//            return null;
//        }
//    }
//
//    // Hàm giải phóng kết nối
//    public void freeConnection(Connection c) {
//        try {
//            if (c != null) {
//                c.close();
//            }
//        } catch (SQLException e) {
//            System.out.println(e);
//        }
//    }
//}

package data;

import java.sql.*;
import javax.sql.DataSource;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class ConnectionPool {

    private static ConnectionPool pool = null;
    private static DataSource dataSource = null;

    // Constructor private: Chỉ chạy 1 lần để tìm DataSource từ Tomcat
    private ConnectionPool() {
        try {
            InitialContext ic = new InitialContext();
            // Tìm Resource có tên "jdbc/murach" như đã khai báo trong XML
            dataSource = (DataSource) ic.lookup("java:/comp/env/jdbc/murach");
        } catch (NamingException e) {
            System.out.println("Lỗi lookup JNDI: " + e);
        }
    }

    public static synchronized ConnectionPool getInstance() {
        if (pool == null) {
            pool = new ConnectionPool();
        }
        return pool;
    }

    // Lấy connection từ DataSource (Pool) chứ không tạo mới thủ công
    public Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            System.out.println("Lỗi lấy connection từ Pool: " + e);
            return null;
        }
    }

    public void freeConnection(Connection c) {
        try {
            if (c != null) {
                c.close(); // Trả kết nối về Pool để tái sử dụng
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}