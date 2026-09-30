package RDM.planificarDistribucion.ProgramacionDePedidos;

import java.util.HashMap;

import org.testng.annotations.Test;

import RDM.util.PlanificarDistribucion;
import modelo.BaseExecution; import RDM.util.GlobalVariables;
import utils.SikuliUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLUtil;

public class ATC_FT_006_Validarquesepuedarealizarbalanceodeproduccionasignaciondepersonalyliberacion extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_006_Validarquesepuedarealizarbalanceodeproduccionasignaciondepersonalyliberacion_Test(
			HashMap<String, String> data) throws Exception {

		/*
		 * VARIABLES
		 */
		
        
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
		addStep("Dar click en la siguiente ruta: Distribution Planning/ XXFC-Programacion y Balanceo/ XXFC Balanceo Produccion");
		System.out.println("PASO 3");
		p.screenshot();
		// PASO 4
		addStep("Ingresar datos en los campos: - Fecha de produccion - Turno - Mesa de Control Dar click en el boton Ejecutar.");
		System.out.println("PASO 4");
		p.screenshot();
		// PASO 5
		addStep("Dar click en el boton Calcular");
		System.out.println("PASO 5");
		p.screenshot();
		// PASO 6
		addStep("Dar click en el boton Guardar");
		System.out.println("PASO 6");
		p.clickBotonLiberacion();
		// PASO 7
		addStep("Dar click en el boton YES");
		System.out.println("PASO 7");
		testCase.addScreenShotCurrentStep(u,"Dar click en el boton YES");
		p.screenshot();
		// PASO 8
		addStep("Dar click en el boton Det. Balanceo");
		System.out.println("PASO 8");
		p.screenshot();
		// PASO 9
		addStep("Capturar los siguientes datos: Fecha de Pruduccion Turno Mesa de control Dar click en el boton Ejecutar");
		System.out.println("PASO 9");
		p.screenshot();
		// PASO 10
		addStep("Seleccionar la casilla de la ruta folio a liberar y dar click en el boton Asignacion");
		System.out.println("PASO 10");
		p.screenshot();
		// PASO 11
		addStep("Ingresar los siguientes datos: Fecha de Pruduccion Turno Mesa de control Gp Surtido Tpo surtido: U (piezas) Dar click en el boton Ejecutar");
		System.out.println("PASO 11");
		p.screenshot();
		// PASO 12
		addStep("Dar click en el boton Surtidores y seleccionar un surtidor. Enseguida colocar 100 en el campo % del surtidor asignado.");
		System.out.println("PASO 12");
		p.screenshot();
		// PASO 13
		addStep("Dar click en el boton Asigna Manual");
		System.out.println("PASO 13");
		p.screenshot();
		// PASO 14
		addStep("Seleccionar la casilla de la linea de registros.");
		System.out.println("PASO 14");
		p.screenshot();
		// PASO 15
		addStep("Dar click en el boton Grabar Plan");
		System.out.println("PASO 15");
		p.screenshot();
		// PASO 16
		addStep("Dar click en el boton Yes y enseguida el boton salir.");
		System.out.println("PASO 16");
		p.screenshot();
		// PASO 17
		addStep("Posicionarse en el Tpo Surtido y seleccionar la opcion CF (cajas) Dar click en el boton Ejecutar");
		System.out.println("PASO 17");
		p.screenshot();
		// PASO 18
		addStep("Dar click en el boton Surtidores y seleccionar un surtidor a. Enseguida colocar 100 en el campo % del surtidor asignado.");
		System.out.println("PASO 18");
		p.screenshot();
		// PASO 19
		addStep("Dar click en el boton Asigna Manual");
		System.out.println("PASO 19");
		p.screenshot();
		// PASO 20
		addStep("Seleccionar la casilla de la linea de registros.");
		System.out.println("PASO 20");
		p.screenshot();
		// PASO 21
		addStep("Dar click en el boton Grabar Plan");
		System.out.println("PASO 21");
		p.screenshot();
		// PASO 22
		addStep("Dar click en el boton Yes y enseguida el boton salir.");
		System.out.println("PASO 22");
		p.screenshot();
		// PASO 23
		addStep("Dar click en el boton Salir");
		System.out.println("PASO 23");
		p.screenshot();
		// PASO 24
		addStep("Dar click en el boton Liberacion");
		System.out.println("PASO 24");
		p.screenshot();
		// PASO 25
		addStep("Dar click en el boton OK");
		System.out.println("PASO 25");
		p.screenshot();
		// PASO 26
		addStep("Ejecutar el job XXFC_Select_Stock_Order_AB_MTY en control M");
		System.out.println("PASO 26");
		p.screenshot();
		// PASO 27
		addStep("Regresar a la pantalla XXFC Balanceo Detalle de RDM y dar click en el boton Grabar Plan");
		System.out.println("PASO 27");
		p.screenshot();
		// PASO 28
		addStep("Ejecutar Cadena: RunDistribution.sh en control M");
		System.out.println("PASO 28");
		p.screenshot();
		// PASO 29
		addStep("Regresar a la pantalla XXFC Balanceo Detalle y dar click en el boton Grabar Plan");
		System.out.println("PASO 29");
		p.screenshot();
	    u.close();

	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setPrerequisites() {
		return ".- Acceso a RDM G10"
				+ ".- Usuario y contrasena validos"
				+ ".- Ruta/Folio a liberar"
				+ ".- Control M y credenciales de acceso"
				+ ".- Job XXFC Select Stock Order"
				+ ".- cadena de Job RunDistribution.sh_MT";
	}

	@Override
	public String setTestDescription() {
		return "Se validara que que se pueda realizar balanceo de produccion , asignacion de personal y liberacion de rutas ingresando desde la pantalla XXFC Balanceo de Produccion en RDM";
	}

	@Override
	public String setTestDesigner() {
		return "ricardo.palacios@neoris.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_006_Validarquesepuedarealizarbalanceodeproduccionasignaciondepersonalyliberacion";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

}