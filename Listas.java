import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Listas{

   private ArrayList<Projeto> lProjeto = new ArrayList<>();
   private ArrayList<Avaliador> lAvaliador = new ArrayList<>();
   private ArrayList<Solicitante> lSolicitante = new ArrayList<>();
   private ArrayList<AreaPesquisa> lPesquisa = new ArrayList<>();
   private ArrayList<Avaliacao> lAvaliacao = new ArrayList<>();
   private ArrayList<Projeto> lProjetoS = new ArrayList<>();
      
   public ArrayList<Projeto> buscaProjeto(Connection cnn){
      String sqlSelect = "SELECT cod_projeto, cod_curso, cpf_solicitante, instituicao,duracao_meses," +
                           " orcamento, titulo FROM projeto";      
      
      try(PreparedStatement stm = cnn.prepareStatement(sqlSelect);
            ResultSet rs = stm.executeQuery();){
         while(rs.next()){
            Projeto p = new Projeto();            
            p.setCodInterno(rs.getInt("cod_projeto"));
            AreaPesquisa a = new AreaPesquisa(rs.getString("cod_curso"), cnn);
            p.setPesquisa(a);
            Solicitante s = new Solicitante(rs.getString("cpf_solicitante"), cnn);
            p.setSolicitante(s);
            p.setLocalProjeto(rs.getString("instituicao"));
            p.setDuracao(rs.getInt("duracao_meses"));
            p.setOrcamento(rs.getDouble("orcamento"));
            p.setTitulo(rs.getString("titulo"));
            lProjeto.add(p);
         }      
      }catch(Exception x){
         x.printStackTrace();
         try{
            cnn.rollback();
         }catch(Exception x9){
            x9.printStackTrace();
         }
      }
      return lProjeto;
   }
      
   public ArrayList<Avaliador> buscaAvaliador(Connection cnn){
      String sqlSelect = "SELECT cod_avaliador, cpf_avaliador, rg_avaliador, nome_avaliador, sexo, data_nasct," +
                        "nivel_academico, local_trab FROM avaliador";
         
      try(PreparedStatement stm = cnn.prepareStatement(sqlSelect);
            ResultSet rs = stm.executeQuery();){
         while(rs.next()){
            Avaliador a = new Avaliador();
            a.setCodigo(rs.getInt("cod_avaliador"));
            a.setCpf(rs.getString("cpf_avaliador"));
            a.setRg(rs.getString("rg_avaliador"));
            a.setNome(rs.getString("nome_avaliador"));
            a.setSexo(rs.getString("sexo"));
            a.setDataNascimento(rs.getString("data_nasct"));
            a.setNivel(rs.getString("nivel_academico"));
            a.setTrabalha(rs.getString("local_trab"));
            lAvaliador.add(a);
         }
      }catch(Exception x){
         x.printStackTrace();
         try{
            cnn.rollback();
         }catch(Exception x9){
            x9.printStackTrace();
         }
      }
      return lAvaliador;      
   }
   public ArrayList<Solicitante> buscaSolicitante(Connection cnn){
      String sqlSelect ="SELECT cpf_solicitante, rg_solicitante, nome_solicitante, sexo, data_nasc,"+
                        " nivel_academico, local_graduacao FROM solicitante";
                        
      try(PreparedStatement stm = cnn.prepareStatement(sqlSelect);
            ResultSet rs = stm.executeQuery();){
         while(rs.next()){
            Solicitante s = new Solicitante();
            s.setCpf(rs.getString("cpf_solicitante"));
            s.setRg(rs.getString("rg_solicitante"));
            s.setNome(rs.getString("nome_solicitante"));
            s.setSexo(rs.getString("sexo"));
            s.setDataNascimento(rs.getString("data_nasc"));
            s.setNivel(rs.getString("nivel_academico"));
            s.setLocalGraduacao(rs.getString("local_graduacao"));
            lSolicitante.add(s);            
         }         
      }catch(Exception x){
         x.printStackTrace();
         try{
            cnn.rollback();
         }catch(Exception x9){
            x9.printStackTrace();
         }
      }
      return lSolicitante;
   }
   
   public ArrayList<AreaPesquisa> buscaCurso(Connection cnn){
      String sqlSelect = "SELECT cod_especifica, nome_especifica, cod_area, nome_area FROM area_pesquisa";
      
      try(PreparedStatement stm = cnn.prepareStatement(sqlSelect);
            ResultSet rs = stm.executeQuery();){
         while(rs.next()){
            AreaPesquisa ap = new AreaPesquisa();
            ap.setECodArea(rs.getString("cod_especifica"));
            ap.setENome(rs.getString("nome_especifica"));
            ap.setNome(rs.getString("cod_area"));
            ap.setCodPesquisa(rs.getString("nome_area"));
            lPesquisa.add(ap);
         }      
      }catch(Exception x){
         x.printStackTrace();
         try{
            cnn.rollback();
         }catch(Exception x9){
            x9.printStackTrace();
         }
      }
      return lPesquisa;      
   }
   public ArrayList<Avaliacao> buscaAvaliacao(Connection cnn, int codAvaliador){
      String sqlSelect = "SELECT cod_avaliacao, cod_interno, data_entrada, data_saida, resposta FROM avaliacao WHERE cod_avaliador = ?";
            
      try(PreparedStatement stm = cnn.prepareStatement(sqlSelect);){
         stm.setInt(1, codAvaliador);
         try(ResultSet rs = stm.executeQuery();){
            while(rs.next()){
               Avaliacao ap = new Avaliacao();
               ap.setCodAvaliacao(rs.getInt("cod_avaliacao"));
               Projeto p = new Projeto(rs.getInt("cod_interno"));
               ap.setProjeto(p);            
               ap.setDataEntrada(rs.getString("data_entrada"));
               ap.setDataSaida(rs.getString("data_saida"));
               ap.setResultado(rs.getString("resposta"));
               lAvaliacao.add(ap);
            }      
         }catch(Exception x){
            x.printStackTrace();
         }
      }catch(Exception x1){
         x1.printStackTrace();
         try{
            cnn.rollback();
         }
         catch(Exception x9){
            x9.printStackTrace();
         }
      }
      return lAvaliacao;      
   } 
   public ArrayList<Projeto> buscaProjetos(Connection cnn, String cpf){
      String sqlSelect = "SELECT cod_projeto, cod_curso,cpf_solicitante, instituicao,duracao_meses," +
                           " orcamento, titulo FROM projeto WHERE cpf_solicitante = ?";      
      
      try(PreparedStatement stm = cnn.prepareStatement(sqlSelect);){
         stm.setString(1, cpf);
         try(ResultSet rs = stm.executeQuery();){
            while(rs.next()){
               Projeto p = new Projeto();            
               p.setCodInterno(rs.getInt("cod_projeto"));
               AreaPesquisa a = new AreaPesquisa(rs.getString("cod_curso"), cnn);
               p.setPesquisa(a);
               Solicitante s = new Solicitante(rs.getString("cpf_solicitante"), cnn);
               p.setSolicitante(s);
               p.setLocalProjeto(rs.getString("instituicao"));
               p.setDuracao(rs.getInt("duracao_meses"));
               p.setOrcamento(rs.getDouble("orcamento"));
               p.setTitulo(rs.getString("titulo"));
               lProjetoS.add(p);
            }      
         }catch(Exception x){
            x.printStackTrace();
         }
      }catch(Exception x9){
         x9.printStackTrace();
      }
   
      return lProjetoS;
   }
     
}