/**
 * Student Dashboard application for managing student profiles and course enrollments.
 *
 * @author majid
 */
package com.mycompany.projectdbs;

import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

public class StudentDashboard extends JFrame {

    // Database connection details
    private static final String DB_URL = "jdbc:mysql://localhost:3306/SubjectManagementStore";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";
    
    // Panels
    private JPanel profilePanel, enrollmentPanel;

    // Student Profile components
    private JTextField studentFirstNameField, studentLastNameField, studentDOBField, studentAddressField, studentIDField, studentEmailField, studentPasswordField;
    private JTextField studentProgramField;

    // Course enrolling components
    private JComboBox<String> courseCourseIDComboBox; // Dropdown for Course IDs
    private JTextField studentStudentID; 
    private JTable courseEnrolledTable; // Table to display enrolled courses
    private DefaultTableModel tableModel;

    public StudentDashboard() {
        // Set up the frame
        setTitle("Student Dashboard");
        setSize(800, 600);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window
        setLayout(null); // Using no layout manager (absolute positioning)

        // Add the background image 
        ImageIcon background = new ImageIcon("/Users/majid/Desktop/ProjectDBS/src/main/java/com/mycompany/projectdbs/bg.png");
        JLabel bg = new JLabel(background);
        bg.setBounds(0, 0, 800, 600);

        // Create the top panel (tabPanel) to hold the title and tabbedPane
        JPanel tabPanel = new JPanel();
        tabPanel.setLayout(null);
        tabPanel.setBounds(50, 5, 1100, 100);
        tabPanel.setOpaque(false);

        // Add a title to tabPanel 
        JLabel titleLabel = new JLabel("Student Dashboard");
        titleLabel.setFont(new Font("Aharoni", Font.BOLD, 38)); 
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(5, 20, 800, 40); // Set bounds for titleLabel
        tabPanel.add(titleLabel);

        // Create panels for student profile and course enrollment
        setupStudentProfilePanel();
        setupEnrollmentPanel();

        // Create the tabbed pane and add the created panels
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setOpaque(false); // Ensure transparency for the background
        
        // Font size for tab labels
        Font tabFont = new Font("Arial", Font.BOLD, 15);
        tabbedPane.setFont(tabFont);

        // Add tabs and set foreground color for each tab
        tabbedPane.addTab("Profile", null, profilePanel, "Manage your profile");
        tabbedPane.setForegroundAt(0, Color.WHITE); // Set text color for this tab

        tabbedPane.addTab("Enrollment", null, enrollmentPanel, "Register your courses");
        tabbedPane.setForegroundAt(1, Color.WHITE);

        // Handle the selected tab color change
        tabbedPane.addChangeListener(e -> {
            for (int i = 0; i < tabbedPane.getTabCount(); i++) {
                tabbedPane.setForegroundAt(i, Color.WHITE); // Default to white
            }
            tabbedPane.setForegroundAt(tabbedPane.getSelectedIndex(), Color.BLACK); // Highlight the active tab
        });
        
        tabbedPane.setBounds(75, 110, 650, 400); // Position the tabbedPane

        // Add the tabbedPane to the tabPanel (not directly to the frame)
        add(tabbedPane); // Add the tabbedPane to the frame at the correct position
        add(tabPanel); // Add the tabPanel (containing the titleLabel) to the frame
        
        // Create the button panel at the bottom of the frame
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBounds(180, 500, 350, 60);
        buttonPanel.setLayout(null);
        buttonPanel.setOpaque(false);

        JButton logoutButton = new JButton("Logout");
        logoutButton.setBounds(120, 15, 100, 40);
        logoutButton.setFont(new Font("Serif", Font.BOLD, 20));
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setBorder(new BevelBorder(BevelBorder.RAISED, Color.BLACK, Color.GRAY));
        
        JButton quitButton = new JButton("Quit");
        quitButton.setBounds(230, 15, 100, 40);
        quitButton.setFont(new Font("Serif", Font.BOLD, 20));
        quitButton.setForeground(Color.WHITE);
        quitButton.setBorder(new BevelBorder(BevelBorder.RAISED, Color.BLACK, Color.GRAY));

        // Action listeners for buttons
        logoutButton.addActionListener(e -> {
            // Log out and show Login frame
            new Login().setVisible(true);
            dispose();     
        });

        quitButton.addActionListener(e -> System.exit(0));
        
        // Add buttons to the button panel
        buttonPanel.add(logoutButton);
        buttonPanel.add(quitButton);

        // Add the button panel to the frame
        add(buttonPanel);
        add(bg);
        
        setVisible(true);
    }
    
