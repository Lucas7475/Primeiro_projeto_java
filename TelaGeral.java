import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Container;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.SQLException;

public class TelaGeral  extends JFrame {

   private JButton btnProjetos, btnAvaliador, btnPesquisador, btnSair;
   private TelaProjeto telaProjeto;
   private TelaAvaliador telaAvaliador;
   private TelaSolicitante telaSolicitante;

   public TelaGeral(){
      super("Menu Principal");
      //Instanciando paineis e container
      Container caixa = getContentPane();
      caixa.setLayout(new BorderLayout());
      JPanel ladoNorth = new JPanel(new FlowLayout());
      JPanel ladoCenter = new JPanel(new FlowLayout());
      JPanel ladoSouth = new JPanel(new FlowLayout());
   
      btnProjetos = new JButton("Projetos");
      btnAvaliador = new JButton("Avaliador");
      btnPesquisador = new JButton("Pesquisador");
      btnSair = new JButton("Sair");
   
      btnProjetos.addActionListener(
         new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               Connection cnn = null;
               try{
                  Conexao_projeto bp = new Conexao_projeto();
                  cnn = bp.conecta();
                  cnn.setAutoCommit(false);
                  setVisible(false);
                  telaProjeto = new TelaProjeto(cnn);
                  cnn.commit();
               }catch(SQLException x){
                  x.printStackTrace();
                  try{
                     cnn.rollback();
                  }catch(Exception x9){
                     x9.printStackTrace();
                  }
               }finally {
                  if (cnn != null) {
                     try {
                        cnn.close();
                     } catch (Exception x1) {
                        x1.printStackTrace();
                     }
                  }
               }
            }
         });
   
      btnAvaliador.addActionListener(
         new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               Connection cnn = null;
               try{
                  Conexao_projeto bp = new Conexao_projeto();
                  cnn = bp.conecta();
                  cnn.setAutoCommit(false);
                  setVisible(false);
                  telaAvaliador = new TelaAvaliador(cnn);
                  cnn.commit();
               }catch(SQLException x){
                  x.printStackTrace();
                  try{
                     cnn.rollback();
                  }catch(Exception x9){
                     x9.printStackTrace();
                  }
               }finally {
                  if (cnn != null) {
                     try {
                        cnn.close();
                     } catch (Exception x1) {
                        x1.printStackTrace();
                     }
                  }
               }
            }
         });
         
         btnPesquisador.addActionListener(
         new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               Connection cnn = null;
               try{
                  Conexao_projeto bp = new Conexao_projeto();
                  cnn = bp.conecta();
                  cnn.setAutoCommit(false);
                  setVisible(false);
                  telaSolicitante = new TelaSolicitante(cnn);
                  cnn.commit();
               }catch(SQLException x){
                  x.printStackTrace();
                  try{
                     cnn.rollback();
                  }catch(Exception x9){
                     x9.printStackTrace();
                  }
               }finally {
                  if (cnn != null) {
                     try {
                        cnn.close();
                     } catch (Exception x1) {
                        x1.printStackTrace();
                     }
                  }
               }
            }
         });
         btnSair.addActionListener(
         new ActionListener(){
            public void actionPerformed(ActionEvent x){
               System.exit(0);
            }
         });

   
      ladoCenter.add(btnProjetos);
      ladoCenter.add(btnAvaliador);
      ladoCenter.add(btnPesquisador);
      ladoCenter.add(btnSair);
      
      caixa.add(ladoSouth, BorderLayout.SOUTH);
      caixa.add(ladoCenter, BorderLayout.CENTER);
      //caixa.add(ladoNorth, BorderLayout.NORTH);
   
      //detalhes finais
      setLocationRelativeTo(null);
      setSize(500, 200);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setVisible(true);
   }
}
