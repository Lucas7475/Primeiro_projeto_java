import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.event.*;
import java.sql.Connection;
import java.util.ArrayList;
import java.sql.SQLException;
import java.util.Random;
import javax.swing.JOptionPane;

public class TelaProjeto extends JFrame implements ActionListener, ItemListener{
   
   private JButton btnNovo, btnConsulta, btnAltera, btnDeleta, btnSair, btnConfirma,
            btnConsultar, btnAlterar, btnDeletar, btnProjeto, btnAvaliador, btnPesquisador, btnFechar;
   private JLabel lblCpf, lblAvaliador, lblCodProjeto, lblCurso, lblInstituicao,
             lblDuracao, lblOrcamento, lblTitulo;
   private JTextField txtCodProjeto, txtInstituicao, txtDuracao, txtOrcamento, txtTitulo; 
   private Connection cnn, cn;
   private JComboBox<String> cbCpf;
   private JComboBox<String> cbAvaliador;
   private JComboBox<String> cbCurso;
   private String [] cpfs, cursos;
   private Integer [] avaliadores;
   private Projeto projeto;
   private Solicitante solicitante;
   private AreaPesquisa aPesquisa;
   private String codCurso, cpfEscolhido;
   private Avaliacao avaliacao;
   private Avaliador avaliador;
   private Random gerador;
      
   public TelaProjeto(Connection cnn){
      super("Tela de projetos");
      this.cnn = cnn;
      //instanciando os botões
      btnNovo = new JButton("Novo projeto");
      btnConsulta = new JButton("Consulta projeto");
      btnAltera = new JButton("Altera projeto");
      btnDeleta = new JButton("Deleta projeto");
      btnSair = new JButton("Voltar");
      btnConfirma = new JButton("Confirma");
      btnConsultar = new JButton ("Consulta");
      btnAlterar = new JButton ("Altera");
      btnDeletar = new JButton ("Deleta");
      
      btnConfirma.setVisible(false);
      btnConsultar.setVisible(false);
      btnAlterar.setVisible(false);
      btnDeletar.setVisible(false);
      
      //Instanciando as caixas de texto
      txtCodProjeto = new JTextField(6);
      txtInstituicao = new JTextField(18);
      txtDuracao = new JTextField(8);
      txtOrcamento = new JTextField(8);
      txtTitulo = new JTextField(12);
      
      txtCodProjeto.setEditable(false);
      txtInstituicao.setEditable(false);
      txtDuracao.setEditable(false);
      txtOrcamento.setEditable(false);
      txtTitulo.setEditable(false);
      
      //instancianco os textos
      lblCpf = new JLabel("CPF solicitante");
      lblAvaliador = new JLabel("Código avaliador");
      lblCodProjeto = new JLabel("Código do projeto:");
      lblCurso = new JLabel("Curso:");
      lblInstituicao = new JLabel("Instituição:");
      lblDuracao = new JLabel("Duração do projeto:");
      lblOrcamento = new JLabel("Orçamento:");
      lblTitulo = new JLabel("Título do projeto:");
      
      //instanciando Comboboxs
      avaliadores = vetorAvaliador(cnn);
      cbAvaliador = new JComboBox(avaliadores);
      cpfs = vetorSolicitante(cnn);
      cbCpf = new JComboBox(cpfs);
      cursos = vetorCurso(cnn);
      cbCurso = new JComboBox(cursos);
      
      cbCpf.setEnabled(false);
      cbCurso.setEnabled(false);
      cbAvaliador.setEnabled(false);
      
      //Instanciando paineis e container
      Container caixa = getContentPane();
      caixa.setLayout(new BorderLayout());
      JPanel ladoCenter = new JPanel(new FlowLayout());
      JPanel ladoWest = new JPanel(new GridLayout(10,2));
      JPanel ladoSouth = new JPanel(new FlowLayout());
      
      //preparando os paineis
      ladoWest.add(lblCpf);
      ladoWest.add(lblAvaliador);
      ladoWest.add(cbCpf);
      ladoWest.add(cbAvaliador);
      
      
      ladoWest.add(lblCodProjeto);
      ladoWest.add(txtCodProjeto);
      ladoWest.add(lblCurso);
      ladoWest.add(cbCurso);
      ladoWest.add(lblInstituicao);
      ladoWest.add(txtInstituicao);
      
      ladoWest.add(lblDuracao);
      ladoWest.add(txtDuracao);
      ladoWest.add(lblOrcamento);
      ladoWest.add(txtOrcamento);
      ladoWest.add(lblTitulo);
      ladoWest.add(txtTitulo);
      
      ladoWest.add(btnConfirma);
      ladoWest.add(btnConsultar);
      ladoWest.add(btnAlterar);
      ladoWest.add(btnDeletar);
      
      ladoSouth.add(btnNovo);
      ladoSouth.add(btnConsulta);
      ladoSouth.add(btnAltera);
  
      ladoSouth.add(btnSair);
      
      ladoCenter.add(ladoWest);
        
      //colocando no container
   
      caixa.add(ladoSouth, BorderLayout.SOUTH);
      caixa.add(ladoCenter, BorderLayout.CENTER);
      
      //adcionando olheiros
      btnNovo.addActionListener(this);
      btnConsulta.addActionListener(this);
      btnAltera.addActionListener(this);
      btnDeleta.addActionListener(this);
      btnSair.addActionListener(this);
      btnConfirma.addActionListener(this);
      btnConsultar.addActionListener(this);
      btnAlterar.addActionListener(this);
      btnDeletar.addActionListener(this);
      cbCpf.addItemListener(this);
      cbAvaliador.addItemListener(this);
      cbCurso.addItemListener(this);
      
      //detalhes finais
      setLocationRelativeTo(null);
      setSize(700, 350);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setVisible(true);
   }
   public void limpa(){
      txtCodProjeto.setText("");
      txtInstituicao.setText("");
      txtDuracao.setText("");
      txtOrcamento.setText("");
      txtTitulo.setText("");
   }
   public void oculta(){
      txtCodProjeto.setEditable(false);
      txtInstituicao.setEditable(false);
      txtDuracao.setEditable(false);
      txtOrcamento.setEditable(false);
      txtTitulo.setEditable(false);
      cbCpf.setEnabled(false);
      cbCurso.setEnabled(false);
      cbAvaliador.setEnabled(false);
   }
   
