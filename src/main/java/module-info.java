module com.example.libraryassistant {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.sql.rowset;


    opens com.employeeManagement to javafx.fxml;
    exports com.employeeManagement;
    exports com.employeeManagement.controller;
    opens com.employeeManagement.controller to javafx.fxml;
}