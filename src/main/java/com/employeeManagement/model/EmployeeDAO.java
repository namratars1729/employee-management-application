/*
The Employee DAO class handles employee-related database operations such as searching,
deleting, updating employees with declared SQL statements.

This class needs to be informed about any changes made to the list of employees.
It is important for the view to be synchronized with the data.
For this purpose, we use ObservableList collection to hold the employees in this list.

selectOneEmployee and selectAllEmployees methods use DBUtil class’s dbExecuteQuery() method.
The other methods (update/delete/insert), use DBUtil class’s dbExecuteUpdate() method.

 */

package com.employeeManagement.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.employeeManagement.util.DBUtil;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeDAO {
    // Search for an employee given the employeeID
    public static Employee selectOneEmployee( String empID ){
        String selectQuery = String.format("SELECT * FROM employees WHERE emp_id = %s;", empID );

        Employee employee = null;

        try( ResultSet resultSet = DBUtil.dbExecuteQuery( selectQuery ); ){
            employee = getSingleEmpInfo( resultSet );
        } catch ( SQLException | ClassNotFoundException ex ) {
            System.out.println( "While searching for the employee with " + empID +
                               " id, an error occurred: " + ex );
            ex.printStackTrace();
        }
        return employee;
    }

    public static Employee getSingleEmpInfo( ResultSet resultSet ) throws SQLException {
        Employee employee = new Employee();
        if (resultSet.next()) {
            employee.setEmployeeID( resultSet.getInt( "EMP_ID") );
            employee.setFirstName( resultSet.getString("FIRST_NAME") );
            employee.setLastName( resultSet.getString("LAST_NAME") );
            employee.setEmail( resultSet.getString("EMAIL") );
            employee.setPhoneNumber( resultSet.getString("PHONE_NUM") );
        }
        return employee;
    }

    public static ObservableList<Employee> selectAllEmployees() throws SQLException, ClassNotFoundException {
        String selectQuery = "SELECT * FROM employees;";
        ObservableList<Employee> allEmployeesList = null;
        try( ResultSet resultSet = DBUtil.dbExecuteQuery( selectQuery ); ){
            // get an ObservableList of Employees
            allEmployeesList = getAllEmployeesInfo( resultSet );
        } catch ( SQLException | ClassNotFoundException ex ) {
            System.out.println( "An error occurred while selecting all the employees " );
            ex.printStackTrace();
        }
        return allEmployeesList;
    }

    public static ObservableList<Employee> getAllEmployeesInfo( ResultSet resultSet ) throws SQLException {
        ObservableList<Employee> allEmployeesList = FXCollections.observableArrayList();
        while (resultSet.next()) {
            Employee employee = new Employee();

            employee.setEmployeeID( resultSet.getInt( "EMP_ID") );
            employee.setFirstName( resultSet.getString("FIRST_NAME") );
            employee.setLastName( resultSet.getString("LAST_NAME") );
            employee.setEmail( resultSet.getString("EMAIL") );
            employee.setPhoneNumber( resultSet.getString("PHONE_NUM") );

            allEmployeesList.add( employee );
        }
        return allEmployeesList;
    }

    public static int updateNewEmail( String empId, String newEmail ){
        String updateQuery = String.format( "UPDATE employees SET email = \"%s\" WHERE emp_id = %s;",
                                             newEmail, empId );
       // UPDATE products SET quantity = quantity - 100 WHERE name = 'Pen Red';
        int rows = 0;
        try{
            rows = DBUtil.dbExecuteUpdate( updateQuery );
        } catch (SQLException ex) {
            System.out.println("Error occurred during the UPDATE operation");
            ex.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return rows;
    }

    public static int insertEmployee( int empID, String firstName, String lastName,
                                       String email, String phoneNum ) throws SQLException, ClassNotFoundException{
        int rows = 0;
        String insertQuery = String.format( "INSERT INTO employees VALUES (%d, \"%s\", \"%s\", \"%s\", \"%s\");",
                                            empID, firstName, lastName, email, phoneNum );
        try{
            rows = DBUtil.dbExecuteUpdate( insertQuery );

        } catch (SQLException ex) {
            System.out.println("Error occurred during the UPDATE operation");
            ex.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("ClassNotFoundException: com.mysql.cj.jdbc.Driver");;
        }
        return rows;
    }

    public static int deleteEmployee( int employeeID ){
        String deleteQuery = String.format( "DELETE FROM employees WHERE emp_id = %d;", employeeID );
        int rows = 0;
        try{
            rows = DBUtil.dbExecuteUpdate( deleteQuery );
        } catch (SQLException ex) {
            System.out.println("Error occurred during the DELETE operation");
            ex.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return rows;
    }
}
