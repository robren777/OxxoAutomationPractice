package RDM.planificarDistribucion.monitoreoDePedidos;

import java.util.HashMap;

import org.testng.annotations.Test;

import RDM.util.PlanificarDistribucion;
import modelo.BaseExecution; import RDM.util.GlobalVariables;
import utils.SikuliUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLUtil;

public class ATC_FT_001_Validarquesepuedarealizarlaliberaciondepedidos extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_001_Validarquesepuedarealizarlaliberaciondepedidos_Test(
			HashMap<String, String> data) throws Exception {

		/*
		 * VARIABLES
		 */
		String fProduccion = data.get("fProduccion");
		String turno = data.get("turno");
		String mesaControl = data.get("mesaControl");
		String fechaLiberacion = data.get("fechaLiberacion");
        
		/*
		 * UTILERIAS
		 */
		SQLUtil dbRdm = new SQLUtil(GlobalVariables.DB_HOST_RDM, GlobalVariables.DB_USER_RDM,GlobalVariables.DB_PASSWORD_RDM);
		SeleniumUtil u = new SeleniumUtil(new IETest());
		PlanificarDistribucion p = new PlanificarDistribucion(u, testCase, data.get("server"));
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
		p.logIn();
		// PASO 3
		addStep("Dar click en la siguiente ruta: Distribution Planning XXFC Programacion y Balanceo XXFC Balanceo Produccion Det");
		System.out.println("PASO 3");
		p.clickDistributionPlanningBalanceoProduccionDet();
		p.screenshot();
		// PASO 4
		addStep("Seleccionar todas las casillas de las Rutas a liberar");
		System.out.println("PASO 4");
		p.ingresarDatosFproduccionTurnoMesaControl(fProduccion, turno, mesaControl);
		p.screenshot();
		// PASO 5
		addStep("Ingresar en la fecha de liberacion la hora a la que se desea liberar la ruta (colocar al menos un minuto de diferencia entre ruta y ruta)");
		System.out.println("PASO 5");
		p.screenshot();
		// PASO 6
		addStep("Dar click en el boton Liberacion");
		System.out.println("PASO 6");
		p.clickBotonLiberacion();
		p.screenshot();
		// PASO 7
		addStep("Dar click en el boton OK");
		System.out.println("PASO 7");
		p.nEnter(1);
		u.hardWait(5);
		p.screenshot();
		// PASO 8
		addStep("Dar click en el boton Salir");
		System.out.println("PASO 8");
		p.screenshot();
	    u.close();

	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setPrerequisites() {
		return ".- Acceso a RDM 10g"
			+ ".- Usuario y contrasena validos"
			+ ".- Pedidos por ruta programados.";
	}

	@Override
	public String setTestDescription() {
		return "Se validara que se pueda realizar la liberacion de las rutas.";
	}

	@Override
	public String setTestDesigner() {
		return "ricardo.palacios@neoris.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_001_Validarquesepuedarealizarlaliberaciondepedidos";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

}
