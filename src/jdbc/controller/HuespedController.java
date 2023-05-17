package jdbc.controller;

import java.time.LocalDate;
import java.util.List;

import jdbc.dao.HuespedDAO;
import jdbc.factory.ConnectionFactory;
import jdbc.modelo.Huesped;

public class HuespedController {
	
	private HuespedDAO huespedDAO;

	public HuespedController() {
		this.huespedDAO = new HuespedDAO(new ConnectionFactory().recuperaConexion());
	}
	
	public void guardar(Huesped huesped) {
		this.huespedDAO.guardar(huesped);
	}
	
	public List<Huesped> listar() {
		return this.huespedDAO.listar();
	}
	
	public List<Huesped> listarPorId(String id) {
		return this.huespedDAO.listarPorId(id);
	}
	
	public List<Huesped> listarPorApellido(String apellido) {
		return this.huespedDAO.listarPorApellido(apellido);
	}
	
	public void modificar(String nombre, String apellido, LocalDate fechaNacimiento, String nacionalidad,
			String telefono, Integer idReserva, Integer id) {
		this.huespedDAO.modificar(nombre, apellido, fechaNacimiento, nacionalidad, telefono, idReserva, id);
	}
	
	public void eliminar(Integer id) {
		this.huespedDAO.eliminar(id);
	}
}
