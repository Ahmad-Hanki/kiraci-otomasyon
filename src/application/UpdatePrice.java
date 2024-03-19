package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class UpdatePrice {
	
	@FXML
    private Label summary;

    @FXML
    private TableView<Object[]> table;

    @FXML
    private TableColumn<Object[], String> fullNameColumn;
    
    @FXML

    private TableColumn<Object[], String> idColumn;


    @FXML
    private TableColumn<Object[], String> rentalPeriodColumn;

    @FXML
    private TableColumn<Object[], Double> priceColumn;

    @FXML
    private TableColumn<Object[], Boolean> paidColumn;
    
    @FXML
    private TextField fullname_txt;
    
    @FXML
    private TextField id_txt;
    
    
    @FXML
    private TextField renatl_period_txt;

    @FXML
    private TextField price_txt;

    @FXML
    private CheckBox isPaid;
    

    @FXML
    private Label label_price;
    
    
    @FXML
    public void initialize() {
    	
        String currentMonth = getCurrentMonth();
        paidColumn.setText("Paid (" + currentMonth + ")");

    	
    	 table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
             if (newSelection != null) {
                 Object[] rowData = newSelection;
                 fullname_txt.setText((String) rowData[0]);
                 renatl_period_txt.setText((String) rowData[1]);
                 price_txt.setText(String.valueOf((Double) rowData[2]));
                 isPaid.setSelected((Boolean) rowData[3]);
                 id_txt.setText((String) rowData[4]);
             }
         });
 
    	
    	
        fullNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty((String) cellData.getValue()[0]));
        rentalPeriodColumn.setCellValueFactory(cellData -> new SimpleStringProperty((String) cellData.getValue()[1]));
        priceColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty((Double) cellData.getValue()[2]).asObject());
        paidColumn.setCellValueFactory(cellData -> new SimpleBooleanProperty((Boolean) cellData.getValue()[3]).asObject());
        idColumn.setCellValueFactory(cellData -> new SimpleStringProperty((String) cellData.getValue()[4]));

        
        try {
            ResultSet resultSet = database.getData();
            while (resultSet.next()) {
                table.getItems().add(new Object[] {
                        resultSet.getString("full_name"),
                        resultSet.getString("rental_period"),
                        resultSet.getDouble("price"),
                        resultSet.getBoolean("paid"),
                        resultSet.getString("tenant_id")

                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private String getCurrentMonth() {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("MMMM");
        return currentDate.format(monthFormatter);
    }

    @FXML
    public void handleUpdatePrice(ActionEvent event) {
    	    	

        double price = 0; 
        
        try {
            price = Double.parseDouble(price_txt.getText());
        } catch (Exception e) {    	
            label_price.setText("Price must be a number");
            return; 
        }
    	
        System.out.println(price);

        if (price < 1) {
            label_price.setText("Price must be greater than 0");
            return;
        }
        if (price > 99999999) {
            label_price.setText("Price must be less than 8 digits");
            return;
        }

        String tenant_id = id_txt.getText();

        if (tenant_id.isEmpty()) {
            label_price.setText("Please select a tenant");
            return;
        }

        boolean paid = isPaid.isSelected();
        System.out.println(paid);
        
        try {
            database.updatePricePaid(tenant_id, price, paid);
            label_price.setText("Price updated successfully");
            refreshTable();
            clearFields();

            
        } catch (Exception e) {
            e.printStackTrace();
        }
  
    }
    
    @FXML
    public void refreshTable() {
        table.getItems().clear();
        
        try {
            ResultSet resultSet = database.getData();
            while (resultSet.next()) {
                table.getItems().add(new Object[] {
                        resultSet.getString("full_name"),
                        resultSet.getString("rental_period"),
                        resultSet.getDouble("price"),
                        resultSet.getBoolean("paid"),
                        resultSet.getString("tenant_id")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    public void clearFields() {
        fullname_txt.setText("");
        renatl_period_txt.setText("");
        price_txt.setText("");
        isPaid.setSelected(false);
        id_txt.setText("");
        label_price.setText(""); 
    }


    
	
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
		public void TenantControlHandler(MouseEvent event) {
	    	 try {
	             Parent root = FXMLLoader.load(getClass().getResource("/TenantControlPage.fxml"));

	             Stage stage = new Stage();
	             stage.setScene(new Scene(root));
	             stage.setTitle("Control Tenants");
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
	    
}
