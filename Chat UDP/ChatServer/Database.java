package chatserver;

import java.sql.*;
import java.util.concurrent.*;

public class Database {
    
    private Connection connection;
    private Statement statement;
    
    private Semaphore semaphore;
    
    public Database() {

        connection = null;
        semaphore = new Semaphore(1);

        try {

            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:bin/data-chat.db");
            statement = connection.createStatement();

        } catch (ClassNotFoundException e) {

            e.printStackTrace();
            System.out.println("Cannot load driver");
            System.exit(1);

        } catch (Exception e) {

            e.printStackTrace();
            System.exit(1);
        }

        System.out.println("Database successfully opened");
    }
    
    public boolean register(String name, String password) {
        
        boolean signed = false;
        
        try {
            
            semaphore.acquire();
            
            String query = "INSERT INTO User (Name, Password) VALUES (\"" + name + "\",\"" + password + "\");";
            statement.executeQuery(query);
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            semaphore.release();
            signed = true;
        }
        
        return signed;
        
    }
    
    public boolean log(String name, String password) {
        
        boolean logged = false;
        
        try {
            
            semaphore.acquire();
            
            String query = "SELECT * FROM (User INNER JOIN Privilege ON User.IDPrivilege=Privilege.IDPrivilege) WHERE User.Name=\"" + name + "\" AND User.Password=\"" + password + "\";";
            ResultSet rs = statement.executeQuery(query);
            
            logged = rs.next();
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            semaphore.release();
        }
        
        return logged;
    }
}
