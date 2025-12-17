import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.io.FileInputStream;

public class DbConnection {

        private static  String   dbUrl ;
        private static  String   dbUsername ;
        private static  String   dbPassword ;

        static {
            try {
                // Optional for modern JDBC, but safe to keep basically it lodes JDBC driver
                Class.forName("com.mysql.cj.jdbc.Driver");

                // loading properties
                Properties prop = new Properties();
                FileInputStream fis = new FileInputStream("db.properties");
                prop.load(fis);

                dbUrl = prop.getProperty("db.dbUrl");
                dbUsername = prop.getProperty("db.dbUsername");
                dbPassword = prop.getProperty("db.dbPassword");
            } catch (ClassNotFoundException e) {
                System.out.println("MySQL JDBC Driver not found. Add the connector JAR to your project.");
                e.printStackTrace();
            } catch (IOException e) {
                System.out.println("please create a db.properties file beside the src and place your username and password for DB there ");
                e.printStackTrace();
            }
        }
        public static Connection getConnection() throws SQLException{
            return DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
        }
}
