🎓 Student Management System (Java + MySQL)

A console-based Student Management System built using Java, JDBC, and MySQL, designed to manage students and courses efficiently with a clean, modular, and object-oriented architecture.

📌 Features
👨‍🎓 Student Management

Register a new student

Search student by ID

List all students

Update student details:

Name

Age

Mobile number

Email

Course

Delete a student with confirmation

📚 Course Management

Add new courses (with name & description validation)

List all courses

Delete courses with confirmation

Prevent invalid course selection during student registration/update

🛠️ Technologies Used

Java (Core Java)

JDBC

MySQL

IntelliJ IDEA

Git & GitHub

🧩 Project Structure

StudentManagementSystem/

│

├── src/

│   ├── Main.java

│   ├── DbConnection.java

│   ├── StudentHandler.java

│   ├── CourseHandler.java

│   ├── StudentOperations.java

│   ├── CourseOperations.java

│

├── db.properties

├── README.md


🗄️ Database Schema
courses table
CREATE TABLE courses (
    course_id INT PRIMARY KEY AUTO_INCREMENT,
    course_name VARCHAR(100) UNIQUE NOT NULL,
    course_desc VARCHAR(255)
);

students table
CREATE TABLE students (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100),
    age INT,
    mobile_number VARCHAR(15),
    email VARCHAR(100),
    course_id INT,
    FOREIGN KEY (course_id) REFERENCES courses(course_id)
);

⚙️ Configuration

Update your database credentials in db.properties:

db.url=jdbc:mysql://localhost:3306/student_db
db.username=your_username
db.password=your_password

▶️ How to Run the Project

Clone the repository:

git clone https://github.com/aman-a25/StudentManagementSystem.git


Open the project in IntelliJ IDEA

Make sure:

MySQL server is running

Database and tables are created

JDBC driver is added

Run:

Main.java

🧠 Design Highlights

Uses interfaces for abstraction (StudentOperations, CourseOperations)

Clear separation of concerns

Reusable helper methods for safe input handling

PreparedStatements used to prevent SQL Injection

Validation checks for:

Student ID

Course ID

Course name duplication

User-friendly confirmation before destructive actions (delete/update)

🚀 Future Enhancements

Prevent deleting courses that have registered students

Search students by name

Pagination for listing students/courses

Logging instead of System.out.println

Unit testing

GUI / REST API version

👨‍💻 Author

Aman Kumar

GitHub: aman-a25

📄 License

This project is for learning and educational purposes.
