package RDM.mermaYDev.ReciboDeDev;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import RDM.util.MermaYDev;
import modelo.BaseExecution; import RDM.util.GlobalVariables;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLUtil;

public class ATC_FT_002_ReciboMercanciaDevAdjust extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_002_ReciboMercDevAdjust_Test(HashMap<String, String> data) throws Exception {

		/*
		 * VARIABLES
		 */
		String noCita = data.get("noCita");
		String noAsn = data.get("noAsn");
		String noItem = data.get("noItem");
		String unidadesRcvd = data.get("unidadesRcvd");
		String opcion = data.get("opcion");
		String unrcCode = data.get("unrcCode");

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
		addStep("Ingresar usuario y contrasena en el sistema RDM y Presionar el boton \"Login\"");
		System.out.println("PASO 1");
		
		m.logIn();
		
		// PASO 2
		addStep("En el menu principal de RDM ingresar a la opcion \"Receiving\" "
				+ "Ingresar a la opcion XXFC ASN / NSC Receiving "
				+ "Nota: Ademas se debe verificar y validar que la mercancia cumpla "
				+ "con los requisitos de las devoluciones.");
		System.out.println("PASO 2");
		
		m.clickMenuReceivingXxfcAsnNscReceiving();
		
		// PASO 3
		addStep("Ingresar en el campo \"RCVD\" la cantidad de unidades recibdas."
				+ "En el campo \"UNRC CODE\" elegir la razon por la cual "
				+ "no se acredito la mercancia en la tienda. "
				+ "Presionar la opcion \"Receive - Adjust\"");
		System.out.println("PASO 3");
		
		String[] info = m.ingresarInfo(noCita, noAsn, noItem, unidadesRcvd, opcion, unrcCode);
		
		assertNotNull(info, "La cantidad a recibir no es valida");

		String item = info[0];
		int nuevaQty = Integer.valueOf(info[1]) - Integer.valueOf(unidadesRcvd);
		
		m.recibirPorAdjust();
		
		boolean apareceCorrectamente = false;
		if (nuevaQty == 0) {
			apareceCorrectamente = m.recibidoCompletamente(item);
		} else {
			apareceCorrectamente = m.recibidoIncompletamente(nuevaQty + "");
		}
		
		assertTrue(apareceCorrectamente, noAsn + " con el item " + noItem + " "
				+ "de la cita = " + noCita + " no se recibio correctamente");
		
		u.close();		
	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setPrerequisites() {
		return "Tener una cita para devolucion con ASN agregado";
	}

	@Override
	public String setTestDescription() {
		return "Construido. Mediante el sistema RDM se debe realizar el recibo de "
				+ "mercancia de devoluciones NSC y ajustar la mercancia";
	}

	@Override
	public String setTestDesigner() {
		return "guerrero.vargas@neoris.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_002_ReciboMercDevAdjust_Test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

}
