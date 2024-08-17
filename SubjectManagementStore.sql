CREATE TABLE user_registrations (
    id INT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    role ENUM('Admin', 'Teacher', 'Student'),
    email VARCHAR(255) UNIQUE,
    password VARCHAR(255),
    address TEXT,
    phone VARCHAR(15)
);

CREATE TABLE student_registration (
    student_id INT PRIMARY KEY,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    date_of_birth DATE,
    address TEXT,
    program VARCHAR(255),
    FOREIGN KEY (student_id) REFERENCES user_registrations(id)
);

CREATE TABLE teacher_registration (
    teacher_id INT PRIMARY KEY,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    address TEXT,
    field VARCHAR(255),
    FOREIGN KEY (teacher_id) REFERENCES user_registrations(id)
);

CREATE TABLE subject_addition (
    course_id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255),
    field VARCHAR(255),
    lecturer_id INT,
    FOREIGN KEY (lecturer_id) REFERENCES teacher_registration(teacher_id)
);

CREATE TABLE assigned_courses (
    assigned_courses_id INT PRIMARY KEY AUTO_INCREMENT,
    course_id INT,
    teacher_id INT,
    FOREIGN KEY (course_id) REFERENCES subject_addition(course_id),
    FOREIGN KEY (teacher_id) REFERENCES teacher_registration(teacher_id)
);

CREATE TABLE enrolled_courses (
    enrolled_course_id INT PRIMARY KEY AUTO_INCREMENT,
    course_id INT,
    student_id INT,
    FOREIGN KEY (course_id) REFERENCES subject_addition(course_id),
    FOREIGN KEY (student_id) REFERENCES student_registration(student_id)
);
