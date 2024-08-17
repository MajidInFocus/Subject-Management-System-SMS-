
/**
 *
 * @author majid
 */


package com.mycompany.projectdbs;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

public class AdminDashboard extends JFrame {
    // Database connection details
    private static final String DB_URL = "jdbc:mysql://localhost:3306/SubjectManagementStore";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    // Panels
    private JPanel studentPanel, teacherPanel, subjectPanel, coursePanel;

    // Student registration components
    private JTextField studentFirstNameField, studentLastNameField, studentDOBField, studentAddressField, studentIDField;
    private JComboBox<String> studentProgramComboBox;

    // Teacher registration components
    private JTextField teacherFirstNameField, teacherLastNameField, teacherAddressField, teacherIDField;
    private JComboBox<String> teacherFieldComboBox;

    // Subject addition components
    private JTextField subjectTitleField, subjectCourseIDField;
    private JComboBox<String> subjectFieldComboBox;

    // Course assigning components
    
    // Course assigning components
    private JComboBox<String> courseCourseIDComboBox;  // Dropdown for Course IDs
    private JComboBox<String> courseTeacherIDComboBox; // Dropdown for Teacher IDs
    private JTable courseTable;                        // Table to display course assignments
    private DefaultTableModel tableModel;              // Table model for the courseTable


    public AdminDashboard() {
        // Set up the frame
        setTitle("Admin Dashboard");
        setSize(1000, 700);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); 
        setLayout(null); // Using no layout manager (absolute positioning)
     
        
        //Add the background image 
        ImageIcon background = new ImageIcon("/Users/majid/Desktop/ProjectDBS/src/main/java/com/mycompany/projectdbs/bg.png");
        JLabel bg = new JLabel(background);
        bg.setBounds(0, 0, 1000, 700);
        

        // ==========================================================================
        // Create the top panel (tabPanel) to hold the title and tabbedPane
        JPanel tabPanel = new JPanel();
        tabPanel.setLayout(null);
        tabPanel.setBounds(50, 5, 1100, 100);
        tabPanel.setOpaque(false);
        //tabPanel.setBackground(new Color(0x8F1402));
       

        // Add a title to tabPanel 
        JLabel titleLabel = new JLabel("Admin Dashboard");
        titleLabel.setFont(new Font("Aharoni", Font.BOLD, 38)); 
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(10, 20, 400, 40); // Set bounds for titleLabel
        tabPanel.add(titleLabel);

        // Create panels for student, teacher, subject, and course management
        createStudentPanel();
        createTeacherPanel();
        createSubjectPanel();
        createCoursePanel();

        
        // Create the tabbed pane and add the created panels
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setOpaque(false); // Ensure transparency for the background

        // Font size for tab labels
        Font tabFont = new Font("Arial", Font.BOLD, 15);
        tabbedPane.setFont(tabFont);

        // Add tabs and set foreground color for each tab
        tabbedPane.addTab("Student Registration", null, studentPanel, "Manage student registrations");
        tabbedPane.setForegroundAt(0, Color.WHITE); // Set text color for this tab

        tabbedPane.addTab("Teacher Registration", null, teacherPanel, "Manage teacher registrations");
        tabbedPane.setForegroundAt(1, Color.WHITE);

        tabbedPane.addTab("Subject Addition", null, subjectPanel, "Add new subjects");
        tabbedPane.setForegroundAt(2, Color.WHITE);

        tabbedPane.addTab("Course Assigning", null, coursePanel, "Assign courses to students");
        tabbedPane.setForegroundAt(3, Color.WHITE);

        // To handle the selected tab, change the color of the active tab's label
        tabbedPane.addChangeListener(e -> {
            for (int i = 0; i < tabbedPane.getTabCount(); i++) {
                tabbedPane.setForegroundAt(i, Color.WHITE); // Default to white
            }
            tabbedPane.setForegroundAt(tabbedPane.getSelectedIndex(),  Color.BLACK); // Highlight the active tab (gold color)
        });

        
        tabbedPane.setBounds(80, 120, 800, 400); // Position the tabbedPane

        // Add the tabbedPane to the tabPanel (not directly to the frame)
        add(tabbedPane); // Add the tabbedPane to the frame at the correct position
        add(tabPanel); // Add the tabPanel (containing the titleLabel) to the frame
        
        // ==========================================================================
        // Create the button panel at the bottom of the frame
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBounds(320, 600, 350, 60);
        buttonPanel.setLayout(null);
        buttonPanel.setOpaque(false);
        //buttonPanel.setBackground(Color.WHITE);

