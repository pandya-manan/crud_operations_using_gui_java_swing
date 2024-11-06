package business;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestConnection {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/overseas_transport_management_system";
        String username = "postgres";
        String password = "12345678";

        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            System.out.println("Connected successfully");
        } catch (SQLException e) {
            System.err.println("Connection failed: " + e.getMessage());
        }
    }
}
