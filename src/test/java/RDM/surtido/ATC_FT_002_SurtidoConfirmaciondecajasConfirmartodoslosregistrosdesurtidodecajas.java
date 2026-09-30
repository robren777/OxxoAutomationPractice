package RDM.surtido;


import java.util.HashMap;

import org.testng.annotations.Test;

import RDM.util.GlobalVariables;
import RDM.util.Surtido;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLUtil;

public class ATC_FT_002_SurtidoConfirmaciondecajasConfirmartodoslosregistrosdesurtidodecajas extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_002_SurtidoConfirmaciondecajasConfirmartodoslosregistrosdesurtidodecajas_Test(
			HashMap<String, String> data) throws Exception {

		/*
		 * VARIABLES
		 */

		/*
		 * UTILERIAS
		 */
		SQLUtil dbRdm = new SQLUtil(GlobalVariables.DB_HOST_RDM, GlobalVariables.DB_USER_RDM,GlobalVariables.DB_PASSWORD_RDM);
		SeleniumUtil u = new SeleniumUtil(new IETest());
		Surtido s = new Surtido(u, testCase, data.get("server"));
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
		s.logIn();
		// PASO 3
		addStep("Dar click en la opcion Inventory Management del menu principal");
		s.clickInventoryManagement();
		s.screenshot();
		// PASO 4
		addStep("Dar click en la opcion Pick Confirmation");
		System.out.println("PASO 4");
		s.clickPickConfirmation();
		s.screenshot();
		// PASO 5
		addStep("Dar click en la opcion XXFC Confirm Paper Pick to Pallet");
		System.out.println("PASO 5");
		s.clickXxfcConfirmPaper();
		s.screenshot();
		// PASO 6
		addStep("En la pantalla de XXFC Confirm Paper Pick to Pallet seleccionar el filtro deseado para que se despliegue lo que sera surtido");
		System.out.println("PASO 6");
		s.clickCheckAprovacionSurtido();
		s.screenshot();
		// PASO 7
		addStep("Dar click en la paloma rojo para realizar la busqueda");
		System.out.println("PASO 7");
		s.clickCheckAprovacionSurtido();
		s.screenshot();
		// PASO 8
		addStep("En la pantalla principal el sistema muestra el resultado de la busqueda");
		System.out.println("PASO 8");
		s.screenshot();
		// PASO 9
		addStep("Capturar PICK QTY");
		System.out.println("PASO 9");
		s.screenshot();
		// PASO 10
		addStep("Dar click en el boton Confirm All");
		System.out.println("PASO 10");
		s.clickConfirmAll();
		s.screenshot();;
		// PASO 11
		addStep("Dar Click en el boton Save");
		System.out.println("PASO 11");
		s.clickSaveSurtido();
		s.screenshot();
		u.close();
		
	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setPrerequisites() {
		return "- Contar con la liga de acceso a RDM\r\n"
				+ "- Contar con credenciales para RDM\r\n";
	}

	@Override
	public String setTestDescription() {
		return "Mediante el sistema RDM se debe mostrar los pasos para Confirmar todos los registros al surtido de cajas";
	}

	@Override
	public String setTestDesigner() {
		return "ricardo.palacios@neoris.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_002_SurtidoConfirmaciondecajasConfirmartodoslosregistrosdesurtidodecajas";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

}