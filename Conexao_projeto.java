import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;

public class Conexao_projeto{
   
   public Connection conecta() throws SQLException{
      String servidor = "localhost";
      String porta = "3306";
      String database = "teste_projeto";
      String usuario = "root";
      String senha = "";
      
      return DriverManager.getConnection("jdbc:mysql://"+ servidor +":"+ porta + "/"+ database +
                                         "?user="+ usuario + "&password=" + senha +
                                         "&useTimezone=true&serverTimezone=America/Sao_Paulo"); 
   }
   public static void desconectar(Connection conn) throws SQLException {
      conn.close();
   }
}