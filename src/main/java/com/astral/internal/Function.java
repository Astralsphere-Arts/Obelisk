package com.astral.internal;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.draw.LineSeparator;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.SecureRandom;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Locale;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author Astralsphere Arts
 */

public class Function {
    public static File invPath;
    static String Seed = "0123456789";
    static SecureRandom random = new SecureRandom();
    public static File invFolder = new File(FileSystemView.getFileSystemView()
        .getDefaultDirectory().getPath() + File.separator + "Astral Invoice");
    static java.awt.Color TableHeader = new java.awt.Color(224, 224, 224);
    
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
    
    public static TableModel productTableModel() {
        ResultSet prodResult = com.astral.internal.SQLite.productData();
        DefaultTableModel prodTableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return columnIndex != 0;
            }
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class,
                java.lang.Double.class
            };
            @Override
            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        };
        try {
            ResultSetMetaData prodMeta = prodResult.getMetaData();
            int numberOfColumns = prodMeta.getColumnCount();
            for (int columnIndex = 0; columnIndex < numberOfColumns; columnIndex++)
                prodTableModel.addColumn(prodMeta.getColumnLabel(columnIndex + 1));
            Object[] row = new Object[numberOfColumns];
            while (prodResult.next()) {
                for (int i = 0; i < numberOfColumns; i++)
                    row[i] = prodResult.getObject(i+1);
                prodTableModel.addRow(row);
            }
            return prodTableModel;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex, "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
       
    public static void invoicePDF(String invID, String custName, String custContact, String custAddress,
            String saleDate, double saleAmount, int prodSelected, String invoiceTable[][]) {
        invFolder.mkdir();
        File invSubFolder = new File(invFolder + File.separator + new java.text.SimpleDateFormat("yyyy - MMMM")
            .format(new java.util.Date()));
        invSubFolder.mkdir();
        invPath = new File(invSubFolder + File.separator + invID + ".pdf");
        try (Document document = new Document()) {
            PdfWriter.getInstance(document, new FileOutputStream(invPath));
            Font IBMPlex = new Font(BaseFont.createFont("/com/astral/resources/IBMPlex.ttf", BaseFont.IDENTITY_H,
                    BaseFont.EMBEDDED), 10);
            document.open();
            Paragraph para = new Paragraph(com.astral.internal.SQLite.getConfigValue("Business Name"),
                FontFactory.getFont(FontFactory.TIMES_BOLD, 20));
            para.setAlignment(Element.ALIGN_CENTER);
            document.add(para);
            para = new Paragraph(com.astral.internal.SQLite.getConfigValue("Business Location"));
            para.setAlignment(Element.ALIGN_CENTER);
            document.add(para);
            para = new Paragraph("Contact Number : " + com.astral.internal.SQLite.getConfigValue("Contact Number")
                + "    Email : " + com.astral.internal.SQLite.getConfigValue("Email Address"));
            para.setAlignment(Element.ALIGN_CENTER);
            para.setSpacingAfter(30f);
            document.add(para);
            document.add(new LineSeparator());
            PdfPTable table = new PdfPTable(2);
            para = new Paragraph();
            Chunk chunk = new Chunk("Invoice Number : ", FontFactory.getFont(FontFactory.HELVETICA_BOLD));
            para.add(chunk);
            chunk = new Chunk(invID, FontFactory.getFont(FontFactory.HELVETICA));
            para.add(chunk);
            PdfPCell cell = new PdfPCell(para);
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setPadding(0);
            table.addCell(cell);
            para = new Paragraph();
            chunk = new Chunk("Invoice Date : ", FontFactory.getFont(FontFactory.HELVETICA_BOLD));
            para.add(chunk);
            chunk = new Chunk(saleDate, FontFactory.getFont(FontFactory.HELVETICA));
            para.add(chunk);
            cell = new PdfPCell(para);
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setPadding(0);
            table.addCell(cell);
            table.setSpacingAfter(20f);
            table.setSpacingBefore(20f);
            table.setWidthPercentage(100);
            document.add(table);
            para = new Paragraph();
            chunk = new Chunk("Billed To : ", FontFactory.getFont(FontFactory.HELVETICA_BOLD));
            para.add(chunk);
            chunk = new Chunk(custName, FontFactory.getFont(FontFactory.HELVETICA));
            para.add(chunk);
            document.add(para);
            para = new Paragraph();
            chunk = new Chunk("Contact Number : ", FontFactory.getFont(FontFactory.HELVETICA_BOLD));
            para.add(chunk);
            chunk = new Chunk(custContact, FontFactory.getFont(FontFactory.HELVETICA));
            para.add(chunk);
            document.add(para);
            para = new Paragraph();
            chunk = new Chunk("Billing Address : ", FontFactory.getFont(FontFactory.HELVETICA_BOLD));
            para.add(chunk);
            chunk = new Chunk(custAddress, FontFactory.getFont(FontFactory.HELVETICA));
            para.add(chunk);
            document.add(para);
            float[] widths = {10f, 44f, 13f, 16f, 17f};
            table = new PdfPTable(widths);
            cell = new PdfPCell(new Paragraph("S.No.", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10)));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBackgroundColor(TableHeader);
            cell.setPadding(10f);
            table.addCell(cell);
            cell = new PdfPCell(new Paragraph("Product Name", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10)));
            cell.setBackgroundColor(TableHeader);
            cell.setPadding(10f);
            table.addCell(cell);
            cell = new PdfPCell(new Paragraph("Quantity", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10)));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBackgroundColor(TableHeader);
            cell.setPadding(10f);
            table.addCell(cell);
            cell = new PdfPCell(new Paragraph("Price", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10)));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBackgroundColor(TableHeader);
            cell.setPadding(10f);
            table.addCell(cell);
            cell = new PdfPCell(new Paragraph("Net Amount", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10)));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBackgroundColor(TableHeader);
            cell.setPadding(10f);
            table.addCell(cell);
            for (int row = 0; row < prodSelected; row++) {
                cell = new PdfPCell(new Paragraph(String.format("%02d", row + 1), IBMPlex));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setPadding(10f);
                table.addCell(cell);
                cell = new PdfPCell(new Paragraph(invoiceTable[row][1], IBMPlex));
                cell.setPadding(10f);
                table.addCell(cell);
                cell = new PdfPCell(new Paragraph(String.format("%02d", Integer.valueOf(invoiceTable[row][3])), IBMPlex));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setPadding(10f);
                table.addCell(cell);
                cell = new PdfPCell(new Paragraph(com.ibm.icu.text.NumberFormat.getCurrencyInstance(new Locale("en", "in"))
                    .format(new BigDecimal(invoiceTable[row][2])), IBMPlex));
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell.setPadding(10f);
                table.addCell(cell);
                cell = new PdfPCell(new Paragraph(com.ibm.icu.text.NumberFormat.getCurrencyInstance(new Locale("en", "in"))
                    .format(new BigDecimal(invoiceTable[row][4])), IBMPlex));
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell.setPadding(10f);
                table.addCell(cell);
            }
            cell = new PdfPCell(new Paragraph("Total Amount", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10)));
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell.setPadding(10f);
            cell.setColspan(4);
            table.addCell(cell);
            cell = new PdfPCell(new Paragraph(com.ibm.icu.text.NumberFormat.getCurrencyInstance(new Locale("en", "in"))
                    .format(new BigDecimal(saleAmount)), IBMPlex));
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell.setPadding(10f);
            table.addCell(cell);
            para = new Paragraph();
            chunk = new Chunk("Total Amount in Words : ", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10));
            para.add(chunk);
            chunk = new Chunk("Rupees " + com.ibm.icu.lang.UCharacter.toTitleCase(new com.ibm.icu.text.RuleBasedNumberFormat
                (new Locale("en", "in"), com.ibm.icu.text.RuleBasedNumberFormat.SPELLOUT).format(saleAmount, "%spellout-numbering"),
                com.ibm.icu.text.BreakIterator.getWordInstance()) + " Only", IBMPlex);
            para.add(chunk);
            cell = new PdfPCell(para);
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell.setPadding(10f);
            cell.setColspan(5);
            table.addCell(cell);
            table.setSpacingBefore(40f);
            table.setWidthPercentage(100);
            document.add(table);
            table = new PdfPTable(2);
            cell = new PdfPCell(new Paragraph("THANK YOU FOR YOUR BUSINESS!", new Font(BaseFont.createFont("/com/astral/resources/IBMPlex.ttf",
                BaseFont.IDENTITY_H, BaseFont.EMBEDDED), 12, Font.BOLD)));
            cell.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell);
            cell = new PdfPCell(new Paragraph("AUTHORIZED SIGNATURE", new Font(BaseFont.createFont("/com/astral/resources/IBMPlex.ttf",
                BaseFont.IDENTITY_H, BaseFont.EMBEDDED), 12)));
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell);
            table.setSpacingBefore(80f);
            table.setWidthPercentage(100);
            document.add(table);
            document.close();
        } catch (DocumentException | IOException ex) {
            JOptionPane.showMessageDialog(null, ex, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public static void prodCSVex(File CSV) {
        ResultSet productData = com.astral.internal.SQLite.productData();
        try (FileWriter prodCSV = new FileWriter(CSV)) {
            ResultSetMetaData invMeta = productData.getMetaData();
            prodCSV.write("\"" + invMeta.getColumnLabel(1) + "\",");
            prodCSV.write("\"" + invMeta.getColumnLabel(2) + "\",");
            prodCSV.write("\"" + invMeta.getColumnLabel(3) + "\"\r\n");
            while (productData.next()) {
                prodCSV.write("\"" + productData.getString("Product ID") + "\",");
                prodCSV.write("\"" + productData.getString("Product Name") + "\",");
                prodCSV.write("\"" + productData.getString("Price") + "\"\r\n");
            }
            prodCSV.close();
        } catch (IOException | SQLException ex) {
            JOptionPane.showMessageDialog(null, ex, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public static void prodCSVim(File CSV) {
        try {
            String CSV_Line;
            java.io.BufferedReader CSV_Reader = new java.io.BufferedReader(new java.io.FileReader(CSV));
            while ((CSV_Line = CSV_Reader.readLine()) != null) {
                String[] CSV_Parts = CSV_Line.replace("\"", "").split(",");
                String PID = CSV_Parts[0];
                String name = CSV_Parts[1];
                String price = CSV_Parts[2];
                if (!PID.equals("Product ID"))
                    com.astral.internal.SQLite.updateProduct(PID, name, price);
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, ex, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
