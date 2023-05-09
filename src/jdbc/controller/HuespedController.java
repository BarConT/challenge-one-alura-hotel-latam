package jdbc.controller;

import jdbc.dao.HuespedDAo;
import jdbc.factory.ConnectionFactory;
import jdbc.modelo.Huesped;

public class HuespedController {
	
	private HuespedDAo huespedDAO;

	public HuespedController() {
		this.huespedDAO = new HuespedDAo(new ConnectionFactory().recuperaConexion());
	}
	
	public void guardar(Huesped huesped) {
		this.huespedDAO.guardar(huesped);
	}
}
