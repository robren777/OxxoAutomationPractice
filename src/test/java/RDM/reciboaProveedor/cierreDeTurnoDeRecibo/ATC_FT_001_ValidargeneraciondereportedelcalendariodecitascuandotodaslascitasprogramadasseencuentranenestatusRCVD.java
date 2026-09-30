package RDM.reciboaProveedor.cierreDeTurnoDeRecibo;

import java.util.HashMap;

import org.sikuli.script.Key;
import org.testng.annotations.Test;

import RDM.util.ReciboProveedor;
import modelo.BaseExecution; import RDM.util.GlobalVariables;
import utils.SikuliUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLUtil;

public class ATC_FT_001_ValidargeneraciondereportedelcalendariodecitascuandotodaslascitasprogramadasseencuentranenestatusRCVD
		extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_001_ValidargeneraciondereportedelcalendariodecitascuandotodaslascitasprogramadasseencuentranenestatusRCVD_Test(
			HashMap<String, String> data) throws Exception {

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
		addStep("Dar click en las siguientes opciones del menu principal: > Appointments > Appointment Schedule");
		System.out.println("PASO 3");
		r.clickAppointmentSchedule();
		r.screenshot();
		// PASO 4
		addStep("Posicionarse en el campo APPOINTMENT DATE y dar click en el boton Lista de valores");
		System.out.println("PASO 4");
		r.screenshot();
        r.cambiarDate(fecha);
        sk.screen.keyDown(Key.ENTER);
        sk.screen.keyUp(Key.ENTER);
		// PASO 5
		addStep("Seleccionar la fecha requerida Mes,dia,ano");
		System.out.println("PASO 5");
		r.screenshot();
		// PASO 6
		addStep("Dar click en el boton OK");
		System.out.println("PASO 6");
		r.screenshot();
		// PASO 7
		addStep("Dar click en el boton Print del lado izquierdo de la pantalla Appointment Schedule");
		System.out.println("PASO 7");
		r.screenshot();
        r.clickPrint();
		// PASO 8
		addStep("Dar click en la pestana Destination");
		System.out.println("PASO 8");
		r.screenshot();
		// PASO 9
		addStep("Dar click en el boton Save");
		System.out.println("PASO 9");
		sk.screen.click();
		r.clickSave();
		u.hardWait(3);
		r.screenshot();
        u.close();

	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setPrerequisites() {
		return ".- Acceso a RDM 10G\r\n" + ".- Usuario y contrasena activos\r\n"
				+ ".- Estatus de las citas en RCVD (Receiving)";
	}

	@Override
	public String setTestDescription() {
		return "Se validara generacion de Reporte del calendario de Citas cuando todas las citas programadas se encuentran en estatus RCVD Receiving";
	}

	@Override
	public String setTestDesigner() {
		return "ricardo.palacios@neoris.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_001_ValidargeneraciondereportedelcalendariodecitascuandotodaslascitasprogramadasseencuentranenestatusRCVD";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

}