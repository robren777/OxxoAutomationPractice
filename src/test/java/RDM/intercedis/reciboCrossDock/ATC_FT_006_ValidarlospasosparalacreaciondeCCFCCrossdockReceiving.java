package RDM.intercedis.reciboCrossDock;

import java.util.HashMap;

import org.sikuli.script.Key;
import org.testng.annotations.Test;

import RDM.util.Intercedis;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_006_ValidarlospasosparalacreaciondeCCFCCrossdockReceiving extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_006_ValidarlospasosparalacreaciondeCCFCCrossdockReceiving_Test(HashMap<String, String> data)
			throws Exception {

		/*
		 * VARIABLES
		 */
		String numeroCita = "95197";//95210data.get("numeroCita");
		String tipoReporte = data.get("tipoReporte");

		/*
		 * UTILERIAS
		 */
		/*SQLUtil dbRdm = new SQLUtil(GlobalVariables.DB_HOST_RDM, GlobalVariables.DB_USER_RDM,
				GlobalVariables.DB_PASSWORD_RDM);*/
		SeleniumUtil u = new SeleniumUtil(new IETest());
		Intercedis i = new Intercedis(u, testCase, data.get("server"));
		SikuliUtil sk = new SikuliUtil();

		/*
		 * PASOS DEL CASO DE PRUEBA
		 */

		// PASO 1
		addStep("Accesar a RDM mediante la liga: http://10.180.49.81:8011/rdm01x.html");
		System.out.println("PASO 1");
		//System.out.println(dbRdm.getHost());
		// PASO 2
		addStep("Ingresar usuario y contrasena y dar click en el boton Login");
		System.out.println("PASO 2");
		i.logIn();
		
		// PASO 3
		addStep("Dar click en las siguientes opciones Receiving / XXFC Cross Dock Receiving");
		System.out.println("PASO 3");
		i.screenshot();
	    i.clickReceivingXxfcCrossDockReceiving();
		// PASO 4
		addStep("Ingresar un numero de cita en el campo APPT NBR en estatus SCHD y dar click en el boton Ejecutar");
		System.out.println("PASO 4");
		u.hardWait(5);
		i.type(numeroCita);
		sk.screen.keyDown(Key.ENTER);
		sk.screen.keyUp(Key.ENTER);
		u.hardWait(5);
		i.screenshot();
		// PASO 5
		addStep("Dar click en el boton Detail para consultar el Reporte Crossdock Receiving Receipt");
		System.out.println("PASO 5");
		u.hardWait(10);
		i.clickDetail();
		i.screenshot();
		// PASO 6
		addStep("Seleccionar RPT_PO Reporte por PO");
		System.out.println("PASO 6");
		i.screenshot();
		// PASO 7
		addStep("Dar click en boton ACCEPT");
		System.out.println("PASO 7");
		i.clickTipoReporte(tipoReporte);
		i.screenshot();
		u.hardWait(5);
		sk.screen.keyDown(Key.ENTER);
		sk.screen.keyUp(Key.ENTER);
		sk.screen.keyDown(Key.ENTER);
		sk.screen.keyUp(Key.ENTER);
		i.screenshot();
		// PASO 8
		addStep("Regresar a la pantalla XXFC Crossdock Receiving y dar click en el boton Confirm Record");
		System.out.println("PASO 8");
	    u.hardWait(3);
	    i.minimizarWindow();
		i.clickConfirmRecord();
		i.screenshot();
		// PASO 9
		addStep("Dar click en el boton Save");
		System.out.println("PASO 9");
		u.hardWait(10);
		i.clickSaveIntercedis();
		i.screenshot();
		// PASO 10
		addStep("Dar click en el boton Print Labels");
		System.out.println("PASO 10");
		i.clickPrintLabels();
		i.screenshot();
		// PASO 11
		addStep("Dar click en el boton Save");
		System.out.println("PASO 11");
		u.hardWait(5);
		i.screenshot();
	    i.clickSaveIntercedis2();
	    i.screenshot();
		// PASO 12
		addStep("Regresar a la pantalla XXFC Crossdock Receiving y dar click en el boton Summary");
		System.out.println("PASO 12");
		u.hardWait(5);
	    i.minimizarWindow();
	    u.hardWait(2);
	    i.screenshot();
		i.clickSummaryIntercedis();
		i.screenshot();
		// PASO 13
		addStep("Regresar a la pantalla XXFC Crossdock Receiving y dar click en el boton Close APPT");
		System.out.println("PASO 13");
		u.hardWait(5);
	    i.minimizarWindow();
	    u.hardWait(2);
	    i.screenshot();
		i.clickCloseAppt();
        u.close();

	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setPrerequisites() {
		return "contar con acceso a RDM -contar con el numero de cita";
	}

	@Override
	public String setTestDescription() {
		return "Se validara el recibo Crossdock, consulta del Reporte Crossdock Receiving Receipt, generacion del archivo con el codigo para la impresora Zebra (generar etiquetas), consulta del Reporte Sumarizado (Receiving Receipt) y el cierre de la cita que se recibio.";
	}

	@Override
	public String setTestDesigner() {
		return "ricardo.palacios@neoris.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_006_ValidarlospasosparalacreaciondeCCFCCrossdockReceiving";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

}
