import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class CourseHandler implements CourseOperations {

    private final Scanner scan = new Scanner(System.in);

    @Override
    public void addCourse() {
        System.out.println("\n ====== REGISTER NEW COURSE ======");

        String name ;

        System.out.println("Enter the name of the new course: ");

        // Force valid course name
        while (true) {
            System.out.print("Enter the name of the new course: ");
            name = scan.nextLine().trim();

            if (!name.isEmpty()) {
                break;
            }
            System.out.println("❌ Course name cannot be empty. Please try again.");
        }
        String sql = "INSERT INTO courses (course_name) VALUES (?)";

        try (
                Connection con = DbConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ){

            // preparing the statement with the pre created sql string
            ps.setString(1, name);

            // executing the prepared statement
            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("✅ course added successfully!");
            } else {
                System.out.println("❌ Failed to add course.");
            }
        } catch (SQLException e) {

            if (e.getMessage().toLowerCase().contains("duplicate")) {
                System.out.println("❌ This course already exists.");
            } else {
                System.out.println("❌ Error adding course: " + e.getMessage());
            }
        }

    }

    // todo: isValidCourseId() here

    // todo: listCourses() formatting

    // todo: Course deletion protection (don’t delete course with students)

    @Override
    public void listCourses() {
        // we implement next
    }

    @Override
    public void deleteCourse() {
        // later
    }

    public boolean courseExists(int courseId) {
        return isValidCourseId(courseId);
    }

    private boolean isValidCourseId(int courseId) {
        String sql = "SELECT course_id FROM courses WHERE course_id = ?";
        //todo
        return false;
    }

}
