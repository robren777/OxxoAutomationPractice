package WM.EvolucionRetiroSinTarjeta.migraciones.om.tablas;

import java.util.List;

public class Tabla {

	private String id;
	private List<String> columnas;
	private List<String[]> filas;
	
	public Tabla() {
	}
	
	public Tabla(String id, List<String> columnas, List<String[]> filas) {
		this.id = id;
		this.columnas = columnas;
		this.filas = filas;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<String> getColumnas() {
		return columnas;
	}

	public void setColumnas(List<String> columnas) {
		this.columnas = columnas;
	}

	public List<String[]> getFilas() {
		return filas;
	}

	public void setFilas(List<String[]> filas) {
		this.filas = filas;
	}
	
	
	
}
