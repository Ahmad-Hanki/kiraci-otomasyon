package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class db {
    // Establish connection method
    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/javafx", "root", "ZxOoO1234");
    }

    // Method to fetch data from the database and return as a list of objects
    public static List<Object[]> selectAll() {
        List<Object[]> data = new ArrayList<>();

        // Establish connection
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM tenant")) {

            // Iterate over the result set and fetch data
            while (rs.next()) {
                Object[] rowData = new Object[6]; // Assuming 6 columns in your table

                // Populate rowData with data from the result set
                rowData[0] = rs.getInt("tenant_id");
                rowData[1] = rs.getString("full_name");
                rowData[2] = rs.getString("gender");
                rowData[3] = rs.getString("phone");
                rowData[4] = rs.getDate("rental_date").toString();
                rowData[5] = rs.getString("rental_period");

                // Add rowData to the list
                data.add(rowData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return data;
    }

    
    // Method to insert a new record into the database
    public static void createOne(int tenantId, String fullName, String gender, String phone, String rentalDate, String rentalPeriod) {
        String query = "INSERT INTO tenant (tenant_id, full_name, gender, phone, rental_date, rental_period) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, tenantId);
            stmt.setString(2, fullName);
            stmt.setString(3, gender);
            stmt.setString(4, phone);
            stmt.setString(5, rentalDate);
            stmt.setString(6, rentalPeriod);
            
            // Execute the update
            stmt.executeUpdate();
            
            System.out.println("New record inserted successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}