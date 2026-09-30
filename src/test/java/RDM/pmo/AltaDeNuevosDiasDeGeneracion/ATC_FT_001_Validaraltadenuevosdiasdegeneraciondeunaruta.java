package RDM.pmo.AltaDeNuevosDiasDeGeneracion;

import java.util.HashMap;
import org.testng.annotations.Test;
import modelo.BaseExecution;
import RDM.util.GlobalVariables;
import RDM.util.Pmo;
import utils.SikuliUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLUtil;

public class  ATC_FT_001_Validaraltadenuevosdiasdegeneraciondeunaruta extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void  ATC_FT_001_Validaraltadenuevosdiasdegeneraciondeunaruta_Test(
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
		addStep("Dar click en las siguientes opciones del menu: Support Functions > Reports > XXFC PMO Ruta Horario");
		System.out.println("PASO 3");
		p.clickSupportFuntionsRutaHorario();
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
		addStep("Dar click en la casilla que se encuentra del lado derecho del nombre de la Ruta a la que se le aplicaran los cambios");
		System.out.println("PASO 9");//+++++++++++++++++++++++++++++
		p.screenshot();
		// PASO 10
		addStep("Dar click en el boton Editar Ruta Hrio");
		System.out.println("PASO 10");
		p.clickEditarRutaHrio();
		p.screenshot();
		// PASO 11
		addStep("En la parte superior se eligen los nuevos dias a configurar");
		System.out.println("PASO 11");
		p.screenshot();
		// PASO 12
		addStep("Capturar los datos correspondientes (dias y horarios)");
		System.out.println("PASO 12");
		p.screenshot();
		// PASO 13
		addStep("Dar click en el boton Save");
		System.out.println("PASO 13");
		p.clickSaveEditRuta();
		p.screenshot();
		u.close();

	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setPrerequisites() {
		return ".-Acceso al aplicativo RDM 10G"
		     + ".- Credenciales válidas de acceso";
	}

	@Override
	public String setTestDescription() {
		return "Se validara que se puedan dar de alta nuevos dias de generacion de una ruta";
	}

	@Override
	public String setTestDesigner() {
		return "ricardo.palacios@neoris.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_001_Validaraltadenuevosdiasdegeneraciondeunaruta";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

}
