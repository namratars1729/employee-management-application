package com.employeeManagement.util;
import com.sun.rowset.CachedRowSetImpl;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;
import java.sql.*;

/*
– dbConnect() method connects to DB.

– dbDisconnect() method closes DB connection.

– dbExecuteQuery(String sqlStmt) method executes given SQL statement
  and returns a cachedRowSet set.
  In order to eliminate “java.sql.SQLRecoverableException: Closed Connection: next” error
  we return cachedRowSet instead of ResultSet.
  Thus, we can use cachedRowSet in other classes and manipulate that data.

– dbExecuteUpdate(String sqlStmt) method executes given Update, Insert, Delete SQL operations.
 */

import java.sql.* ;
public class DBUtil {
     private static Connection conn = null;

    // Establish connection with the database
    public static void dbConnect() throws ClassNotFoundException, SQLException  {
        // check if JDBC Driver class exists
        Class.forName("com.mysql.cj.jdbc.Driver");

        // The database-url is in the form of "jdbc:mysql://{host}:{port}/{database-name}"
        final String URL = "jdbc:mysql://localhost:3306/employeemanagement";
        final String USERNAME = "myuser";
        final String PASSWORD = "nrsdbuser";

        try {
            //Registering the Driver
            conn = DriverManager.getConnection( URL, USERNAME, PASSWORD );
            System.out.println( ".....Established connection with MySQL.....");
        } catch ( SQLException ex ) {
            System.out.println("========== Connection Failed! Check output console" + ex );
            ex.printStackTrace();
        }
    }

    public static Connection getConnectionVariable() {
        return DBUtil.conn;
    }

    public static void dbDisconnect() throws SQLException {
        try{
            if ( DBUtil.getConnectionVariable() != null && !DBUtil.getConnectionVariable().isClosed() ) {
                conn.close();
                System.out.println( ".....Database disconnected.....");
            }
        } catch (SQLException ex) {
            System.out.println("Database NOT closed: \n" + ex.getCause() );
            ex.printStackTrace();
        }
    }

    public static ResultSet dbExecuteQuery( String sqlStatement ) throws SQLException, ClassNotFoundException {

        CachedRowSet cachedRowSet = RowSetProvider.newFactory().createCachedRowSet();

        //Connect to DB
        DBUtil.dbConnect();

        try(
                Statement stmt = DBUtil.getConnectionVariable().createStatement();
                ResultSet resultSet = stmt.executeQuery( sqlStatement );
            ) {
            /*
            A CachedRowSet object is a container for rows of data that caches its rows in memory,
            which makes it possible to operate without always being connected to its data source.
            A CachedRowSet object typically contains rows from a result set, but it can also
            contain rows from any file with a tabular format.
            A CachedRowSet object is a disconnected Rowset, which means that it makes use of a
            connection to its data source only briefly. It connects to its data source while
            it is reading data to populate itself with rows and again while it is propagating
            changes back to its underlying data source. The rest of the time, a CachedRowSet object
            is disconnected, including while its data is being modified.
            Also, in order to prevent "java.sql.SQLRecoverableException: Closed Connection: next" error
            we are using CachedRowSet
             */

            // Populate the CachedRowSet object with data obtained from the ResultSet
            cachedRowSet.populate( resultSet );

        } catch (SQLException ex) {
            System.out.println("Problem occurred in the executeQuery operation : " + ex);
            ex.printStackTrace();
        }
        finally {
            DBUtil.dbDisconnect();
       }
        //Return CachedRowSet
        return cachedRowSet;
    }

    public static int dbExecuteUpdate( String sqlStatement ) throws SQLException, ClassNotFoundException {
        int rows = 0;
       DBUtil.dbConnect();

        try( Statement stmt = DBUtil.getConnectionVariable().createStatement(); ) {
            rows = stmt.executeUpdate( sqlStatement );
        } catch (SQLException ex) {
            System.out.println("Problem occurred in the executeQuery operation : " + ex);
            ex.printStackTrace();
        }
        finally {
            DBUtil.dbDisconnect();
        }
        return rows;
    }
}
