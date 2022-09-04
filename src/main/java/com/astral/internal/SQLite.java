package com.astral.internal;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

/**
 *
 * @author Astralsphere Arts
 */

public class SQLite {
    public static boolean firstUse;
    static File dbFolder = new File("data");
    static Path currentDirectory = Paths.get(System.getProperty("user.dir"));
    static Connection mainDB = null;
    static Connection configDB = null;
    
    public static void initDB() {
        if (!Files.isWritable(currentDirectory))
            dbFolder = new File(System.getenv("localappdata") + File.separator + "Astral Invoice");
        firstUse = dbFolder.mkdir();
        dbConnect();
        if (firstUse)
            dbTables();
    }
    
    public static void closeDB() {
        try {
            mainDB.close();
            configDB.close();
        } catch (SQLException ex) { 
            JOptionPane.showMessageDialog(null, ex, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    static void dbConnect() {
        try {
            mainDB = DriverManager.getConnection("jdbc:sqlite:" + dbFolder + File.separator
                + "main.sqlite");
            configDB = DriverManager.getConnection("jdbc:sqlite:" + dbFolder + File.separator
                + "config.sqlite");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    static void dbTables() {
        if (mainDB == null || configDB == null)
            dbConnect();
        String products = "CREATE TABLE IF NOT EXISTS Products (\"Product ID\" TEXT"
            + " NOT NULL UNIQUE, \"Product Name\" TEXT, \"Price\" REAL, PRIMARY"
            + " KEY(\"Product ID\"));";
        String configuration = "CREATE TABLE IF NOT EXISTS Configuration (Parameter"
            + " TEXT NOT NULL UNIQUE, Value TEXT, PRIMARY KEY(Parameter));";
        String configData = "INSERT INTO Configuration (Parameter) VALUES ('Business Owner Name'),"
            + " ('Business Name'), ('Contact Number'), ('Email Address'),"
            + " ('Business Location');";
        try {
            Statement mainDBquery = mainDB.createStatement();
            Statement configDBquery = configDB.createStatement();
            mainDBquery.execute(products);
            configDBquery.execute(configuration);
            configDBquery.execute(configData);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public static void setConfigData(String ownerName, String bizName, String bizNumber, String bizMail,
            String bizAddress) {
        if (configDB == null)
            dbConnect();
        String username = "UPDATE Configuration SET Value = ? WHERE Parameter = 'Business Owner Name';";
        String name = "UPDATE Configuration SET Value = ? WHERE Parameter = 'Business Name';";
        String number = "UPDATE Configuration SET Value = ? WHERE Parameter = 'Contact Number';";
        String email = "UPDATE Configuration SET Value = ? WHERE Parameter = 'Email Address';";
        String address = "UPDATE Configuration SET Value = ? WHERE Parameter = 'Business Location';";
        try {
            PreparedStatement configDBquery = configDB.prepareStatement(username);
            configDBquery.setString(1, ownerName);
            configDBquery.executeUpdate();
            configDBquery = configDB.prepareStatement(name);
            configDBquery.setString(1, bizName);
            configDBquery.executeUpdate();
            configDBquery = configDB.prepareStatement(number);
            configDBquery.setString(1, bizNumber);
            configDBquery.executeUpdate();
            configDBquery = configDB.prepareStatement(email);
            configDBquery.setString(1, bizMail);
            configDBquery.executeUpdate();
            configDBquery = configDB.prepareStatement(address);
            configDBquery.setString(1, bizAddress);
            configDBquery.executeUpdate();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public static String getConfigValue(String param) {
        if (configDB == null)
            dbConnect();
        String value = null;
        String configuration = "SELECT Value FROM Configuration WHERE Parameter = ?;";
        try {
            PreparedStatement configDBquery = configDB.prepareStatement(configuration);
            configDBquery.setString(1, param);
            ResultSet configResult = configDBquery.executeQuery();
            value = configResult.getString("Value");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex, "Error", JOptionPane.ERROR_MESSAGE);
        }
        return value;
    }
    
    public static void setConfigValue(String param, String value) {
        if (configDB == null)
            dbConnect();
        String configuration = "UPDATE Configuration SET Value = ? WHERE Parameter = ?;";
        try {
            PreparedStatement configDBquery = configDB.prepareStatement(configuration);
            configDBquery.setString(1, value);
            configDBquery.setString(2, param);
            configDBquery.executeUpdate();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public static ResultSet productData() {
        if (mainDB == null)
            dbConnect();
        ResultSet prodResult = null;
        String products = "SELECT * FROM Products;";
        try {
            Statement mainDBquery = mainDB.createStatement();
            prodResult = mainDBquery.executeQuery(products);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex, "Error", JOptionPane.ERROR_MESSAGE);
        }
        return prodResult;
    }
    
    public static void updateProduct(String PID, String name, String price) {
        if (mainDB == null)
            dbConnect();
        String product = "REPLACE INTO Products (\"Product ID\", \"Product Name\","
            + " \"Price\") VALUES(?, ?, ?);";
        try {
            PreparedStatement mainDBquery = mainDB.prepareStatement(product);
            mainDBquery.setString(1, PID);
            mainDBquery.setString(2, name);
            mainDBquery.setString(3, price);
            mainDBquery.executeUpdate();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public static void removeProduct(String PID) {
        if (mainDB == null)
            dbConnect();
        String product = "DELETE FROM Products WHERE \"Product ID\" = ?;";
        try {
            PreparedStatement mainDBquery = mainDB.prepareStatement(product);
            mainDBquery.setString(1, PID);
            mainDBquery.executeUpdate();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
