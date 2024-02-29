package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;


public class ForgotPassword {
	
	Stage stage;
	Scene scene;
	Parent root;
    
    public void BackToLogin(ActionEvent event) {
        try {
			Parent root = FXMLLoader.load(getClass().getResource("/LoginForm.fxml"));
			stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			scene = new Scene(root);
		       stage.setTitle("Login Form");
		     stage.getIcons().add(new Image(getClass().getResourceAsStream("/rent.png")));

			stage.setScene(scene);
			stage.show();

			
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
