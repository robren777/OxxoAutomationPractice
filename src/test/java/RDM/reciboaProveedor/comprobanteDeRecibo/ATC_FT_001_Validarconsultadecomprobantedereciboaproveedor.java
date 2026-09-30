package RDM.reciboaProveedor.comprobanteDeRecibo;

import java.util.HashMap;

import org.testng.annotations.Test;

import RDM.util.ReciboProveedor;
import modelo.BaseExecution; import RDM.util.GlobalVariables;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLUtil;

public class ATC_FT_001_Validarconsultadecomprobantedereciboaproveedor extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_001_Validarconsultadecomprobantedereciboaproveedor_Test(
			HashMap<String, String> data) throws Exception {

		/*
		 * VARIABLES
		 */
		String numeroCita = data.get("numerocita");

		/*
		 * UTILERIAS
		 */
		SQLUtil dbRdm = new SQLUtil(GlobalVariables.DB_HOST_RDM, GlobalVariables.DB_USER_RDM,
				GlobalVariables.DB_PASSWORD_RDM);
		SeleniumUtil u = new SeleniumUtil(new IETest());
		ReciboProveedor r = new ReciboProveedor(u, testCase, data.get("server"));

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
		addStep("Dar click en las siguientes opciones: Receiving > Reports > Receiving Receipt");
		System.out.println("PASO 3");
		r.clickReceivingReceipts();
		r.screenshot();
		// PASO 4
		addStep("Posicionarse en el campo <APPT NBR> e ingresar el numero de cita");
		System.out.println("PASO 4");
		r.type(numeroCita);
		r.screenshot();
		// PASO 5
		addStep("Dar click en el boton Imprimir");
		System.out.println("PASO 5");
		r.screenshot();
		r.clickImprimirCita();
		u.hardWait(8);
		u.close();
	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setPrerequisites() {
		return " - Liga de Acceso a RDM\r\n"
				+ ".- Usuario y contraseña activos\r\n"
				+ ".- Numero de cita ya recibida\r\n"
				+ ".- Acceso a la BD";
	}

	@Override
	public String setTestDescription() {
		return "Validar consulta de comprobante de recibo a proveedor";
	}

	@Override
	public String setTestDesigner() {
		return "ricardo.palacios@neoris.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_001_Validarconsultadecomprobantedereciboaproveedor";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

}