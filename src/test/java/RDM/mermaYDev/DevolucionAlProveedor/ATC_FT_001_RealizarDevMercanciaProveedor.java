package RDM.mermaYDev.DevolucionAlProveedor;

import static org.testng.Assert.assertFalse;

import java.util.HashMap;

import org.testng.annotations.Test;

import RDM.util.MermaYDev;
import modelo.BaseExecution;
import RDM.util.BaseRDM;
import RDM.util.GlobalVariables;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLResult;
import utils.sql.SQLUtil;

public class ATC_FT_001_RealizarDevMercanciaProveedor extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_001_RealizarDevMercanciaProveedor_test(HashMap<String, String> data) throws Exception {

		/*
		 * VARIABLES
		 */
		String containerId = data.get("containerId");
		String auth = data.get("auth");
		String month = data.get("month");
		String storagePallets = data.get("storagePallets");
		String destructionPallets = data.get("destructionPallets");
		
		/*
		 * UTILERIAS
		 */
		SQLUtil dbRdm = new SQLUtil(GlobalVariables.DB_HOST_RDM_RDMVIEW, GlobalVariables.DB_USER_RDM_RDMVIEW ,
				GlobalVariables.DB_PASSWORD_RDM_RDMVIEW);
		SeleniumUtil u = new SeleniumUtil(new IETest());
		MermaYDev m = new MermaYDev(u, testCase, data.get("server"));
		BaseRDM b = new BaseRDM(u, testCase, data.get("server"));
		/*
		 * PASOS DEL CASO DE PRUEBA
		 */
		
		System.out.println(dbRdm.getHost());
		
		// PASO 1
		addStep("Ingresar a la BD de RDM y realizar la consulta para obtener contenedores "
				+ "con ESTATUS = T");
		System.out.println("PASO 1");
		
		/*String contenedorStatusT = "SELECT CONTAINER_ID, ITEM_ID, CONTAINER_QTY, UNIT_QTY "
		+ "FROM CONTAINER_ITEM WHERE CONTAINER_ID IN "
		+ "(SELECT CONTAINER_ID FROM CONTAINER WHERE CONTAINER_STATUS = 'T') "
		+ "AND ROWNUM = 1";*/
        String contenedorStatusT = "SELECT CONTAINER_ID, ITEM_ID, CONTAINER_QTY, UNIT_QTY "
		+ "FROM CONTAINER_ITEM WHERE CONTAINER_ID IN "
		+ "(SELECT CONTAINER_ID FROM CONTAINER WHERE CONTAINER_STATUS = 'T') "
		+ "AND CONTAINER_ID = '"+containerId+"'";
		System.out.println(contenedorStatusT);

		SQLResult executeQuery = executeQuery(dbRdm, contenedorStatusT);
		//String containerId = "";
		String itemId = "";
		
		boolean statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			containerId = executeQuery.getData(0, "CONTAINER_ID");
			itemId = executeQuery.getData(0, "ITEM_ID");
			testCase.addQueryEvidenceCurrentStep(executeQuery);		
			testCase.addTextEvidenceCurrentStep("CONTAINER_ID = " + containerId);
			testCase.addTextEvidenceCurrentStep("ITEM_ID = " + itemId);
		}

		assertFalse(statusQuery, "No se encontraron contendedores con STATUS = T");
		
		// PASO 2
		addStep("Realizar la consulta para obtener el proveedor");
		System.out.println("PASO 2");
		
		String itemMaster = String.format("SELECT ITEM_ID, DESCRIPTION, VENDOR_NBR "
				+ "FROM ITEM_MASTER WHERE ITEM_ID IN ('%s')", itemId);
		System.out.println(itemMaster);

		executeQuery = executeQuery(dbRdm, itemMaster);
		String vendorNbr = "";
		
		statusQuery = executeQuery.isEmpty();
		if (!statusQuery) {
			vendorNbr = executeQuery.getData(0, "VENDOR_NBR");
			testCase.addQueryEvidenceCurrentStep(executeQuery);		
			testCase.addTextEvidenceCurrentStep("VENDOR_NBR = " + vendorNbr);
		}

		assertFalse(statusQuery, "No se encontraron registros en ITEM_MASTER con ITEM_ID = " + itemId);
		
		// PASO 3
		addStep("Ingresar usuario y contrasena en el sistema RDM y presionar el boton \"Login\"");
		System.out.println("PASO 3");
		
		m.logIn();
		
		// PASO 4
		addStep("Dar click en la opcion \"Inventory Management\" del menu principal. "
				+ "Dar click en la opcion \"Return Vendor\"");
		System.out.println("PASO 4");
		
		m.clickMenuInventoryMngReturnVendor(); 
		
		// PASO 5
		addStep("Posicionarse en el campo \"CONTAINER ID\" e ingresar el numero del contenedor. "
				+ "NOTA:El contenedor debe tener solo articulos del mismo proveedor. "
				+ "Presionar en el boton Ejecutar. ");
		System.out.println("PASO 5");
		
		m.llenarInfoReturnToVendor(containerId, vendorNbr, auth, month, storagePallets, 
				destructionPallets);
		
		// NO SE CUENTA CON INFORMACION DEL MES - PROVEEDOR - TARIMA
		
		// PASO 6
		addStep("Elegir la pestana Destination. "
				+ "Presionar el boton \"Save\" para guardar la informacion.");
		System.out.println("PASO 6");
		
		m.descargarXxfcRtvAdvice();
		
		//assertEquals(b.getTextDelNavegador(vendorNbr), vendorNbr, "No se encontro el numero del proveedor");
		String rtvId =  m.getRtvId("RTV");
		 
		//String rtvId = values.get(3);
		
		// TODO
		
		// PASO 7
		addStep("Realizar la consulta para obtener la devolucion creada");
		System.out.println("PASO 7");
		
		//String rtv = "SELECT RTV_ID, RTN_AUTH_NBR, VENDOR_NBR, CONTAINER_QTY, CREATION_TS FROM RTV WHERE RTN_AUTH_NBR = '"+auth+"' and RTV_ID = '"+rtvId+"'";
		String rtv = "SELECT RTV_ID, RTN_AUTH_NBR, VENDOR_NBR, CONTAINER_QTY, CREATION_TS FROM RTV WHERE RTN_AUTH_NBR = '"+auth+"' AND VENDOR_NBR = '"+vendorNbr+"' ORDER BY CREATION_TS DESC";
		System.out.println(rtv);

		executeQuery = executeQuery(dbRdm, rtv);		
		statusQuery = executeQuery.isEmpty();
		testCase.addQueryEvidenceCurrentStep(executeQuery);		

		assertFalse(statusQuery, "No se encontraron registros en RTV con RTN_AUTH_NBR = " + auth+" y RTV_ID = "+rtvId);
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
		return "Construido. Mediante el sistema RDM se debe realizar "
				+ "una devolucion de mercancia al Proveedor";
	}

	@Override
	public String setTestDesigner() {
		return "guerrero.vargas@neoris.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_001_RealizarDevMercanciaProveedor_Test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

}
