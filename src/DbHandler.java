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

        System.out.println("Enter the age of the new student : ");
        int age = readIntSafe();

        System.out.println("Enter the email of the new student : ");
        String email = scan.nextLine();

        System.out.println("Enter the course: of the new student : ");
        String course = scan.nextLine();

        System.out.println("\nStudent data captured — Next step is DB insert.");

        String sql = "INSERT INTO students (name, age, email, course) VALUES (?, ?, ?, )";

        try (
                Connection con = DbConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ){
            ps.setString(1, name);
            ps.setInt(2, age);
            ps.setString(3,email);
            ps.setString(4, course);
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