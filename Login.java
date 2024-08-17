
/**
 * @author majid
 */

package com.mycompany.projectdbs;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.*;

public class Login extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;
    private JComboBox<String> roleComboBox;
    
    private static final String DB_URL = "jdbc:mysql://localhost:3306/SubjectManagementStore";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    public Login() {
        // Set the frame properties
        setTitle("User Verification");
        setSize(650, 420);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); 
        setLayout(null);
        

        // Panel 1: Title Panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBounds(0, 5, 650, 50);
        titlePanel.setOpaque(false);
        JLabel titleLabel = new JLabel("USER AUTHENTICATION");
        titleLabel.setFont(new Font("Aharoni", Font.BOLD, 30));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);
  
        // Set background image
        ImageIcon background = new ImageIcon("/Users/majid/Desktop/ProjectDBS/src/main/java/com/mycompany/projectdbs/bg.png");
        JLabel bg = new JLabel(background);
        bg.setBounds(0, 0, 650, 420);
        
        //======================================================================
        // Panel 2: Logo and Subheading
        JPanel logoPanel = new JPanel();
        logoPanel.setBounds(10, 150, 280, 330);
        logoPanel.setOpaque(false);
        
       
         // Define the dimensions for the scaled image
        int logoWidth = 150; 
        int logoHeight = 50; 

        // Adding the logo icon after scaling it down to fit in
        ImageIcon originalIcon = new ImageIcon("/Users/majid/Desktop/ProjectDBS/src/main/java/com/mycompany/projectdbs/logo.png"); 
        Image originalImage = originalIcon.getImage();
        Image scaledImage = originalImage.getScaledInstance(logoWidth, logoHeight, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        JLabel labelImage = new JLabel(scaledIcon);
        
        JLabel subheadingLabel = new JLabel("Subject Management System", JLabel.CENTER);
        subheadingLabel.setForeground(Color.WHITE);
        subheadingLabel.setFont(new Font("Aharoni", Font.BOLD, 16));

        logoPanel.add(labelImage, BorderLayout.CENTER);
        logoPanel.add(subheadingLabel, BorderLayout.SOUTH);

        //======================================================================
        // Panel 3: Login Form
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 5, 20));
        formPanel.setBounds(320, 80, 320, 250);
        //formPanel.setBackground(Color.RED);
        formPanel.setOpaque(false);
        
        emailField = new JTextField();
        passwordField = new JPasswordField();
        roleComboBox = new JComboBox<>(new String[]{"Admin", "Student", "Teacher"});
        
        JLabel eName = new JLabel("Email:");
        eName.setFont(new Font("SansSerif", Font.BOLD, 15));
        formPanel.add(eName);
        formPanel.add(emailField);
        
        JLabel pName = new JLabel("Password:");
        pName.setFont(new Font("SansSerif", Font.BOLD, 15));
        formPanel.add(pName);
        formPanel.add(passwordField);
        
        JLabel rName = new JLabel("Role:");
        rName.setFont(new Font("SansSerif", Font.BOLD, 15));
        formPanel.add(rName);
        formPanel.add(roleComboBox);

        //======================================================================
        //Add a button Panel4 
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBounds(320, 320, 320, 70);
        buttonPanel.setLayout(null);
        buttonPanel.setBackground(Color.GRAY);
        buttonPanel.setOpaque(false);
        
        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("Serif", Font.BOLD, 15));
        loginButton.setBackground(Color.WHITE);
        loginButton.setForeground(Color.BLACK);
        loginButton.setBounds(5, 10, 100, 40 );
        loginButton.addActionListener(new LoginActionListener());
        
        JButton clearButton = new JButton("Clear");
        clearButton.setFont(new Font("Serif", Font.BOLD, 15));
        clearButton.setBackground(Color.WHITE);
        clearButton.setForeground(Color.BLACK);
        clearButton.setBounds(110, 10, 100, 40 );
        clearButton.addActionListener(e -> clearFields());
        
        JButton quitButton = new JButton("Quit");
        quitButton.setFont(new Font("Serif", Font.BOLD, 15));
        quitButton.setBackground(Color.WHITE);
        quitButton.setForeground(Color.BLACK);
        quitButton.setBounds(215, 10, 100, 40 );
        quitButton.addActionListener(e -> System.exit(0));
        
        buttonPanel.add(loginButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(quitButton);
    
        //======================================================================
        // Add Panels to the Frame
        add(buttonPanel);
        add(formPanel);
        add(logoPanel);
        add(titlePanel);
        add(bg);

    }
    //======================================================================
    private void clearFields() {
        emailField.setText("");
        passwordField.setText("");
        roleComboBox.setSelectedIndex(0);
    }

    private class LoginActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());
            String role = (String) roleComboBox.getSelectedItem();
            
            if (email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(Login.this, "Email and Password are required.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                String query = "SELECT * FROM user_registrations WHERE email = ? AND password = ? AND role = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, email);
                preparedStatement.setString(2, password);
                preparedStatement.setString(3, role);
                ResultSet resultSet = preparedStatement.executeQuery();
                
                if (resultSet.next()) {
                    JOptionPane.showMessageDialog(Login.this, "Login Successful", "Success", JOptionPane.INFORMATION_MESSAGE);
                    // Based on role, open respective dashboard
                    if("Admin".equals(role)){
                        AdminDashboard adminDashboard = new AdminDashboard();
                        dispose();
                    } else if("Student".equals(role)){
                        StudentDashboard studentDashboard = new StudentDashboard();
                        dispose();
                    }
   
                    // Add conditions for Student and Teacher roles if needed
                } else {
                    JOptionPane.showMessageDialog(Login.this, "Invalid credentials. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
            }       try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                String query = "SELECT * FROM user_registrations WHERE email = ? AND password = ? AND role = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, email);
                preparedStatement.setString(2, password);
                preparedStatement.setString(3, role);
                ResultSet resultSet = preparedStatement.executeQuery();
                
                if (resultSet.next()) {
                    JOptionPane.showMessageDialog(Login.this, "Login Successful", "Success", JOptionPane.INFORMATION_MESSAGE);
                    switch (role) {
                        case "Admin" -> {
                            AdminDashboard adminDashboard = new AdminDashboard();
                            adminDashboard.setVisible(true);
                            dispose();
                            break;
                        }
                        case "Student" -> {
                            StudentDashboard studentDashboard = new StudentDashboard();
                            studentDashboard.setVisible(true);
                            dispose();
                            break;
                        }
                        case "Teacher" -> {
                            TeacherDashboard teacherDashboard = new TeacherDashboard();
                            teacherDashboard.setVisible(true);
                            dispose();
                            break;
                        }
                        default -> {
                            break;
                            }
                    }
                } else {
                    JOptionPane.showMessageDialog(Login.this, "Invalid credentials. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
            } catch (SQLException ex) {
                }
        }
    }
}


