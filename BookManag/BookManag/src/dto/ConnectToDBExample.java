package dto;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectToDBExample {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        //tai driver cho app
        Class.forName("com.mysql.cj.jdbc.Driver");
        
        String url = "jdbc:mysql://localhost:3306/T4";
        String user = "root";
        String password = "2001";
        //lay ket noi den CSDL db221403
        connection = DriverManager.getConnection(url, user, password);

        System.out.println(connection.toString());
    }

}