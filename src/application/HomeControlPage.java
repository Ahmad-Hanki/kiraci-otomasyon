package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
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
    public void initialize() {
        // Call a method to retrieve the row count and update the label when the page loads
        updateRowCountLabel();
    }

    @FXML
    public void logOutHandler(MouseEvent event) {
        try {
            // Get the source node of the event
            Parent root = FXMLLoader.load(getClass().getResource("/LoginForm.fxml"));

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Sign In");
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

    private void updateRowCountLabel() {
        // Database connection parameters
        String url = "jdbc:mysql://localhost:3306/javaFx";
        String username = "root";
        String password = "ZxOoO1234";

        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            // Create a statement
            Statement statement = conn.createStatement();

            // Execute query to count rows
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) AS row_count FROM tenant");

            // Retrieve the row count
            if (resultSet.next()) {
                int rowCount = resultSet.getInt("row_count");
                // Update the label with the row count
                TotalLbl.setText(String.valueOf(rowCount));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}