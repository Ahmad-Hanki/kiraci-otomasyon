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
    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/javafx", "root", "ZxOoO1234");
    }

    public static List<Object[]> selectAll() {
        List<Object[]> data = new ArrayList<>();

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM tenant")) {

            while (rs.next()) {
                Object[] rowData = new Object[6]; 

                rowData[0] = rs.getString("tenant_id");
                rowData[1] = rs.getString("full_name");
                rowData[2] = rs.getString("gender");
                rowData[3] = rs.getString("phone");
                rowData[4] = rs.getDate("rental_date").toString();
                rowData[5] = rs.getString("rental_period");

                data.add(rowData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return data;
    }

    public static String createOne(String tenantId, String fullName, String gender, String phone, String rentalDate, String rentalPeriod) {
    	
    	boolean exists = checkTenantExists(tenantId);
    	
		if (exists) {
			return "Tenant ID already exists.";
		}
	
        

        String[] parts = rentalPeriod.split(" ");
        if (parts.length != 2 || !parts[1].equals("Months")) {
            return "Invalid rental period format. Expected format: <number> Months";
        }

        if (tenantId.length() < 1 || tenantId.length() > 20) {
            return "Invalid tenant ID length. It must be between 1 and 20 characters.";
        }

        String query = "INSERT INTO tenant (tenant_id, full_name, gender, phone, rental_date, rental_period) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, tenantId);
            stmt.setString(2, fullName);
            stmt.setString(3, gender);
            stmt.setString(4, phone);
            stmt.setString(5, rentalDate); 
            stmt.setString(6, rentalPeriod); 

            // Execute the update
            stmt.executeUpdate();

            System.out.println("New record inserted successfully!");
            return null; 
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error inserting record: " + e.getMessage(); 
        }
    }

    
    
    public static String updateOne(String tenantId, String fullName, String gender, String phone, String rentalDate, String rentalPeriod) {


        boolean exists = checkTenantExists(tenantId);
        if (!exists) {
            return "No tenant found with ID: " + tenantId;
        }


     

        
        String[] parts = rentalPeriod.split(" ");
        if (parts.length != 2 || !parts[1].equals("Months")) {
            return "Invalid rental period format. Expected format: <number> Months";
        }

        
        String query = "UPDATE tenant SET full_name=?, gender=?, phone=?, rental_date=?, rental_period=? WHERE tenant_id=?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
        	stmt.setString(1, fullName);
        	stmt.setString(2, gender);
        	stmt.setString(3, phone);
        	stmt.setString(4, rentalDate); 
        	stmt.setString(5, rentalPeriod); 
        	stmt.setString(6, tenantId);
        	
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                return "Failed to update tenant with ID: " + tenantId;
            }

            System.out.println("Record updated successfully!");
            return null; 
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error updating record: " + e.getMessage(); 
        }
    }



    public static void deleteAll() {
        String query = "DELETE FROM tenant";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.executeUpdate();
            System.out.println("All tenants deleted successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String deleteById(String tenantId) {
        // Check if the tenant ID exists in the database
        boolean exists = checkTenantExists(tenantId);
        if (!exists) {
            return "Tenant does not exist";
        }

        String query = "DELETE FROM tenant WHERE tenant_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, tenantId);
            stmt.executeUpdate();
            System.out.println("Tenant with ID " + tenantId + " deleted successfully!");
            return null; // Return null to indicate success
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error deleting tenant: " + e.getMessage(); // Return error message
        }
    }
    
    private static boolean checkTenantExists(String tenantId) {
        String query = "SELECT COUNT(*) FROM tenant WHERE tenant_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, tenantId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    return count > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
