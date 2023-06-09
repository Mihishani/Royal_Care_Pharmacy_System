package lk.ijse.royal_care_pharmacy.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private Connection connection;
    private static DBConnection dBConnection;

    private DBConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://localhost/royal_care_pharmacy", "root", "1234");

    }
    public static DBConnection getInstance() throws ClassNotFoundException, SQLException {
        if (dBConnection == null) {
            dBConnection = new DBConnection();
        }
        return dBConnection;
    }

    public static DBConnection getDbConnection()  {
        try {
            return dBConnection==null?dBConnection=new DBConnection():dBConnection;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dBConnection;
    }

    public Connection getConnection() {

        return connection;
    }
}
