package EBS.tools;

import EBS.util.pageObjectModel.EBSPlantillaDeProveedores;
import modelo.TestCase;
import utils.selenium.SeleniumUtil;

/**
 * Herramienta para llenar campos de busqueda dentro de Plantilla de
 * Proveedores: Plantillas
 *
 * @author edwin.ramirez@neoris.com
 * @version 1.0
 * @see EBSPlantillaDeProveedores
 * @since 5/23/2022
 */
public class PlantillaProveedoresBusqueda {
	SeleniumUtil u;
	TestCase testCase;
	EBSPlantillaDeProveedores planProv;

	public PlantillaProveedoresBusqueda(SeleniumUtil u, TestCase testCase) {
		super();
		this.u = u;
		this.testCase = testCase;
		planProv = new EBSPlantillaDeProveedores(u, testCase);
	}

	/**
	 * Metodo para seleccionar Tipo
	 *
	 * @param tipo String con el tipo(visibleText) a elegir
	 * @author edwin.ramirez@neoris.com
	 * @since 5/23/2022
	 */
	public void selectTipo(String tipo) {
		planProv.selectTipo(tipo);
	}

	/**
	 * Metodo para seleccionar Nivel
	 *
	 * @param nivel String con el nivel(Proveedor o Sucursal) a elegir
	 * @author edwin.ramirez@neoris.com
	 * @since 5/23/2022
	 */
	public void selectNivel(String nivel) {
		planProv.selectNivel(nivel);
	}

	public void realizarBusqueda() {
		planProv.clickIr();
		testCase.addScreenShotCurrentStep(u, "Busqueda Proveedores");
	}

	public void selectTasa(String tasaOpt) {
		planProv.selectTipoTasa(tasaOpt);
	}

	public void selectPersona(String personaOpt) {
		planProv.selectTipoPersona(personaOpt);
	}

	public Boolean verifyColumnTittle(String column) {
		String actualColumn;
		actualColumn = planProv.getColumnTitle(column);
		return actualColumn.equalsIgnoreCase(column);
	}

	public Boolean verifyFilterLabel(String filtro) {
		String actualLabel;
		actualLabel = planProv.getFilterLabelText(filtro);
		return actualLabel.equalsIgnoreCase(filtro);
	}

}
