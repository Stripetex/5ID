/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smartserver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Marco
 */
public class CsvReader {
    private String path;
    Statement stmt;
   
    
    public CsvReader() throws ClassNotFoundException, SQLException{
        path = "./ml-latest/";
        Class.forName("org.relique.jdbc.csv.CsvDriver");
        Connection conn = DriverManager.getConnection("jdbc:relique:csv:" + path);
        stmt = conn.createStatement();
        
    }
    
    public ArrayList readFilms(String genre) throws SQLException{
        ArrayList movies = new ArrayList();
        String query = "select title from movies where genres like '%" + genre + "%'";
        ResultSet rs = stmt.executeQuery(query);
        while(rs.next()){
            movies.add(rs.getString("title"));      
        }
        
        return movies;
    }
}
