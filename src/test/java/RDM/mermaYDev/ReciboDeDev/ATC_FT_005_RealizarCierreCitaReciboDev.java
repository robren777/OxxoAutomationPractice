package RDM.mermaYDev.ReciboDeDev;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.testng.annotations.Test;

import RDM.util.MermaYDev;
import modelo.BaseExecution; import RDM.util.GlobalVariables;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLUtil;

public class ATC_FT_005_RealizarCierreCitaReciboDev extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_005_RealizarCierreCitaReciboDev_Test(HashMap<String, String> data) throws Exception {

		/*
		 * VARIABLES
		 */
		String noCita = data.get("noCita");
		String unrcCode = data.get("unrcCode");
		String sealNbr = data.get("sealNbr");

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
		addStep("Ingresar numero de cita en el campo APPT NBR y presionar el boton \"Ejecutar\". "
				+ "Presionar la opcion \"CLOSE APPT\". ");
		System.out.println("PASO 3");
		
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
		return "1) Tener una cita para devolucion "
				+ "2) Agregar ASN a la cita";
	}

	@Override
	public String setTestDescription() {
		return "Construido. Mediante el sistema RDM se debe realizar el cierre de la cita, "
				+ "despues de haber realizado el recibo de mercancia de devolucion";
	}

	@Override
	public String setTestDesigner() {
		return "guerrero.vargas@neoris.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_005_RealizarCierreCitaReciboDev_Test";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

}
