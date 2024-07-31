package persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {
    private static final String URL = "jdbc:mysql://localhost:3306/quanlysachh";
    private static final String USER = "root";
    private static final String PASSWORD = "2001";
    
    private static Connect instance;
    private Connection connection;

    // Constructor private để ngăn tạo đối tượng từ bên ngoài lớp
    private Connect() {
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
    }

    // Phương thức để lấy đối tượng Connect (Singleton Pattern)
    public static Connect getInstance() {
        if (instance == null) {
            synchronized (Connect.class) {
                if (instance == null) {
                    instance = new Connect();
                }
            }
        }
        return instance;
    }

    // Phương thức để lấy kết nối cơ sở dữ liệu
    public Connection getConnection() {
        if (connection == null || !isConnectionValid()) {
            // Tạo lại kết nối nếu kết nối hiện tại bị đóng hoặc không tồn tại
            try {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Kết nối mới thành công: " + connection.toString());
            } catch (SQLException e) {
                System.err.println("Lỗi khi tạo kết nối mới: " + e.getMessage());
            }
        }
        return connection;
    }

    // Phương thức để kiểm tra xem kết nối có còn hợp lệ không
    public boolean isConnectionValid() {
        try {
            return connection != null && !connection.isClosed();
        } catch (SQLException e) {
            System.err.println("Lỗi khi kiểm tra trạng thái kết nối: " + e.getMessage());
            return false;
        }
    }

    // Phương thức để đóng kết nối cơ sở dữ liệu
    public void closeConnection() {
        if (connection != null) {
            try {
                if (!connection.isClosed()) {
                    connection.close();
                    System.out.println("Kết nối đã được đóng.");
                }
            } catch (SQLException e) {
                System.err.println("Lỗi khi đóng kết nối: " + e.getMessage());
            }
        }
    }
}
