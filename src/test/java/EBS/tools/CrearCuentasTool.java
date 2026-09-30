package EBS.tools;

import EBS.util.pageObjectModel.EBSCrearCuentas;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class CrearCuentasTool {
	/**
	 * Herramienta para llenar campos de busqueda dentro de "Crear Cuentas"
	 *
	 * @author edwin.ramirez@neoris.com
	 * @version 1.0
	 * @see EBSCrearCuentas
	 * @since 13/6/2022
	 */
	SeleniumUtil u;
	TestCase testCase;
	EBSCrearCuentas crearCuentas;

	public CrearCuentasTool(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		crearCuentas = new EBSCrearCuentas(u, testCase);
	}

	/**
	 * Herramienta para llenar los campos obligatorios de "Crear Cuenta"
	 *
	 * @param formaPago  String con el valor a introducir en el campo "Forma Pago"
	 * @param cuenta     String con el valor a introducir en el campo "Cuenta"
	 * @param auxiliar   String con el valor a introducir en el campo "Auxiliar"
	 * @param nombre     String con el valor a introducir en el campo "Nombre"
	 * @param fechaDesde String con el valor a introducir en el campo "Fecha Desde
	 * @param fechaHasta String con el valor a introducir en el campo "Hasta"
	 * @author edwin.ramirez@neoris.com
	 * @version 1.0
	 */
	public void camposObligatoriosCrearCuentas(String formaPago, String cuenta, String auxiliar, String nombre,
			String fechaDesde, String fechaHasta) {
		u.waitForLoadPage();
		crearCuentas.formaPagoElement.sendKeys(formaPago);
		crearCuentas.cuentaElement.sendKeys(cuenta);
		crearCuentas.auxiliarElement.sendKeys(auxiliar);
		crearCuentas.nombreElement.sendKeys(nombre);
		crearCuentas.fechaDesdeElement.sendKeys(fechaDesde);
		crearCuentas.fechaHastaElement.sendKeys(fechaHasta);
		crearCuentas.aplicarElement.click();
		u.hardWait(2);
	}
}
