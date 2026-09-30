package RDM.intercedis.programacionLiberacion;

import java.util.HashMap;

import org.testng.annotations.Test;

import RDM.util.Intercedis;
import modelo.BaseExecution; import RDM.util.GlobalVariables;
import utils.SikuliUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLUtil;

public class ATC_FT_002_Validarprogramacionliberacionysurtidoporruta extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_002_Validarprogramacionliberacionysurtidoporruta_Test(
			HashMap<String, String> data) throws Exception {

		/*
		 * VARIABLES
		 */
		String fechaInicial = data.get("fechaInicial");
		String fechaFinal = data.get("fechaFinal");
		String tDistrib = data.get("tDistrib");
		String distro = data.get("distro");

		/*
		 * UTILERIAS
		 */
		SQLUtil dbRdm = new SQLUtil(GlobalVariables.DB_HOST_RDM, GlobalVariables.DB_USER_RDM,
				GlobalVariables.DB_PASSWORD_RDM);
		SeleniumUtil u = new SeleniumUtil(new IETest());
		Intercedis i = new Intercedis(u, testCase, data.get("server"));
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
		i.logIn();
		// PASO 3
		addStep("Dar click en las siguientes opciones del menu Distribution Planning / XXFC Programacion y balanceo / XXFC-Mod Programacion p Ruta");
		System.out.println("PASO 3");
		i.clickDistributionPlaningXxfcModpRuta();
		i.screenshot();
		// PASO 4
		addStep("Ingresar datos en los siguientes campos: -F. Inicial: Seleccionar una fecha -F. Final: Seleccionar una fecha -T. Distrib: RUTA INT (corresponde a ruta Intercedis) -Distro: Ingresar si se tiene el dato, si no, seleccionar la opcion ALL");
		System.out.println("PASO 4");
		i.ingresarParametros(fechaInicial, fechaFinal, tDistrib, distro);
		i.screenshot();
		// PASO 5
		addStep("Dar click en el boton Ejecutar");
		System.out.println("PASO 5");
		i.clickEjecutar();
		i.screenshot();
		// PASO 6
		addStep("Dar click en el boton Select ALL en caso de querer seleccionar todas las rutas mostradas o seleccionar manualmente las casillas de las rutas que se requieran liberar");
		System.out.println("PASO 6");
		i.clickSelectAll();
		i.screenshot();
		// PASO 7
		addStep("Dar click en el boton Item Detail");
		System.out.println("PASO 7");
		i.clickItemDetail();
		i.screenshot();
		// PASO 8
		addStep("Regresar a la pantalla XXFC Programacion Surtido Route y dar click en el boton Balancear");
		System.out.println("PASO 8");
		i.clickBalancear();
		i.screenshot();
		//pendientes no son iguales que los casos de la evidencia
		// PASO 9
		addStep("Ingresar los siguientes datos -Fecha produccion -Turno -Mesa Control Y presionas el boton balanceo");
		System.out.println("PASO 9");
		i.screenshot();
		// PASO 10
		addStep("Dar click en el boton Calcular y despues dar click en botón Guardar");
		System.out.println("PASO 10");
		i.screenshot();
		u.close();
		
		
	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setPrerequisites() {
		return "- Contar con acceso a RDM";
	}

	@Override
	public String setTestDescription() {
		return "Se validara programacion, liberacion y surtido por ruta";
	}

	@Override
	public String setTestDesigner() {
		return "ricardo.palacios@neoris.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_002_Validarprogramacionliberacionysurtidoporruta";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

}