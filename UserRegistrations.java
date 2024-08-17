/**
 * @author majid
 */

package com.mycompany.projectdbs;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableModel;

public class UserRegistrations extends JFrame {
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JComboBox<String> roleComboBox;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JTextField addressField;
    private JTextField phoneField;
    private JTable table;
    private DefaultTableModel tableModel;
    private Image backgroundImage;

    private static final String DB_URL = "jdbc:mysql://localhost:3306/SubjectManagementStore";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    public UserRegistrations() { 
        setTitle("User Registration");
        setSize(1000, 600);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  
        setLocationRelativeTo(null);  
        setLayout(null);  // Using null layout for custom positioning
        
        //Add a Panel Title 
        JPanel titlePanel = new JPanel();
        titlePanel.setBounds(0, 5, 1000, 50);
        titlePanel.setBackground(new Color(0x8F1402));
        //Title Label
        JLabel titleLabel = new JLabel("USER REGISTRATION");
        titleLabel.setFont(new Font("Aharoni", Font.BOLD, 40));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);
        
        //Add a panel for logo
        JPanel logoPanel = new JPanel();
        logoPanel.setBounds(20, 75, 330, 250);
        
        //Scalling logo and then adding it to the panel 
        int logoWidth = 130; 
        int logoHeight = 100; 

