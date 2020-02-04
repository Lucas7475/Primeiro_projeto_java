import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class AreaPesquisa extends AreaEspecifica{
   
   private String nome;
   private String codPesquisa;   
      
   public AreaPesquisa(String nome, String codPesquisa, String ENome, String ECodArea){
      super(ENome, ECodArea);
      setNome(nome);
      setCodPesquisa(codPesquisa);
      
   }
   public AreaPesquisa(String ECodArea, Connection cnn){
      super(ECodArea, cnn);
      seleciona(cnn);  
   }
   public AreaPesquisa(String ECodArea){
      super(ECodArea);
   }
   public AreaPesquisa(){}
   
   public String getNome(){
      return nome;
   }
   public String getCodPesquisa(){
      return codPesquisa;
   }
   public void setNome(String nome){
      this.nome = nome;
   }
   public void setCodPesquisa(String codPesquisa){
      this.codPesquisa = codPesquisa;
   }
   public String toString(){
      String saida = "Área de pesquisa: " + codPesquisa + " - " + nome +
                     super.toString();
      return saida;
   }
   public void insert(Connection cnn){
      String sqlInsert = "INSERT INTO area_pesquisa(cop_especifica, nome_especifica, cod_area, nome_area) VALUES (?,?,?,?)";
      
      try(PreparedStatement stm = cnn.prepareStatement(sqlInsert);){
         stm.setString(1, super.getECodArea());
         stm.setString(2, super.getENome());
         stm.setString(3, getCodPesquisa());
         stm.setString(4, getNome());
         stm.execute();
         
      }catch(Exception x){
         x.printStackTrace();
         try{
            cnn.rollback();
         }catch(SQLException x9){
            x9.printStackTrace();
         }
      }
   }   
   public void deletar(Connection cnn){
      String sqlDelete = "DELETE FROM area_pesquisa WHERE cod_especifica = ? ";
      
      try(PreparedStatement stm = cnn.prepareStatement(sqlDelete);){
         stm.setString(1, super.getECodArea());
         stm.execute();
         
      }catch(Exception x){
         x.printStackTrace();
         try{
            cnn.rollback();
         }catch(SQLException x9){
            x9.printStackTrace();
         }
      }
   }
   public void atualizar(Connection cnn){
      String sqlUpdate = "uPDATE area_pesquisa SET nome_area = ? WHERE cod_area = ?";
      
      try(PreparedStatement stm = cnn.prepareStatement(sqlUpdate);){
         stm.setString(1, getCodPesquisa());
         stm.execute();
         
      }catch(Exception x){
         x.printStackTrace();
         try{
            cnn.rollback();
         }catch(SQLException x9){
            x9.printStackTrace();
         }
      }
   }
   public void seleciona (Connection cnn){
      String sqlSelect = "SELECT * FROM area_pesquisa WHERE cod_especifica = ?";
      
      try(PreparedStatement stm = cnn.prepareStatement(sqlSelect);){
         stm.setString(1, super.getECodArea());
         
         try(ResultSet rs = stm.executeQuery();){
            if(rs.next()){
               super.setENome(rs.getString(2));
               this.setCodPesquisa(rs.getString(3));
               this.setNome(rs.getString(4));
            }           
         }catch(Exception x){
            x.printStackTrace();
         }
      }catch(Exception x9){
         x9.printStackTrace();
         try{
            cnn.rollback();
         }catch(SQLException x1){
            x1.printStackTrace();
         }
      }
   }
}