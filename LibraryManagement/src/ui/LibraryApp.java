package ui;

import db.DBConnection;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LibraryApp extends JFrame {

    JTextField titleField, authorField, qtyField, userField, bookIdField;
    JTextArea displayArea;
    Connection conn;

    public LibraryApp() {
        conn = DBConnection.connect();

        setTitle("Library Management System");
        setSize(700, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Top Panel
        JPanel panel = new JPanel(new GridLayout(6, 2, 5, 5));

        panel.add(new JLabel("Book Title:"));
        titleField = new JTextField();
        panel.add(titleField);

        panel.add(new JLabel("Author:"));
        authorField = new JTextField();
        panel.add(authorField);

        panel.add(new JLabel("Quantity:"));
        qtyField = new JTextField();
        panel.add(qtyField);

        panel.add(new JLabel("Book ID:"));
        bookIdField = new JTextField();
        panel.add(bookIdField);

        panel.add(new JLabel("User Name:"));
        userField = new JTextField();
        panel.add(userField);

        JButton addBtn = new JButton("Add Book");
        JButton issueBtn = new JButton("Issue Book");
        JButton viewBtn = new JButton("View Books");

        panel.add(addBtn);
        panel.add(issueBtn);

        add(panel, BorderLayout.NORTH);

        // Display Area
        displayArea = new JTextArea();
        add(new JScrollPane(displayArea), BorderLayout.CENTER);
        add(viewBtn, BorderLayout.SOUTH);

        // Button Actions
        addBtn.addActionListener(e -> addBook());
        issueBtn.addActionListener(e -> issueBook());
        viewBtn.addActionListener(e -> viewBooks());
    }

    // Add Book
    void addBook() {
        try {
            String sql = "INSERT INTO books(title, author, quantity) VALUES(?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, titleField.getText());
            ps.setString(2, authorField.getText());
            ps.setInt(3, Integer.parseInt(qtyField.getText()));
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Book Added Successfully!");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // Issue Book
    void issueBook() {
        try {
            int bookId = Integer.parseInt(bookIdField.getText());

            PreparedStatement check = conn.prepareStatement(
                    "SELECT quantity FROM books WHERE id=?");
            check.setInt(1, bookId);
            ResultSet rs = check.executeQuery();

            if (rs.next() && rs.getInt("quantity") > 0) {
                PreparedStatement issue = conn.prepareStatement(
                        "INSERT INTO issued(book_id, user_name, issue_date) VALUES(?,?,date('now'))");
                issue.setInt(1, bookId);
                issue.setString(2, userField.getText());
                issue.executeUpdate();

                PreparedStatement update = conn.prepareStatement(
                        "UPDATE books SET quantity = quantity - 1 WHERE id=?");
                update.setInt(1, bookId);
                update.executeUpdate();

                JOptionPane.showMessageDialog(this, "Book Issued!");
            } else {
                JOptionPane.showMessageDialog(this, "Book Not Available!");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // View Books
    void viewBooks() {
        try {
            displayArea.setText("");
            ResultSet rs = conn.createStatement()
                    .executeQuery("SELECT * FROM books");

            while (rs.next()) {
                displayArea.append(
                        "ID: " + rs.getInt("id") +
                        ", Title: " + rs.getString("title") +
                        ", Author: " + rs.getString("author") +
                        ", Qty: " + rs.getInt("quantity") + "\n");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LibraryApp().setVisible(true));
    }
}
