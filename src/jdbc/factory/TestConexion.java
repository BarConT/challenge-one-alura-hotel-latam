package jdbc.factory;

import java.sql.Connection;
import java.sql.SQLException;

public class TestConexion {
	 public static void main(String[] args) throws SQLException {
		 
			ConnectionFactory connectionFactory = new ConnectionFactory();
			Connection con = connectionFactory.recuperaConexion();
	        con.close();
	        System.out.println("Cerrando la conexión");
		
    }
}
