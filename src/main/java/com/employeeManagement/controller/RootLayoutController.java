package com.employeeManagement.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;

/*
We handle Exit and Help methods.
Exit method closes the program and the Help method gives information
about the program by using the Alert class.
 */

public class RootLayoutController {

    @FXML
    void handleExit(ActionEvent event) {
        // Exit the program
        System.exit( 0 );
    }

    @FXML
    void handleHelp(ActionEvent event) {
        Alert alert = new Alert( Alert.AlertType.INFORMATION );
        alert.setTitle("Employee Management App Info");
        alert.setHeaderText( "This is an Employee Management App" );
        alert.setContentText(
                "This app is a learning experience.\n" +
                "It incorporates JavaFX and JDBC. \n" +
                "You can: \n\t- insert an employee's information, \n" +
                "\t- delete an employee's information using the empID\n" +
                "\t- update an employee's email \n" +
                "\t- search for an employee's info using the empID\n" +
                "\t- view all employees' information" );
        alert.show();
    }
}
