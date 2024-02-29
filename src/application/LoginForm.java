package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LoginForm {

    @FXML
    private TextField username_text;

    @FXML
    private TextField password_text;

    // Database credentials
    private  String url = "jdbc:mysql://localhost:3306/JavaFx";
    private  String user = "root"; // MySQL username
    private  String password = "ZxOoO1234"; // MySQL password

    @FXML
    public void verifyLogin(ActionEvent event) {
        String username = username_text.getText();
        String enteredPassword = password_text.getText(); // Rename the local variable

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String sql = "SELECT * FROM verifylogin WHERE username = ? AND password = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, username);
                statement.setString(2, enteredPassword); // Use the renamed local variable
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        // Credentials are correct, go to the main page
                        navigateToControlPage();
                    } else {
                        // Credentials are incorrect
                        System.out.println("Login failed: Incorrect username or password");
                        // Reset text fields and set placeholders
                        username_text.setText("");
                        username_text.setPromptText("Invalid username");
                        password_text.setText("");
                        password_text.setPromptText("Invalid password");
                        username_text.setStyle("-fx-prompt-text-fill: red;");
                        password_text.setStyle("-fx-prompt-text-fill: red;");

                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Login failed: Database connection error");
            e.printStackTrace();
        }
    }



    private void navigateToControlPage() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/ControlPage.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.initStyle(StageStyle.UTILITY);
            stage.setResizable(false);
            stage.show();

            // Close the current stage (login form)
            Stage loginStage = (Stage) username_text.getScene().getWindow();
            loginStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    public void forgotHandler(MouseEvent event) {
        try {
            // Load the ForgotPassword.fxml file
            Parent root = FXMLLoader.load(getClass().getResource("/ForgotPassword.fxml"));

            // Create a new stage for the ForgotPassword scene
            Stage stage = new Stage();

            stage.setScene(new Scene(root));
	        stage.initStyle(StageStyle.UTILITY);
            stage.setResizable(false);
            stage.show();


            // Close the current stage (login form)
            Stage loginStage = (Stage) username_text.getScene().getWindow();
            loginStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
