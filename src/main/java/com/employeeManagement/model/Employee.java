package com.employeeManagement.model;

/*
JavaFX Properties store the inner state of a control and allow us to LISTEN to
state change from JavaFX UI controls.
JavaFX Properties can be bound to one another. Binding behavior allows properties
to synchronize their values based on a changed value from another property.

JavaFX's properties hold actual values and provide change support, invalidation
support, and binding capabilities.

All JavaFX property classes are located in the javafx.beans.property.* package namespace.

There are two types of JavaFX Properties:
- Read/Writable
  The Properties prefixed with "Simple"
  Eg.
  StringProperty password  = new SimpleStringProperty("java2s.com"); // The actual value is the
                                                                     // string "java2s.com"
  password.set("example.com");  // or setValue()
  System.out.println("Modified StringProperty "  + password.get() ); // or getValue()

- Read-Only
  The Properties prefixed with "ReadOnly"
  Creating a read-only property needs two steps.
  - instantiate a read-only wrapper class
        ReadOnlyStringWrapper userName = new ReadOnlyStringWrapper("java2s.com");
  - invoke the method getReadOnlyProperty() to return a true read-only property object
        ReadOnlyStringProperty readOnlyUserName  = userName.getReadOnlyProperty();

JavaFX Property objects contain an addListener() method, which accept two types of
functional interfaces:
- ChangeListener and
- invalidationListener

All JavaFX properties are descendants of the ObservableValue and Observable interfaces,
which provide the addListener() methods for ChangeListener and invalidationListener, respectively.
---------------------------------------------------------------------------------------

A model class to hold information about the employee.
This class holds all fields of the Employee such as name, last name, email, etc.
It contains set and get methods and properties for all fields of a model class.
A Property notifies us when any variable such as name, last name, etc. is changed.
This helps us keep the view in sync with the data.

 */
import javafx.beans.property.*;
import java.sql.Date;

public class Employee {
    private IntegerProperty employeeID;
    private StringProperty firstName;
    private StringProperty lastName;
    private StringProperty email;
    private StringProperty phoneNumber;

    // constructor
    public Employee() {
        this.employeeID = new SimpleIntegerProperty();
        this.firstName = new SimpleStringProperty();
        this.lastName = new SimpleStringProperty();
        this.email = new SimpleStringProperty();
        this.phoneNumber = new SimpleStringProperty();
    }
//-------------------------------------------
    public int getEmployeeID() {
        return employeeID.get();
    }

    public IntegerProperty employeeIDProperty() {
        return employeeID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID.set(employeeID);
    }
//-------------------------------------------
    public String getFirstName() {
        return firstName.get();
    }

    public StringProperty firstNameProperty() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }
    //-------------------------------------------
    public String getLastName() {
        return lastName.get();
    }

    public StringProperty lastNameProperty() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }
    //-------------------------------------------
    public String getEmail() {
        return email.get();
    }

    public StringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }
    //-------------------------------------------
    public String getPhoneNumber() {
        return phoneNumber.get();
    }

    public StringProperty phoneNumberProperty() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber.set(phoneNumber);
    }
}
