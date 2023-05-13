package jdbc.dao;

import jdbc.modelo.Reserva;
import jdbc.factory.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

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
	
	public List<Reserva> listar() {
		List<Reserva> listaReservas = new ArrayList<>();
		ConnectionFactory factory = new ConnectionFactory();
		final Connection con = factory.recuperaConexion();
		
		try(con) {
			String query = "SELECT id, fecha_entrada, fecha_salida, valor, forma_de_pago FROM reservas";
			final PreparedStatement statement = con
					.prepareStatement(query);
			try(statement) {
				statement.execute();
				
				final ResultSet resultSet = statement.getResultSet();
				transformarResultado(listaReservas, resultSet);
			}
			return listaReservas;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public List<Reserva> listarPorId(String id) {
		List<Reserva> listaReservas = new ArrayList<>();
		ConnectionFactory factory = new ConnectionFactory();
		final Connection con = factory.recuperaConexion();
		
		try(con) {
			String query = "SELECT id, fecha_entrada, fecha_salida, valor, forma_de_pago FROM reservas WHERE id=?";
			final PreparedStatement statement = con
					.prepareStatement(query);
			try(statement) {
				statement.setString(1, id);
				statement.execute();
				
				final ResultSet resultSet = statement.getResultSet();
				transformarResultado(listaReservas, resultSet);
			}
			return listaReservas;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public void modificar(LocalDate fechaE, LocalDate fechaS, String valor, String formaDePago, Integer id) {
		ConnectionFactory factory = new ConnectionFactory();
		final Connection con = factory.recuperaConexion();
		try(con) {
			final PreparedStatement statement = con.prepareStatement("UPDATE reservas SET "
				    + " fecha_entrada = ? "
				    + ", fecha_salida = ? "
				    + ", valor = ? "
				    + ", forma_de_pago = ? "
				    + " WHERE ID = ? ");
			try (statement) {
				statement.setObject(1, fechaE);
		    	statement.setObject(2, fechaS);
		    	statement.setString(3, valor);
		    	statement.setString(4, formaDePago);
		    	statement.setInt(5, id);
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
			final PreparedStatement statement = con.prepareStatement("DELETE FROM reservas WHERE id=?");
			try(statement) {
				statement.setInt(1, id);
				statement.execute();
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	private void transformarResultado(List<Reserva> listaReservas, ResultSet resultSet) throws SQLException {
		try(resultSet) {
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				LocalDate fechaEntrada = resultSet.getDate("fecha_entrada").toLocalDate();
				LocalDate fechaSalida = resultSet.getDate("fecha_salida").toLocalDate();
				String valor = resultSet.getString("valor");
				String formaDePago = resultSet.getString("forma_de_pago");
				Reserva fila = new Reserva(id, fechaEntrada, fechaSalida, valor, formaDePago);
				listaReservas.add(fila);
			}
		}
	}
}
