import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import br.com.caelum.stella.validation.CPFValidator;


public class Avaliador{
   private int codigo;
   private String cpf;
   private String nome;
   private String rg;
   private String nivel;
   private String trabalha;
   private String dataNascimento;
   private String sexo;

   
   public  Avaliador(String cpf, String nome, String rg, String nivel, String trabalha, String dataNascimento, String sexo){
      setCodigo(codigo);
      setCpf(cpf);
      setNome(nome);
      setRg(rg);
      setNivel(nivel);
      setTrabalha(trabalha);
      setDataNascimento(dataNascimento);
      setSexo(sexo);
   }
   public Avaliador(){}
   
   public Avaliador(int codigo){
      setCodigo(codigo);
   }
   
   // parte dos gets
   public int getCodigo(){
      return codigo; 
   }
   public String getCpf(){
      return cpf;
   }
   public String getNome(){
      return nome;
   }
   public String getRg(){
      return rg;
   }
   public String getNivel(){
      return nivel;
   }
   public String getTrabalha(){
      return trabalha;
   }
   public String getDataNascimento(){
      return dataNascimento; 
   }
   public String getSexo(){
      return sexo;
   }
   
   // parte dos sets
   public void setCodigo(int codigo){
      this.codigo = codigo;
   }
   public void setCpf(String cpf){
      CPFValidator cpfValidator = new CPFValidator();
      try{
         cpfValidator.assertValid(cpf);
         this.cpf = cpf;
      }catch(Exception x9){
         x9.printStackTrace();
         this.cpf = "Inválido!";
      }
   }
   public void setNome(String nome){
      this.nome = nome;
   }
   public void setRg(String rg){
      int tamanho = rg.length();
      if (tamanho == 11 || tamanho == 12){
         this.rg = rg;
      }
      else{
         this.rg = "Inválido";
      }      
   }
   public void setNivel(String nivel){
      this.nivel = nivel;
   }
   public void setTrabalha(String trabalha){
      this.trabalha = trabalha;
   }
   public void setDataNascimento(String dataNascimento){
      this.dataNascimento = dataNascimento;
   }
   public void setSexo(String sexo){
      this.sexo = sexo;
   }
   public String toString(){
      String saida = "Nome: " + nome + "\nRG: " + rg + "\nCPF: " + cpf + "\nSexo: " + sexo +
                  "\nData de nascimento: " + dataNascimento + "\nNivel acadêmico: " + nivel +
                  "\nLocal onde trabalha: " + trabalha;
      return saida;
   }
   public void inserir(Connection cnn){
      String sqlInsert = "INSERT INTO avaliador(cpf_avaliador, rg_avaliador, nome_avaliador," +
                            "sexo, data_nasct, nivel_academico, local_trab) VALUES (?,?,?,?,?,?,?)";
      
      try(PreparedStatement stm = cnn.prepareStatement(sqlInsert);){
         stm.setString(1, getCpf());
         stm.setString(2, getRg());
         stm.setString(3, getNome());
         stm.setString(4, getSexo());
         stm.setString(5, getDataNascimento());
         stm.setString(6, getNivel());
         stm.setString(7, getTrabalha());
         stm.execute();
         String query = "SELECT LAST_INSERT_ID()";
         
         try(PreparedStatement stm2 = cnn.prepareStatement(query);
               ResultSet rs = stm2.executeQuery();){
            if(rs.next()){
               setCodigo(rs.getInt(1));
            }else{
               throw new SQLException("Falha ao gerar o código");
            }      
         }catch(Exception x){
            x.printStackTrace();
            try{
               cnn.rollback();
            }catch(Exception x9){
               x9.printStackTrace();
            }
         }
         
      }catch(Exception x){
         x.printStackTrace();
         try{
            cnn.rollback();
         }catch(SQLException x9){
            x9.printStackTrace();
         }
      }
   }
   public void excluir(Connection conn) {
      String sqlDelete = "DELETE FROM avaliador WHERE cod_avaliador = ?";
      try (PreparedStatement stm = conn.prepareStatement(sqlDelete);) {
         stm.setInt(1, getCodigo());
      
         stm.execute();
      } 
      catch (Exception x) {
         x.printStackTrace();
         try {
            conn.rollback();
         } 
         catch (SQLException x9) {
            x9.printStackTrace();
         }
      } 
   }
	
   public void atualizar(Connection conn) {
      String sqlUpdate = "UPDATE avaliador SET nome_avaliador = ?, sexo =?, nivel_academico=?, local_trab=? WHERE cod_avaliador = ?";
    
      try (PreparedStatement stm = conn.prepareStatement(sqlUpdate);){
         stm.setString(1, getNome());
         stm.setString(2, getSexo());
         stm.setString(3, getNivel());
         stm.setString(4, getTrabalha());
         stm.setInt(5, getCodigo());
         stm.execute();
      } 
      catch (Exception x) {
         x.printStackTrace();
         try {
            conn.rollback();
         } 
         catch (SQLException x9) {
            x9.printStackTrace();
         }
      } 
   }

   public void seleciona(Connection conn) {
      String sqlSelect = "SELECT * FROM avaliador WHERE cod_avaliador = ?";
   
      try (PreparedStatement stm = conn.prepareStatement(sqlSelect);){
         stm.setInt(1, getCodigo());
         try (ResultSet rs = stm.executeQuery();){
            if (rs.next()) {
               this.setCpf(rs.getString(2));
               this.setRg(rs.getString(3));
               this.setNome(rs.getString(4));
               this.setSexo(rs.getString(5));
               this.setDataNascimento(rs.getString(6));
               this.setNivel(rs.getString(7));
               this.setTrabalha(rs.getString(8));
            }
         } 
         catch (Exception x) {
            x.printStackTrace();
         }
      }
      catch (Exception x9) {
         x9.printStackTrace();
         try{
            conn.rollback();
         }catch(SQLException x1){
            x1.printStackTrace();
         }
      }
   }
}