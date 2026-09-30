package RDM.planificarDistribucion.monitoreoDePedidos;

import java.util.HashMap;

import org.testng.annotations.Test;

import RDM.util.PlanificarDistribucion;
import modelo.BaseExecution; import RDM.util.GlobalVariables;
import utils.SikuliUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLUtil;

public class ATC_FT_002_Validarquesepuedamodificarturnodeliberacion extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_002_Validarquesepuedamodificarturnodeliberacion_Test(
			HashMap<String, String> data) throws Exception {

		/*
		 * VARIABLES
		 */
		String fechaLiberaIni = data.get("fechaLiberaIni");
		String fechaLiberaFin = data.get("fechaLiberaFin");
		String mesaControl = data.get("mesaControl");
		
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
		addStep("Dar click en la siguiente ruta: Distribution Planning / XXFC Programacion y Balanceo XXFC Control de Liberaciones Automaticas");
		System.out.println("PASO 3");
		p.clickDistributionPlanningBalanceoLiberacionesAutomaticas();
		p.screenshot();
		// PASO 4
		addStep("Dar click en checkbox de Fecha de Liberacion Ini y colocar el rango de fechas en F. Libera Ini y F. Libera Fin");
		System.out.println("PASO 4");
		p.clickFechaLiberacioncheck();
		p.ingresarDatosFechas(fechaLiberaIni, fechaLiberaFin, mesaControl);
		p.screenshot();
		// PASO 5
		addStep("Posicionarse en el campo Mesa de Control y seleccionar opcion ALL");
		System.out.println("PASO 5");
		p.screenshot();
		// PASO 6
		addStep("Dar click en el boton Ejecutar");
		System.out.println("PASO 6");
		p.clickDistributionCheck();
		p.screenshot();
		// PASO 7
		addStep("Dar click en la casilla de la ruta en la que se va a modificar la fecha o turno de liberacion");
		System.out.println("PASO 7");
		p.screenshot();
		// PASO 8
		addStep("Dar click en el boton M. Fecha Libera e ingresar la nueva fecha y turno de liberacion");
		System.out.println("PASO 8");
		p.clickFechaLibera();
		u.hardWait(4);
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
			+ ".- Pedidos por Ruta programados";
	}

	@Override
	public String setTestDescription() {
		return "Se validara que se pueda realizar reprogramacion de pedidos modifcando turno de liberacion";
	}

	@Override
	public String setTestDesigner() {
		return "ricardo.palacios@neoris.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_002_Validarquesepuedamodificarturnodeliberacion";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

}
