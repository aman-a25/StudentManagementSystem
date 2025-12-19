import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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

    }
    @Override
    public void listAll() {

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
}