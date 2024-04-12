package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
	@Override
	public void start(Stage stage) {
	    try {
	    	stage.initStyle(StageStyle.DECORATED);
            stage.setResizable(false);
	        Parent root = FXMLLoader.load(getClass().getResource("/LoginForm.fxml"));
	        stage.setTitle("Login Form");
	        stage.getIcons().add(new Image(getClass().getResourceAsStream("/rent.png")));
	        Scene loginScene = new Scene(root);
	        loginScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm()); // Corrected path

	        
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
