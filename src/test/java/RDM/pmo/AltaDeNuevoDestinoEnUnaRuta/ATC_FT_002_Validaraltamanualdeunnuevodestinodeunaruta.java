package RDM.pmo.AltaDeNuevoDestinoEnUnaRuta;

import java.util.HashMap;
import org.testng.annotations.Test;
import modelo.BaseExecution;
import RDM.util.GlobalVariables;
import RDM.util.Pmo;
import utils.SikuliUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLUtil;

public class  ATC_FT_002_Validaraltamanualdeunnuevodestinodeunaruta extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void  ATC_FT_002_Validaraltamanualdeunnuevodestinodeunaruta_Test(
			HashMap<String, String> data) throws Exception {

		/*
		 * VARIABLES
		 */
		String distrib = data.get("distrib");
		String plaza = data.get("plaza");
		String ruta = data.get("ruta");
		String destino = data.get("destino");
		String surtido = data.get("surtido");
		String tipoPedido = data.get("tipoPedido");

		/*
		 * UTILERIAS
		 */
		SQLUtil dbRdm = new SQLUtil(GlobalVariables.DB_HOST_RDM, GlobalVariables.DB_USER_RDM,GlobalVariables.DB_PASSWORD_RDM);
		SeleniumUtil u = new SeleniumUtil(new IETest());
		Pmo p = new Pmo(u, testCase, data.get("server"));
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
		addStep("Dar click en las siguientes opciones del menu: Support Functions > Reports > XXFC PMO Ruta Destino");
		System.out.println("PASO 3");
		p.clickSupportFuntionsRutaDestino();
		p.screenshot();
		// PASO 4
		addStep("Capturar las siguiente informacion en los siguientes campos: - T. Distrib: ALL - Plaza: ALL - Ruta: ALL - Destino: ALL - L. Surtido: ALL - Tipo Pedido: ALL");
		System.out.println("PASO 4");
		p.ingresarDatosDistribaTipoPedido(distrib, plaza, ruta, destino, surtido, tipoPedido);
		p.screenshot();
		// PASO 5
		addStep("Marcar el checkbox del campo ALL para seleccionar todos los dias de la semana.");
		System.out.println("PASO 5");
		p.clickCheckAll();
		p.screenshot();
		// PASO 6
		addStep("Marcar el checkbox del campo ALL para seleccionar todos Turnos");
		System.out.println("PASO 6");
		p.clickCheckAll();
		p.screenshot();
		// PASO 7
		addStep("Marcar Radiobutton: Gen. Ped");
		System.out.println("PASO 7");
		p.clickCheckGen();
		p.screenshot();
		// PASO 8
		addStep("Dar click en el boton Ejecutar");
		System.out.println("PASO 8");
		p.clickEjecutar();
		p.screenshot();
		// PASO 9
		addStep("Dar click en el campo de la ruta");
		System.out.println("PASO 9");
		p.screenshot();
		// PASO 10
		addStep("Dar click en el boton Editar Ruta…");
		System.out.println("PASO 10");
		p.screenshot();
		// PASO 11
		addStep("Dar click en el boton Edit");
		System.out.println("PASO 11");
		p.screenshot();
		// PASO 12
		addStep("Seleccionar el campo Dest_Id vacio");
		System.out.println("PASO 12");
		p.screenshot();
		// PASO 13
		addStep("Dar click en el boton Listado del campo Dest_Id vacio");
		System.out.println("PASO 13");
		p.screenshot();
		// PASO 14
		addStep("Seleccionar una opcion");
		System.out.println("PASO 14");
		p.screenshot();
		// PASO 15
		addStep("Dar click en el boton OK");
		System.out.println("PASO 15");
		p.screenshot();
		// PASO 16
		addStep("Ingresar los siguientes datos del destino: - Secuencia - Dia/Hora. de Entrega Estimada - Dia/Hora. Ini Entrega - Dia/Hora. Fin Entrega - L sp. - Tipo Ped");
		System.out.println("PASO 16");
		p.screenshot();
		// PASO 17
		addStep("Validar los dias de generacion de la ruta");
		System.out.println("PASO 17");
		p.screenshot();
		// PASO 18
		addStep("Dar click en los días de generacion ubicados en la parte inferior izquierda para aplicar asignacion de nuevo destino");
		System.out.println("PASO 18");
		p.screenshot();
		// PASO 19
		addStep("Dar click en el boton Aplicar Destino y validar que se haya aplicado el nuevo destino a los días de generacion");
		System.out.println("PASO 19");
		p.screenshot();
		// PASO 20
		addStep("Dar doble click en el boton Save y validar que se hayan guardado los cambios");
		System.out.println("PASO 20");
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
		     + ".- Alta de rutas en el sistema";
	}

	@Override
	public String setTestDescription() {
		return "Se validara alta manual de un nuevo destino de una ruta";
	}

	@Override
	public String setTestDesigner() {
		return "ricardo.palacios@neoris.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_002_Validaraltamanualdeunnuevodestinodeunaruta";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

}
