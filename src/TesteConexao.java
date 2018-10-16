/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BancoTest2;


import java.util.List;





public class TesteConexao {
    public static void main(String[] args){
     
    // List<Comprador> listaComprador =  selecionarTudo();
    // System.out.println(listaComprador);
    //  CompradorDB.selectMetaData();
     //   CompradorDB.checkDriverStatus();
    // CompradorDB.testTypeScroll();
    CompradorDB.updateNomesToLowerCase();
    }      
    
    public static void inserir(){
    Comprador comprador = new Comprador("Miguel", "4225889574");
    CompradorDB.save(comprador);
 }
   public static void deletar(){
      Comprador comprador = new Comprador();
      comprador.setId(1);
      CompradorDB.delete(comprador);
   }
   
   public static void atualizar(){
      Comprador comprador = new Comprador(2, "Pedro", "46592316899");
      CompradorDB.update(comprador);
   }
   
   public static  List<Comprador> selecionarTudo(){
        return CompradorDB.selectAll();
   }
   
}