   public void actionPerformed(ActionEvent x){
      if(x.getSource() == btnNovo){
         limpa();
         cbCpf.setEnabled(true);
         btnConfirma.setVisible(true);
      }
      else if(x.getSource() == btnConsulta){
         limpa();
         txtCodProjeto.setEditable(true);
         btnConsultar.setVisible(true);         
      }
      else if(x.getSource() == btnAltera){
         btnAlterar.setVisible(true);
         txtInstituicao.setEditable(true);
         txtDuracao.setEditable(true);
         txtOrcamento.setEditable(true);
         txtTitulo.setEditable(true);           
      }

      else if(x.getSource() == btnConfirma){
         try{
            Conexao_projeto con = new Conexao_projeto();
            cn = con.conecta();
            cn.setAutoCommit(false);
            aPesquisa = new AreaPesquisa(codCurso);
            solicitante = new Solicitante(cpfEscolhido);
            int dura = Integer.parseInt(txtDuracao.getText());
            double valor = Double.parseDouble(txtOrcamento.getText());
            projeto = new Projeto(txtInstituicao.getText(), txtTitulo.getText(), dura, valor,
                     solicitante, aPesquisa);
            gerador = new Random();
            avaliador = new Avaliador(avaliadores[gerador.nextInt(5)]);
            avaliacao = new Avaliacao(projeto, avaliador);            
            projeto.inserir(cn);
            avaliacao.inserir(cn);
            cn.commit();
            txtCodProjeto.setText("" + projeto.getCodInterno());
            oculta();
            btnConfirma.setVisible(false);
            JOptionPane.showMessageDialog(this, "Projeto cadastrado com sucesso",  "Confirmação", JOptionPane.INFORMATION_MESSAGE);
         }catch(Exception x9){
            x9.printStackTrace();
            try{
               cn.rollback();
            }catch(Exception x1){
               x1.printStackTrace();
            }
         }         
      }
      else if(x.getSource() == btnConsultar){
         try{
            //voltar depois e tentar ajustar os detalhes da comboBox
            // outro ponto que tentei fazer mas sem sucesso
            
            Conexao_projeto con = new Conexao_projeto();
            cn = con.conecta();
            cn.setAutoCommit(false);
            projeto = new Projeto(Integer.parseInt(txtCodProjeto.getText()));
            projeto.selecionar(cn);
            cn.commit();
            txtCodProjeto.setText(""+ projeto.getCodInterno());
            txtInstituicao.setText(projeto.getLocalProjeto());
            txtDuracao.setText(""+projeto.getDuracao());
            txtOrcamento.setText(""+projeto.getOrcamento());
            txtTitulo.setText(projeto.getTitulo());
            btnDeletar.setVisible(true);
            
         }catch(Exception x9){
            x9.printStackTrace();
            try{
               cnn.rollback();
            }catch(Exception x1){
               x1.printStackTrace();
            }
         }
      }
      else if(x.getSource() == btnAlterar){
         try{
            Conexao_projeto con = new Conexao_projeto();
            cn = con.conecta();
            cn.setAutoCommit(false);
            int dura = Integer.parseInt(txtDuracao.getText());
            double valor = Double.parseDouble(txtOrcamento.getText());
            projeto = new Projeto(Integer.parseInt(txtCodProjeto.getText()));
            projeto.setLocalProjeto(txtInstituicao.getText());
            projeto.setTitulo(txtTitulo.getText());
            projeto.setDuracao(dura);
            projeto.setOrcamento(valor);
            projeto.atualizar(cn);
            cn.commit();
            oculta();
            btnAlterar.setVisible(false);
            JOptionPane.showMessageDialog(this,"Projeto alterado com sucesso", "Confirmação", JOptionPane.INFORMATION_MESSAGE);                        
            
         }catch(Exception x9){
            x9.printStackTrace();
            try{
               cnn.rollback();
            }catch(Exception x1){
               x1.printStackTrace();
            }
         }         
      }
      else if(x.getSource() == btnDeletar){
         try{
            Conexao_projeto con = new Conexao_projeto();
            cn = con.conecta();
            cn.setAutoCommit(false);
            projeto = new Projeto(Integer.parseInt(txtCodProjeto.getText()));
            projeto.deletar(cn);
            cn.commit();
            limpa();
            btnDeletar.setVisible(false);
            JOptionPane.showMessageDialog(this, "Projeto excluido com sucesso", "Confirmação", JOptionPane.INFORMATION_MESSAGE);                        
            
         }catch(Exception x9){
            x9.printStackTrace();
            try{
               cnn.rollback();
            }catch(Exception x1){
               x1.printStackTrace();
            }
         }             
      } else if (x.getSource() == btnSair) {
         Teste.telaGeral.setVisible(true);
         setVisible(false);
      }
   }
   public void itemStateChanged(ItemEvent y){
   // evento criado ao alterar a opção no comboBox
      if(y.getItemSelectable() == cbCpf){
         if(y.getStateChange() == ItemEvent.SELECTED){
            txtInstituicao.setEditable(true);
            txtDuracao.setEditable(true);
            txtOrcamento.setEditable(true);
            txtTitulo.setEditable(true);
            cbCurso.setEnabled(true);
            cpfEscolhido = cpfs[ItemEvent.SELECTED];
         }   
      }
      if(y.getItemSelectable() == cbCurso){
         if(y.getStateChange() == ItemEvent.SELECTED){
            String med= cursos[ItemEvent.SELECTED];
            codCurso = med.substring(0,4);
         }
      }
   }
   
   public Integer[] vetorAvaliador(Connection cnn){
      Listas aval = new Listas();
      ArrayList<Avaliador> list = aval.buscaAvaliador(cnn);
      Integer []avali = new Integer[list.size()];
      Avaliador vale;
      
      for(int x = 0; x < list.size(); x++){
         vale = list.get(x);
         avali[x] = vale.getCodigo();         
      }
      return avali;
   }
   public String[] vetorSolicitante(Connection cnn){
      Listas sol = new Listas();
      ArrayList<Solicitante> list = sol.buscaSolicitante(cnn);
      String[] soli = new String[list.size()];
      Solicitante solici;
      
      for(int x = 0; x< list.size(); x++){
         solici = list.get(x);
         soli[x] = solici.getCpf();
      }
      return soli;
   }
   
   public String [] vetorCurso(Connection cnn){
      Listas cur = new Listas();
      ArrayList<AreaPesquisa> list = cur.buscaCurso(cnn);
      String [] curs = new String[list.size()];
      AreaPesquisa ap;
      
      for(int x = 0; x<list.size(); x++){
         ap = list.get(x);
         curs[x] = (ap.getECodArea() + " - " + ap.getENome());
      }
      return curs;
   }
}