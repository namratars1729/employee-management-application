package com.employeeManagement;

import com.employeeManagement.util.DBUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class MainApp extends Application {
    /*
   - The Stage is the main container which is usually a Window with a border
     and the typical minimize, maximize and close buttons.
   - Inside the Stage you add a Scene which can, of course, be switched out
     by another Scene.
   - Inside the Scene the actual JavaFX nodes like AnchorPane, TextBox, etc.
     are added.
    */

    //This is our PrimaryStage (It contains everything)
    private Stage primaryStage;

    //This is the BorderPane of RootLayout
    private BorderPane rootLayout;

    @Override
    public void start(Stage primaryStage) throws IOException {
        // ------- 1) Declare a primary stage (Everything will be on this stage)
        this.primaryStage = primaryStage;

        //Optional: Set a title for primary stage
        this.primaryStage.setTitle("Employee Registration Sample JavaFX App");

        // ------- 2) Initialize RootLayout
        initRootLayout();

        // ------- 3) Display the EmployeeOperations View inside the RootLayout
        displayEmployeeOperationsView();
    }

    public void initRootLayout() {
        final String rootLayoutfile = "/view/RootLayout.fxml";
        final FXMLLoader loader = new FXMLLoader();
        try {
            //-------- 1. Load root layout from RootLayout.fxml
            loader.setLocation( MainApp.class.getResource( rootLayoutfile ) );
            rootLayout = (BorderPane) loader.load();


            //-------- 2. Show the scene containing the root layout
            Scene scene = new Scene( rootLayout ); // Send the rootLayout to the Scene
            primaryStage.setScene(scene); //Set the scene in the primary stage.

            //-------- 3. Display the primary stage
            primaryStage.show();

        } catch( IOException ex) {
            System.out.println( ex.getCause().getClass() );
            System.out.println( "Error! Could not find FXML-file: " + rootLayoutfile );
            ex.printStackTrace();
        }
    }

    public void displayEmployeeOperationsView() {
        final String empOperViewfile = "/view/EmployeeView.fxml";
        final FXMLLoader loader = new FXMLLoader();
        try{
            //-------- 1. Load EmployeeOperationsView from EmployeeOperations.fxml
            loader.setLocation( this.getClass().getResource( empOperViewfile ) );
            AnchorPane empOperViewAnchorPane = loader.load();

            //-------- 2. Set EmployeeOperations View into the center of root layout.
            rootLayout.setCenter( empOperViewAnchorPane );
        } catch( IOException ex) {
            System.out.println( "Error! Could not find FXML-file: " + empOperViewfile );
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        launch();
    }
}