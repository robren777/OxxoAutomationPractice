package RDM.planificarDistribucion.RuteoDinamico;

import java.util.HashMap;

import org.testng.annotations.Test;

import RDM.util.PlanificarDistribucion;
import modelo.BaseExecution; import RDM.util.GlobalVariables;
import utils.SikuliUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLUtil;

public class ATC_FT_003_ValidarquesepuedarechazarpropuestadeRuteoDinamicopormotivoVolumetria extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_003_ValidarquesepuedarechazarpropuestadeRuteoDinamicopormotivoVolumetria_Test(
			HashMap<String, String> data) throws Exception {

		/*
		 * VARIABLES
		 */
		String FcreaIni = data.get("FcreaIni");
		String FcreaFin = data.get("FcreaFin");
		String EstProp = data.get("EstProp");
        
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
		addStep("Dar click en las siguientes opciones del menu: Distribution Planning/ XXFC - Programacion y Balanceo/ XXFC - Ruteo Dinamico");
		System.out.println("PASO 3");
		p.clickDistributionPlanningRuteoDinamico();
		p.screenshot();
		// PASO 4
		addStep("Ingresar informacion en los campos de busqueda:"
				+ "F. Crea Ini: Ingresar una fecha"
				+ "F. Crea Fin: Ingresar una fecha"
				+ "T. Distrib: ALL"
				+ "Plaza: ALL"
				+ "Ruta. Orig: ALL"
				+ "Ruta Prop: ALL"
				+ "Dest: All "
				+ "Est. Folio: ALL"
				+ "Est. Proc. ALL"
				+ "Est. Prop Propuesta"
				+ "Generado: ALL"
				+ "Cambio Ruta: ALL"
	            + "Distro: Número de Distro");
		System.out.println("PASO 4");
		p.ingresarDatosRuteoDinamico(FcreaIni, FcreaFin, EstProp);
		p.screenshot();
		// PASO 5
		addStep("Dar click en radiobutton del campo Ordenar por en la opcion: Ruta Propuesta");
		System.out.println("PASO 5");
		p.screenshot();
		// PASO 6
		addStep("Dar click en el boton icono Ejecutar");
		System.out.println("PASO 6");
		p.clickDistributionCheck();
		p.screenshot();
		// PASO 7
		addStep("Dar click en checkbox  para seleccionar aquellas rutas a las que se desea rechazar la propuesta en color blanco");
		System.out.println("PASO 7");
		p.screenshot();
		// PASO 8
		addStep("Dar click en el boton Rechazar Prop");
		System.out.println("PASO 8");
		p.clickRechazarPropDistribucion();
		p.screenshot();
		// PASO 9
		addStep("Dar click en la opcion Volumetria");
		System.out.println("PASO 9");
		p.screenshot();
		// PASO 10
		addStep("Dar click en el boton Continuar");
		System.out.println("PASO 9");
		p.screenshot();
		// PASO 11
		addStep("Dar click en el boton Yes");
		System.out.println("PASO 11");
		p.screenshot();
		u.close();
		

	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setPrerequisites() {
		return ".- Acceso a RDM"
				+ ".- Usuario y contrasena activos del aplicativo"
				+ ".- Elaboracion de Ruteo de las propuestas por parte de FL";
	}

	@Override
	public String setTestDescription() {
		return "Se validara que se pueda rechazar propuesta de Ruteo Dinamico por motivo de rechazo: Volumetria";
	}

	@Override
	public String setTestDesigner() {
		return "ricardo.palacios@neoris.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_003_ValidarquesepuedarechazarpropuestadeRuteoDinamicopormotivoVolumetria";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

}