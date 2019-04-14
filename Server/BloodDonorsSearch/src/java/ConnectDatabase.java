
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author AnveshanReddy
 */
public class ConnectDatabase {
    protected static Connection connector() throws SQLException {
        DriverManager.registerDriver(new com.mysql.jdbc.Driver());
        String host = "jdbc:mysql://127.0.0.1:3306/BloodDonorsList";
        String user = "root";
        String password = "testpaswd";
        Connection connection = DriverManager.getConnection(host, user, password);
        return connection;
    }
}
