import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.util.Calendar;
import java.sql.SQLException;

public class Avaliacao{
   
   private int codAvaliacao;
   private Projeto projeto;
   private Avaliador avaliador;
   private String resultado;
   private Calendar c = Calendar.getInstance();
   private String dataEntrada, dataSaida;
   
   public Avaliacao(){}
   
   public Avaliacao(Projeto projeto, Avaliador avaliador){
      setProjeto(projeto);
      setAvaliador(avaliador);
      setCodAvaliacao(codAvaliacao);
      setResultado("não avaliado");
      setDataEntrada(c.get(Calendar.DAY_OF_MONTH) + "/"+c.get(Calendar.MONTH)+"/"+c.get(Calendar.YEAR));
      
   }
   public Avaliacao(int codAvaliacao, String resultado){
      setCodAvaliacao(codAvaliacao);
      setResultado(resultado);
      setDataSaida(c.get(Calendar.DAY_OF_MONTH) + "/"+c.get(Calendar.MONTH)+"/"+c.get(Calendar.YEAR));
   }
   
   //modificadores
   public int getCodAvaliacao(){
      return codAvaliacao; 
   }
   public String getResultado(){
      return resultado; 
   }
   public String getDataEntrada(){
      return dataEntrada; 
   }
   public String getDataSaida(){
      return dataSaida; 
   }
   public Projeto getProjeto(){
      return projeto;
   }
   public Avaliador getAvaliador(){
      return avaliador; 
   }
   
   public void setCodAvaliacao(int codAvaliacao){
         this.codAvaliacao = codAvaliacao;      
   }
   public void setResultado(String resultado){
      this.resultado = resultado;
   }
   public void setDataEntrada(String dataEntrada){
      this.dataEntrada = dataEntrada;
   }
   public void setDataSaida(String dataSaida){
      this.dataSaida = dataSaida;
   }
   public void setProjeto(Projeto projeto){
      this.projeto = projeto;
   }
   public void setAvaliador(Avaliador avaliador){
      this.avaliador = avaliador;
   }
   public String saidaSolicitante(){
      return "Código da avaliação: " + codAvaliacao + "\nProjeto: " +projeto.getCodInterno() +"-"+ projeto.getTitulo() +
                 "\nData de entrada:" + dataEntrada+ "\nData de resposta:" + dataSaida + "\nStatus: " + resultado;
   }
   public String saidaEmpresa(){
      return "Código da avaliação: " + codAvaliacao + "\nCódigo do projeto: " +projeto.getCodInterno() +
                 "\nAvaliador: " + avaliador.getCodigo() +"-"+ avaliador.getNome() + "\nStatus: " + resultado;
   }
   
   //conexão banco
   public void inserir(Connection cnn){
      String sqlInsert = "INSERT INTO avaliacao(cod_interno, cod_avaliador, data_entrada, resposta) VALUES(?,?,?,?)";
      
      try(PreparedStatement stm = cnn.prepareStatement(sqlInsert);){
         stm.setInt(1, projeto.getCodInterno());
         stm.setInt(2, avaliador.getCodigo());
         stm.setString(3, getDataEntrada());
         stm.setString(4, getResultado());
         stm.execute();
         String query = "SELECT LAST_INSERT_ID()";
         try(PreparedStatement stm2 = cnn.prepareStatement(query);
               ResultSet rs = stm2.executeQuery();){
            if(rs.next()){
               setCodAvaliacao(rs.getInt(1));
            }else{
               throw new SQLException("Falha ao gerar o código");
            }      
         }catch(Exception x){
            x.printStackTrace();
            try{
               cnn.rollback();
            }catch(Exception x9){
               x.printStackTrace();
            }
         }
         
      }catch(Exception x){
         x.printStackTrace();
         try{
            cnn.rollback();
         }catch(Exception x9){
            x9.printStackTrace();
         }
      }
   }
   public void deletar(Connection cnn){
      String sqlDelete = "DELETE FROM avaliacao WHERE cod_avaliacao = ?";
      
      try(PreparedStatement stm = cnn.prepareStatement(sqlDelete);){
         stm.setInt(1, getCodAvaliacao());
         stm.execute();
      }catch(Exception x){
         x.printStackTrace();
         try{
            cnn.rollback();
         }catch(Exception x9){
            x.printStackTrace();
         }
      }
   }
   public void atualizar(Connection cnn){
      String sqlUpdate = "UPDATE avaliacao SET data_saida = ?, resposta = ? WHERE cod_avaliacao = ?";
      
      try(PreparedStatement stm = cnn.prepareStatement(sqlUpdate);){
         stm.setString(1, getDataSaida());
         stm.setString(2, getResultado());
         stm.setInt(3, getCodAvaliacao());
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
   public void carregar(Connection cnn){
      String sqlSelect = "SELECT * FROM avaliacao WHERE cod_avaliacao = ?";
      
      try(PreparedStatement stm = cnn.prepareStatement(sqlSelect);){
         stm.setInt(1, getCodAvaliacao());
         try(ResultSet rs = stm.executeQuery();){
            if(rs.next()){
               projeto.setCodInterno(rs.getInt(2));
               avaliador.setCodigo(rs.getInt(3));
               setDataEntrada(rs.getString(4));
               setResultado(rs.getString(5));
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
   public void selecionar (Connection cnn){
      String sqlSelect = "SELECT cod_avaliacao, cod_interno, cod_avaliador ,data_entrada , data_saida ,resposta FROM avaliacao WHERE cod_interno = ?";
      
      try(PreparedStatement stm = cnn.prepareStatement(sqlSelect);){
         stm.setInt(1, projeto.getCodInterno());
         try(ResultSet rs = stm.executeQuery();){
            if(rs.next()){
               setCodAvaliacao(rs.getInt("cod_avaliacao"));
               projeto.setCodInterno(rs.getInt("cod_interno"));
               setDataEntrada(rs.getString("data_entrada"));
               setDataSaida(rs.getString("data_saida"));
               setResultado(rs.getString("resposta"));
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

}