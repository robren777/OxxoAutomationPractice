package RDM.pmo.ConfiguracionDeLineaDeSurtido;

import java.util.HashMap;

import org.testng.annotations.Test;

import RDM.util.GlobalVariables;
import RDM.util.Pmo;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLUtil;

public class ATC_FT_002_Validarconfiguraciondelineadesurtidoparaundestino extends BaseExecution {

	@Test(dataProvider = "data-provider")
	public void ATC_FT_002_Validarconfiguraciondelineadesurtidoparaundestino_Test(
			HashMap<String, String> data) throws Exception {

		/*
		 * VARIABLES
		 */

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
		addStep("Capturar las siguiente informacion en los siguientes campos: T. Distrib: ALL - Plaza: ALL - Ruta: ALL - Destino: ALL - L. Surtido: ALL - Tipo Pedido: ALL");
		System.out.println("PASO 4");
		p.screenshot();
		// PASO 5
		addStep("Marcar el checkbox del campo ALL para seleccionar todos los dias de la semana");
		System.out.println("PASO 5");
		p.screenshot();
		// PASO 6
		addStep("Marcar el checkbox del campo ALL para seleccionar todos Turnos");
		System.out.println("PASO 6");
		p.screenshot();
		// PASO 7
		addStep("Marcar Radiobutton: Gen. Ped");
		System.out.println("PASO 7");
		p.screenshot();
		// PASO 8
		addStep("Dar click en el boton Ejecutar");
		System.out.println("PASO 8");
		p.screenshot();
		// PASO 9
		addStep("Dar click en el campo de la ruta.");
		System.out.println("PASO 9");
		p.screenshot();
		// PASO 10
		addStep("Dar click en el boton Editar Ruta/…");
		System.out.println("PASO 10");
		p.screenshot();
		// PASO 11
		addStep("Dar click en el boton Edit");
		System.out.println("PASO 11");
		p.screenshot();
		// PASO 12
		addStep("Seleccionar el checkbox del destino donde se realizara la configuracion");
		System.out.println("PASO 12");
		p.screenshot();
		// PASO 13
		addStep("Dar click en el boton Lista de valores de la columna Ls p.");
		System.out.println("PASO 13");
		p.screenshot();
		// PASO 14
		addStep("Dar click en las casillas de la lineas de surtido correspondientes");
		System.out.println("PASO 14");
		p.screenshot();
		// PASO 15
		addStep("Dar click en el boton Save");
		System.out.println("PASO 15");
		p.screenshot();
		// PASO 16
		addStep("Dar doble click en el boton Save y validar que se hayan guardado los cambios");
		System.out.println("PASO 16");
		p.screenshot();
        u.close();

	}

	@Override
	public void beforeTest() {
	}

	@Override
	public String setPrerequisites() {
		return ".- Liga de Acceso a RDM 10g\r\n"
				+ ".- Credenciales de Acceso (usuario y contraseña activos)\r\n"
				+ ".- Alta de rutas en el aplicativo\r\n";
	}

	@Override
	public String setTestDescription() {
		return "Se validara la configuracion de lineas de surtido aplicada a un destino";
	}

	@Override
	public String setTestDesigner() {
		return "ricardo.palacios@neoris.com";
	}

	@Override
	public String setTestFullName() {
		return "ATC_FT_002_Validarconfiguraciondelineadesurtidoparaundestino";
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

}