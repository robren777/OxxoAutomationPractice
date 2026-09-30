package RDM.mermaYDev.DevolucionAlProveedor;

import java.util.HashMap;

import org.testng.annotations.Test;

import RDM.util.MermaYDev;
import modelo.BaseExecution; import RDM.util.GlobalVariables;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLUtil;

public class ATC_FT_003_ValidarReturnToVendorAdvice extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_003_ValidarReturnToVendorAdvice_Test(HashMap<String, String> data) throws Exception {

		/*
		 * VARIABLES
		 */
		String containerId = data.get("containerId");
		String vendorNbr = data.get("vendorNbr");
		String auth = data.get("auth");
		String month = data.get("month");
		String storagePallets = data.get("storagePallets");
		String destructionPallets = data.get("destructionPallets");
		
		/*
		 * UTILERIAS
		 */
		SQLUtil dbRdm = new SQLUtil(GlobalVariables.DB_HOST_RDM, GlobalVariables.DB_USER_RDM,
				GlobalVariables.DB_PASSWORD_RDM);
		SeleniumUtil u = new SeleniumUtil(new IETest());
		MermaYDev m = new MermaYDev(u, testCase, data.get("server"));

		/*
		 * PASOS DEL CASO DE PRUEBA
		 */
		
		System.out.println(dbRdm.getHost());
		
		// PASO 1
		addStep("Ingresar usuario y contrasena en el sistema RDM y presionar el boton \"Login\"");
		System.out.println("PASO 1");
		
		m.logIn();
		
		// PASO 2
		addStep("Dar click en la opcion \"Inventory Management\" del menu principal. "
				+ "Dar click en la opcion \"Return Vendor\"");
		System.out.println("PASO 2");
		
		m.clickMenuInventoryMngReturnVendor();
		
		// PASO 3
		addStep("Posicionarse en el campo \"CONTAINER ID\" e ingresar el numero del contenedor. "
				+ "Presionar en el boton \"Ejecutar\". "
				+ "Ingresar dato en el campo Vendor NBR."
				+ "En el campo AUTHORIZATION, ingresar un numero con la nomenclatura dia, mes, anio."
				+ "En el campo MONTH ingresar DVP(MES)."
				+ "Seleccionar el checkbox del campo: STORAGE PALLETS e ingresar el numero 1."
				+ "Seleccionar el checkbox del campo: DESTRUCTION PALLETS e ingresar el numero 0."
				+ "Dar click en el boton RTV."
				+ "Dar click en el boton YES.");
		System.out.println("PASO 3");
		
		m.llenarInfoReturnToVendor(containerId, vendorNbr, auth, month, storagePallets, 
				destructionPallets);
		
		// NO SE CUENTA CON INFORMACION DEL MES - PROVEEDOR - TARIMA
		
		// PASO 4
		addStep("Validar que el campo DESTYPE se muestre la opcion FILE y en el campo "
				+ "DESFORMAT: Adobe Reader PDF y Dar click en Save");
		System.out.println("PASO 4");
		
		// TODO
				
	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setPrerequisites() {
		return null;
	}

	@Override
	public String setTestDescription() {
		return "Construido. Mediante el sistema RDM se debe validar la reimpresion del reporte de "
				+ "\"RETURN TO VENDOR ADVICE\".";
	}

	@Override
	public String setTestDesigner() {
		return "guerrero.vargas@neoris.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_003_ValidarReturnToVendorAdvice_Test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

}
