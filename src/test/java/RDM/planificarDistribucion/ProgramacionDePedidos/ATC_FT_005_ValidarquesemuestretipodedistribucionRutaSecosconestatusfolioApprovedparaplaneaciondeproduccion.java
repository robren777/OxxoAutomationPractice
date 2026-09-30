package RDM.planificarDistribucion.ProgramacionDePedidos;

import java.util.HashMap;

import org.testng.annotations.Test;

import RDM.util.PlanificarDistribucion;
import modelo.BaseExecution; import RDM.util.GlobalVariables;
import utils.SikuliUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLUtil;

public class ATC_FT_005_ValidarquesemuestretipodedistribucionRutaSecosconestatusfolioApprovedparaplaneaciondeproduccion
		extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_005_ValidarquesemuestretipodedistribucionRutaSecosconestatusfolioApprovedparaplaneaciondeproduccion_Test(
			HashMap<String, String> data) throws Exception {

		/*
		 * VARIABLES
		 */
		String fInicial = data.get("fInicial");
		String fFinal = data.get("fFinal");
		String distro = data.get("distro");

		/*
		 * UTILERIAS
		 */
		SQLUtil dbRdm = new SQLUtil(GlobalVariables.DB_HOST_RDM, GlobalVariables.DB_USER_RDM,
				GlobalVariables.DB_PASSWORD_RDM);
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
		addStep("Dar click en la siguiente ruta: Distribution Planning/XXFC Programacion y Balanceo/ XXFC. Mod. Programacion p Ruta");
		System.out.println("PASO 3");
		p.clickDistributionPlanningBalanceoPorRuta();
		p.screenshot();
		// PASO 4
		addStep("Se ingresa informacion en los siguientes campos: - F. inicial > Seleccionar una fecha - F. Final > Seleccionar una fecha - Estatus Folio > Approved - Route > ALL - T. Distrib. > P Ruta Secos");
		System.out.println("PASO 4");
		p.ingresarParametrosFechasDistro(fInicial, fFinal, distro);
		p.screenshot();
		// PASO 5
		addStep("Dar click en el icono Ejecutar");
		System.out.println("PASO 5");
		p.clickDistributionCheck();
		p.screenshot();
		// PASO 6
		addStep("Seleccionar casilla de la ruta a balancear");
		System.out.println("PASO 6");
		p.clickBalancear();
		p.screenshot();
		// PASO 7
		addStep("Dar click en el boton Balancear para agregar los pedidos a una fecha de produccion");
		System.out.println("PASO 7");
		p.screenshot();
		// PASO 8
		addStep("Se capturan los siguientes datos: - Fecha de produccion > Ingresar fecha - Turno > Ingresar turno - Mesa de control > Ingresar mesa de control");
		System.out.println("PASO 8");
		p.screenshot();
		// PASO 9
		addStep("Dar click en el boton Balancear para agregar los pedidos a una fecha de produccion");
		System.out.println("PASO 9");
		p.screenshot();
		u.close();

	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setPrerequisites() {
		return ".- Acceso a RDM G10" + "Usuario y contrasena validos" + "Plan de produccion de acuerdo al PMO"
				+ ".- Acceso a la BD";
	}

	@Override
	public String setTestDescription() {
		return "Se validara que se muestre la configuracion Tipo de distribucion Ruta Secos (P) cuando se selecciona Estatus folio Approved y se seleccionan todas las rutas en la planeacion de produccion";
	}

	@Override
	public String setTestDesigner() {
		return "ricardo.palacios@neoris.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_005_ValidarquesemuestretipodedistribucionRutaSecosconestatusfolioApprovedparaplaneaciondeproduccion";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

}