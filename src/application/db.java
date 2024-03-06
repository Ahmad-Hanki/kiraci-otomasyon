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
 // Method to insert a new record into the database
    public static String createOne(String tenantId, String fullName, String gender, String phone, String rentalDate, String rentalPeriod) {
        // Validate inputs
        if (fullName.isEmpty()) {
            return "Full name cannot be empty.";
        }

        if (gender.isEmpty() || (!gender.equalsIgnoreCase("Male") && !gender.equalsIgnoreCase("Female"))) {
            return "Invalid gender. Gender must be either 'Male' or 'Female'.";
        }

        if (phone.isEmpty()) {
            return "Phone number cannot be empty.";
        }

        if (rentalDate.isEmpty()) {
            return "Rental date cannot be empty.";
        }

        // Validate phone number format and length
        if (!phone.matches("\\d{8,11}")) {
            return "Invalid phone number format or length.";
        }

        // Extract the number part from the rental period string
        String[] parts = rentalPeriod.split(" ");
        if (parts.length != 2 || !parts[1].equals("Months")) {
            return "Invalid rental period format. Expected format: <number> Months";
        }

        // Validate tenant ID length
        if (tenantId.length() < 1 || tenantId.length() > 20) {
            return "Invalid tenant ID length. It must be between 1 and 20 characters.";
        }

        // Proceed with inserting data into the database
        String query = "INSERT INTO tenant (tenant_id, full_name, gender, phone, rental_date, rental_period) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, tenantId);
            stmt.setString(2, fullName);
            stmt.setString(3, gender);
            stmt.setString(4, phone);
            stmt.setString(5, rentalDate); // Pass rentalDate as String
            stmt.setString(6, rentalPeriod); // Inserting rental period as string

            // Execute the update
            stmt.executeUpdate();

            System.out.println("New record inserted successfully!");
            return null; // Return null to indicate success
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error inserting record: " + e.getMessage(); // Return error message
        }
    }


}
