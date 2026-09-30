package EBS.tools;

import EBS.util.pageObjectModel.EBSCrearComisiones;
import EBS.util.pageObjectModel.EBSCrearCuentas;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class CrearComisionesTool {
	/**
	 * Herramienta para llenar campos de busqueda dentro de "Crear Comisiones"
	 *
	 * @author edwin.ramirez@neoris.com
	 * @version 1.0
	 * @see EBSCrearCuentas
	 * @since 13/6/2022
	 */
	SeleniumUtil u;
	TestCase testCase;
	EBSCrearComisiones crearComisiones;

	public CrearComisionesTool(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		crearComisiones = new EBSCrearComisiones(u, testCase);
	}

	/**
	 * Herramienta para llenar los campos obligatorios de "Crear Comisiones"
	 *
	 * @param formaPago
	 * @param comision
	 * @param fechaDesde
	 * @param fechaHasta
	 */
	public void camposObligatoriosCrearComisiones(String formaPago, String comision, String fechaDesde,
			String fechaHasta) {
		u.waitForLoadPage();
		crearComisiones.formaPagoElement.sendKeys(formaPago);
		crearComisiones.selectMetodoPago();
		crearComisiones.comisionElement.sendKeys(comision);
		crearComisiones.fechaDesdeElement.sendKeys(fechaDesde);
		crearComisiones.fechaHastaElement.sendKeys(fechaHasta);
		u.hardWait(2);
	}
}
