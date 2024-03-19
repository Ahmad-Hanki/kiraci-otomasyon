package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class HomeControlPage {

    @FXML
    private Label TotalLbl;
    
    @FXML
    private Label totalPaid;
    
    @FXML
    private Label willBePaid;
    
    @FXML
    private Label unpaidtenants;
    
    @FXML
    public void initialize() {
        updateRowCountLabel();
    }

    @FXML
    public void logOutHandler(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/LoginForm.fxml"));

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Sign In");
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/rent.png")));
            stage.initStyle(StageStyle.DECORATED);
            stage.setResizable(false);
            stage.show();

            stage = (Stage) TotalLbl.getScene().getWindow();
            stage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void updateRowCountLabel() {
        String url = "jdbc:mysql://localhost:3306/javaFx";
        String username = "root";
        String password = "ZxOoO1234";

        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            // Query to get the total number of tenants
            String totalTenantsQuery = "SELECT COUNT(*) AS total_tenants FROM tenant";

            try (Statement statement = conn.createStatement()) {
                // Get total number of tenants
                ResultSet totalTenantsResultSet = statement.executeQuery(totalTenantsQuery);
                if (totalTenantsResultSet.next()) {
                    int totalTenantsCount = totalTenantsResultSet.getInt("total_tenants");
                    TotalLbl.setText(String.valueOf(totalTenantsCount));
                }

                // Query to get the sum of prices where paid is true
                String totalPaidQuery = "SELECT SUM(price) AS total_paid FROM tenant WHERE paid = true";

                // Query to get the sum of prices where paid is false
                String willBePaidQuery = "SELECT SUM(price) AS will_be_paid FROM tenant WHERE paid = false";

                // Query to count the number of unpaid tenants
                String unpaidTenantsQuery = "SELECT COUNT(*) AS unpaid_tenants FROM tenant WHERE paid = false";

                // Get total paid amount
                ResultSet totalPaidResultSet = statement.executeQuery(totalPaidQuery);
                if (totalPaidResultSet.next()) {
                    double totalPaidAmount = totalPaidResultSet.getDouble("total_paid");
                    totalPaid.setText(String.valueOf(totalPaidAmount));
                }

                // Get total amount to be paid
                ResultSet willBePaidResultSet = statement.executeQuery(willBePaidQuery);
                if (willBePaidResultSet.next()) {
                    double willBePaidAmount = willBePaidResultSet.getDouble("will_be_paid");
                    willBePaid.setText(String.valueOf(willBePaidAmount));
                }

                // Get the number of unpaid tenants
                ResultSet unpaidTenantsResultSet = statement.executeQuery(unpaidTenantsQuery);
                if (unpaidTenantsResultSet.next()) {
                    int unpaidTenantsCount = unpaidTenantsResultSet.getInt("unpaid_tenants");
                    unpaidtenants.setText(String.valueOf(unpaidTenantsCount));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    
    @FXML
	public void tenantControlHandler(MouseEvent event) {
    	 try {
             Parent root = FXMLLoader.load(getClass().getResource("/TenantControlPage.fxml"));

             Stage stage = new Stage();
             stage.setScene(new Scene(root));
             stage.setTitle("Control Tenants");
             stage.getIcons().add(new Image(getClass().getResourceAsStream("/rent.png")));
             stage.initStyle(StageStyle.DECORATED);
             stage.setResizable(false);
             stage.show();

             stage = (Stage) TotalLbl.getScene().getWindow();
             stage.close();
         } catch (IOException e) {
             e.printStackTrace();
         }
	}
    
    @FXML
   	public void UpdatePriceHandler(MouseEvent event) {
       	 try {
                // Get the source node of the event
                Parent root = FXMLLoader.load(getClass().getResource("/UpdatePrice.fxml"));

                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("Update Price");
                stage.getIcons().add(new Image(getClass().getResourceAsStream("/rent.png")));
                stage.initStyle(StageStyle.DECORATED);
                stage.setResizable(false);
                stage.show();

                // Close the current stage
                stage = (Stage) TotalLbl.getScene().getWindow();
                stage.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
   	}
    
}