    // Method to set up the student profile panel
    private void setupStudentProfilePanel(){
        profilePanel = new JPanel();
        profilePanel.setBackground(new Color(0xCADCFC));
        profilePanel.setLayout(new GridLayout(8, 2, 10, 10));
        profilePanel.setBorder(new BevelBorder(BevelBorder.RAISED, Color.ORANGE, Color.GRAY));
        
        // Student registration form fields
        JLabel fName = new JLabel("First name:");
        fName.setFont(new Font("Serif", Font.BOLD, 15));
        studentFirstNameField = new JTextField();
        profilePanel.add(fName);
        profilePanel.add(studentFirstNameField);

        JLabel lName = new JLabel("Last name:");
        lName.setFont(new Font("Serif", Font.BOLD, 15));
        studentLastNameField = new JTextField();
        profilePanel.add(lName);
        profilePanel.add(studentLastNameField);

        JLabel dName = new JLabel("Date of Birth (YYYY MM DD):");
        dName.setFont(new Font("Serif", Font.BOLD, 15));
        studentDOBField = new JTextField();
        profilePanel.add(dName);
        profilePanel.add(studentDOBField);

        JLabel aName = new JLabel("Address:");
        aName.setFont(new Font("Serif", Font.BOLD, 15));
        studentAddressField = new JTextField();
        studentAddressField.setBorder(new LineBorder(Color.BLACK, 1));
        profilePanel.add(aName);
        profilePanel.add(studentAddressField);

        JLabel iName = new JLabel("Student ID:");
        iName.setFont(new Font("Serif", Font.BOLD, 15));
        studentIDField = new JTextField();
        profilePanel.add(iName);
        profilePanel.add(studentIDField);

        JLabel pName = new JLabel("Program:");
        pName.setFont(new Font("Serif", Font.BOLD, 15));
        studentProgramField = new JTextField();
        profilePanel.add(pName);
        profilePanel.add(studentProgramField);
        
        
        // Buttons
        JButton clearButton = new JButton("Clear");
        clearButton.setFont(new Font("Serif", Font.BOLD, 20));
        JButton updateButton = new JButton("Update");
        updateButton.setFont(new Font("Serif", Font.BOLD, 20));
        
        clearButton.addActionListener(e -> clearStudentProfile());
        updateButton.addActionListener(e -> updateStudentProfile());

        profilePanel.add(clearButton);
        profilePanel.add(updateButton);
        
        
        String studentId = "B09230020";
        loadStudentProfile(studentId);
    }
    
    // Method to set up the subject enrollment panel
    private void setupEnrollmentPanel(){
        enrollmentPanel = new JPanel();
        enrollmentPanel.setBackground(new Color(0xB3515B)); // Set background color
        enrollmentPanel.setLayout(new BorderLayout());
        enrollmentPanel.setBorder(new BevelBorder(BevelBorder.RAISED, Color.BLACK, Color.GRAY)); // Set border

        JPanel enrollmentFormPanel = new JPanel();
        enrollmentFormPanel.setBackground(new Color(0xB3515B)); // Set background color for form panel
        enrollmentFormPanel.setLayout(new GridLayout(4, 2, 10, 10));
        enrollmentFormPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Set padding

        // Course enrolling form fields
        JLabel courseIDLabel = new JLabel("Course ID:");
        courseIDLabel.setFont(new Font("Serif", Font.BOLD, 15)); // Font styling
        enrollmentFormPanel.add(courseIDLabel);
 
        courseCourseIDComboBox = new JComboBox<>(getCourseIDs());
        enrollmentFormPanel.add(courseCourseIDComboBox);

        JLabel studentIDLabel = new JLabel("Student ID:");
        studentIDLabel.setFont(new Font("Serif", Font.BOLD, 15)); // Font styling
        studentStudentID = new JTextField();
        enrollmentFormPanel.add(studentIDLabel);
        enrollmentFormPanel.add(studentStudentID);

        JButton enrollButton = new JButton("Enroll");
        enrollButton.setFont(new Font("Serif", Font.BOLD, 20)); // Font styling
        enrollButton.addActionListener(e -> enrollCourse());
        enrollmentFormPanel.add(enrollButton);

        JButton clearButton = new JButton("Clear");
        clearButton.setFont(new Font("Serif", Font.BOLD, 20)); // Font styling
        clearButton.addActionListener(e -> clearEnrollmentForm());
        enrollmentFormPanel.add(clearButton);

        // Table for displaying enrolled courses
        tableModel = new DefaultTableModel(new Object[]{"Course ID", "Title", "Field", "Teacher ID"}, 0);
        courseEnrolledTable = new JTable(tableModel);
        courseEnrolledTable.setFont(new Font("Serif", Font.PLAIN, 15));
        courseEnrolledTable.setFillsViewportHeight(true); // Ensure the table fills the available space
        JScrollPane scrollPane = new JScrollPane(courseEnrolledTable);
        courseEnrolledTable.setBorder(new LineBorder(Color.BLACK, 1));
        
        // Adding components to the enrollmentPanel
        enrollmentPanel.add(enrollmentFormPanel, BorderLayout.NORTH); // Add form panel at the top
        enrollmentPanel.add(scrollPane, BorderLayout.CENTER); // Add table in the center
    }

