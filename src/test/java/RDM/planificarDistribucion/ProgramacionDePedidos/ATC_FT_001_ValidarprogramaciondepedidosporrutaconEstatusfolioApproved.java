package RDM.planificarDistribucion.ProgramacionDePedidos;

import java.util.HashMap;

import org.testng.annotations.Test;

import RDM.util.PlanificarDistribucion;
import modelo.BaseExecution; import RDM.util.GlobalVariables;
import utils.SikuliUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLUtil;

public class ATC_FT_001_ValidarprogramaciondepedidosporrutaconEstatusfolioApproved extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_001_ValidarprogramaciondepedidosporrutaconEstatusfolioApproved_Test(
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
		addStep("Dar click en la siguiente Ruta: Distribution Planning xxfc-Programacion y Balanceo> Programacion por ruta Ingresa el numero del distro para realizar la consulta y presiona el boton ejecutar");
		System.out.println("PASO 3");
		p.clickDistributionPlanningBalanceoPorRuta();
		p.ingresarParametrosFechasDistro(fInicial, fFinal, distro);
		p.clickDistributionCheck();
		p.screenshot();
		// PASO 4
		addStep("Seleccionamos la ruta y presionamos el boton Balancear, en la pantalla que aparece se coloca el turno, la mesa de control y se presiona el boton balanceo");
		System.out.println("PASO 4");
		p.clickBalancear();
		p.screenshot();
		// PASO 5
		addStep("En la pantalla datos de balanceo ingresa los siguientes datos Turno, Mesa de control y presiona el boton balanceo");
		System.out.println("PASO 5");
		p.screenshot();
		// PASO 6
		addStep("En la pantalla XXFC CON PROG BALANCEO presiona al boton calcular y luego al boton guardar y confirmar la propuesta");
		System.out.println("PASO 6");
		p.screenshot();
		// PASO 7
		addStep("Dar click en el boton Reporte");
		System.out.println("PASO 7");
		p.screenshot();
		// PASO 8
		addStep("Presionar el boton Det. De Balanceo");
		System.out.println("PASO 8");
		p.screenshot();
		// PASO 9
		addStep("Modificar la Fecha y hora de liberacion para que el proceso lo pueda tomar, ya que se modifica presionar el boton Grabar Plan");
		System.out.println("PASO 9");
		p.screenshot();
		// PASO 10
		addStep("Seleccionar la ruta y presionar el boton Asignacion");
		System.out.println("PASO 10");
		p.screenshot();
		// PASO 11
		addStep("Ingresa los siguientes campos, Turno, Mesa de Control, Gpo Surtido y Tpo Surtido y hacer click en el boton ejecutar");
		System.out.println("PASO 11");
		p.screenshot();
		// PASO 12
		addStep("Presiona el boton Surtidores para signar las tareas, los seleccionas, le pones en su registro al 100% y le das click al boton Asigna Man");
		System.out.println("PASO 12");
		p.screenshot();
		// PASO 13
		addStep("En la pantalla XXFC Asignacion, seleccionas el registro de la ruta con el folio y presionas el boton Grabar");
		System.out.println("PASO 13");
		p.screenshot();
		// PASO 14
		addStep("Presiona el boton salir hasta llegar a la pantalla de Balanceo Detalle");
		System.out.println("PASO 14");
		p.screenshot();
		// PASO 15
		addStep("Presiona el boton liberacion");
		System.out.println("PASO 15");
		p.screenshot();
		// PASO 16
		addStep("correr el job xxfc_select_stock_order_ab_MTY");
		System.out.println("PASO 16");
		p.screenshot();
		// PASO 17
		addStep("En la pantalla Detalle Balanceo dar click en el boton Grabar Plan");
		System.out.println("PASO 17");
		p.screenshot();
		// PASO 18
		addStep("Revisar las tablas de la Base de datos para validar que se haya asignado la ola Xxfc_ab_libera Xxfc_distribution_queue Wave");
		System.out.println("PASO 18");
		p.screenshot();
		// PASO 19
		addStep("Correr los Jobs de la cadena del Run Distribution");
		System.out.println("PASO 19");
		p.screenshot();
		// PASO 20
		addStep("En la pantalla de Detalle Balanceo dar click en el boton Grabar Plan y Revisar la Liberacion en la Base de Datos en la tabla pick_directive");
		System.out.println("PASO 20");
		testCase.addScreenShotCurrentStep(u,"En la pantalla de Detalle Balanceo dar click en el boton Grabar Plan y Revisar la Liberacion en la Base de Datos en la tabla pick_directive");
	    u.close();

	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setPrerequisites() {
		return "..- Acceso a RDM G10"
				+ ".- Usuario y contrasena validos"
				+ ".- Plan de produccion de acuerdo al PMO";
	}

	@Override
	public String setTestDescription() {
		return "Se validara la programacion de pedidos por Ruta con Estatus folio Approved, consulta del Reporte Balanceo de Produccion, y asignacion a detalle";
	}

	@Override
	public String setTestDesigner() {
		return "ricardo.palacios@neoris.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_001_ValidarprogramaciondepedidosporrutaconEstatusfolioApproved";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

}