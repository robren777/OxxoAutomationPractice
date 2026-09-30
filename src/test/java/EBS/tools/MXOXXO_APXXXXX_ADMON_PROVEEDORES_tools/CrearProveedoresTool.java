package EBS.tools.MXOXXO_APXXXXX_ADMON_PROVEEDORES_tools;

import java.util.Random;

import EBS.util.pageObjectModel.MXOXXO_APXXXXX_ADMON_PROVEEDORES_POM.CrearProveedor;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class CrearProveedoresTool {
	/**
	 * Herramienta para llenar campos de proveedores dentro de "Crear Proveedores"
	 *
	 * @author edwin.ramirez@neoris.com
	 * @version 1.0
	 * @see CrearProveedor
	 * @since 5/26/2022
	 */
	SeleniumUtil u;
	TestCase testCase;
	CrearProveedor crearProveedor;

	/**
	 * Constructor de la clase
	 *
	 * @param u        Instancia de SeleniumUtil
	 * @param testCase Instancia de TestCase
	 */
	public CrearProveedoresTool(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		crearProveedor = new CrearProveedor(u, testCase);
	}

	/**
	 * Llena los Campos Obligatorios, con los valores de parametros
	 *
	 * @param nombreDeOrganizacion texto con el Nombre de Organizacion
	 * @param tipoPersona          texto con el tipo de Persona
	 * @param numDeProveedor       texto con el Numero de Proveedor
	 */
	public void llenarCamposObligatorios(String nombreDeOrganizacion, String tipoPersona, String numDeProveedor) {
		Random rand = new Random();// temporal
		int n = rand.nextInt(1000);
		crearProveedor.sendNombreOrganizacion(nombreDeOrganizacion + Integer.toString(n));
		crearProveedor.sendPersona(tipoPersona);
		crearProveedor.sendNumProveedore(numDeProveedor + Integer.toString(n));
	}

}