    // Method to clear student profile form fields
    private void clearStudentProfile() {
        studentFirstNameField.setText("");
        studentLastNameField.setText("");
        studentDOBField.setText("");
        studentAddressField.setText("");
        studentIDField.setText("");
        studentProgramField.setText("");
        studentEmailField.setText("");
        studentPasswordField.setText("");
    }
    
    // Method to prefill the form fields with data from the student_registration table
    private void loadStudentProfile(String studentId) {
        // Trim the student ID to remove any accidental leading or trailing spaces
        studentId = studentId.trim();

        // SQL query to retrieve data from the student_registration table
        String query = "SELECT sr.first_name, sr.last_name, sr.date_of_birth, sr.address, sr.program " +
                       "FROM student_registration sr WHERE sr.student_id = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // Output studentId for debugging purposes
            System.out.println("Searching for student ID: " + studentId);

            stmt.setString(1, studentId);  // Bind the student ID to the query
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Prefill the form fields with data from the database
                studentFirstNameField.setText(rs.getString("first_name"));
                studentLastNameField.setText(rs.getString("last_name"));
                studentDOBField.setText(rs.getString("date_of_birth"));
                studentAddressField.setText(rs.getString("address"));
                studentIDField.setText(studentId);  // This field is already known
                studentProgramField.setText(rs.getString("program"));
            } else {
                JOptionPane.showMessageDialog(this, "No record found for the provided student ID.", "Load Failed", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to load profile. Please try again.", "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method to update only the address in the database
    private void updateStudentProfile() {
        String address = studentAddressField.getText();
        String studentId = studentIDField.getText();  // Ensure student ID is prefilled and accurate

        // Perform input validation before updating the database
        if (address.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter the new address.", "Incomplete Information", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement("UPDATE student_registration SET address=? WHERE student_id=?")) {

            // Setting parameters for the PreparedStatement
            stmt.setString(1, address);
            stmt.setString(2, studentId);

            // Execute the update and provide feedback to the user
            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(this, "Address updated successfully!", "Update Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "No record found for the provided student ID.", "Update Failed", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to update address. Please try again.", "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

     private String[] getCourseIDs() {
    // Fetch course IDs from the database
    try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
        String query = "SELECT course_id FROM subject_addition";
        PreparedStatement stmt = conn.prepareStatement(query);
        ResultSet rs = stmt.executeQuery();

        java.util.List<String> courseIDs = new java.util.ArrayList<>();
        while (rs.next()) {
            courseIDs.add(rs.getString("course_id"));
        }
        return courseIDs.toArray(new String[0]);
    } catch (SQLException e) {
        e.printStackTrace();
        return new String[]{};
        }
    }

    // Method to enroll a course for the student
    private void enrollCourse() {
        String courseID = (String) courseCourseIDComboBox.getSelectedItem();
        String studentID = studentStudentID.getText();
        
        // Perform input validation before proceeding
        if (courseID == null || studentID.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a course ID and enter your student ID.", "Incomplete Information", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO enrolled_courses (course_id, title, field, teache_id, student_id) VALUES (?, ?, ?, ?, ?)")) {
            
            // Fetch course details to populate the enrollment
            try (PreparedStatement courseStmt = conn.prepareStatement("SELECT title, field, TeacherID FROM assigned_courses WHERE course_id=?")) {
                courseStmt.setString(1, courseID);
                ResultSet rs = courseStmt.executeQuery();
                
                if (rs.next()) {
                    stmt.setString(1, courseID);
                    stmt.setString(2, rs.getString("title"));
                    stmt.setString(3, rs.getString("field"));
                    stmt.setString(4, rs.getString("TeacheID"));
                    stmt.setString(5, studentID);
                    
                    // Execute the enrollment statement
                    stmt.executeUpdate();

                    // Add the course to the JTable
                    tableModel.addRow(new Object[]{courseID, rs.getString("title"), rs.getString("field"), rs.getString("TeacheID")});
                    JOptionPane.showMessageDialog(this, "Course enrolled successfully!", "Enrollment Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid Course ID.", "Enrollment Failed", JOptionPane.ERROR_MESSAGE);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to enroll course. Please try again.", "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method to clear the enrollment form fields
    private void clearEnrollmentForm() {
        courseCourseIDComboBox.setSelectedIndex(-1);
        studentStudentID.setText("");
    }

}
