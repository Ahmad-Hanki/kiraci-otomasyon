package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class database {

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/javafx", "root", "ZxOoO1234");
    }

    // Method to get data from the database
    public static ResultSet getData() {
        try {
            Connection conn = getConnection();
            String query = "SELECT tenant_id, full_name, rental_period, price, paid from tenant";
            PreparedStatement statement = conn.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            return resultSet;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    

 // Method to update the price and paid of a tenant
    public static void updatePricePaid(String id, double price, boolean paid) {
        try {
            Connection conn = getConnection();
            String query = "UPDATE tenant SET price = ?, paid = ? WHERE tenant_id = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setDouble(1, price);
            statement.setBoolean(2, paid);
            statement.setString(3, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    
}
