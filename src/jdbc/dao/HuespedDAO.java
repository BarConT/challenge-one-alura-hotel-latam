package jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jdbc.factory.ConnectionFactory;
import jdbc.modelo.Huesped;

public class HuespedDAO {

final private Connection con;
	
	public HuespedDAO(Connection con) {
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

	public List<Huesped> listar() {
		List<Huesped> listaHuespedes = new ArrayList<>();
		ConnectionFactory factory = new ConnectionFactory();
		final Connection con = factory.recuperaConexion();
		
		try(con) {
			String query = "SELECT id, nombre, apellido, fecha_nac, nacionalidad, telefono, id_reserva FROM huespedes";
			final PreparedStatement statement = con
					.prepareStatement(query);
			try(statement) {
				statement.execute();
				
				final ResultSet resultSet = statement.getResultSet();
				transformarResultado(listaHuespedes, resultSet);
			}
			return listaHuespedes;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public List<Huesped> listarPorId(String id) {
		List<Huesped> listaHuespedes = new ArrayList<>();
		ConnectionFactory factory = new ConnectionFactory();
		final Connection con = factory.recuperaConexion();
		
		try(con) {
			String query = "SELECT id, nombre, apellido, fecha_nac, nacionalidad, telefono, id_reserva FROM huespedes WHERE id=?";
			final PreparedStatement statement = con
					.prepareStatement(query);
			try(statement) {
				statement.setString(1, id);
				statement.execute();
				
				final ResultSet resultSet = statement.getResultSet();
				transformarResultado(listaHuespedes, resultSet);
			}
			return listaHuespedes;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public List<Huesped> listarPorApellido(String apellido) {
		List<Huesped> listaHuespedes = new ArrayList<>();
		ConnectionFactory factory = new ConnectionFactory();
		final Connection con = factory.recuperaConexion();
		
		try(con) {
			String query = "SELECT id, nombre, apellido, fecha_nac, nacionalidad, telefono, id_reserva FROM huespedes WHERE apellido LIKE ?";
			final PreparedStatement statement = con
					.prepareStatement(query);
			try(statement) {
				statement.setString(1, "%" + apellido + "%");
				statement.execute();
				
				final ResultSet resultSet = statement.getResultSet();
				transformarResultado(listaHuespedes, resultSet);
			}
			return listaHuespedes;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	
	public void modificar(String nombre, String apellido, LocalDate fechaNacimiento, String nacionalidad,
			String telefono, Integer idReserva, Integer id) {
		ConnectionFactory factory = new ConnectionFactory();
		final Connection con = factory.recuperaConexion();
		try(con) {
			final PreparedStatement statement = con.prepareStatement("UPDATE huespedes SET "
				    + " nombre = ? "
				    + ", apellido = ? "
				    + ", fecha_nac = ? "
				    + ", nacionalidad = ? "
				    + ", telefono = ?"
				    + ", id_reserva = ?"
				    + " WHERE ID = ? ");
			try (statement) {
				statement.setString(1, nombre);
		    	statement.setString(2, apellido);
		    	statement.setObject(3, fechaNacimiento);
		    	statement.setString(4, nacionalidad);
		    	statement.setString(5, telefono);
		    	statement.setInt(6, idReserva);
		    	statement.setInt(7, id);
				statement.execute();
			}	
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void eliminar(Integer id) {
		ConnectionFactory factory = new ConnectionFactory();
		final Connection con = factory.recuperaConexion();
		try(con) {
			final PreparedStatement statement = con.prepareStatement("DELETE FROM huespedes WHERE id=?");
			try(statement) {
				statement.setInt(1, id);
				statement.execute();
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	private void transformarResultado(List<Huesped> listaHuespedes, ResultSet resultSet) throws SQLException {
		try(resultSet) {
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String nombre = resultSet.getString("nombre");
				String apellido = resultSet.getString("apellido");
				LocalDate fechaNac = resultSet.getDate("fecha_nac").toLocalDate();
				String nacionalidad = resultSet.getString("nacionalidad");
				String telefono = resultSet.getString("telefono");
				int idReserva = resultSet.getInt("id_reserva");
				Huesped fila = new Huesped(id, nombre, apellido, fechaNac, nacionalidad, telefono, idReserva);
				listaHuespedes.add(fila);
			}
		}
	}
}
