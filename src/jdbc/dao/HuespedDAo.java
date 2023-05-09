package jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import jdbc.modelo.Huesped;

public class HuespedDAo {

final private Connection con;
	
	public HuespedDAo(Connection con) {
		this.con = con;
	}
	
	public void guardar(Huesped huesped) {
		
		try(con){
			final PreparedStatement statement = con.prepareStatement(
			"INSERT INTO huespedes(nombre, apellido, fecha_nac, nacionalidad, telefono, id_reserva)"
					+ "VALUES(?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
			try(statement) {
				statement.setString(1, huesped.getNombre());
				statement.setString(2, huesped.getApellido());
				statement.setObject(3, huesped.getFechaNacimiento());
				statement.setString(4, huesped.getNacionalidad());
				statement.setString(5, huesped.getTelefono());
				statement.setInt(6, huesped.getIdReserva());
				statement.execute();
				final ResultSet resultSet = statement.getGeneratedKeys();
				
				try(resultSet) {
					while (resultSet.next()) {
						huesped.setId(resultSet.getInt(1));
					}
				}
			} 
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
