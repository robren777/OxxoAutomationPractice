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

public class ATC_FT_004_ReciboMercanciaDevNoAcreditadas extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_004_ReciboMercanciaDevNoAcreditadas_Test(HashMap<String, String> data) throws Exception {

		/*
		 * VARIABLES
		 */
		String noCita = data.get("noCita");
		String sealNbr = data.get("sealNbr");
		String noAsn = data.get("noAsn");
		String noItem = data.get("noItem");
		String unidadesRcvd = data.get("unidadesRcvd");
		String opcion = data.get("opcion");
		String unrcCode = data.get("unrcCode");
		String containerId = data.get("containerId");

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
				+ "Ingresar a la opcion XXFC ASN / NSC Receiving");
		System.out.println("PASO 2");
		
		m.clickMenuReceivingXxfcAsnNscReceiving();
		
		// PASO 3
		addStep("Ingresar numero de cita en el campo APPT NBR y presionar el boton Ejecutar " 
				+ "En el campo \"UNRC CODE\" elegir la razon por la cual "
				+ "no se acredito la mercancia en la tienda. "
				+ "Seleccionar el boton Receive");
		System.out.println("PASO 3");
		
		String[] info = m.ingresarInfo(noCita, noAsn, noItem, unidadesRcvd, opcion, unrcCode);
		
		assertNotNull(info, "La cantidad a recibir no es valida");

		//String item = info[0];
		//int nuevaQty = Integer.valueOf(info[1]) - Integer.valueOf(unidadesRcvd);
		
		m.itemRecibido(containerId);
		
		// PASO 4
		addStep("Presionar el boton \"CLOSE APPT\" para cerrar la cita. "
				+ "Seleccionar la opcion \"YES\". "
				+ "Ingresar el SEAL NBR (numero de empleado del recibidor).");
		
		m.ingresarNoCita(noCita);

		boolean seElimino = m.closeAppt(unrcCode, sealNbr);
		
		assertTrue(seElimino, "Hubo un error y no se elimino la cita " + noCita);
		
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
		return "Construido. Mediante el sistema RDM se debe realizar el recibo de mercancia de "
				+ "devoluciones con cantidades no acreditadas y realizar el cierre de la cita";
	}

	@Override
	public String setTestDesigner() {
		return "guerrero.vargas@neoris.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_004_ReciboMercanciaDevNoAcreditadas_Test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

}
