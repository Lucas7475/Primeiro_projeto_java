import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class Projeto{
   
   private int codInterno;
   private String localProjeto;
   private String titulo;
   private int duracao;
   private double orcamento;
   private Solicitante solicitante;
   private AreaPesquisa pesquisa;
   
   public Projeto(){};
   
   public Projeto(String localProjeto, String titulo,int duracao,
                  double orcamento, Solicitante solicitante, AreaPesquisa pesquisa){
      setCodInterno(codInterno);
      setLocalProjeto(localProjeto);
      setTitulo(titulo);
      setDuracao(duracao);
      setOrcamento(orcamento);
      setSolicitante(solicitante);
      setPesquisa(pesquisa);                      
   }
   public Projeto(int codInterno){
      setCodInterno(codInterno);
   }
   public int getCodInterno(){
      return codInterno;
   }
   public String getLocalProjeto(){
      return localProjeto;
   } 
   public String getTitulo(){
      return titulo;
   }
   public int getDuracao(){
      return duracao;
   }
   public double getOrcamento(){
      return orcamento; 
   }
   public Solicitante getSolicitante(){
      return solicitante;
   }
   public AreaPesquisa getPesquisa(){
      return pesquisa;
   }
   
   public void setCodInterno(int codInterno){
      this.codInterno = codInterno;
   }
   public void setLocalProjeto(String localProjeto){
      this.localProjeto = localProjeto;
   } 
   public void setTitulo(String titulo){
      this.titulo = titulo;
   }
   public void setDuracao(int duracao){
      this.duracao = duracao;
   }
   public void setOrcamento(double orcamento){
      this.orcamento = orcamento; 
   }
   public void setSolicitante(Solicitante solicitante){
      this.solicitante = solicitante;
   }
   public void setPesquisa(AreaPesquisa pesquisa){
      this.pesquisa = pesquisa;
   }
   public String toString(){
      String o = String.format("R$%.2f", orcamento);
      String saida = "\n\nCódigo interno do projeto: " + codInterno + "\nNome do projeto: " + titulo +
                     "\nDuração do projeto: " + duracao + " meses \nOrçamento do projeto: " + o +
                     "\nLocal onde será realizado: " + localProjeto + "\n" + pesquisa.toString();
      return saida;
   }
   public void inserir(Connection cnn){
      String sqlInsert = "INSERT INTO projeto(cod_curso, cpf_solicitante, instituicao, duracao_meses, orcamento, titulo)" +
                        " VALUES (?,?,?,?,?,?)";
      try(PreparedStatement stm = cnn.prepareStatement(sqlInsert);){
         stm.setString(1, pesquisa.getECodArea());
         stm.setString(2, solicitante.getCpf());
         stm.setString(3, getLocalProjeto());
         stm.setInt(4, getDuracao());
         stm.setDouble(5, getOrcamento());
         stm.setString(6, getTitulo());
         stm.execute();         
         
         String query = "SELECT LAST_INSERT_ID()";
         try(PreparedStatement stm2 = cnn.prepareStatement(query);
               ResultSet rs = stm2.executeQuery();){
            if(rs.next()){
               setCodInterno(rs.getInt(1));
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
   public void deletar (Connection cnn){
      String sqlDelete = "DELETE FROM projeto WHERE cod_projeto = ?";
      
      try(PreparedStatement stm = cnn.prepareStatement(sqlDelete);){
         stm.setInt(1, getCodInterno());
         
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
      String sqlUpdate = "UPDATE projeto SET instituicao =?, duracao_meses =?, orcamento= ?, titulo = ? WHERE cod_projeto = ?";
      
      try(PreparedStatement stm = cnn.prepareStatement(sqlUpdate);){
         stm.setString(1, getLocalProjeto());
         stm.setInt(2, getDuracao());
         stm.setDouble(3, getOrcamento());
         stm.setString(4, getTitulo());
         stm.setInt(5, getCodInterno());
         
         stm.execute();
      }catch(Exception x){
         x.printStackTrace();
         try{
            cnn.rollback();
         }catch(Exception x9){
            x9.printStackTrace();
         }
      }
   }
   public void selecionar (Connection cnn){
      String sqlSelect = "SELECT * FROM projeto WHERE cod_projeto = ?";
      
      try(PreparedStatement stm = cnn.prepareStatement(sqlSelect);){
         stm.setInt(1, getCodInterno());
         
         try(ResultSet rs = stm.executeQuery();){
            if(rs.next()){
               AreaPesquisa a = new AreaPesquisa(rs.getString(2));
               this.setPesquisa(a);
               Solicitante s = new Solicitante(rs.getString(3));
               this.setSolicitante(s);
               this.setLocalProjeto(rs.getString(4));
               this.setDuracao(rs.getInt(5));
               this.setOrcamento(rs.getDouble(6));
               this.setTitulo(rs.getString(7));
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