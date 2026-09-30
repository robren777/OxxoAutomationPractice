package RDM.mermaYDev.ReciboDeDev;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import RDM.util.MermaYDev;
import modelo.BaseExecution;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_001_ReciboMercanciaDevReceive extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_001_ReciboMercanciaDevReceive_Test(HashMap<String, String> data) throws Exception {

		/*
		 * VARIABLES
		 */
		String noCita = data.get("noCita");
		String noAsn = data.get("noAsn");
		String noItem = data.get("noItem");
		String unidadesRcvd = data.get("unidadesRcvd");
		String containerId = data.get("containerId");
		String opcion = data.get("opcion");
		String unrcCode = data.get("unrcCode");

		/*
		 * UTILERIAS
		 */
		/*SQLUtil dbRdm = new SQLUtil(GlobalVariables.DB_HOST_RDM, GlobalVariables.DB_USER_RDM,
				GlobalVariables.DB_PASSWORD_RDM);*/
		SeleniumUtil u = new SeleniumUtil(new IETest());
		MermaYDev m = new MermaYDev(u, testCase, data.get("server"));

		/*
		 * PASOS DEL CASO DE PRUEBA
		 */
			
		//System.out.println(dbRdm.getHost());
		
		// PASO 1
		addStep("Ingresar usuario y contrasena en el sistema RDM y Presionar el boton \"Login\"");
		System.out.println("PASO 1");
		
		m.logIn();
		
		// PASO 2
		addStep("En el menu principal de RDM ingresar a la opcion \"Receiving\" "
				+ "Ingresar a la opcion XXFC ASN / NSC Receiving "
				+ "Nota: Ademas se debe verificar y validar que la mercancia cumpla "
				+ "con los requisitos de las devoluciones.");
		System.out.println("PASO 2");
		
		u.hardWait(10);
		m.clickMenuReceivingXxfcAsnNscReceiving();
		
		// PASO 3
		addStep("Ingresar en el campo \"RCVD\" la cantidad de unidades recibidas."
				+ "En el campo \"UNRC CODE\" elegir la razon por la cual "
				+ "no se acredito la mercancia en la tienda. "
				+ "Presionar la opcion \"Receive\"");
		System.out.println("PASO 3");
		
		String[] info = m.ingresarInfo(noCita, noAsn, noItem, unidadesRcvd, opcion, unrcCode);
		
		assertNotNull(info, "La cantidad a recibir no es valida");
		
		// PASO 4
		addStep("Ingresar el numero del contenedor en el campo \"Container id\". "
				+ "Seleccionar la opcion \"OK\". ");
		System.out.println("PASO 4");
		
		String item = info[0];
		String i = info[1].trim();
		System.out.println("-"+info[0]+"-"+info[1]+"-");
		
		Integer nuevaQty = Integer.parseInt(i) - Integer.parseInt(unidadesRcvd.trim());
		
		
		m.itemRecibido(containerId);

		boolean apareceCorrectamente = false;
		if (nuevaQty == 0) {
			apareceCorrectamente = m.recibidoCompletamente(item);
		} else {
			apareceCorrectamente = m.recibidoIncompletamente(nuevaQty + "");
		}
		
		assertTrue(apareceCorrectamente, noAsn + " con el item " + item + " "
				+ "de la cita = " + noCita + " no se recibio correctamente");
	
		u.close();

	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setPrerequisites() {
		return "1) Crear una cita para devoluciones"
				+ "2) Agregar un ASN a la cita";
	}

	@Override
	public String setTestDescription() {
		return "Construido. Mediante el sistema RDM se debe realizar el recibo de "
				+ "mercancia de devoluciones";
	}

	@Override
	public String setTestDesigner() {
		return "guerrero.vargas@neoris.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_001_ReciboMercanciaDevReceive_Test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

}
