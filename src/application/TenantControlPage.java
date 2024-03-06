package application;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;


public class TenantControlPage {
	
	@FXML
	private TableColumn<Object[], String> ID;

	@FXML
	private TableColumn<Object[], String> full_name;

	@FXML
	private TableColumn<Object[], String> gender;

	@FXML
	private TableColumn<Object[], String> phone;

	@FXML
	private TableColumn<Object[], String> rental_date;

	@FXML
	private TableColumn<Object[], String> rental_period;

	@FXML
	private Label summary;
	
	 @FXML
	 private TableView<Object[]> table;
	 
	

	    @FXML
	    private TextField id_txt;

	    @FXML
	    private TextField fullname_txt;

	    @FXML
	    private TextField phone_txt;

	    @FXML
	    private DatePicker rentaldate_txt;

	    @FXML
	    private RadioButton male;

	    @FXML
	    private RadioButton female;

	    @FXML
	    private RadioButton three_months;

	    @FXML
	    private RadioButton six_months;

	    @FXML
	    private RadioButton nine_months;

	    @FXML
	    private RadioButton twelve_months;

	    @FXML
	    private Button clear;

	    @FXML
	    private Button delete;

	    @FXML
	    private Button update;

	    @FXML
	    private Button add;
	    
	    @FXML
	    private Label invalid;


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

	      // Check if the data list is empty
	      if (data.isEmpty()) {
	          // Display a message or handle the empty table scenario
	          System.out.println("Table is empty.");
	          return;
	      }

	      // Set the cell value factories for existing table columns
	      ID.setCellValueFactory(cellData ->
	              new SimpleObjectProperty<String>(String.valueOf(cellData.getValue()[0])));

	      full_name.setCellValueFactory(cellData ->
	              new SimpleObjectProperty<String>((String) cellData.getValue()[1]));

	      gender.setCellValueFactory(cellData ->
	              new SimpleObjectProperty<String>((String) cellData.getValue()[2]));

	      phone.setCellValueFactory(cellData ->
	              new SimpleObjectProperty<String>((String) cellData.getValue()[3]));

	      rental_date.setCellValueFactory(cellData ->
	              new SimpleObjectProperty<String>(cellData.getValue()[4].toString()));

	      rental_period.setCellValueFactory(cellData ->
	              new SimpleObjectProperty<String>((String) cellData.getValue()[5]));

	      // Set the table data
	      table.getItems().setAll(data);
	  }


	  @FXML
	  public void addHandler(ActionEvent event) {
	      // Retrieve input values from JavaFX controls
	      String tenantId = id_txt.getText().toString(); // Parsing as String
	      // Validate tenant ID format and length
	      if (!tenantId.matches("\\d{1,20}")) {
	          invalid.setText("Invalid ID: Please enter a valid tenant ID.");
	          return;
	      }

	      String fullName = fullname_txt.getText();
	      String gender = male.isSelected() ? "Male" : "Female";
	      String phone = phone_txt.getText().toString();
	      LocalDate rentalDate = rentaldate_txt.getValue(); // Retrieve as LocalDate
	      String rentalDateString = rentalDate.toString(); // Convert LocalDate to String
	      String rentalPeriod = getSelectedRentalPeriod().toString();

	      // Print debug information
	      System.out.println("tenantId: " + tenantId);
	      System.out.println("fullName: " + fullName);
	      System.out.println("gender: " + gender);
	      System.out.println("phone: " + phone);
	      System.out.println("rentalDate: " + rentalDateString); // Pass rentalDateString instead
	      System.out.println("rentalPeriod: " + rentalPeriod);

	      // Validate and insert data into the database
	      String errorMessage = db.createOne(tenantId, fullName, gender, phone, rentalDateString, rentalPeriod); // Pass rentalDateString
	      if (errorMessage == null) {
	          refreshTable();
	      } else {
	          // Data insertion failed, show error message
	          invalid.setText(errorMessage);
	      }
	  }


	  


	  // Method to get the selected rental period
	  private String getSelectedRentalPeriod() {
	      if (three_months.isSelected()) {
	          return "3 Months";
	      } else if (six_months.isSelected()) {
	          return "6 Months";
	      } else if (nine_months.isSelected()) {
	          return "9 Months";
	      } else if (twelve_months.isSelected()) {
	          return "12 Months";
	      }
	      return null;
	  }



	  @FXML
	    public void refreshTable() {
	        // Fetch the latest data from the database
	        List<Object[]> newData = db.selectAll();

	        // Convert the list to an observable list
	        ObservableList<Object[]> newDataList = FXCollections.observableArrayList(newData);

	        // Update the table with the new data
	        table.getItems().clear(); // Clear existing data
	        table.getItems().addAll(newDataList); // Add new data to the table
	    }
		
		
}


	  
	  
	  
