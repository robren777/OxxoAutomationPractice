package Mobile.preinventariosinteligentes.om;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModalConteos {

	/*
	 * Variables Globales
	 ******************************************************************************************/
	private static List<Map<String, Object>> modalConteos = new ArrayList<>();
	private String sku;
	private String teorico;
	private String fisico;

	/**
	 * Constructor de la clase ModalConteos
	 * @author Marcos Morales
	 * @param sku Codigo sku del producto
	 * @param teorico Valor teorico del sku
	 * @param fisico Valor capturado como conteo
	 */
	public ModalConteos(String sku, String teorico, String fisico, boolean debeGuardar) {
		this.sku = sku;
		this.teorico = teorico;
		this.fisico = fisico;

		if (debeGuardar) {
			guardarConteo();
		}
	}

	/**
	 * Devuelve el SKU
	 * @author Marcos Morales
	 */
	public String getSKU() {
		return this.sku;
	}

	/**
	 * Devuelve el valor de Teorico
	 * @author Marcos Morales
	 */
	public String getTeorico() {
		return this.teorico;
	}

	/**
	 * Devuelve el valor de Fisico
	 * @author Marcos Morales
	 */
	public String getFisico() {
		return this.fisico;
	}

	/**
	 * Establece el valor para un SKU
	 * @author Marcos Morales
	 */
	public void setSKU(String sku) {
		this.sku = sku;
	}

	/**
	 * Establece el valor de Teorico
	 * @author Marcos Morales
	 */
	public void setTeorico(String teorico) {
		this.teorico = teorico;
	}

	/**
	 * Establece el valor de Fisico
	 * @author Marcos Morales
	 */
	public void setFisico(String fisico) {
		this.fisico = fisico;
	}

	/**
	 * Guarda en un ArrayList los valores de SKU, Teorico y Fisico
	 * cada vez que se instancia la clase
	 * @author Marcos Morales
	 */
	private void guardarConteo() {
		Map<String, Object> fila = new HashMap<>();
		fila.put("sku", this.sku);
		fila.put("teorico", this.teorico);
		fila.put("fisico", this.fisico);
		modalConteos.add(fila);
	}

	/**
	 * Metodo para obtener el ArrayList con los valores de SKU, Teorico y Fisico
	 * de todos los registros que se han almacenado
	 * @author Marcos Morales
	 */
	public static List<Map<String, Object>> getConteos() {
		return new ArrayList<>(modalConteos);
	}
}