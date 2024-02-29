package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
	@Override
	public void start(Stage stage) {
	    try {
	        stage.initStyle(StageStyle.UTILITY);
            stage.setResizable(false);
	        Parent root = FXMLLoader.load(getClass().getResource("/LoginForm.fxml"));

	        Scene loginScene = new Scene(root);
	        loginScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm()); // Corrected path

	        // Set the scene for the login form
	        
	        stage.setScene(loginScene);
	        stage.show();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}


    public static void main(String[] args) {
        launch(args);
    }
}
