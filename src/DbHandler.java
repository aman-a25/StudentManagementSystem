import java.sql.*;
import java.util.Scanner;

public class DbHandler implements StudentOperations {

    private final Scanner scan = new Scanner(System.in);

    @Override
    public void register() {

        System.out.println("\n ====== REGISTER NEW STUDENT ======");

        System.out.println("Enter the name of the new student : ");
        String name = scan.nextLine();
        if (name.trim().isEmpty()) {
            System.out.println("you have entered a empty string as name please try again");
        }

        System.out.println("Enter the age of the new student : ");
        int age = readIntSafe();
        // todo: implement a logic for making sure the string is not empty one

        System.out.println("Enter the mobile no.  of the new student : ");
        String moNo = scan.nextLine();

        System.out.println("Enter the email of the new student : ");
        String email = scan.nextLine();

        System.out.println("Enter the course: of the new student  ");
        new CourseHandler().listCourses();
        System.out.println("please enter course ID : ");

        int course = readIntSafe();
        // todo: implement a logic for checking that is course id exists or not in sql table

        System.out.println("\nStudent data captured — Next step is DB insert.");

        String sql = "INSERT INTO students (name, age, mobile_number, email, course_id) VALUES (?, ?, ?, ?, ?)";

        try (
                Connection con = DbConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
                ){

            // preparing the statement with the pre created sql string
            ps.setString(1, name);
            ps.setInt(2, age);
            ps.setString(3, moNo);
            ps.setString(4, email);
            ps.setInt(5, course);

            // executing the prepared statement
            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("✅ Student registered successfully!");
            } else {
                System.out.println("❌ Failed to register student.");
            }
        } catch (SQLException e) {
            System.out.println("❌ Error during registration: " + e.getMessage());
        }
    }
    @Override
    public void search() {

        System.out.println("\n==== SEARCH STUDENT ====");

        System.out.println("Enter student ID : ");
        int id = readIntSafe();

        //validate student ID
        if(!isValidStudentId(id)){
            System.out.println("❌ No student found with ID: " + id);
            return;
        }

        String sql = """
            SELECT s.id, s.name, s.age, s.mobile_number, s.email, c.course_name
            FROM students s
            JOIN courses c ON s.course_id = c.course_id
            WHERE s.id = ?
        """;

        try(Connection con = DbConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                System.out.println("\n----- STUDENT DETAILS -----");
                System.out.println("ID       : " + rs.getInt("id"));
                System.out.println("Name     : " + rs.getString("name"));
                System.out.println("Age      : " + rs.getInt("age"));
                System.out.println("Mobile   : " + rs.getString("mobile_number"));
                System.out.println("Email    : " + rs.getString("email"));
                System.out.println("Course   : " + rs.getString("course_name"));
                System.out.println("---------------------------");
            }
        } catch (SQLException e) {
            System.out.println("failed during search "+ e.getMessage());

        }

    }

    @Override
    public void listAll() {
        System.out.println("=========================== list of all students ===========================");
        String sql = """
            SELECT s.id, s.name, s.age, s.mobile_number, s.email, c.course_name
            FROM students s
            JOIN courses c ON s.course_id = c.course_id
            """;
        try(Connection con = DbConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ){

            boolean found = false;

            System.out.printf(
                    "%-5s %-20s %-5s %-15s %-25s %-20s%n",
                    "ID", "Name", "Age", "Mobile", "Email", "Course"
            );
            System.out.println("-------------------------------------------------------------------------------");

            while (rs.next()) {
                found = true;

                System.out.printf(
                        "%-5d %-20s %-5d %-15s %-25s %-20s%n",
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("mobile_number"),
                        rs.getString("email"),
                        rs.getString("course_name")
                );
            }

            if (!found) {
                System.out.println("❌ No students found.");
            }

        } catch (SQLException e ){
            System.out.printf("failed to list all students "+ e.getMessage());
        }

    }
    @Override
    public void updateOne() {

    }
    @Override
    public void delete() {

    }
    private int readIntSafe() {
        while (true) {
            try {

                return Integer.parseInt(scan.nextLine());

            } catch (Exception e) {

                System.out.print("Invalid number — try again: ");
            }
        }
    }

    private boolean isValidStudentId(int id) {
        String sql = "SELECT id from students where id = ?";

        try(Connection con = DbConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setInt(1 , id);
            ResultSet rs = ps.executeQuery();
            return rs.next(); // true if student exists
        } catch (SQLException e) {
            System.out.println("❌ Error validating student ID: " + e.getMessage());
            return false;
        }

    }
}