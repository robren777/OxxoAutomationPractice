package RDM.intercedis.reciboCrossDock;

import static org.testng.Assert.assertTrue;

import java.util.HashMap;

import org.sikuli.script.Key;
import org.testng.annotations.Test;

import RDM.util.Citas;
import RDM.util.Intercedis;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;

public class ATC_FT_005_Validarcreaciondeunacitarecibocrossdock extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_005_Validarcreaciondeunacitarecibocrossdock_Test(
			HashMap<String, String> data) throws Exception {

		/*
		 * VARIABLES
		 */
		String fecha = data.get("fecha");
		String carrier = data.get("carrier");
		String trailerId = data.get("trailerId");
		String door = data.get("door");
		String type = data.get("type");
		String duracion = data.get("duracion");

		/*
		 * UTILERIAS
		 */
		/*SQLUtil dbRdm = new SQLUtil(GlobalVariables.DB_HOST_RDM, GlobalVariables.DB_USER_RDM,
				GlobalVariables.DB_PASSWORD_RDM);*/
		SeleniumUtil u = new SeleniumUtil(new IETest());
		Intercedis i = new Intercedis(u, testCase, data.get("server"));
		SikuliUtil sk = new SikuliUtil();
		Citas c = new Citas(u, testCase, data.get("server"));

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
		addStep("Dar click en las siguientes opciones del menu principal: > Appointments > Appointment Schedule");
		System.out.println("PASO 3");
		i.clickAppointmentSchedule();
		i.screenshot();
		// PASO 4
		addStep("Dar click en el boton Create Record");
		System.out.println("PASO 4");
		i.clickCreateRecord();
		i.screenshot();
		// PASO 5
		addStep("Ingresar una fecha y dar click en el boton Listado del campo CARRIER");
		System.out.println("PASO 5");
		u.hardWait(3);
		i.clickStartImagen();
		u.hardWait(3);
		String fechaRDM = i.cambiarDate(fecha);
		//i.nTAB(1);
		i.screenshot();
		// PASO 6
		addStep("Seleccionar la opcion PROV y dar click en el boton OK");
		System.out.println("PASO 6");
		i.typeAndTab(carrier);
		i.screenshot();
		// PASO 7
		addStep("Dar click en el boton Listado del campo TRAILER ID");
		System.out.println("PASO 7");
		i.screenshot();
		// PASO 8
		addStep("Seleccionar opcion: PROV y dar click en boton OK");
		System.out.println("PASO 8");
		i.typeAndTab(trailerId);
		i.screenshot();
		// PASO 9
		addStep("Dar click en el boton Listado del campo DOOR");
		System.out.println("PASO 9");
		i.screenshot();
		// PASO 10
		addStep("Seleccionar opcion: MRRE y dar click en boton OK");
		System.out.println("PASO 10");
		i.typeAndTab(door);
		i.screenshot();
		// PASO 11
		addStep("Dar click en el boton Listado del campo TYPE");
		System.out.println("PASO 11");
		i.screenshot();
		// PASO 12
		addStep("Seleccionar opcion: X y dar click en boton OK");
		System.out.println("PASO 12");
		i.typeAndTab(type);
		i.screenshot();
		// PASO 13
		addStep("Ingresar el tiempo en el campo DURACION");
		System.out.println("PASO 13");
		sk.screen.type(Key.DELETE);
		i.type(duracion);
		i.screenshot();
		// PASO 14
		addStep("Dar Enter");
		System.out.println("PASO 14");
		i.screenshot();
		// PASO 15
		addStep("Dar click en el boton Save");
		System.out.println("PASO 15");
		u.hardWait(5);
		i.clickSave();
		i.screenshot();
		// PASO 16
		addStep("Validar que se haya guardado la cita creada");
		System.out.println("PASO 16");
		i.screenshot();
		i.nEnter(10);
		
		boolean mismoPo = c.validarOrdenDeCompraAgregada(fechaRDM);
		
		assertTrue(mismoPo, "No se creo la cita: " + fechaRDM);
		
        u.close();

	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setPrerequisites() {
		return ".- Acceso a RDM 10g\r\n"
				+ ".- Credenciales de acceso validas";
	}

	@Override
	public String setTestDescription() {
		return "Se validara la creacion de una cita para Recibo Crossdock";
	}

	@Override
	public String setTestDesigner() {
		return "ricardo.palacios@neoris.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_005_Validarcreaciondeunacitarecibocrossdock";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

}