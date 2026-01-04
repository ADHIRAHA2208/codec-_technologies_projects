package ui;

import db.DBConnection;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class LibrarySwingApp extends JFrame {

    JTextField titleField, authorField, qtyField, bookIdField;
    JTextArea displayArea;
    Connection con;

    public LibrarySwingApp() {
        con = DBConnection.getConnection();

        setTitle("Library Management System");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // -------- FORM PANEL --------
        JPanel form = new JPanel(new GridLayout(2, 4, 10, 10));
        form.setBorder(BorderFactory.createEmptyBorder(15, 15, 10, 15));

        form.add(new JLabel("Book Title"));
        titleField = new JTextField();
        form.add(titleField);

        form.add(new JLabel("Author"));
        authorField = new JTextField();
        form.add(authorField);

        form.add(new JLabel("Quantity"));
        qtyField = new JTextField();
        form.add(qtyField);

        form.add(new JLabel("Book ID"));
        bookIdField = new JTextField();
        form.add(bookIdField);

        add(form, BorderLayout.NORTH);

        // -------- BUTTON PANEL --------
        JPanel buttons = new JPanel();

        JButton addBtn = new JButton("Add Book");
        JButton issueBtn = new JButton("Issue Book");
        JButton returnBtn = new JButton("Return Book");
        JButton viewBtn = new JButton("View Books");

        buttons.add(addBtn);
        buttons.add(issueBtn);
        buttons.add(returnBtn);
        buttons.add(viewBtn);

        add(buttons, BorderLayout.CENTER);

        // -------- DISPLAY AREA --------
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        displayArea.setFont(new Font("Monospaced", Font.PLAIN, 14));

        add(new JScrollPane(displayArea), BorderLayout.SOUTH);

        // -------- BUTTON ACTIONS --------
        addBtn.addActionListener(e -> addBook());
        issueBtn.addActionListener(e -> issueBook());
        returnBtn.addActionListener(e -> returnBook());
        viewBtn.addActionListener(e -> viewBooks());
    }

    // ADD BOOK
    void addBook() {
        try {
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO books(title,author,quantity) VALUES(?,?,?)");
            ps.setString(1, titleField.getText());
            ps.setString(2, authorField.getText());
            ps.setInt(3, Integer.parseInt(qtyField.getText()));
            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Book Added Successfully");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error Adding Book");
        }
    }

    // ISSUE BOOK
    void issueBook() {
        try {
            int id = Integer.parseInt(bookIdField.getText());

            PreparedStatement ps = con.prepareStatement(
                    "SELECT quantity FROM books WHERE id=?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                con.prepareStatement(
                        "INSERT INTO issued_books(book_id,issue_date) VALUES(" +
                                id + ",CURDATE())").execute();

                con.prepareStatement(
                        "UPDATE books SET quantity=quantity-1 WHERE id=" + id)
                        .execute();

                JOptionPane.showMessageDialog(this, "Book Issued");
            } else {
                JOptionPane.showMessageDialog(this, "Book Not Available");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid Book ID");
        }
    }

    // RETURN BOOK + LATE FEE
    void returnBook() {
        try {
            int id = Integer.parseInt(bookIdField.getText());

            PreparedStatement ps = con.prepareStatement(
                    "SELECT issue_date FROM issued_books WHERE book_id=?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                LocalDate issueDate = rs.getDate(1).toLocalDate();
                LocalDate today = LocalDate.now();

                long lateDays =
                        ChronoUnit.DAYS.between(issueDate.plusDays(7), today);
                long fine = lateDays > 0 ? lateDays * 5 : 0;

                con.prepareStatement(
                        "DELETE FROM issued_books WHERE book_id=" + id).execute();
                con.prepareStatement(
                        "UPDATE books SET quantity=quantity+1 WHERE id=" + id)
                        .execute();

                JOptionPane.showMessageDialog(this,
                        "Book Returned\nLate Days: " +
                                Math.max(lateDays, 0) +
                                "\nFine: ₹" + fine);
            } else {
                JOptionPane.showMessageDialog(this, "No Issue Record Found");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error Returning Book");
        }
    }

    // VIEW BOOKS
    void viewBooks() {
        try {
            displayArea.setText("");
            ResultSet rs =
                    con.createStatement().executeQuery("SELECT * FROM books");

            while (rs.next()) {
                displayArea.append(
                        "ID: " + rs.getInt("id") +
                        ", Title: " + rs.getString("title") +
                        ", Author: " + rs.getString("author") +
                        ", Qty: " + rs.getInt("quantity") + "\n");
            }
        } catch (Exception e) {
            displayArea.setText("Error Loading Data");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() ->
                new LibrarySwingApp().setVisible(true));
    }
}
