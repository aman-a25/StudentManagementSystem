import java.sql.*;
import java.util.Scanner;

public class StudentHandler implements StudentOperations {

    private final Scanner scan = new Scanner(System.in);

    @Override
    public void register() {

        System.out.println("\n ====== REGISTER NEW STUDENT ======");

        System.out.println("Enter the name of the new student : ");
        String name = readStringSafe();

        System.out.println("Enter the age of the new student : ");
        int age = readIntSafe();

        System.out.println("Enter the mobile no.  of the new student : ");
        String moNo = readStringSafe();

        System.out.println("Enter the email of the new student : ");
        String email = readStringSafe();

        System.out.println("Enter the course: of the new student  ");
        new CourseHandler().listCourses();
        System.out.println("please enter course ID : ");

        int course = readIntSafe();

       while (!(new CourseHandler().courseExists(course))){
           System.out.println("the given course ID is not a valid course ID please enter a valid ID : ");
           course = readIntSafe();
       }



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
// todo : implement search by id and search by name feature
        System.out.println("\n==== SEARCH STUDENT ====");

        System.out.println("Enter student ID : ");
        int id = readIntSafe();

        searchById(id);
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
            System.out.println("failed to list all students "+ e.getMessage());
        }

    }

    @Override
    public void updateOne() {
        System.out.println("=========================== update student ===========================");

        // taking id to search then update
        System.out.println("Please enter the student ID :");
        int id = readIntSafe();

        if (!isValidStudentId(id)) {
            System.out.println("Invalid student ID.");
            return;
        }

        // searching for data and displaying to update it
        searchById(id);
        boolean firstRun = true;


        try (Connection con = DbConnection.getConnection()) {
            // updating
            while (true) {
                if (!firstRun) {
                    System.out.println("do you want to update again for the same student ID \n (y/n) : ");
                    String again = scan.nextLine().trim().toLowerCase();

                    if (!again.equals("y")) {
                        System.out.println("update finished.");
                        return;
                    }
                }
                firstRun = false;
                System.out.println("\nWhat do you want to update?");
                System.out.println("1. Name");
                System.out.println("2. Age");
                System.out.println("3. Mobile Number");
                System.out.println("4. Email");
                System.out.println("5. Course");
                System.out.println("6. Cancel");

                int choice = readIntSafe();

                String sql;
                String newValue;

                switch (choice) {

                    case 1 -> {
                        System.out.print("Enter new name: ");
                        newValue = readStringSafe();

                        sql = "UPDATE students SET name = ? WHERE id = ?";
                        try (PreparedStatement ps = con.prepareStatement(sql)) {
                            ps.setString(1, newValue);
                            ps.setInt(2, id);
                            ps.executeUpdate();
                        }
                        System.out.println("✅ Name updated successfully!");
                    }

                    case 2 -> {
                        System.out.print("Enter new age: ");
                        int age = readIntSafe();

                        sql = "UPDATE students SET age = ? WHERE id = ?";
                        try (PreparedStatement ps = con.prepareStatement(sql)) {
                            ps.setInt(1, age);
                            ps.setInt(2, id);
                            ps.executeUpdate();
                        }
                        System.out.println("✅ Age updated successfully!");
                    }

                    case 3 -> {
                        System.out.print("Enter new mobile number: ");
                        newValue = readStringSafe();

                        sql = "UPDATE students SET mobile_number = ? WHERE id = ?";
                        try (PreparedStatement ps = con.prepareStatement(sql)) {
                            ps.setString(1, newValue);
                            ps.setInt(2, id);
                            ps.executeUpdate();
                        }
                        System.out.println("✅ Mobile number updated successfully!");
                    }

                    case 4 -> {
                        System.out.print("Enter new email: ");
                        newValue = readStringSafe();

                        sql = "UPDATE students SET email = ? WHERE id = ?";
                        try (PreparedStatement ps = con.prepareStatement(sql)) {
                            ps.setString(1, newValue);
                            ps.setInt(2, id);
                            ps.executeUpdate();
                        }
                        System.out.println("✅ Email updated successfully!");
                    }

                    case 5 -> {
                        CourseHandler ch = new CourseHandler();
                        ch.listCourses();

                        System.out.print("Enter new course ID: ");
                        int courseId = readIntSafe();

                        if (!ch.courseExists(courseId)) {
                            System.out.println("❌ Invalid course ID.");
                            return;
                        }

                        sql = "UPDATE students SET course_id = ? WHERE id = ?";
                        try (PreparedStatement ps = con.prepareStatement(sql)) {
                            ps.setInt(1, courseId);
                            ps.setInt(2, id);
                            ps.executeUpdate();
                        }
                        System.out.println("✅ Course updated successfully!");
                    }

                    case 6 -> {
                        System.out.println("Update cancelled.");
                        return;
                    }

                    default -> System.out.println("Invalid choice.");
                }

            }
        } catch (SQLException e) {
                System.out.println("❌ Error updating student: " + e.getMessage());
        }

    }

    @Override
    public void delete() {

        System.out.println("=========================== delete a student ===========================");

        System.out.print("Enter student ID that you want to Delete: ");
        int id = readIntSafe();

        searchById(id);
        System.out.println("ARE YOU SURE THAT YOU WANT TO DELETE ALL DATA RELATED TO THIS STUDENT");
        System.out.println("====(PLEASE NOTE THE DELETION IS IRREVERSIBLE)====");

        System.out.print("please answer y for yes , n for no (y/n) : ");
        String ans = scan.nextLine().trim().toLowerCase();

        if(ans.equals("n")){

            System.out.println("n received");
            System.out.println("Exiting deletion process ");
            return;

        }
        if (ans.equals("y")){

            System.out.println("y received");
            System.out.println("Deleting the student data associated by given student ID : "+ id);

            String sql = "DELETE FROM students WHERE id = ?";

            try(Connection con = DbConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1 , id );

                int rows = ps.executeUpdate();

                if (rows > 0) {
                    System.out.println("✅ Student deleted successfully.");
                } else {
                    System.out.println("❌ Deletion failed.");
                }

            } catch (SQLException e){
                System.out.println("error occurred during the deletion process : " + e.getMessage() );
            }
        }
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

    private void searchById(int id){

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