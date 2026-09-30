package RDM.reciboaProveedor.nulificacionDeEtiquetas;

import java.util.HashMap;

import org.sikuli.script.Key;
import org.testng.annotations.Test;

import RDM.util.ReciboProveedor;
import modelo.BaseExecution; import RDM.util.GlobalVariables;
import utils.SikuliUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLUtil;

public class ATC_FT_001_ValidarnulificaciondeetiquetaspormediodelIDdelcontenedor extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_001_ValidarnulificaciondeetiquetaspormediodelIDdelcontenedor_Test(HashMap<String, String> data)
			throws Exception {

		/*
		 * VARIABLES
		 */
		String containerId = data.get("containerId");

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
		addStep("Dar click en la siguientes opciones del menu: Support Functions > Processing/ Returns Setup > Reprint/ Null Labels");
		System.out.println("PASO 3");
		r.screenshot();
		r.clickNullLabels();
		// PASO 4
		addStep("Posicionarse en el campo <CONTAINER ID> e ingresar el Container ID");
		System.out.println("PASO 4");
        r.type(containerId);
        sk.screen.keyDown(Key.ENTER);
		sk.screen.keyUp(Key.ENTER);
		r.screenshot();
		// PASO 5
		addStep("Dar click en el boton Null");
		System.out.println("PASO 5");
		u.hardWait(3);
		r.clickNull();
		u.hardWait(4);
		r.screenshot();
		// PASO 6
		addStep("Dar click en el boton Salir");
		System.out.println("PASO 5");
		r.screenshot();
        u.close();
	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setPrerequisites() {
		return ".- Acceso a RDM 10G.\r\n"
				+ ".- Usuario y contrasena activos.\r\n"
				+ ".- Acceso a la BD\r\n"
				+ ".- Container ID en estatus A\r\n"
				+ "";
	}

	@Override
	public String setTestDescription() {
		return "Se validara que permita nulificar etiquetas por medio del ID del contenedor";
	}

	@Override
	public String setTestDesigner() {
		return "ricardo.palacios@neoris.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_001_ValidarnulificaciondeetiquetaspormediodelIDdelcontenedor";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

}