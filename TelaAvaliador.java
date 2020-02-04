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

public class TelaAvaliador extends JFrame implements ActionListener, ItemListener, ListSelectionListener{
  
   
   private JTextField txtNome;
   private JLabel lblNome, lblAvaliador;
   private JButton btnVoltar;
   private Avaliador avaliador;
   private Connection conn;
   //clientela sera usado para criar o JComboBox
   private Integer[] avaliadores;
   //colunas e pedidos serao usados para criar a JTable
   private String[] colunas = {"Cod. Avaliação", "Cod. Projeto", "Data entrada", "Data Saída", "Status"};
   private Object[][] projetos;
   private JComboBox <Integer> cbAvaliador;
   private JTable tabelaProjetos;
   private JScrollPane rolagem;
   private JPanel ladoCenter;
   private Container caixa;
   
   public TelaAvaliador(Connection conn){
      super("Consulta de Avaliadores");
      this.conn = conn;
      //instanciando espaços de textos
      txtNome = new JTextField(20);
      txtNome.setEditable(false);
      
      //instanciando mensagens
      lblAvaliador = new JLabel("Cod. Avaliador");
      lblNome = new JLabel("Nome");
      
      //instanciando botão
      btnVoltar = new JButton("Voltar");      
      
      
      
      // instanciando comboBox
      avaliadores = vetorAvaliador(conn);
      cbAvaliador = new JComboBox(avaliadores);
      
   
      
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
      
      ladoNorth.add(lblAvaliador);
      ladoNorth.add(cbAvaliador);
      ladoNorth.add(lblNome);
      ladoNorth.add(txtNome);
      
      ladoSouth.add(btnVoltar);
            
      ladoCenter.add(rolagem);
   
      btnVoltar.addActionListener(this);
      
      cbAvaliador.addItemListener(this);
      
      caixa.add(ladoNorth, BorderLayout.NORTH);
      caixa.add(ladoSouth, BorderLayout.SOUTH);
      caixa.add(ladoCenter, BorderLayout.CENTER);
      
      setSize(600,500);
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
      /* colocar dentro deste if porque o evento e disparado duas vezes e assim 
         filtra-se somente um deles */
      
   

      if(e.getValueIsAdjusting()){
         String resultado = 
            tabelaProjetos.getColumnName(0)+": "+
            tabelaProjetos.getValueAt(tabelaProjetos.getSelectedRow(),0)+
            "\n"+tabelaProjetos.getColumnName(1)+": "+
            tabelaProjetos.getValueAt(tabelaProjetos.getSelectedRow(),1)+
            "\n"+tabelaProjetos.getColumnName(2)+": "+
            tabelaProjetos.getValueAt(tabelaProjetos.getSelectedRow(),2)+
            "\n"+tabelaProjetos.getColumnName(3)+": "+
            tabelaProjetos.getValueAt(tabelaProjetos.getSelectedRow(),3)+
            "\n"+tabelaProjetos.getColumnName(4)+": "+
            tabelaProjetos.getValueAt(tabelaProjetos.getSelectedRow(),4);
         JOptionPane.showMessageDialog(this, resultado);
      }
      
      // tentei arranjar uma forma de usa modificar a informação através da JTable mas não tive sucesso
      // deixei para mostrar a tentativa
   
   }
   
   public Integer[] vetorAvaliador(Connection conn){
      Listas aval = new Listas();
      ArrayList<Avaliador> list = aval.buscaAvaliador(conn);
      Integer []avali = new Integer[list.size()];
      Avaliador vale;
      
      for(int x = 0; x < list.size(); x++){
         vale = list.get(x);
         avali[x] = vale.getCodigo();         
      }
      return avali;
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
      ArrayList<Avaliacao> lista = aval.buscaAvaliacao(cn, cbAvaliador.getItemAt(cbAvaliador.getSelectedIndex()));
      
      avaliador = new Avaliador(cbAvaliador.getItemAt(cbAvaliador.getSelectedIndex()));
      avaliador.seleciona(cn);
      txtNome.setText(avaliador.getNome());
   
      String[][] saida = new String[lista.size()][colunas.length];
      Avaliacao avalia;
      for(int i = 0; i < lista.size(); i++){
         avalia = lista.get(i);
         saida[i][0] = avalia.getProjeto().getCodInterno() +"";
         saida[i][1] = avalia.getCodAvaliacao() + "";
         saida[i][2] = avalia.getDataEntrada();
         saida[i][3] = avalia.getDataSaida();
         saida[i][4] = avalia.getResultado();
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
      rolagem.setPreferredSize(new Dimension(400,300));
   }
}