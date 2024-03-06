package application;

import java.io.IOException;
import java.time.LocalDate;
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


	  @SuppressWarnings("unused")
	@FXML
	  public void addHandler(ActionEvent event) {
	      // Retrieve input values from JavaFX controls
	      String tenantId = id_txt.getText().trim(); // Parsing as String and trim whitespace
	      String fullName = fullname_txt.getText().trim();
	      String phone = phone_txt.getText().trim();
	      LocalDate rentalDate = rentaldate_txt.getValue(); // Retrieve as LocalDate
	      String rentalPeriod = getSelectedRentalPeriod();
	      String gender = male.isSelected() ? "Male" : (female.isSelected() ? "Female" : "");


	      // Check if any field is empty
	      if (tenantId.isEmpty() || fullName.isEmpty() || phone.isEmpty() || rentalDate == null || rentalPeriod == null) {
	          invalid.setText("Please fill in all fields."); // Show error message
	          return;
	      }
	      
	      
			if (rentalDate == null) {
				invalid.setText("Please select a rental date."); // Show error message
				return;
			}
	      
	      // Validate tenant ID format and length
	      if (!tenantId.matches("\\d{1,20}")) {
	          invalid.setText("Invalid ID: Please enter a valid tenant ID."); // Show error message
	          return;
	      }

	      // Validate phone number format
	      if (!phone.matches("\\d{10}")) {
	          invalid.setText("Invalid phone number: Please enter a 10-digit number."); // Show error message
	          return;
	      }

	      
			if (rentalPeriod.isEmpty()) {
				invalid.setText("Please select a rental period."); // Show error message
				return;
			}
			
			//validate fullname
			if (fullName.isEmpty()) {
				invalid.setText("Please enter the full name."); // Show error message
				return;
			}
			
			   if (gender.isEmpty()) {
			        invalid.setText("Please select a gender.");
			        return;
			    }
	
			
			
	      // Proceed with database insertion if validation passes
	      String rentalDateString = rentalDate.toString(); // Convert LocalDate to String
	      
	 
	

	      
	      // Print debug information
	      System.out.println("tenantId: " + tenantId);
	      System.out.println("fullName: " + fullName);
	      System.out.println("gender: " + gender);
	      System.out.println("phone: " + phone);
	      System.out.println("rentalDate: " + rentalDateString);
	      System.out.println("rentalPeriod: " + rentalPeriod);

	      // Validate and insert data into the database
	      String errorMessage = db.createOne(tenantId, fullName, gender, phone, rentalDateString, rentalPeriod);
	      if (errorMessage == null) {
	          // Reset all fields
	          id_txt.setText("");
	          fullname_txt.setText("");
	          phone_txt.setText("");
	          rentaldate_txt.setValue(null);
	          male.setSelected(false);
	          female.setSelected(false);
	          three_months.setSelected(false);
	          six_months.setSelected(false);
	          nine_months.setSelected(false);
	          twelve_months.setSelected(false);
	          invalid.setText(""); // Clear any error message
	          refreshTable();
	      } else {
	          // Data insertion failed, show error message
	          invalid.setText(errorMessage);
	      }
	  }


	  @FXML
	  public void updateHandler(ActionEvent event) {
	      // Retrieve input values from JavaFX controls
	      String tenantId = id_txt.getText().toString(); // Parsing as String
	      String fullName = fullname_txt.getText().toString();
	      String gender = male.isSelected() ? "Male" : (female.isSelected() ? "Female" : "");
	      String phone = phone_txt.getText().toString();
	      LocalDate rentalDate = rentaldate_txt.getValue(); // Retrieve as LocalDate
	      String rentalDateString = rentalDate != null ? rentalDate.toString() : ""; // Convert LocalDate to String or set to empty string if null
	      String rentalPeriod = getSelectedRentalPeriod();
	      

	      // Validate tenant ID format and length
	      if (!tenantId.matches("\\d{1,20}")) {
	          invalid.setText("Invalid ID: Please enter a valid tenant ID.");
	          return;
	      }


	      // Validate rental date
	      if (rentalDate == null) {
	          invalid.setText("Please select a rental date.");
	          return;
	      }
	      
	      if (gender == "") {
	          invalid.setText("Please select a gender.");
	          return;
	      }

	      // Validate rental period
	      if (rentalPeriod == null) {
	          invalid.setText("Please select a rental period.");
	          return;
	      }
	      
	      // Validate phone number format
			if (!phone.matches("\\d{10}")) {
				invalid.setText("Invalid phone number: Please enter a 10-digit number."); // Show error message
				return;
			}
			
			//validate fullname
			if (fullName.isEmpty()) {
				invalid.setText("Please enter the full name."); // Show error message
				return;
			}
			
			


	      // Print debug information
	      System.out.println("tenantId: " + tenantId);
	      System.out.println("fullName: " + fullName);
	      System.out.println("gender: " + gender);
	      System.out.println("phone: " + phone);
	      System.out.println("rentalDate: " + rentalDateString); // Pass rentalDateString instead
	      System.out.println("rentalPeriod: " + rentalPeriod);

	      // Validate and update data in the database
	      String errorMessage = db.updateOne(tenantId, fullName, gender, phone, rentalDateString, rentalPeriod); // Pass rentalDateString
	      if (errorMessage == null) {
	          refreshTable();
	          // Reset all fields
	          id_txt.setText("");
	          fullname_txt.setText("");
	          phone_txt.setText("");
	          rentaldate_txt.setValue(null);
	          male.setSelected(false);
	          female.setSelected(false);
	          three_months.setSelected(false);
	          six_months.setSelected(false);
	          nine_months.setSelected(false);
	          twelve_months.setSelected(false);
	          invalid.setText(""); // Clear any error message
	      } else {
	          // Data update failed, show error message
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


	  
	  
	  
