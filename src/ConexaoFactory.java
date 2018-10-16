/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BancoTest2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConexaoFactory {
    
    public static Connection getConexao(){
        String url =  "jdbc:postgresql://localhost:5432/agencia";
        String user = "postgres";
        String password = "123456";
        try{
        Class.forName("org.postgresql.Driver");    
        return DriverManager.getConnection(url, user, password);
        
        
        }catch (SQLException | ClassNotFoundException  e){
            e.printStackTrace();
        }
        return  null;
    }

    
public static void close(Connection connection){
    try{
        if(connection != null)
        connection.close();    
    } catch (SQLException e){
     e.printStackTrace(); 
    }
}  

public static void close(Connection connection, Statement stnt){
    close(connection);
     try{
        if(stnt != null)
        stnt.close();    
    } catch (SQLException e){
     e.printStackTrace(); 
    } 
}

public static void close(Connection connection, Statement stnt, ResultSet rs){
    close(connection, stnt);
     try{
        if(rs != null)
        rs.close();    
    } catch (SQLException e){
     e.printStackTrace(); 
    } 
  }
}

