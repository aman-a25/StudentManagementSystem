import java.util.InputMismatchException;
import java.util.Scanner;
import java.sql.Connection;

public class Main {
    static  void main() {
        System.out.println("---------------------------------------------------------------------------------");
        System.out.println("\n STUDENT MANAGEMENT SYSTEM \n");
        System.out.println("---------------------------------------------------------------------------------");

        // Step 1 : try database connection before anything else
        System.out.println("checking database connection..... ");

        try (Connection con = DbConnection.getConnection()){
            System.out.println("✅ Connected successfully!\n");

        } catch (Exception e) {
            System.out.println("❌ Connection failed!");
            System.out.println("Reason : " + e.getMessage());
            System.out.println("\nprogram will now exit because we failed to connect to DB");
            return; // ❗ Stop program
        }

// 🔥 If connection was successful -> continue program

        System.out.println("please tell us which operation you want to perform ");
        System.out.println(" ");
        System.out.println("please choose one option from the menu BELOW");
        System.out.println("\n ----------------------------------------------------- \n");

        // menu
        System.out.println("1. Register a new student");
        System.out.println("2. Search a student ");
        System.out.println("3. List all students");
        System.out.println("4. Update a existing record of a student");
        System.out.println("5. Delete record of a student ");
        System.out.println("\n ----------------------------------------------------- \n");


        // menu selection
        Scanner Scan = new Scanner(System.in);
        System.out.print("Enter your option: ");

        int input = readInput(Scan , 1 , 5 );

        // opening specific function for selected menu option
        StudentOperations DB = new DbHandler();


        switch (input){

            case 1 :
                DB.register();
                break;

            case 2 :
                DB.search();
                break;

            case 3 :
                DB.listAll();
                break;

            case 4 :
                DB.updateOne();
                break;

            case 5 :
                DB.delete();
                break;

            default :
                System.out.println("please enter a valid input ");
                System.out.println("the number you will enter should be within the range of 1 - 5 ");

        }
        Scan.close();
    }

    private static int readInput(Scanner Scan, int min , int max ){

        while (true){
            try{
                int temp = Scan.nextInt();
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
