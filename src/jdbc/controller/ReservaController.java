package jdbc.controller;

import java.time.LocalDate;
import java.util.List;

import jdbc.dao.ReservaDAO;
import jdbc.factory.ConnectionFactory;
import jdbc.modelo.Reserva;

public class ReservaController {
	
	private ReservaDAO reservaDAO;
	
	public ReservaController() {
		this.reservaDAO = new ReservaDAO(new ConnectionFactory().recuperaConexion());
	}
	
	public void guardar(Reserva reserva) {
		this.reservaDAO.guardar(reserva);
	}
	
	public List<Reserva> listar() {
		return this.reservaDAO.listar();
	}
	
	public List<Reserva> listarPorId(String id) {
		return this.reservaDAO.listarPorId(id);
	}
	
	public void modificar(LocalDate fechaE, LocalDate fechaS, String valor, String formaDePago, Integer id) {
		this.reservaDAO.modificar(fechaE, fechaS, valor, formaDePago, id);
	}
	
	public void eliminar(Integer id) {
		this.reservaDAO.eliminar(id);
	}
}
