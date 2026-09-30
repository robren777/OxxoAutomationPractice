package Mobile.preinventariosinteligentes.om;

public class DetallePlantilla {

	/*
	 * Variables Globales
	 ******************************************************************************************/
	private String nombrePlantilla;

	/**
	 * Constructor de la clase DetallePlantilla
	 * @author Marcos Morales
	 * @param nombrePlantilla Nombre de la plantilla
	 */
	public DetallePlantilla(String nombrePlantilla) {
		this.nombrePlantilla = nombrePlantilla;
	}

	/**
	 * Devuelve el nombre de la plantilla
	 * @author Marcos Morales
	 */
	public String getPlantilla() {
		return this.nombrePlantilla;
	}

	/**
	 * Establece el nombre de la plantilla
	 * @author Marcos Morales
	 */
	public void setPlantilla(String nombrePlantilla) {
		this.nombrePlantilla = nombrePlantilla;
	}
}