        // Adding the logo icon after scaling it down to fit in
        ImageIcon originalIcon = new ImageIcon("/Users/majid/Desktop/ProjectDBS/src/main/java/com/mycompany/projectdbs/userRegLogo.png"); 
        Image originalImage = originalIcon.getImage();
        Image scaledImage = originalImage.getScaledInstance(logoWidth, logoHeight, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        JLabel labelImage = new JLabel(scaledIcon);
        labelImage.setBounds(100, 100, 140, 110); 
        
        logoPanel.add(labelImage);
 
        // Form Panel for User Inputs
        JPanel formPanel = new JPanel(new GridLayout(7, 2, 5, 5));
        formPanel.setBounds(360, 75, 400, 250);

        // Creating Form Fields
        firstNameField = new JTextField(20);
        lastNameField = new JTextField(20);
        roleComboBox = new JComboBox<>(new String[]{"Student", "Teacher"});
        emailField = new JTextField(20);
        passwordField = new JPasswordField(20);
        addressField = new JTextField(20);
        phoneField = new JTextField(20);

        // Adding Form Labels and Fields to Form Panel
        JLabel fName = new JLabel("First Name:");
        fName.setFont(new Font("Prociono", Font.PLAIN, 15));
        formPanel.add(fName);
        formPanel.add(firstNameField);
        
        JLabel lName = new JLabel("Last Name:");
        lName.setFont(new Font("Prociono", Font.PLAIN, 15));
        formPanel.add(lName);
        formPanel.add(lastNameField);
        
        JLabel rName = new JLabel("Role:");
        rName.setFont(new Font("Prociono", Font.PLAIN, 15));
        formPanel.add(rName);
        formPanel.add(roleComboBox);
        
        JLabel eName = new JLabel("Email:");
        eName.setFont(new Font("Prociono", Font.PLAIN, 15));
        formPanel.add(eName);
        formPanel.add(emailField);
        
        JLabel pName = new JLabel("Password:");
        pName.setFont(new Font("Prociono", Font.PLAIN, 15));
        formPanel.add(pName);
        formPanel.add(passwordField);
        
        JLabel aName = new JLabel("Address:");
        aName.setFont(new Font("Prociono", Font.PLAIN, 15));
        formPanel.add(aName);
        formPanel.add(addressField);
        
        JLabel phName = new JLabel("Phone:");
        phName.setFont(new Font("Prociono", Font.PLAIN, 15));
        formPanel.add(phName);
        formPanel.add(phoneField);

        // Button Panel for Actions
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setLayout(null);
        buttonPanel.setBounds(770, 75, 180, 250);
       
        
        
        JButton registerButton = new JButton("Register");
        registerButton.setBounds(20, 10, 130, 40);
        registerButton.setBackground(Color.GRAY);
        registerButton.setFont(new Font("Andalus", Font.BOLD, 15));
        registerButton.setBorder(new BevelBorder(BevelBorder.RAISED, Color.BLACK, Color.GRAY));
        
        JButton clearButton = new JButton("Clear");
        clearButton.setBounds(20, 70, 130, 40);
        clearButton.setBackground(Color.GRAY);
        clearButton.setFont(new Font("Andalus", Font.BOLD, 15));
        clearButton.setBorder(new BevelBorder(BevelBorder.RAISED, Color.BLACK, Color.GRAY));
        
        JButton viewButton = new JButton("View");
        viewButton.setBounds(20, 140, 130, 40);
        viewButton.setBackground(Color.GRAY);
        viewButton.setFont(new Font("Andalus", Font.BOLD, 15));
        viewButton.setBorder(new BevelBorder(BevelBorder.RAISED, Color.BLACK, Color.GRAY));
        
        JButton deleteButton = new JButton("Delete");
        deleteButton.setBounds(20, 210, 130, 40);
        deleteButton.setBackground(Color.GRAY);
        deleteButton.setFont(new Font("Andalus", Font.BOLD, 15));
        deleteButton.setBorder(new BevelBorder(BevelBorder.RAISED, Color.BLACK, Color.GRAY));
        
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addUser();
                fetchUsers(); // Refresh table after adding data
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearFields();
            }
        });

        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fetchUsers();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteUser();
                fetchUsers(); // Refresh table after deleting data
            }
        });

        // Adding Buttons to Button Panel
        buttonPanel.add(registerButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(viewButton);
        buttonPanel.add(deleteButton);

        // Table to Display User Data
        tableModel = new DefaultTableModel();
        table = new JTable(tableModel);
        tableModel.addColumn("User ID");
        tableModel.addColumn("First Name");
        tableModel.addColumn("Last Name");
        tableModel.addColumn("Role");
        tableModel.addColumn("Email");
        tableModel.addColumn("Password"); // Added Password Column
        tableModel.addColumn("Address");
        tableModel.addColumn("Phone");
        
        JScrollPane tableScrollPane = new JScrollPane(table);
        tableScrollPane.setBounds(20, 335, 930, 200);
        
        // Adding Components to Main Panel
        add(titlePanel);
        add(logoPanel);
        add(formPanel);
        add(buttonPanel);
        add(tableScrollPane);

        fetchUsers();
    }

    // Method to Add User to Database
    private void addUser() {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String role = (String) roleComboBox.getSelectedItem();
        String email = emailField.getText();
        String password = new String(passwordField.getPassword());
        String address = addressField.getText();
        String phone = phoneField.getText();

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty() || address.isEmpty() || phone.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "INSERT INTO user_registrations (first_name, last_name, role, email, password, address, phone) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, role);
            preparedStatement.setString(4, email);
            preparedStatement.setString(5, password);
            preparedStatement.setString(6, address);
            preparedStatement.setString(7, phone);
            preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(this, "User registered successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to Clear Form Fields
    private void clearFields() {
        firstNameField.setText("");
        lastNameField.setText("");
        roleComboBox.setSelectedIndex(0);
        emailField.setText("");
        passwordField.setText("");
        addressField.setText("");
        phoneField.setText("");
    }

    // Method to Fetch Users from Database and Display in Table
    private void fetchUsers() {
        tableModel.setRowCount(0); // Clear existing rows
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT * FROM user_registrations";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int userId = resultSet.getInt("id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String role = resultSet.getString("role");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password"); // Fetching Password
                String address = resultSet.getString("address");
                String phone = resultSet.getString("phone");
                tableModel.addRow(new Object[]{userId, firstName, lastName, role, email, password, address, phone});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to Delete User from Database
    private void deleteUser() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a user to delete.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int userId = (int) tableModel.getValueAt(selectedRow, 0);
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "DELETE FROM user_registrations WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, userId);
            preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(this, "User deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            }
    }
}