        // Create buttons for user management, logout, and quit
        JButton userButton = new JButton("USER");
        userButton.setBounds(10, 15, 100, 40);
        userButton.setFont(new Font("Serif", Font.BOLD, 20));
        //userButton.setBackground(Color.WHITE);
        userButton.setForeground(Color.WHITE);
        userButton.setBorder(new BevelBorder(BevelBorder.RAISED, Color.BLACK, Color.GRAY));
        
        JButton logoutButton = new JButton("Logout");
        logoutButton.setBounds(120, 15, 100, 40);
        logoutButton.setFont(new Font("Serif", Font.BOLD, 20));
       // logoutButton.setBackground(Color.WHITE);
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setBorder(new BevelBorder(BevelBorder.RAISED, Color.BLACK, Color.GRAY));
        
        JButton quitButton = new JButton("Quit");
        quitButton.setBounds(230, 15, 100, 40);
        quitButton.setFont(new Font("Serif", Font.BOLD, 20));
        //quitButton.setBackground(Color.WHITE);
        quitButton.setForeground(Color.WHITE);
        quitButton.setBorder(new BevelBorder(BevelBorder.RAISED, Color.BLACK, Color.GRAY));

        // Action listeners for buttons
        userButton.addActionListener((ActionEvent e) -> {
          try {
              // Show UserRegistration form
              UserRegistrations userRegistration = new UserRegistrations();
              userRegistration.setVisible(true);

              // Dispose of the current AdminDashboard frame
              //dispose();
          } catch (Exception ex) {
              ex.printStackTrace();
              JOptionPane.showMessageDialog(this, "An error occurred while trying to open the User Registration form.");
          }
        });


        logoutButton.addActionListener(e -> {
            // Log out and show Login frame
            new Login().setVisible(true);
            dispose();
            
        });

        quitButton.addActionListener(e -> System.exit(0));

        // Add buttons to the button panel
        buttonPanel.add(userButton);
        buttonPanel.add(logoutButton);
        buttonPanel.add(quitButton);

        // Add the button panel to the frame
        add(buttonPanel);
        add(bg);

