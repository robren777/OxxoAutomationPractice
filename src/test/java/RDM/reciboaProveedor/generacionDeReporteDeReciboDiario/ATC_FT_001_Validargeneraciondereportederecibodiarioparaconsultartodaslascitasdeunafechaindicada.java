package RDM.reciboaProveedor.generacionDeReporteDeReciboDiario;

import java.util.HashMap;

import org.testng.annotations.Test;

import RDM.util.ReciboProveedor;
import modelo.BaseExecution; import RDM.util.GlobalVariables;
import utils.SikuliUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLUtil;

public class ATC_FT_001_Validargeneraciondereportederecibodiarioparaconsultartodaslascitasdeunafechaindicada extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_001_Validargeneraciondereportederecibodiarioparaconsultartodaslascitasdeunafechaindicada_Test(HashMap<String, String> data)
			throws Exception {

		/*
		 * VARIABLES
		 */
		String fecha = data.get("fecha");

		/*
		 * UTILERIAS
		 */
		SQLUtil dbRdm = new SQLUtil(GlobalVariables.DB_HOST_RDM, GlobalVariables.DB_USER_RDM,
				GlobalVariables.DB_PASSWORD_RDM);
		SeleniumUtil u = new SeleniumUtil(new IETest());
		ReciboProveedor r = new ReciboProveedor(u, testCase, data.get("server"));
		SikuliUtil sk = new SikuliUtil();

		/*
		 * PASOS DEL CASO DE PRUEBA
		 */

		// PASO 1
		addStep("Accesar a RDM mediante la liga: http://10.180.49.81:8011/rdm01x.html");
		System.out.println("PASO 1");
		System.out.println(dbRdm.getHost());
		// PASO 2
		addStep("Ingresar usuario y contrasena y dar click en el boton Login");
		System.out.println("PASO 2");
		r.logIn();
		// PASO 3
		addStep("Dar click en las siguientes opciones del menu principal: Receiving > Reports > Receiving Register");
		System.out.println("PASO 3");
		r.screenshot();
		r.clickReceivingRegister();
		// PASO 4
		addStep("Dar click en el boton Lista de Valores del campo RCPT DATE");
		System.out.println("PASO 4");
        r.cambiarDate(fecha);
        r.screenshot();
		// PASO 5
		addStep("Dar click en le fecha requerida");
		System.out.println("PASO 5");
		r.screenshot();
		// PASO 6
		addStep("Dar click en el boton OK");
		System.out.println("PASO 6");
		r.screenshot();
		// PASO 7
		addStep("Dar click en el boton Imprimir");
		System.out.println("PASO 7");
		r.clickImprimirCita();
		u.hardWait(5);
		r.screenshot();
        u.close();
        
	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setPrerequisites() {
		return ".- Acceso a RDM 10G\r\n"
				+ ".- Usuario y contrasena activos en el sistema.\r\n"
				+ ".- Estatus de la citas RCVD (Recibidas)\r\n"
				+ "";
	}

	@Override
	public String setTestDescription() {
		return "Se validara que se genere el Reporte de Recibo Diario para consultar todas las citas de una fecha indicada";
	}

	@Override
	public String setTestDesigner() {
		return "ricardo.palacios@neoris.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_001_Validargeneraciondereportederecibodiarioparaconsultartodaslascitasdeunafechaindicada";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

}
