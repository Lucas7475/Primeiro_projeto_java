import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import br.com.caelum.stella.validation.CPFValidator;

public class Solicitante
{
   private String cpf,
                    nome,
                    rg,
                    nivel,
                    dataNascimento,
                    localGraduacao,
                    sexo;
   
   //construtores
   public Solicitante (String cpf, String nome, String rg, String nivel, String localGraduacao, String dataNascimento, String sexo)
   {
      setCpf(cpf);
      setNome(nome);
      setRg(rg);
      setNivel(nivel);
      setLocalGraduacao(localGraduacao);
      setSexo(sexo);
      setDataNascimento(dataNascimento);
   } 
   public Solicitante(String cpf, Connection cnn){
      setCpf(cpf);
      seleciona(cnn);
   }
   public Solicitante(String cpf){
      setCpf(cpf);
   }
   public Solicitante(){}                   
   
   //métodos de Acesso = getters
   public String getCpf()
   {
      return cpf;
   }
   public String getNome()
   {
      return nome;
   }
   public String getRg()
   {
      return rg;
   }
   public String getNivel()
   {
      return nivel;
   }
   public String getLocalGraduacao()
   {
      return localGraduacao;
   }
   public String getSexo()
   {
      return sexo;
   }
   public String getDataNascimento()
   {
      return dataNascimento;
   }
   
   //métodos Modificadores
   public void setCpf(String cpf)
   {
      CPFValidator cpfValidator = new CPFValidator();
      try{
         cpfValidator.assertValid(cpf);
         this.cpf = cpf;
      }catch(Exception x9){
         x9.printStackTrace();
         this.cpf = "Inválido!";
      } 
   }
   public void setNome(String nome)
   {
      this.nome = nome;
   }
   public void setRg(String rg)
   {
      int tamanho = rg.length();
      if (tamanho == 11 || tamanho == 12)
      {
         this.rg = rg;
      }
      else 
      {
         this.rg = "inválido";
      }
   }
   public void setNivel(String nivel)
   {
      this.nivel = nivel;
   }
   public void setLocalGraduacao(String localGraduacao)
   {
      this.localGraduacao = localGraduacao;
   }
   public void setSexo(String sexo)
   {
      this.sexo = sexo;
   }
   public void setDataNascimento(String dataNascimento)
   {
      this.dataNascimento = dataNascimento;
   }
   
   //Dados 
   public String toString()
   {
      String saida = "\nNome: " + nome + "\nRG: " + rg + "\nCPF: " + cpf + "\nSexo: " + sexo +
                        "\nData de nascimento: " + dataNascimento + "\nNivel acadêmico: " + nivel +
                        "\nLocal de graduação: " + localGraduacao;
      return saida;
   }
   public void inserir(Connection cnn){
      String sqlInsert = "INSERT INTO solicitante( cpf_solicitante, rg_solicitante, nome_solicitante, sexo, data_nasc," + 
                         " nivel_academico, local_graduacao) VALUES (?,?,?,?,?,?,?)";
      
      try(PreparedStatement stm = cnn.prepareStatement(sqlInsert);){
         stm.setString(1, getCpf());
         stm.setString(2, getRg());
         stm.setString(3, getNome());
         stm.setString(4, getSexo());
         stm.setString(5, getDataNascimento());
         stm.setString(6, getNivel());
         stm.setString(7, getLocalGraduacao());
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
   public void excluir(Connection conn) {
      String sqlDelete = "DELETE FROM solicitante WHERE cpf_solicitante = ?";
      try (PreparedStatement stm = conn.prepareStatement(sqlDelete);) {
         stm.setString(1, getCpf());
      
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
      String sqlUpdate = "UPDATE solicitante SET nome_solicitante = ?, sexo =?, nivel_academico=?, local_graduacao = ? WHERE cpf_solicitante = ?";
    
      try (PreparedStatement stm = conn.prepareStatement(sqlUpdate);){
         stm.setString(1, getNome());
         stm.setString(2, getSexo());
         stm.setString(3, getNivel());
         stm.setString(4, getLocalGraduacao());
         stm.setString(5, getCpf());
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

   public void seleciona (Connection conn) {
      String sqlSelect = "SELECT * FROM solicitante WHERE cpf_solicitante = ?";
   
      try (PreparedStatement stm = conn.prepareStatement(sqlSelect);){
         stm.setString(1, getCpf());
         try (ResultSet rs = stm.executeQuery();){
            if (rs.next()) {
               this.setRg(rs.getString(2));
               this.setNome(rs.getString(3));
               this.setSexo(rs.getString(4));
               this.setDataNascimento(rs.getString(5));
               this.setNivel(rs.getString(6));
               this.setLocalGraduacao(rs.getString(7));
            }
         }catch (Exception x) {
            x.printStackTrace();
         }
      }catch (Exception x9) {
         x9.printStackTrace();
         try{
            conn.rollback();
         }catch(SQLException x1){
            x1.printStackTrace();
         }
      }
   }
}