        // Set the frame to be visible
        setVisible(true);
    }


    private void createStudentPanel() {
        
        studentPanel = new JPanel();
        studentPanel.setBackground(new Color(0xB3515B));
        studentPanel.setLayout(new GridLayout(8, 2));
        studentPanel.setBorder(new BevelBorder(BevelBorder.RAISED, Color.BLACK, Color.GRAY));
        
        
        // Student registration form fields
        JLabel fName = new JLabel("First name:");
        fName.setFont(new Font("Serif", Font.BOLD, 15));
        
        studentFirstNameField = new JTextField();
        studentFirstNameField.setBorder(new LineBorder(Color.BLACK, 1));
        studentPanel.add(fName);
        studentPanel.add(studentFirstNameField);

        JLabel lName = new JLabel("Last name:");
        lName.setFont(new Font("Serif", Font.BOLD, 15));
        studentLastNameField = new JTextField();
        studentPanel.add(lName);
        studentPanel.add(studentLastNameField);

        JLabel dName = new JLabel("Date of Birth (YYYY MM DD):");
        dName.setFont(new Font("Serif", Font.BOLD, 15));
        studentDOBField = new JTextField();
        studentDOBField.setBorder(new LineBorder(Color.BLACK, 1));
        studentPanel.add(dName);
        studentPanel.add(studentDOBField);

        JLabel aName = new JLabel("Address:");
        aName.setFont(new Font("Serif", Font.BOLD, 15));
        studentAddressField = new JTextField();
        studentPanel.add(aName);
        studentPanel.add(studentAddressField);

        JLabel iName = new JLabel("Student ID:");
        iName.setFont(new Font("Serif", Font.BOLD, 15));
        studentIDField = new JTextField();
        studentIDField.setBorder(new LineBorder(Color.BLACK, 1));
        studentPanel.add(iName);
        studentPanel.add(studentIDField);

        JLabel pName = new JLabel("Program:");
        pName.setFont(new Font("Serif", Font.BOLD, 15));
        studentProgramComboBox = new JComboBox<>(new String[]{"BCS", "BCE", "BSE", "DIT", "DISE", "FCE", "FIT", "FCS"});
        studentPanel.add(pName);
        studentPanel.add(studentProgramComboBox);

        // Buttons
        JButton clearButton = new JButton("Clear");
        clearButton.setFont(new Font("Serif", Font.BOLD, 20));
        JButton registerButton = new JButton("Register");
        registerButton.setFont(new Font("Serif", Font.BOLD, 20));
        
        clearButton.addActionListener(e -> clearStudentForm());
        registerButton.addActionListener(e -> registerStudent());

        studentPanel.add(clearButton);
        studentPanel.add(registerButton);
    }

    private void createTeacherPanel() {
        teacherPanel = new JPanel();
        teacherPanel.setBackground(new Color(0xB3515B));
        teacherPanel.setLayout(new GridLayout(7, 2));
        teacherPanel.setBorder(new BevelBorder(BevelBorder.RAISED, Color.BLACK, Color.GRAY));

        // Initialize the JTextFields and JComboBox
        teacherFirstNameField = new JTextField();
        teacherLastNameField = new JTextField();
        teacherAddressField = new JTextField();
        teacherIDField = new JTextField();
        teacherFieldComboBox = new JComboBox<>(new String[]{"Mathematics", "Science", "Computer Science", "Engineering", "Business", "IT"});

        // Teacher registration form fields
        JLabel ftName = new JLabel("First name:");
        ftName.setFont(new Font("Serif", Font.BOLD, 15));
        teacherPanel.add(ftName);
        teacherFirstNameField.setBorder(new LineBorder(Color.BLACK, 1));
        teacherPanel.add(teacherFirstNameField);

        JLabel ltName = new JLabel("Last name:");
        ltName.setFont(new Font("Serif", Font.BOLD, 15));
        teacherPanel.add(ltName);
        teacherPanel.add(teacherLastNameField);

        JLabel atName = new JLabel("Address:");
        atName.setFont(new Font("Serif", Font.BOLD, 15));
        teacherPanel.add(atName);
        teacherAddressField.setBorder(new LineBorder(Color.BLACK, 1));
        teacherPanel.add(teacherAddressField);

        JLabel i_tName = new JLabel("Teacher ID:");
        i_tName.setFont(new Font("Serif", Font.BOLD, 15));
        teacherPanel.add(i_tName);
        teacherPanel.add(teacherIDField);

        JLabel f_tName = new JLabel("Field:");
        f_tName.setFont(new Font("Serif", Font.BOLD, 15));
        teacherPanel.add(f_tName);
        teacherPanel.add(teacherFieldComboBox);

        // Buttons
        JButton clearButton = new JButton("Clear");
        clearButton.setFont(new Font("Serif", Font.BOLD, 20));
        JButton registerButton = new JButton("Register");
        registerButton.setFont(new Font("Serif", Font.BOLD, 20));

        clearButton.addActionListener(e -> clearTeacherForm());
        registerButton.addActionListener(e -> registerTeacher());

        teacherPanel.add(clearButton);
        teacherPanel.add(registerButton);
    }


    private void createSubjectPanel() {
        subjectPanel = new JPanel();
        subjectPanel.setBackground(new Color(0xB3515B)); // Set background color
        subjectPanel.setLayout(new GridLayout(7, 2)); // Set layout
        subjectPanel.setBorder(new BevelBorder(BevelBorder.RAISED, Color.BLACK, Color.GRAY)); // Set border

        // Subject addition form fields
        JLabel titleLabel = new JLabel("Title:");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 15)); // Set font
        subjectPanel.add(titleLabel);
        subjectTitleField = new JTextField();
        subjectTitleField.setBorder(new LineBorder(Color.BLACK, 1)); // Set border
        subjectPanel.add(subjectTitleField);

        JLabel courseIDLabel = new JLabel("Course ID:");
        courseIDLabel.setFont(new Font("Serif", Font.BOLD, 15)); // Set font
        subjectPanel.add(courseIDLabel);
        subjectCourseIDField = new JTextField();
        subjectPanel.add(subjectCourseIDField);

        JLabel fieldLabel = new JLabel("Field:");
        fieldLabel.setFont(new Font("Serif", Font.BOLD, 15)); // Set font
        subjectPanel.add(fieldLabel);
        subjectFieldComboBox = new JComboBox<>(new String[]{"Mathematics", "Science", "Computer Science", "Engineering", "Business", "IT"});
        subjectPanel.add(subjectFieldComboBox);

        // Buttons
        JButton clearButton = new JButton("Clear");
        clearButton.setFont(new Font("Serif", Font.BOLD, 20)); // Set font
        JButton addButton = new JButton("Add");
        addButton.setFont(new Font("Serif", Font.BOLD, 20)); // Set font

        clearButton.addActionListener(e -> clearSubjectForm());
        addButton.addActionListener(e -> addSubject());

        subjectPanel.add(clearButton);
        subjectPanel.add(addButton);
    }


    private void createCoursePanel() {
     coursePanel = new JPanel();
     coursePanel.setBackground(new Color(0xB3515B)); // Set background color
     coursePanel.setLayout(new BorderLayout());
     coursePanel.setBorder(new BevelBorder(BevelBorder.RAISED, Color.BLACK, Color.GRAY)); // Set border

     JPanel formPanel = new JPanel();
     formPanel.setBackground(new Color(0xB3515B)); // Set background color for form panel
     formPanel.setLayout(new GridLayout(5, 2));
     formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Set padding

     // Course assigning form fields
     JLabel courseIDLabel = new JLabel("Course ID:");
     courseIDLabel.setFont(new Font("Serif", Font.BOLD, 15)); // Set font
     formPanel.add(courseIDLabel);

     courseCourseIDComboBox = new JComboBox<>(getCourseIDs());
     formPanel.add(courseCourseIDComboBox);

     JLabel teacherIDLabel = new JLabel("Teacher ID:");
     teacherIDLabel.setFont(new Font("Serif", Font.BOLD, 15)); // Set font
     formPanel.add(teacherIDLabel);

     courseTeacherIDComboBox = new JComboBox<>(getTeacherIDs());
     formPanel.add(courseTeacherIDComboBox);

     // Buttons
     JButton clearButton = new JButton("Clear");
     clearButton.setFont(new Font("Serif", Font.BOLD, 20)); // Set font
     JButton assignButton = new JButton("Assign");
     assignButton.setFont(new Font("Serif", Font.BOLD, 20)); // Set font

     assignButton.addActionListener(e -> assignCourse());

     formPanel.add(clearButton);
     formPanel.add(assignButton);

     // Course table
     tableModel = new DefaultTableModel(new String[]{"Course ID", "Title", "Field", "Teacher ID"}, 0);
     courseTable = new JTable(tableModel);
     courseTable.setFont(new Font("Serif", Font.PLAIN, 14)); // Set font for table
     courseTable.setRowHeight(25); // Set row height
     courseTable.setGridColor(Color.GRAY); // Set grid color

     JScrollPane tableScrollPane = new JScrollPane(courseTable);
     tableScrollPane.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Set border for table scroll pane

     JPanel tableButtonPanel = new JPanel();
     tableButtonPanel.setBackground(new Color(0xB3515B)); // Set background color for button panel
     tableButtonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Set padding

     JButton viewButton = new JButton("View");
     viewButton.setFont(new Font("Serif", Font.BOLD, 20)); // Set font
     JButton deleteButton = new JButton("Delete");
     deleteButton.setFont(new Font("Serif", Font.BOLD, 20)); // Set font

     viewButton.addActionListener(e -> viewCourses());
     deleteButton.addActionListener(e -> deleteCourse());

     tableButtonPanel.add(viewButton);
     tableButtonPanel.add(deleteButton);

     coursePanel.add(formPanel, BorderLayout.NORTH);
     coursePanel.add(tableScrollPane, BorderLayout.CENTER);
     coursePanel.add(tableButtonPanel, BorderLayout.SOUTH);
    }



    // Clear form methods
    private void clearStudentForm() {
        studentFirstNameField.setText("");
        studentLastNameField.setText("");
        studentDOBField.setText("");
        studentAddressField.setText("");
        studentIDField.setText("");
        studentProgramComboBox.setSelectedIndex(0);
    }

    private void clearTeacherForm() {
        teacherFirstNameField.setText("");
        teacherLastNameField.setText("");
        teacherAddressField.setText("");
        teacherIDField.setText("");
        teacherFieldComboBox.setSelectedIndex(0);
    }

    private void clearSubjectForm() {
        subjectTitleField.setText("");
        subjectCourseIDField.setText("");
        subjectFieldComboBox.setSelectedIndex(0);
    }


    private void registerStudent() {
        String firstName = studentFirstNameField.getText();
        String lastName = studentLastNameField.getText();
        String dob = studentDOBField.getText();  // This should be in "yyyy MM dd" format
        String address = studentAddressField.getText();
        String studentID = studentIDField.getText();
        String program = (String) studentProgramComboBox.getSelectedItem();

        try {
            // Update the date pattern to match "yyyy MM dd"
            LocalDate localDate = LocalDate.parse(dob, DateTimeFormatter.ofPattern("yyyy MM dd"));
            String formattedDOB = localDate.toString(); // Converts to "YYYY-MM-DD" format

            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                String query = "INSERT INTO student_registration (student_id, first_name, last_name, date_of_birth, address, program) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setString(1, studentID);
                stmt.setString(2, firstName);
                stmt.setString(3, lastName);
                stmt.setString(4, formattedDOB);
                stmt.setString(5, address);
                stmt.setString(6, program);
                stmt.executeUpdate();
                JOptionPane.showMessageDialog(this, "Student registered successfully.");
                clearStudentForm();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error registering student.");
        } catch (DateTimeParseException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Invalid date format. Please use 'YYYY MM DD'.");
        }
    }

    private void registerTeacher() {
        String firstName = teacherFirstNameField.getText();
        String lastName = teacherLastNameField.getText();
        String address = teacherAddressField.getText();
        String teacherID = teacherIDField.getText();
        String field = (String) teacherFieldComboBox.getSelectedItem();

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "INSERT INTO teacher_registration (teacher_id, first_name, last_name, address, field) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, teacherID);
            stmt.setString(2, firstName);
            stmt.setString(3, lastName);
            stmt.setString(4, address);
            stmt.setString(5, field);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Teacher registered successfully.");
            clearTeacherForm();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error registering teacher.");
        }
    }

    private void addSubject() {
        String title = subjectTitleField.getText();
        String courseID = subjectCourseIDField.getText();
        String field = (String) subjectFieldComboBox.getSelectedItem();

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "INSERT INTO subject_addition (course_id, title, field) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, courseID);
            stmt.setString(2, title);
            stmt.setString(3, field);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Subject added successfully.");
            clearSubjectForm();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error adding subject.");
        }
    }

    private void assignCourse() {
        String courseID = (String) courseCourseIDComboBox.getSelectedItem();
        String teacherID = (String) courseTeacherIDComboBox.getSelectedItem();

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            // Insert the course assignment into the assigned_courses table
            String query = "INSERT INTO assigned_courses (CourseID, TeacherID) VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, courseID);
            stmt.setString(2, teacherID);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Course assigned successfully.");
            viewCourses(); // Refresh the table view after assignment
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error assigning course.");
            }
    }


    private void viewCourses() {
       try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
           String query = "SELECT ac.CourseID, sa.title, sa.field, ac.TeacherID " +
                          "FROM assigned_courses ac " +
                          "JOIN subject_addition sa ON ac.CourseID = sa.course_id";
           PreparedStatement stmt = conn.prepareStatement(query);
           ResultSet rs = stmt.executeQuery();

           tableModel.setRowCount(0); // Clear existing data

           while (rs.next()) {
               String courseID = rs.getString("CourseID");
               String title = rs.getString("title");
               String field = rs.getString("field");
               String teacherID = rs.getString("TeacherID");
               tableModel.addRow(new Object[]{courseID, title, field, teacherID});
           }
       } catch (SQLException e) {
           e.printStackTrace();
           JOptionPane.showMessageDialog(this, "Error retrieving courses.");
            }
    }



    private void deleteCourse() {
        int selectedRow = courseTable.getSelectedRow();
        if (selectedRow != -1) {
            String courseID = (String) tableModel.getValueAt(selectedRow, 0);

            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                // Delete the course assignment from the assigned_courses table
                String query = "DELETE FROM assigned_courses WHERE CourseID = ?";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setString(1, courseID);
                stmt.executeUpdate();
                JOptionPane.showMessageDialog(this, "Course assignment deleted successfully.");
                tableModel.removeRow(selectedRow);
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error deleting course assignment.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a course to delete.");
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
    
    private String[] getTeacherIDs() {
    // Fetch teacher IDs from the database
    try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
        String query = "SELECT teacher_id FROM teacher_registration";
        PreparedStatement stmt = conn.prepareStatement(query);
        ResultSet rs = stmt.executeQuery();

        java.util.List<String> teacherIDs = new java.util.ArrayList<>();
        while (rs.next()) {
            teacherIDs.add(rs.getString("teacher_id"));
        }
        return teacherIDs.toArray(new String[0]);
    } catch (SQLException e) {
        e.printStackTrace();
        return new String[]{};
        }
    }
}
