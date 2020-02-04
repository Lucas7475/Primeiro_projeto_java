import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AreaEspecifica{
   
   private String ENome;
   private String ECodArea;
   
   public AreaEspecifica(String ECodArea){
      setECodArea(ECodArea);
   }
   public AreaEspecifica(String ENome, String ECodArea){
      setENome(ENome);
      setECodArea(ECodArea);      
   }
   public AreaEspecifica(String ECodArea, Connection cnn){
      setECodArea(ECodArea);
      setENome("");
      String sqlSelect = "SELECT * FROM area_pesquisa WHERE cod_especifica = ?";
      try(PreparedStatement stm = cnn.prepareStatement(sqlSelect);){
         stm.setString(1, getECodArea());
         try(ResultSet rs = stm.executeQuery();){
            if(rs.next()){
               this.setENome(rs.getString(2));
            }
         }catch(Exception x){
            x.printStackTrace();            
         }
      }catch(Exception x9){
         x9.printStackTrace();
         try{
            cnn.rollback();
         }catch(Exception x1){
            x1.printStackTrace();
         }
      }      
   }
   public AreaEspecifica(){
      ENome = "";
      ECodArea = "";
   }
   public String getENome(){
      return ENome;
   }
   public String getECodArea(){
      return ECodArea;
   }
   public void setENome(String ENome){
      this.ENome = ENome;
   }
   public void setECodArea(String ECodArea){
      this.ECodArea = ECodArea;
   }
   public String toString(){
      String saida = "\n¡rea de conhecimento: " + ECodArea +" - " + ENome;      
      return saida;
   }
}