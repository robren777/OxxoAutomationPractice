package RDM.util;

import org.sikuli.hotkey.Keys;
import org.sikuli.script.FindFailed;

import modelo.TestCase;
import utils.selenium.SeleniumUtil;

public class Nucleo extends BaseRDM {
	public Nucleo(SeleniumUtil u, TestCase testCase, String url) {
		super(u, testCase, url);
	}

	/**
	 * En el menu da click en Distribution Planning XXFC Programacion y por ruta
	 * 
	 * @author ricardo.palacios
	 */
	public void clickDistributionPlanningBalanceoPorRuta() {
		try {
			sk.screen.wait(imgPattern.get("distributionPlanning")).click();
			wait(2);
			sk.screen.wait(imgPattern.get("XxfcProgramacionBalanceo")).click();
			wait(2);
			sk.screen.wait(imgPattern.get("ProgramacionPporRuta")).click();
			wait(2);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Ingresar datos en las opciones fecha inicial, fecha final
	 * 
	 * @author ricardo.palacios
	 */
	public void ingresarParametrosFechas(String fechaInicial,String fechaFinal) {

		try {

			sk.screen.wait(imgPattern.get("fechaInicialPd")).offset(112, 0).click();
			wait(3);
			this.nBackspace(20);
			sk.screen.type(fechaInicial);
			wait(3);
			sk.screen.wait(imgPattern.get("fechaFinalPd")).offset(112, 0).click();
			wait(3);
			this.nBackspace(20);
			sk.screen.type(fechaFinal);
			wait(3);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * En el menu da click check
	 * 
	 * @author ricardo.palacios
	 */
	public void clickCheck() {
		try {
			sk.screen.wait(imgPattern.get("checkPlanificarDistribucion")).click();
			wait(2);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Click en Distribute
	 * 
	 * @author ricardo.palacios
	 */
	public void clickDistribute() {
		try {
			sk.screen.wait(imgPattern.get("distribute")).click();
			wait(2);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * En el menu da click en Support Functions > DC Setup > Location Setup > Forward Picking Location Editor
	 * 
	 * @author ricardo.palacios
	 */
	public void clickMenuSupportForwardPickingLocationEditor() {
		try {
			sk.screen.wait(imgPattern.get("supportFunctions")).click();
			wait(2);
			sk.screen.wait(imgPattern.get("DCSetupNucleo")).click();
			wait(2);
			sk.screen.wait(imgPattern.get("LocationSetupNucleo")).click();
			wait(2);
			sk.screen.wait(imgPattern.get("ForwardPickingLocationEditorNucleo")).click();
			wait(2);
			screenshot();
			
			wait(2);
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Ingresa un texto en la celda donde esta el puntero
	 * 
	 * @param locationId
	 * @author ricardo.palacios
	 */
	public void ingresarLocationId(String locationId) {
		try {
			
			wait(2);
			sk.screen.type(locationId);
			wait(2);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * En el menu da click en Returns > Reports > XXFC Tarimas Devolucion a Proveedor
	 * 
	 * @author guerrero.vargas
	 */
	public void returnsReportsTarimas() throws FindFailed {
		sk.screen.wait(imgPattern.get("returns")).click();
		sk.screen.wait(imgPattern.get("menuReports")).click();
		sk.screen.hover(imgPattern.get("xxfcTarimasDevolucion"));
		screenshot();
		sk.screen.click();
			
		wait(2);
	}
	
	/**
	 * En el menu da click en Inventory Managment > Return to vendor
	 * 
	 * @author guerrero.vargas
	 */
	public void clickInventoryReturn() throws FindFailed {
		sk.screen.wait(imgPattern.get("inventoryManagement")).click();
		sk.screen.hover(imgPattern.get("returnToVendor"));
		screenshot();
		sk.screen.click();
			
		wait(2);
	}
	
	/**
	 * Busca proveedor en la pantalla del aplicativo
	 * 
	 * @param numeroProveedor id de proveedor a buscar
	 * @author guerrero.vargas
	 */
	public void buscarProveedor(String numeroProveedor) {
		wait(1);
		screenshot();
		sk.screen.type(numeroProveedor);
		sk.screen.type(Keys.TAB);
	}
	
	/**
	 * Coloca el id del contenedor en la pantalla del aplicativo
	 * 
	 * @param idContenedor numero de contenedor que se va a colocar
	 * @author guerrero.vargas
	 */
	public void capturarContenedor(String idContenedor) {
		wait(1);
		screenshot();
		
		sk.screen.type(idContenedor);
		sk.screen.type(Keys.TAB);
		wait(1);
		screenshot();	
	}
	
	/**
	 * Obtiene el numero de vendor del aplicativo
	 * 
	 * @return String con el numero de vendor
	 * @author guerrero.vargas
	 */
	public String obtenerVendor() {
		numLockOff();
		sk.screen.type(Keys.HOME);
		sk.screen.type(Keys.RIGHT, Keys.SHIFT);
		
		wait(2);
		
		return copyPaste();
	}
 
}
