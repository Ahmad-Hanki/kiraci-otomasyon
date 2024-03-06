package application;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class UpdatePrice {
	
	@FXML
	private Label summary;
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

	            stage = (Stage) summary.getScene().getWindow();
	            stage.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	            
	        }
	    }
	  
	  @FXML
	    public void HomePageHandler(MouseEvent event) {
	        try {
	            // Get the source node of the event
	            Parent root = FXMLLoader.load(getClass().getResource("/HomeControlPage.fxml"));

	            Stage stage = new Stage();
	            stage.setScene(new Scene(root));
	            stage.setTitle("Sign In");
	            stage.getIcons().add(new Image(getClass().getResourceAsStream("/rent.png")));
	            stage.initStyle(StageStyle.DECORATED);
	            stage.setResizable(false);
	            stage.show();

	            // Close the current stage
	            stage = (Stage) summary.getScene().getWindow();
	            stage.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	  
	    @FXML
		public void TenantControlHandler(MouseEvent event) {
	    	 try {
	             // Get the source node of the event
	             Parent root = FXMLLoader.load(getClass().getResource("/TenantControlPage.fxml"));

	             Stage stage = new Stage();
	             stage.setScene(new Scene(root));
	             stage.setTitle("Control Tenants");
	             stage.getIcons().add(new Image(getClass().getResourceAsStream("/rent.png")));
	             stage.initStyle(StageStyle.DECORATED);
	             stage.setResizable(false);
	             stage.show();

	             // Close the current stage
	             stage = (Stage) summary.getScene().getWindow();
	             stage.close();
	         } catch (IOException e) {
	             e.printStackTrace();
	         }
		}
	    
}
