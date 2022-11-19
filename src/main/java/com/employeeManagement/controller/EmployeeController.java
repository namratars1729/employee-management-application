package com.employeeManagement.controller;

import com.employeeManagement.model.Employee;
import com.employeeManagement.model.EmployeeDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class EmployeeController {

    @FXML
    private TextField textEmpID;

    @FXML
    private TextField textEmpIDquery;

    @FXML
    private TextField textFirstName;

    @FXML
    private TextField textLastName;

    @FXML
    private TextField textEmail;

    @FXML
    private TextField textPhoneNum;

    @FXML
    private TextField textNewEmail;

    @FXML
    private TableView<Employee> tableEmployee;

    @FXML
    private TableColumn<Employee, Integer> columnEmpID;

    @FXML
    private TableColumn<Employee, String> columnFirstName;

    @FXML
    private TableColumn<Employee, String> columnLastName;

    @FXML
    private TableColumn<Employee, String> columnEmail;

    @FXML
    private TableColumn<Employee, String> columnPhone;

    @FXML
    private TextArea resultArea;



// ------------------------------------------------------------------------------

    //Initializing the controller class.
    //This method is automatically called after the fxml file has been loaded.
    @FXML
    private void initialize () {
        /*
        When working with the table, we use the most common method, setCellValueFactory(),
        for creating a cell on the table.
        It is used to determine which field inside the Employee object should be used
        for the particular column.
        (Another option would be to use a PropertyValueFactory, but this is not type-safe)
        We're only using StringProperty values for our table columns in this example.
        Other than StringProperty values, if you have to use IntegerProperty or DoubleProperty,
        the setCellValueFactory() must have an additional asObject()
        */
        columnEmpID.setCellValueFactory( cell -> cell.getValue().employeeIDProperty().asObject() );
        columnFirstName.setCellValueFactory( cell -> cell.getValue().firstNameProperty() );
        columnLastName.setCellValueFactory( cell -> cell.getValue().lastNameProperty() );
        columnEmail.setCellValueFactory( cell -> cell.getValue().emailProperty() );
        columnPhone.setCellValueFactory( cell -> cell.getValue().phoneNumberProperty() );
    }

    @FXML
    void selectOneEmployee( ActionEvent event ) {
        //--------- Display the result in both the table and in the text area
        // Get the employee's information
        String empId = textEmpIDquery.getText() ;
        Employee employee = EmployeeDAO.selectOneEmployee( empId );
        if (employee != null) {  // if employee exists
            // populate the employee’s information in the table view
            ObservableList<Employee> selectedEmployeeList = FXCollections.observableArrayList();
            selectedEmployeeList.add( employee );
            tableEmployee.setItems(selectedEmployeeList);

            // display the result in the text area
            resultArea.setText( String.format("Employee ID: %d \nFirst Name: %s " +
                                              "\nLast Name: %s" + "Email: %s \nPhone: %s",
                                              employee.getEmployeeID(), employee.getFirstName(),
                                              employee.getLastName(), employee.getEmail(),
                                              employee.getPhoneNumber() ) );
        }
        else resultArea.setText("Employee id: " + empId + " does not exist!\n");
    }

    @FXML
    void selectAllEmployees(ActionEvent event) throws SQLException, ClassNotFoundException {
        ObservableList<Employee> allEmployeesList = EmployeeDAO.selectAllEmployees();
        // Display in the table
        tableEmployee.setItems( allEmployeesList );
    }

    @FXML
    void insertEmployee(ActionEvent event) throws SQLException, ClassNotFoundException {
        try {
            Integer empID = Integer.parseInt( textEmpID.getText() );
            String firstName = textFirstName.getText();
            String lastName = textLastName.getText();
            String email = textEmail.getText();
            String phoneNumber = textPhoneNum.getText();
            int rows = 0;

            rows = EmployeeDAO.insertEmployee( empID, firstName, lastName, email, phoneNumber );
            if ( rows > 0 ) {
                resultArea.setText("Employee inserted! \n");
            }
        } catch ( SQLException ex ) {
            resultArea.setText ("Error while inserting a new employee");
        }
    }

    @FXML
    void deleteEmployee(ActionEvent event) {
        int rows = 0;
        Integer empID = Integer.parseInt( textEmpIDquery.getText() );
        rows = EmployeeDAO.deleteEmployee( empID );
        if (rows > 0) {
            resultArea.setText("Deleted employee id: " + empID + "\n");
        }
        else resultArea.setText("employee id: " + empID + " does not exist\n");
    }

    @FXML
    void updateNewEmail(ActionEvent event) {
        String empId = textEmpIDquery.getText();
        String newEmail = textNewEmail.getText();
        int rows = 0;
        rows = EmployeeDAO.updateNewEmail( empId, newEmail );
        if (rows > 0) {
            resultArea.setText(String.format("Email address of employee id %s has been updated\n",
                                               empId ) );
        }
        else resultArea.setText(String.format("employee id %s does not exist\n", empId) );
    }
}
