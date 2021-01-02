package Game_lat_hinh;

import java.sql.Connection;
import java.sql.DriverManager;
public class ConnectionSQL {
	public static Connection getConnection() {
        Connection conn = null;
        try {
    	    Class.forName("org.postgresql.Driver");
    	   } catch (ClassNotFoundException e1) {
    	    e1.printStackTrace();
    	   }
        try {
            conn = DriverManager.getConnection("jdbc:postgresql://" + "localhost" + ":5432/" + "PJ", "postgres","postgres");
            System.out.println("connect successfully!");
        } catch (Exception ex) {
            System.out.println("connect failure!");
            ex.printStackTrace();
        }
        return conn;
    }
}
