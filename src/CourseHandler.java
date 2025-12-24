import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class CourseHandler implements CourseOperations {

    private final Scanner scan = new Scanner(System.in);

    @Override
    public void addCourse() {
        System.out.println("=========================== REGISTER NEW COURSE ===========================");

        // taking values
        String name ;
        do {
            System.out.println("Enter the name of the new course: ");
            name = readStringSafe();
        }while ( !isValidCourseName(name));

        System.out.println("Enter the description of the new course: ");
        String desc = readStringSafe();

        String sql = "INSERT INTO courses (course_name , course_desc) VALUES (? , ?)";

        try (
                Connection con = DbConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ){

            // preparing the statement with the pre created sql string
            ps.setString(1, name);
            ps.setString(2, desc);

            // executing the prepared statement
            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("✅ course added successfully!");
            } else {
                System.out.println("❌ Failed to add course.");
            }
        } catch (SQLException e) {
                System.out.println("❌ Error adding course: " + e.getMessage());

        }

    }
    // todo: Course deletion protection (don’t delete course with students)

    @Override
    public void listCourses() {
        System.out.println("=========================== list of all courses ===========================");
        String sql = """
            SELECT * FROM courses
            """;

        try(Connection con = DbConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ){

            boolean found = false;

            System.out.printf(
                    "%-5s %-20s %-100s%n",
                    "Course_ID", "Course_Name", "Course_Description"
            );
            System.out.println("-------------------------------------------------------------------------------");

            while (rs.next()) {
                found = true;

                System.out.printf(
                        "%-5s %-20s %-100s%n",
                        rs.getInt("course_id"),
                        rs.getString("course_name"),
                        rs.getString("course_desc")
                );
            }

            if (!found) {
                System.out.println("❌ No courses were found.");
            }

        } catch (SQLException e ){
            System.out.println("failed to list all courses "+ e.getMessage());
        }
    }

    @Override
    public void deleteCourse() {
        System.out.println("=========================== delete a course ===========================");

        System.out.println("please enter the course_ID of the course you want to delete ");
        int id = readIntSafe();

        if(!isValidCourseId(id)){
            System.out.println("the given course id is invalid");
            return;
        }

        String validation ;

        boolean validFlag = false;
        do {
            System.out.println("are you sure you want to delete y/n ");
            validation = readStringSafe();
            if(validation.trim().charAt(0) == 'y' ||validation.trim().charAt(0) == 'n'  ){
                validFlag = true ;
            }else {
                System.out.println("the given input is invalid please try again ");
            }
        } while(!validFlag);

        if(validation.trim().charAt(0) == 'y' ) {

            String sql = """
                    DELETE FROM courses
                    WHERE course_id = ?;
                    """;

            try (Connection con = DbConnection.getConnection();
                 PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, id);

                int row = ps.executeUpdate();

                if (row > 0) {
                    System.out.println("data deleted successfully");
                } else {
                    System.out.println("query ran successfully but no data deleted");
                }

            } catch (SQLException e) {
                System.out.println("error while running course deletion query : " + e.getMessage());

            }

        } else if (validation.trim().charAt(0) == 'n') {
            System.out.println("exiting deletion process");

        }

    }

    public boolean courseExists(int courseId) {
        return isValidCourseId(courseId);
    }


    // helper methods

    private String readStringSafe(){

        String str = scan.nextLine();

        while (str.trim().isEmpty()) {
            System.out.println("Given string is empty, please try again:");
            str = scan.nextLine();
        }
        return str;
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

    private boolean isValidCourseId(int courseId) {
        String sql = "SELECT course_id FROM courses WHERE course_id = ?";
        try(Connection con = DbConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
            ){
            ps.setInt(1,courseId);
            ResultSet rs = ps.executeQuery();

            return rs.next();

        } catch (SQLException e) {
            System.out.println("error while checking the given course id is valid or not : " + e.getMessage());
        }
        return false;
    }

    private boolean isValidCourseName(String courseName) {


        String sql = """
                select * from courses
                where course_name = ? ;
                """;
        try (
                Connection con = DbConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            // preparing the statement with the pre created sql string
            ps.setString(1, courseName);

            // executing the prepared statement
            ResultSet rs = ps.executeQuery();

            return rs.next();

        } catch (SQLException e) {

            System.out.println(" error while validating the name : "+ e.getMessage());
        }
        return false; // safe fallback
    }


}
