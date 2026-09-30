package RDM.intercedis.wsbSeguimientoFacturacion;

import java.util.HashMap;

import org.testng.annotations.Test;

import RDM.util.GlobalVariables;
import RDM.util.Intercedis;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLUtil;

public class ATC_FT_005_ValidarenlapantallastatusbyDistroelprocesodefacturacionintercedis extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_005_ValidarenlapantallastatusbyDistroelprocesodefacturacionintercedis_Test(HashMap<String, String> data) throws Exception {

		/*
		 * VARIABLES
		 */
		
		String status = data.get("status");
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
		addStep("Dar click en la opcion Distribution Planning del menu principal");
		System.out.println("PASO 3");

		// PASO 4
		addStep("Dar click en la opcion Reports");
		System.out.println("PASO 4");

		// PASO 5
		addStep("Dar click en la opcion XXFC-Wave Status By Distro");
		System.out.println("PASO 5");
		i.clickWaveStatus();
        
		// PASO 6
		addStep("En la Pantalla ingresa los datos para generar la busqueda: T. Distrib: Generado: Distro: Numero de distro Plaza: ALL Route: ALL Dest: Destino Loc Class: ALL Zone:ALL Wave: Item: ALL PO L. Surtido");
		System.out.println("PASO 6");
		

		// PASO 7
		addStep("En el campo Status seleccionar la opcion Pendiente Pre-Facturar Dar click en el boton Ejecutar");
		System.out.println("PASO 7");
		i.ingresarStatusDistro(status, distro);
		i.clickEjecutarCheck();

		// PASO 8
		addStep("Marcar el checkbox del Distro a facturar");
		System.out.println("PASO 8");

		// PASO 9
		addStep("Dar click en el boton Facturacion");
		System.out.println("PASO 9");

		// PASO 10
		addStep("Ingresar datos en los siguientes campos: -Trailer -Door ID -Seal -Printer Selection");
		System.out.println("PASO 10");

		// PASO 11
		addStep("Dar click en el boton Facturacion");
		System.out.println("PASO 11");

		// PASO 12
		addStep("Dar click en el boton YES");
		System.out.println("PASO 12");

		// PASO 13
		addStep("Entrar en la Base de Datos de RDM");
		System.out.println("PASO 13");

		// PASO 14
		addStep("Ejecutar el Query select * from xxfc_facturacion_wsb where distro_nbr = numero de distro a facturar");
		System.out.println("PASO 14");

		// PASO 15
		addStep("Ejecutar Job creacion_xxfc_posterior_embarque corre cada 15 minutos automaticamente");
		System.out.println("PASO 15");

		// PASO 16
		addStep("Ejecutar el Query select * from RDM100.xxfc_posterior_embarque where tsf_no = numero de distro");
		System.out.println("PASO 16");

		// PASO 17
		addStep("Ejecutar el Query : select * from xxfc_posterior_emb_ citems where bol_nbr = Numero de bol");
		System.out.println("PASO 17");

		// PASO 18
		addStep("En el menu principal de RDM ingresar a las siguientes opciones: Shipping Reports Bill of Lading Advice Report");
		System.out.println("PASO 18");

		// PASO 19
		addStep("Ingresar BOL NBR Y TSF NBR y dar click en el boton Print");
		System.out.println("PASO 19");
        u.close();

	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setPrerequisites() {
		return "Acceso a RDM con credenciales validas"
				+ "Acceso a la base de datos RDM con credenciales validas"
				+ "Previo proceso de balanceo y asignacion de un pedido"
				+ "Distro del pedido"
				+ "Previo proceso de confirmacion de piezas y cajas en las pantallas Confirm Paper Units to pallet y Confirm Paper Pick to Pallet de RDM";
	}

	@Override
	public String setTestDescription() {
		return "se validara el seguimiento a la facturacion de las transferencias intercedis";
	}

	@Override
	public String setTestDesigner() {
		return "ricardo.palacios@neoris.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_005_ValidarenlapantallastatusbyDistroelprocesodefacturacionintercedis";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

}
