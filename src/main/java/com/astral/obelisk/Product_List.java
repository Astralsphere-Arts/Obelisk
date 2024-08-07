package com.astral.obelisk;

import java.awt.Component;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author Astralsphere Arts
 */
public class Product_List extends javax.swing.JPanel {

    /**
     * Creates new form Product_List
     */
    public Product_List() {
        initComponents();
        this.Products_Table_Model = (DefaultTableModel) this.Products_Table.getModel();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Heading_Icon_Before = new javax.swing.JLabel();
        Heading = new javax.swing.JLabel();
        Products_Table_Container = new javax.swing.JScrollPane();
        Products_Table = new javax.swing.JTable();
        Add = new javax.swing.JButton();
        Remove = new javax.swing.JButton();
        Import = new javax.swing.JButton();
        Export = new javax.swing.JButton();

        setPreferredSize(new java.awt.Dimension(954, 574));

        Heading_Icon_Before.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        Heading_Icon_Before.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/astral/resources/app-icon-45x45.png"))); // NOI18N

        Heading.setFont(new java.awt.Font("Segoe UI Semilight", 1, 24)); // NOI18N
        Heading.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Heading.setText("List of Products");
        Heading.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        Products_Table.setModel(com.astral.internal.Function.productTableModel());
        Products_Table.setShowGrid(true);
        Products_Table.getTableHeader().setReorderingAllowed(false);
        Products_Table.getModel().addTableModelListener(new javax.swing.event.TableModelListener() {
            public void tableChanged(javax.swing.event.TableModelEvent evt) {
                ProductsTableChanged(evt);
            }
        });
        Products_Table_Container.setViewportView(Products_Table);
        final TableColumnModel columnModel = Products_Table.getColumnModel();
        for (int column = 0; column < Products_Table.getColumnCount(); column++) {
            int width = 15;
            for (int row = 0; row < Products_Table.getRowCount(); row++) {
                TableCellRenderer renderer = Products_Table.getCellRenderer(row, column);
                Component comp = Products_Table.prepareRenderer(renderer, row, column);
                width = Math.max(comp.getPreferredSize().width + 1 , width);
            }
            if (width > 300) width=300;
            columnModel.getColumn(column).setPreferredWidth(width);
        }

        Add.setText("Add");
        Add.setToolTipText("Add a New Product to Inventory");
        Add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddActionPerformed(evt);
            }
        });

        Remove.setText("Remove");
        Remove.setToolTipText("Remove Selected Products from Inventory");
        Remove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RemoveActionPerformed(evt);
            }
        });

        Import.setText("Import");
        Import.setToolTipText("Import Inventory Data from a CSV File");
        Import.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ImportActionPerformed(evt);
            }
        });

        Export.setText("Export");
        Export.setToolTipText("Export Inventory Data to a CSV File");
        Export.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ExportActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(Add, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(Remove, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(Import, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(Export, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(Products_Table_Container, javax.swing.GroupLayout.DEFAULT_SIZE, 856, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(Heading_Icon_Before)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Heading, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(53, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(Heading, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Heading_Icon_Before, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(Products_Table_Container, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Remove, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Add, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Export, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Import, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(55, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void ProductsTableChanged(javax.swing.event.TableModelEvent evt) {
        for (int row = 0; row < Products_Table.getRowCount(); row++) {
            String PID = (String) Products_Table.getValueAt(row, 0);
            String name = (String) Products_Table.getValueAt(row, 1);
            String price = null;
            if (Products_Table.getValueAt(row, 2) != null)
                price = Double.toString((Double) Products_Table.getValueAt(row, 2));
            com.astral.internal.SQLite.updateProduct(PID, name, price);
        }
    }
    
    private void AddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddActionPerformed
        Products_Table_Model.addRow(new Object[] {"OSK-" + com.astral.internal.Function.randomID(4)
            + "-" + com.astral.internal.Function.randomID(4), null, null, null});
    }//GEN-LAST:event_AddActionPerformed

    private void RemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RemoveActionPerformed
        int[] rows = Products_Table.getSelectedRows();
        for (int i=0; i<rows.length; i++) {
            String PID = Products_Table.getValueAt(rows[i]-i, 0).toString();
            com.astral.internal.SQLite.removeProduct(PID);
            Products_Table_Model.removeRow(rows[i]-i);
        }
    }//GEN-LAST:event_RemoveActionPerformed

    private void ImportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ImportActionPerformed
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Import from CSV");
        fileChooser.setFileFilter(new FileNameExtensionFilter("CSV File", "csv"));
        if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            com.astral.internal.Function.prodCSVim(fileChooser.getSelectedFile());
            JOptionPane.showMessageDialog(null, "List of Products Imported Successfully!",
                "Success", JOptionPane.INFORMATION_MESSAGE);
            com.astral.obelisk.Main.Content.removeAll();
            com.astral.obelisk.Product_List scene = new Product_List();
            scene.setBounds(0, 0, 954, 574);
            com.astral.obelisk.Main.Content.add(scene).setVisible(true);
        }
    }//GEN-LAST:event_ImportActionPerformed

    private void ExportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ExportActionPerformed
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Export to CSV");
        fileChooser.setDialogType(JFileChooser.SAVE_DIALOG);
        fileChooser.setSelectedFile(new File("Product-List-" + new java.text.SimpleDateFormat("dd-MM-yyyy")
            .format(new java.util.Date()) + ".csv"));
        fileChooser.setFileFilter(new FileNameExtensionFilter("CSV File", "csv"));
        if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            com.astral.internal.Function.prodCSVex(fileChooser.getSelectedFile());
            JOptionPane.showMessageDialog(null, "List of Products Exported Successfully!",
                "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_ExportActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Add;
    private javax.swing.JButton Export;
    private javax.swing.JLabel Heading;
    private javax.swing.JLabel Heading_Icon_Before;
    private javax.swing.JButton Import;
    private javax.swing.JTable Products_Table;
    private javax.swing.JScrollPane Products_Table_Container;
    private javax.swing.JButton Remove;
    // End of variables declaration//GEN-END:variables
    private final DefaultTableModel Products_Table_Model;
}
