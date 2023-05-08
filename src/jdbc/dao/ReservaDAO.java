package jdbc.dao;

import jdbc.modelo.Reserva;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ReservaDAO {

	final private Connection con;
	
	public ReservaDAO(Connection con) {
		this.con = con;
	}
	
	public void guardar(Reserva reserva) {
		
		try(con){
			final PreparedStatement statement = con.prepareStatement(
			"INSERT INTO reservas(fecha_entrada, fecha_salida, valor, forma_de_pago)"
					+ "VALUES(?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
			try(statement) {
				statement.setObject(1, reserva.getFechaE());
				statement.setObject(2, reserva.getFechaS());
				statement.setString(3, reserva.getValor());
				statement.setString(4, reserva.getFormaPago());
				statement.execute();
				final ResultSet resultSet = statement.getGeneratedKeys();
			
				try(resultSet) {
					while (resultSet.next()) {
						reserva.setId(resultSet.getInt(1));
					}
				}
			} 
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
