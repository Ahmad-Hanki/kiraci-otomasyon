package application;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
	            Parent root = FXMLLoader.load(getClass().getResource("/HomeControlPage.fxml"));

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
	   	public void UpdatePriceHandler(MouseEvent event) {
	       	 try {
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
	      List<Object[]> data = db.selectAll();

	      if (data.isEmpty()) {
	          System.out.println("Table is empty.");
	          return;
	      }

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
	      
	      // Add listener for row selection changes
	      table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
	          if (newSelection != null) {
	              // Populate form fields with data from selected row
	              Object[] rowData = newSelection;
	              id_txt.setText(rowData[0].toString());
	              fullname_txt.setText(rowData[1].toString());
	              if (rowData[2].toString().equals("Male")) {
	                  male.setSelected(true);
	              } else {
	                  female.setSelected(true);
	              }
	              phone_txt.setText(rowData[3].toString());
	              rentaldate_txt.setValue(LocalDate.parse(rowData[4].toString()));
	              switch (rowData[5].toString()) {
	                  case "3 Months":
	                      three_months.setSelected(true);
	                      break;
	                  case "6 Months":
	                      six_months.setSelected(true);
	                      break;
	                  case "9 Months":
	                      nine_months.setSelected(true);
	                      break;
	                  case "12 Months":
	                      twelve_months.setSelected(true);
	                      break;
	              }
	          }
	      });

	      table.getItems().setAll(data);
	  }


	  @SuppressWarnings("unused")
	@FXML
	  public void addHandler(ActionEvent event) {
	      String tenantId = id_txt.getText().trim().toString(); 
	      String fullName = fullname_txt.getText().trim();
	      String phone = phone_txt.getText().trim();
	      LocalDate rentalDate = rentaldate_txt.getValue(); 
	      String rentalPeriod = getSelectedRentalPeriod();
	      String gender = male.isSelected() ? "Male" : (female.isSelected() ? "Female" : "");


	      if (tenantId.isEmpty() || fullName.isEmpty() || phone.isEmpty() || rentalDate == null || rentalPeriod == null) {
	          invalid.setText("Please fill in all fields."); 
	          return;
	      }
	      
	      
			if (rentalDate == null) {
				invalid.setText("Please select a rental date."); 
				return;
			}
	      
	      if (!tenantId.matches("\\d{1,20}")) {
	          invalid.setText("Invalid ID: Please enter a valid tenant ID."); 
	          return;
	      }

	      if (!phone.matches("\\d{10}")) {
	          invalid.setText("Invalid phone number: Please enter a 10-digit number.");
	          return;
	      }

	      
			if (rentalPeriod.isEmpty()) {
				invalid.setText("Please select a rental period."); 
				return;
			}
			
			if (fullName.isEmpty()) {
				invalid.setText("Please enter the full name."); 
				return;
			}
			
			   if (gender.isEmpty()) {
			        invalid.setText("Please select a gender.");
			        return;
			    }
	
			
			
	      String rentalDateString = rentalDate.toString(); 
	      
	

	      String errorMessage = db.createOne(tenantId, fullName, gender, phone, rentalDateString, rentalPeriod);
	      
	      if (errorMessage == null) {
	            clearFields();
	            refreshTable();

	      } else {
	          invalid.setText(errorMessage);
	      }
	  }


	  @FXML
	  public void updateHandler(ActionEvent event) {
	      String tenantId = id_txt.getText().toString(); // Parsing as String
	      String fullName = fullname_txt.getText().toString();
	      String gender = male.isSelected() ? "Male" : (female.isSelected() ? "Female" : "");
	      String phone = phone_txt.getText().toString();
	      LocalDate rentalDate = rentaldate_txt.getValue(); // Retrieve as LocalDate
	      String rentalDateString = rentalDate != null ? rentalDate.toString() : ""; // Convert LocalDate to String or set to empty string if null
	      String rentalPeriod = getSelectedRentalPeriod();
	      

	      if (!tenantId.matches("\\d{1,20}")) {
	          invalid.setText("Invalid ID: Please enter a valid tenant ID.");
	          return;
	      }


	      if (rentalDate == null) {
	          invalid.setText("Please select a rental date.");
	          return;
	      }
	      
	      if (gender == "") {
	          invalid.setText("Please select a gender.");
	          return;
	      }

	      if (rentalPeriod == null) {
	          invalid.setText("Please select a rental period.");
	          return;
	      }
	      
			if (!phone.matches("\\d{10}")) {
				invalid.setText("Invalid phone number: Please enter a 10-digit number."); // Show error message
				return;
			}
			
			if (fullName.isEmpty()) {
				invalid.setText("Please enter the full name."); 
				return;
			}
			

	      String errorMessage = db.updateOne(tenantId, fullName, gender, phone, rentalDateString, rentalPeriod); // Pass rentalDateString
	      if (errorMessage == null) {
	          refreshTable();
	            clearFields();

	      } else {
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
	      List<Object[]> newData = db.selectAll();

	      // Clear the existing items and add all new items
	      table.getItems().setAll(newData);
	  }

	  
	  @FXML
	  public void deleteHandler(ActionEvent event) {
	      String tenantId = id_txt.getText().trim().toString(); 

	      // Check if ID is empty
	      if (tenantId.isEmpty()) {
	          invalid.setText("Please enter a tenant ID to delete.");
	          return;
	      }

	      Alert confirmDialog = new Alert(Alert.AlertType.CONFIRMATION);
	      confirmDialog.setTitle("Confirmation Dialog");
	      confirmDialog.setHeaderText("Delete Tenant");
	      confirmDialog.setContentText("Are you sure you want to delete the tenant with ID " + tenantId + "?");

	      confirmDialog.showAndWait().ifPresent(response -> {
	          if (response == ButtonType.OK) {
	              String errorMessage = db.deleteById(tenantId);
	              if (errorMessage == null) {
	                 
	                  refreshTable();
	                  clearFields();

	              } else {
	                  invalid.setText(errorMessage); 
	              }
	          }
	      });
	  }

	  
	  @FXML
	  public void clearHandler(ActionEvent event) {
	      Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
	      alert.setTitle("Confirmation Dialog");
	      alert.setHeaderText("Delete All Tenants");
	      alert.setContentText("Are you sure you want to delete all tenants?");

	      alert.showAndWait().ifPresent(response -> {
	          if (response == ButtonType.OK) {
	              db.deleteAll();
	              refreshTable();
	              clearFields();

	          }
	      });
	  }
		
	  
	  
	  private void clearFields() {
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
		    invalid.setText(""); // Clear error message
		}
		
	  
}


	  
	  
	  