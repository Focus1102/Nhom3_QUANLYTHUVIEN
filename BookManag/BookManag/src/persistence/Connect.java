package persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {
    private static final String URL = "jdbc:mysql://localhost:3306/quanlysachh";
    private static final String USER = "root";
    private static final String PASSWORD = "2001";
    
    // Constructor private để ngăn tạo đối tượng từ bên ngoài lớp
    private Connect() {
        // Không cần khởi tạo kết nối ở đây
    }

    // Phương thức tĩnh để lấy kết nối cơ sở dữ liệu
    public static Connection getConnection() {
        Connection connection = null;
        try {
            // Tải driver cho ứng dụng
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Lấy kết nối đến CSDL
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Kết nối thành công: " + connection.toString());
        } catch (ClassNotFoundException e) {
            System.err.println("Không tìm thấy driver: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Lỗi kết nối cơ sở dữ liệu: " + e.getMessage());
        }
        return connection;
    }
    
    // Phương thức để đóng kết nối cơ sở dữ liệu
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Kết nối đã được đóng.");
            } catch (SQLException e) {
                System.err.println("Lỗi khi đóng kết nối: " + e.getMessage());
            }
        }
    }
}
