package RDM.reciboaProveedor.generacionDePaquetes;

import java.util.HashMap;

import org.sikuli.script.Key;
import org.testng.annotations.Test;

import RDM.util.ReciboProveedor;
import modelo.BaseExecution; import RDM.util.GlobalVariables;
import utils.SikuliUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLUtil;

public class ATC_FT_001_ValidarGeneracionDeReporteDelPaqueteDeReciboParaUnaCitaProgramada extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_001_ValidarGeneracionDeReporteDelPaqueteDeReciboParaUnaCitaProgramada_Test(
			HashMap<String, String> data) throws Exception {

		/*
		 * VARIABLES
		 */
		String fecha = data.get("fecha");
		String ubicacion = data.get("ubicacion");

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
		addStep("Dar clic en las siguientes opciones del menu principal: Receiving Allocation > Receiving Labels");
		System.out.println("PASO 3");
		r.clickReceivingAllocation();
	    r.screenshot();
		r.clickReceivingLabels();
		// PASO 4
		addStep("Posicionarse en el campo DATE y dar click en el boton Lista de valores");
		System.out.println("PASO 4");
		// PASO 5
		addStep("Dar click en el mes y el dia requerido y presionar OK");
		System.out.println("PASO 5");
		r.screenshot();
		r.cambiarUbicacionConTab(ubicacion);
		r.cambiarDateConTab(fecha);
		sk.screen.keyDown(Key.ENTER);
	    sk.screen.keyUp(Key.ENTER);
		// PASO 6
		addStep("Presionar el boton execute check color rojo");
		System.out.println("PASO 6");
		r.screenshot();
		u.hardWait(8);
		r.clickAprovacion();
		u.hardWait(3);
		// PASO 7
		addStep("Seleccionar un registro y marcar el check group print");
		System.out.println("PASO 7");
		// PASO 8
		addStep("Dar click en el boton Print");
		System.out.println("PASO 8");
		r.screenshot();
		r.clickPrint();
		wait(5);
		// PASO 9
		addStep("Dar click en el boton YES");
		System.out.println("PASO 9");
		// PASO 10
		addStep("Dar click en la ventana Destination");
		System.out.println("PASO 10");
		// PASO 11
		addStep("Dar click en el boton Save");
		r.screenshot();
		System.out.println("PASO 11");
		r.nEnter(4);
		u.close();
		
	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setPrerequisites() {
		return " Acceso a RDM\r\n" + ".- Usuario y contraseña activos\r\n" + ".- Previa cita programada";
	}

	@Override
	public String setTestDescription() {
		return "Validar generacion de reporte del paquete de recibo para una cita programada";
	}

	@Override
	public String setTestDesigner() {
		return "ricardo.palacios@neoris.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_001_ValidarGeneracionDeReporteDelPaqueteDeReciboParaUnaCitaProgramada";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

}