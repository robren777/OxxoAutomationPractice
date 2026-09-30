package RDM.reciboaProveedor.generacionDeReporteDeReciboDiario;

import java.util.HashMap;

import org.sikuli.script.Key;
import org.testng.annotations.Test;

import RDM.util.ReciboProveedor;
import modelo.BaseExecution; import RDM.util.GlobalVariables;
import utils.SikuliUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLUtil;

public class ATC_FT_002_Validargeneraciondereportederecibodiariopormediodeunnumerodecita extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_002_Validargeneraciondereportederecibodiariopormediodeunnumerodecita_Test(HashMap<String, String> data)
			throws Exception {

		/*
		 * VARIABLES
		 */
		String numeroCita = data.get("numeroCita");
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
		addStep("Dar click en el boton Lista de Valores del campo RCPT NBR");
		System.out.println("PASO 4");
		u.hardWait(3);
		sk.screen.type(Key.DELETE);
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
		addStep("Dar click en el boton Lista de Valores del campo APPT NBR e ingresar un numero de cita y presionar ok");
		System.out.println("PASO 7");
		r.nTAB(1);
        r.type(numeroCita);
        sk.screen.keyDown(Key.ENTER);
		sk.screen.keyUp(Key.ENTER);
		u.hardWait(2);
		sk.screen.keyDown(Key.ENTER);
		sk.screen.keyUp(Key.ENTER);
		r.screenshot();
		// PASO 8
		addStep("Dar click en el boton Imprimir");
		System.out.println("PASO 7");
		r.clickImprimirCita();
		r.screenshot();
		u.hardWait(5);
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
		return "Se validara generacion de Reporte de Recibo Diarop por medio de un numero de cita.";
	}

	@Override
	public String setTestDesigner() {
		return "ricardo.palacios@neoris.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_002_Validargeneraciondereportederecibodiariopormediodeunnumerodecita";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

}
