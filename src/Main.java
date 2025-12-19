import java.util.InputMismatchException;
import java.util.Scanner;
import java.sql.Connection;

public class Main {
// one scanner for all iteration of input
    private static final Scanner Scan = new Scanner(System.in);

    public static void main(String[] args){
        System.out.println("---------------------------------------------------------------------------------");
        System.out.println("\n STUDENT MANAGEMENT SYSTEM \n");
        System.out.println("---------------------------------------------------------------------------------");

        // Step 1 : try database connection before anything else
        System.out.println("checking database connection..... ");

        try (Connection con = DbConnection.getConnection()) {
            System.out.println("✅ Connected successfully!\n");

        } catch (Exception e) {
            System.out.println("❌ Connection failed!");
            System.out.println("Reason : " + e.getMessage());
            System.out.println("\nprogram will now exit because we failed to connect to DB");
            return; // ❗ Stop program
        }

// 🔥 If connection was successful -> continue program


        System.out.println("please choose one option from the menu BELOW");


        // menu
        while (true) {

            System.out.println("\n ----------------------------------------------------- \n");
            System.out.println("please choose which menu you want to choose ");
            System.out.println("1. Student Management");
            System.out.println("2. Course Management");
            System.out.println("3. Exit ");

            System.out.println("\n ----------------------------------------------------- \n");


            // menu selection

            // taking safe input(1)
            int input1 = readInput(1, 3);

            switch (input1) {
                case 1:
                    studentMenu();
                    break;

                case 2:
                    courseMenu();
                    break;

                case 3:
                    System.out.println("Exiting program...");
                    System.exit(0);


            }
        }
    }

    // ----------------- studentMenu-------------------
    private static void studentMenu(){

        System.out.println("1. Register a new student");
        System.out.println("2. Search a student ");
        System.out.println("3. List all students");
        System.out.println("4. Update a existing record of a student");
        System.out.println("5. Delete record of a student ");
        System.out.println("6. back to main menu ");
        System.out.println("\n ----------------------------------------------------- \n");

        System.out.println("please tell us which operation you want to perform ");
        System.out.println(" ");

        // one object to be used in all iteration of while block (student menu )
        StudentOperations StuOps = new Stud;

        while (true){
            // reading input in each iteration
            int input2 = readInput(1, 6);

            switch (input2) {

                case 1:
                    StuOps.register();
                    break;

                case 2:
                    StuOps.search();
                    break;

                case 3:
                    StuOps.listAll();
                    break;

                case 4:
                    StuOps.updateOne();
                    break;

                case 5:
                    StuOps.delete();
                    break;

                case 6:
                    return; // back to main menu

                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    // ---------------- COURSE MENU ----------------
    private static void courseMenu() {

        CourseOperations courseOps = new CourseHandler();

        while (true) {
            System.out.println("\n--- Course Management ---");
            System.out.println("1. Add Course");
            System.out.println("2. List Courses");
            System.out.println("3. Delete Course");
            System.out.println("4. Back to Main Menu");
            System.out.print("Choose an option: ");

            int choice = readInput(1,4);

            switch (choice) {
                case 1 -> courseOps.addCourse();
                case 2 -> courseOps.listCourses();
                case 3 -> courseOps.deleteCourse();
                case 4 -> {
                    return; // back to main menu
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static int readInput(int min , int max ){
        System.out.print("Enter your option: ");
        while (true){
            try{
                int temp = Scan.nextInt();
                Scan.nextLine(); // after nextInt()
                if(temp >= min && temp <= max){
                    return temp;
                }
                else {
                    System.out.println("entered number is required to be within the range  " + min + " to " + max );
                }
            }
            catch (InputMismatchException e){
                System.out.println("please enter a digit ranging " + min + " to " + max );
                Scan.nextLine();
            }
        }
    }
}
