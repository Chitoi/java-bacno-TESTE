
package BancoTest2;


import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.DriverManager;





public class CompradorDB {
    public static void save(Comprador comprador){
        String sql = "INSERT INTO  comprador (nome, cpf) VALUES ('"+comprador.getNome()+"', '"+comprador.getCpf()+"')";
        Connection conn = ConexaoFactory.getConexao();
        try{
        Statement stnt = conn.createStatement();
        System.out.println(stnt.executeUpdate(sql));
        ConexaoFactory.close(conn, stnt);
        System.out.println("Registro inserido com sucesso!");
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
    
    public static void delete(Comprador comprador){
        if(comprador == null || comprador.getId() == null){
            System.out.println("Não foi possivel excluir o registro");
            return;
         }    
        String sql = "DELETE FROM comprador WHERE id = '"+comprador.getId()+"'";
        Connection conn = ConexaoFactory.getConexao();
        try{
            Statement stnt = conn.createStatement();
            stnt.executeUpdate(sql);
            ConexaoFactory.close(conn, stnt);
            System.out.println("Registro excluido com sucesso!");
            } catch (SQLException e){
                e.printStackTrace();
        }
    }
    
     public static void update(Comprador comprador){
        if(comprador == null || comprador.getId() == null){
            System.out.println("Não foi possivel atualizar o registro");
            return;
         }    
        String sql = "UPDATE comprador SET nome = '"+comprador.getNome()+"', cpf = '"+comprador.getCpf()+"'WHERE id = '"+comprador.getId()+"'";
        Connection conn = ConexaoFactory.getConexao();
        try{
            Statement stnt = conn.createStatement();
            stnt.executeUpdate(sql);
            ConexaoFactory.close(conn, stnt);
            System.out.println("Registro atualizado com sucesso!");
            } catch (SQLException e){
                e.printStackTrace();
        }
    }
     public static List<Comprador> selectAll(){
        
        String sql = "SELECT id, nome, cpf FROM comprador";
        Connection conn = ConexaoFactory.getConexao();
        List<Comprador> compradorList = new ArrayList<>();
        try{
            Statement stnt = conn.createStatement();
            ResultSet  rs = stnt.executeQuery(sql);
            while(rs.next()){
               compradorList.add(new Comprador(rs.getInt("id"), rs.getString("nome"), rs.getString("cpf")));
            }
            ConexaoFactory.close(conn, stnt, rs);
            return compradorList;
            
            } catch (SQLException e){
                e.printStackTrace();
        }
        return null;
     }
     
     public  static void selectMetaData(){
         String sql  = "SELECT * FROM comprador";
         Connection conn = ConexaoFactory.getConexao();
         try{
            Statement stnt = conn.createStatement();
            ResultSet  rs = stnt.executeQuery(sql);
            ResultSetMetaData rsmd = rs.getMetaData();
            rs.next();
            int qtdColunas = rsmd.getColumnCount();
            System.out.println("Quantidade coluna:" +qtdColunas);
            for(int i=1; i <= qtdColunas; i++){
                System.out.println("tabela: " +rsmd.getTableName(i));
                System.out.println("Nome coluna: " +rsmd.getColumnName(i));
                System.out.println("Tamanho coluna: " +rsmd.getColumnDisplaySize(i));  
            }
            ConexaoFactory.close(conn, stnt, rs);
            } catch (SQLException e){
                e.printStackTrace();
        }
     }
     
    public  static void checkDriverStatus(){
         Connection conn = ConexaoFactory.getConexao();
         try{
             DatabaseMetaData dbmd  = conn.getMetaData();
             if(dbmd.supportsResultSetType(ResultSet.TYPE_FORWARD_ONLY)){
                 System.out.println("Suporta TYPE FORWARD ONLY: ");
                 if(dbmd.supportsResultSetConcurrency(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE)){
                     System.out.println("e tambem suporta CONCUR UPDATATABLE");
                 }
             }
             if(dbmd.supportsResultSetType(ResultSet.TYPE_SCROLL_INSENSITIVE)){
                 System.out.println("Suporta TYPE_SCROLL_INSENSITIVE: ");
                 if(dbmd.supportsResultSetConcurrency(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)){
                     System.out.println("e tambem suporta CONCUR UPDATATABLE");
                 }
             }
             if(dbmd.supportsResultSetType(ResultSet.TYPE_SCROLL_SENSITIVE)){
                 System.out.println("Suporta TYPE_SCROLL_SENSITIVE: ");
                 if(dbmd.supportsResultSetConcurrency(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE)){
                     System.out.println("e tambem suporta CONCUR UPDATATABLE");
                 }
             }
             ConexaoFactory.close(conn);
         }catch (SQLException e){
             e.printStackTrace();
         }
     }
     
       public static void testTypeScroll(){
        
        String sql = "SELECT id, nome, cpf FROM comprador";
        Connection conn = ConexaoFactory.getConexao();
        
        try{
            Statement stnt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet  rs = stnt.executeQuery(sql);
            if(rs.last()){
                System.out.println("Ultima lina "+new Comprador(rs.getInt("id"), rs.getString("nome"), rs.getString("cpf")));  
                System.out.println("Ultima linha "  +rs.getRow());
            }
            System.out.println("retornou para a primeira linha: "+rs.first());
            System.out.println("primeira linha: "+rs.getRow());
            rs.absolute(4);
            System.out.println("Linha 3 "+new Comprador(rs.getInt("id"), rs.getString("nome"), rs.getString("cpf")));  
            rs.relative(-2);
            System.out.println(" \t \n "+new Comprador(rs.getInt("id"), rs.getString("nome"), rs.getString("cpf")));  

            ConexaoFactory.close(conn, stnt, rs);
            } catch (SQLException e){
                e.printStackTrace();
        }
  
     }
       
    public static void updateNomesToLowerCase(){
        String sql = "SELECT id, nome, cpf FROM comprador";
        Connection conn = ConexaoFactory.getConexao();
        
        try{
            Statement stnt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet  rs = stnt.executeQuery(sql);
            while(rs.next()){
                rs.updateString("nome", rs.getString("nome").toLowerCase());
                rs.updateRow();
            }
            rs.beforeFirst();
            while(rs.next()){
               System.out.println(rs.getString("nome"));    
            }
            ConexaoFactory.close(conn, stnt, rs);
            } catch (SQLException e){
                e.printStackTrace();
        }
       
    }    
     
    
}





