package RDM.articulosDeValor;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import RDM.util.ArticulosValor;
import modelo.BaseExecution;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_002_ValidarMantengaDesactivacionArticulo extends BaseExecution {
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_002_ValidarMantengaDesactivacionArticulo_test(HashMap<String, String> data) throws Exception {
			
		/*
		 * VARIABLES
		 */
		String item = data.get("item");
		String uda10 = data.get("uda10");
		
		/*
		 * UTILERIAS
		 */	
		SeleniumUtil u = new SeleniumUtil(new IETest());
		ArticulosValor a = new ArticulosValor(u, testCase, data.get("server"));
		
		/*
		 * PASOS DEL CASO DE PRUEBA
		 */			
		// PASO 1
		addStep("Entrar al aplicativo de RDMv10 del cedis en transicion "
				+ "-Ingresar a la siguiente URL en el navegador Internet Explorer "
				+ "<URL valida>"
				+ "Ingresar usuario y password");
		System.out.println("PASO 1"); 
		
		a.logIn();		
		
		// PASO 2
		addStep("En la pantalla Retek Distribution Management Menu "
				+ "Ingresar a la opcion Support Functions "
				+ "En el submenu seleccionar la opcion Item Setup "
				+ "En el submenu seleccionar la opcion Item Master Editor o "
				+ "XXFC Item Master Editor");
		System.out.println("PASO 2");
		
		a.supportFunctionsItemSetupMaster();
				
		// PASO 3
		addStep("Ingresar el articulo en el campo: "
				+ "Item_id [ID del Articulo] "
				+ "Dar click en \"Execute\"");
		System.out.println("PASO 3");
		
		a.ingresarArticuloExecute(item);
		
		// PASO 4
		addStep("Desactivar la configuracion del articulo de valor en el Campo Item_master_uda10 con 'N', "
				+ "o dejar el campo en blanco y Dar Click en el boton Save"
				+ " Item_master_uda10 [   Y   ] ");
		System.out.println("PASO 4");
		
		boolean configuroCorrectamente = a.activarConfigArticulo(uda10);
		
		assertTrue(configuroCorrectamente, "Item_master_uda10 no se configuro correctamente");
		
		String uda10Value = a.validarModificacionUda(uda10);
		
		assertEquals(uda10, uda10Value, "No se encontro " + uda10 + " se encontro " + uda10Value);
		
		u.close();
	}

	@Override
	public void beforeTest() {}

	@Override
	public String setPrerequisites() {
		return null;
	}

	@Override
	public String setTestDescription() {
		return "Se valida que se desactive un articulo como articulo de valor en las "
				+ "pantallas del aplicativo de RDM mediante la configuracion del campo "
				+ "Item_master_uda10 = 'N' o espacio vacio";	
	}

	@Override
	public String setTestDesigner() {
		return "guerrero.vargas@neoris.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_002_ValidarMantengaDesactivacionArticulo_test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

}
