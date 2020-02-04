import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JComboBox;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.sql.Connection;
import java.util.ArrayList;
import java.awt.Dimension;
import javax.swing.JOptionPane;
//para tratar eventos de selecao na tabela
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;

public class TelaSolicitante extends JFrame implements ActionListener, ItemListener, ListSelectionListener{
  

   private JTextField txtNome;
   private JLabel lblNome, lblSolicitante;
   private JButton btnVoltar;
   private Solicitante solicitante;
   private Connection conn;
   private String[] solicitantes;
   //colunas e pedidos serao usados para criar a JTable
   private String[] colunas = {"cod_projeto", " cod_curso"," cpf_solicitante"," instituicao"," duracao_meses"," orcamento"," titulo"};
   private Object[][] projetos;
   private JComboBox <Integer> cbSolicitante;
   private JTable tabelaProjetos;
   private JScrollPane rolagem;
   private JPanel ladoCenter;
   private Container caixa;
   
   public TelaSolicitante(Connection conn){
      super("Consulta de Solicitante");
      this.conn = conn;
      //instanciando espaços de textos
      txtNome = new JTextField(20);
      txtNome.setEditable(false);
      
      //instanciando mensagens
      lblSolicitante = new JLabel("CPF Solicitante");
      lblNome = new JLabel("Nome");
      
      //instanciando botão
      btnVoltar = new JButton("Voltar");      
      
      
      
      // instanciando comboBox
      solicitantes = vetorSolicitante(conn);
      cbSolicitante = new JComboBox(solicitantes);
      
   
      
      //instancia a JTable e coloca em uma barra de rolagem
      //veja metodo no fim da classe, criado porque tem que fazer varias vezes
      instanciaJTableEScrollPane();
      
   
      //Instanciando container 
      
      caixa = getContentPane();
      caixa.setLayout(new BorderLayout());
      
      //Instanciando paineis
      JPanel ladoNorth = new JPanel(new FlowLayout());
      JPanel ladoSouth = new JPanel(new FlowLayout());
      ladoCenter = new JPanel(new FlowLayout());
      
      ladoNorth.add(lblSolicitante);
      ladoNorth.add(cbSolicitante);
      ladoNorth.add(lblNome);
      ladoNorth.add(txtNome);
      
      ladoSouth.add(btnVoltar);
            
      ladoCenter.add(rolagem);
      
   
      btnVoltar.addActionListener(this);      
      cbSolicitante.addItemListener(this);
      
      caixa.add(ladoNorth, BorderLayout.NORTH);
      caixa.add(ladoSouth, BorderLayout.SOUTH);
      caixa.add(ladoCenter, BorderLayout.CENTER);
      
      setSize(700,500);
      setLocationRelativeTo(null);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setVisible(true);
      
   }
   
   public void actionPerformed(ActionEvent e){
   
      if(e.getSource() == btnVoltar){
         new TelaGeral();
         dispose();
      }  
   }
   
   /*
    * metodo invocado quando muda o item selecionado na combobox
    */ 
   public void itemStateChanged(ItemEvent e){
      //sempre que muda o id selecionado na combobox este evento e gerado
      if(e.getStateChange() == ItemEvent.SELECTED){

         caixa.remove(ladoCenter);
         ladoCenter.remove(rolagem);
         //instancia uma nova JTable e uma nova barra de rolagem, pois nao da para mudar o conteudo da antiga
         instanciaJTableEScrollPane(); 
         //coloca de volta
         ladoCenter.add(rolagem);
         caixa.add(ladoCenter, BorderLayout.CENTER);
         /* adicionar elementos torna o container invalido
          * por isso precisa revalidar
          */
         validate();
         //redesenha o container
         repaint();
      }
   }
   
   /*
    * metodo invocado quando um item da tabela e selecionado
    */
   public void valueChanged(ListSelectionEvent e){

      if(e.getValueIsAdjusting()){
         int resul = Integer.parseInt("" +tabelaProjetos.getValueAt(+tabelaProjetos.getSelectedRow(),0));
         Connection conx;
         try{
            Conexao_projeto bd = new Conexao_projeto();
            conx = bd.conecta();
            Projeto p = new Projeto(resul);
            p.selecionar(conx);            
            Avaliacao a = new Avaliacao ();
            a.setProjeto(p);
            a.selecionar(conx);
            JOptionPane.showMessageDialog(this, a.saidaSolicitante()); 
         }catch(Exception x){
            x.printStackTrace();
         }
         
      }
   }
   
   public String[] vetorSolicitante(Connection conn){
      Listas sol = new Listas();
      ArrayList<Solicitante> list = sol.buscaSolicitante(conn);
      String[] soli = new String[list.size()];
      Solicitante solici;
      
      for(int x = 0; x< list.size(); x++){
         solici = list.get(x);
         soli[x] = solici.getCpf();
      }
      return soli;
   }
     
   public String[][] carregaDados(Connection conn){

      Connection cn = null;
      try{
         Conexao_projeto bd = new Conexao_projeto();
         cn = bd.conecta();
      }catch(Exception x9){
         x9.printStackTrace();
      }       
      Listas aval = new Listas();
      String cpf = solicitantes[cbSolicitante.getSelectedIndex()];
      
      ArrayList<Projeto> proj = aval.buscaProjetos(cn, cpf);
      solicitante = new Solicitante(cpf);
      solicitante.seleciona(cn);
      txtNome.setText(solicitante.getNome());

            
      String[][] saida = new String[proj.size()][colunas.length];
      Projeto proja;
      for(int i = 0; i < proj.size(); i++){
         proja = proj.get(i);
         saida[i][0] = proja.getCodInterno() +"";
         saida[i][1] = proja.getPesquisa()+ "";
         saida[i][2] = proja.getSolicitante().getCpf();
         saida[i][3] = proja.getLocalProjeto();
         saida[i][4] = proja.getDuracao() + "";
         saida[i][5] = proja.getOrcamento() + "";
         saida[i][6] = proja.getTitulo();

      }
      return saida;      
   } 
   
   //metodo para centralizar a instanciacao da JTable e nao ficar repetindo codigo
   public void instanciaJTableEScrollPane(){
      //carrega a matriz de pedidos para instanciar a JTable
      projetos = carregaDados(conn);
      tabelaProjetos = new JTable(projetos, colunas);
      tabelaProjetos.getSelectionModel().addListSelectionListener(this);
      //coloca a JTable em um scroll pane para ter a barra de rolagem
      rolagem = new JScrollPane(tabelaProjetos);
      /*
       * fixa a dimensao do scroll pane, senao ele fica grande o bastante para
       * que a JTable sempre caiba nele e por isso a barra de rolagem nao aparece
       * nunca
      */
      rolagem.setPreferredSize(new Dimension(600,300));
   }
}