package RDM.mermaYDev.CierreDeTurno;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import RDM.util.MermaYDev;
import modelo.BaseExecution;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_004_ValidarMuellesContenedoresVacios extends BaseExecution {
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_004_ValidarMuellesContenedoresVacios_Test(HashMap<String, String> data) throws Exception {
			
		/*
		 * VARIABLES
		 */
		String locationId = data.get("locationId");
		
		/*
		 * UTILERIAS
		 */	
		SeleniumUtil u = new SeleniumUtil(new IETest());
		MermaYDev m = new MermaYDev(u, testCase, data.get("server"));
		
		/*
		 * PASOS DEL CASO DE PRUEBA
		 */			
		// PASO 1
		addStep("Ingresar usuario y contrasena en el sistema RDM. "
				+ "Presionar el boton \"Login\"");
		System.out.println("PASO 1"); 
		
		m.logIn();		
		
		// PASO 2
		addStep("Dar click en la opcion \"Inventory Management\" del menu principal. "
				+ "Dar click en la opcion \"Inventory  Inquiry by Location\" ");
		System.out.println("PASO 2");
		
		m.clickMenuInventoryMngIventoryLocation();
		
		// PASO 3
		addStep("Posicionarse en el campo LOCATION ID e ingresar el  muelle de Recibo Devoluciones a verificar. "
				+ "Presionar el boton \"Palomita o listo\" para desplegar la informacion en pantalla. "); 
		System.out.println("PASO 3");
		
		m.ingresarMuelleDev(locationId);
		
		// PASO 4
		addStep("Presionar el boton \"X\" para cerrar la ventana. "
				+ "Nota: Si el sistema despliega que los muelles de recibo devoluciones no estan vacios, "
				+ "valida que todos los  contenedores se encuentren en Status T."); 
		System.out.println("PASO 4");
		
		boolean critical = m.validarMuelle();
		
		if (!critical) {
			critical = m.validarStatusT();
		}
		
		assertTrue(critical, "El muelle " + locationId + " no esta vacio");
		
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
		return "Construido. Verificar que los muelles de recibo de devoluciones se encuentren vacios."
				+ "Funciona tambien para: MTC-FT-005 Verificar que los contenedores "
				+ "de las localizaciones de Merma y Devoluciones se encuentren en Status T";
	
	}

	@Override
	public String setTestDesigner() {
		return "guerrero.vargas@neoris.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_004_ValidarMuellesContenedoresVacios_Test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

}
