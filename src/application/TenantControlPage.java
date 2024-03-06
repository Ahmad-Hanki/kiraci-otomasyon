package application;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.beans.property.SimpleObjectProperty;


public class TenantControlPage {
	@FXML
	private TableColumn<Object[], Integer> ID;

	@FXML
	private TableColumn<Object[], String> full_name;

	@FXML
	private TableColumn<Object[], String> gender;

	@FXML
	private TableColumn<Object[], String> phone; // Corrected type to match FXML

	@FXML
	private TableColumn<Object[], String> rental_date;

	@FXML
	private TableColumn<Object[], Integer> rental_period;
	
	@FXML
	private Label summary;
	
	 @FXML
	 private TableView<Object[]> table;

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
	                stage = (Stage) summary.getScene().getWindow();
	                stage.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	   	}
	  
	  
	  
	  
	  @FXML
	  public void initialize() {
	      // Call Db.selectAll() to fetch data
	      List<Object[]> data = db.selectAll();

	      // Create table columns (assuming 6 columns in your table)
	      TableColumn<Object[], Integer> idColumn = new TableColumn<>("ID");
	      idColumn.setCellValueFactory(cellData ->
	          new SimpleObjectProperty<Integer>((Integer) cellData.getValue()[0]));

	      TableColumn<Object[], String> fullNameColumn = new TableColumn<>("Full Name");
	      fullNameColumn.setCellValueFactory(cellData ->
	          new SimpleObjectProperty<String>((String) cellData.getValue()[1]));

	      // Other columns
	      TableColumn<Object[], String> genderColumn = new TableColumn<>("Gender");
	      genderColumn.setCellValueFactory(cellData ->
	          new SimpleObjectProperty<String>((String) cellData.getValue()[2]));

	      TableColumn<Object[], String> phoneColumn = new TableColumn<>("Phone");
	      phoneColumn.setCellValueFactory(cellData ->
	          new SimpleObjectProperty<String>((String) cellData.getValue()[3]));

	      TableColumn<Object[], String> rentalDateColumn = new TableColumn<>("Rental Date");
	      rentalDateColumn.setCellValueFactory(cellData ->
	          new SimpleObjectProperty<String>((String) cellData.getValue()[4]));

	      TableColumn<Object[], Integer> rentalPeriodColumn = new TableColumn<>("Rental Period");
	      rentalPeriodColumn.setCellValueFactory(cellData ->
	          new SimpleObjectProperty<Integer>((Integer) cellData.getValue()[5]));

	      // Add columns to the table
	      List<TableColumn<Object[], ?>> columns = Arrays.asList(
	          idColumn, fullNameColumn, genderColumn, phoneColumn, rentalDateColumn, rentalPeriodColumn
	      );

	      // Convert the list to an array and set it to the table
	      @SuppressWarnings("unchecked")
	      TableColumn<Object[], ?>[] columnsArray = new TableColumn[columns.size()];
	      columnsArray = columns.toArray(columnsArray);
	      table.getColumns().setAll(columnsArray);

	      // Set the table data
	      table.getItems().setAll(data);
	  }


  
}


	  
	  
	  
