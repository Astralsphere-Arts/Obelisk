package com.astral.internal;

import java.security.SecureRandom;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author Astralsphere Arts
 */

public class Function {
    static String Seed = "0123456789";
    static SecureRandom random = new SecureRandom();
    
    public static String randomID(int length) {
        StringBuilder builder = new StringBuilder(length);
        for (int i=0; i<length; i++)
            builder.append(Seed.charAt(random.nextInt(Seed.length())));
        return builder.toString();
    }
    
    public static TableModel newInvoTableModel() {
        ResultSet invoResult = com.astral.internal.SQLite.productData();
        DefaultTableModel invoTableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return columnIndex == 0 || columnIndex == 4;
            }
            Class[] types = new Class [] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class,
                java.lang.Double.class, java.lang.Integer.class
            };
            @Override
            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        };
        try {
            ResultSetMetaData invoMeta = invoResult.getMetaData();
            int numberOfColumns = invoMeta.getColumnCount();
            invoTableModel.addColumn("Purchased");
            for (int columnIndex = 0; columnIndex < numberOfColumns; columnIndex++)
                invoTableModel.addColumn(invoMeta.getColumnLabel(columnIndex + 1));
            invoTableModel.addColumn("Quantity");
            Object[] row = new Object[numberOfColumns + 2];
            while (invoResult.next()) {
                row[0] = false;
                for (int i = 1; i < numberOfColumns + 1; i++)
                    row[i] = invoResult.getObject(i);
                row[4] = 1;
                invoTableModel.addRow(row);
            }
            return invoTableModel;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex, "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
}
