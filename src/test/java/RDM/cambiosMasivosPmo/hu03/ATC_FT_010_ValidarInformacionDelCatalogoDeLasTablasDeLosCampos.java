package RDM.cambiosMasivosPmo.hu03;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Pattern;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import RDM.util.CambiosMasivos;
import RDM.util.CambiosMasivosQueries;
import RDM.util.GlobalVariables;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;
import utils.sql.SQLUtil;

/**
 * DESCRIPCIÓN: Realizar una serie de pruebas que nos permitan la validación de los catálogos de las tablas por cada campo a usar.
 * 
 * NOMBRE DE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * - MTC_FT_010_ValidarInformacionDelCatalogoDeLasTablasDeLosCampos
 * 
 * PREREQUISITOS:
 * 1. Edge
 * 2.-Tener acceso a la aplicación
 * 
 * INSUMOS:
 * Perfil: Coordinador y/o Analista de Sistemas
 * Usuario
 * Contraseña
 * 
 * @author Josué Cruz García
 * @date 03/Enero/2025
 */

public class ATC_FT_010_ValidarInformacionDelCatalogoDeLasTablasDeLosCampos extends BaseExecution {

	SeleniumUtil u = new SeleniumUtil(new IETest());

	@Test(dataProvider = "data-provider")
	public void ATC_FT_010_ValidarInformacionDelCatalogoDeLasTablasDeLosCampos_test(HashMap<String, String> data)
			throws Exception {
		/*
		 * Variables
		 */

		/*
		 * UTILERIAS
		 */
		SQLUtil dbConecta = new SQLUtil(GlobalVariables.DB_HOST_FCRD71CU, GlobalVariables.DB_USER_FCRD71CU,
				GlobalVariables.DB_PASSWORD_FCRD71CU);
		CambiosMasivosQueries cq = new CambiosMasivosQueries(testCase);
		SikuliUtil sk = new SikuliUtil();
		String pathImg = sk.PatternPath("RDM\\"+"CambiosMasivos");
		Pattern pathimg = new Pattern();
		ArrayList<String> ListImage = new ArrayList<String>();
		HashMap<String, Pattern> ImgPatter = new HashMap<String, Pattern>();
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		File Fol = new File(pathImg);
		CambiosMasivos c = new CambiosMasivos(sk, u, pathimg, ListImage, ImgPatter, patterns, Fol, testCase);

		// PASO 1
		addStep("Ingresar a la liga http://10.180.49.86:8071");
		System.out.println("PASO 1");
		// PASO 2
		addStep("Ingresar a RDM como Coordinador y/o Analista de Sistemas" + "Capturar:" + "- Usuario" + "- Contraseña"
				+ "Y dar clic al boton de Logon");
		System.out.println("PASO 2");
		c.logInCambiosMasivos();
		// PASO 3
		addStep("Dar clic al menu: Support Functions > Reports > XXFC-PMO_Carga_Masivos ");
		System.out.println("PASO 3");
		c.clickMenuPmoCargaMasivos();
		// PASO 4
		addStep("Validar la carga de un archivo con los campos correctamente cargados de tipo: \"RUTAS FORANEAS\"\n"
				+ "\n" + "TIENDA \n" + "TIPO_RUTA \n" + "SERVICE_CODE \n" + "ROUTE \n" + "ROUTE_DESCRIPTION \n"
				+ "TIPO_PEDIDOS_RUTA \n" + "DIA_GEN_PED \n" + "DIA_RECEPCION \n" + "DIA_LIBERACION \n"
				+ "H_LIBERACION \n" + "DIA_INI_PROD \n" + "H_INI_PROD \n" + "DIA_INI_CARGA \n" + "H_INI_CARGA \n"
				+ "DIA_SALIDA \n" + "H_SALIDA \n" + "DIA_SALIDA_PT \n" + "H_SALIDA_PT \n" + "RECORRIDO \n"
				+ "CICLO_ATN EN TIENDA \n" + "CICLO_TOT  \n" + "DIA_DISP_UNI_CEDIS  \n" + "H_DISP_UNI_CEDIS )\n"
				+ "SEC_CARGA \n" + "DIA_INI_VENT_ENTREGA  \n" + "H_INI_VENT_ENTREGA  \n" + "DIA_FIN_VENT_ENTREGA \n"
				+ "H_FIN_VENT_ENTREGA  \n" + "LINEA DE SURTIDO \n" + "HORA OBJETIVO \n");
		System.out.println("PASO 4");
		c.clickExcecute();
		testCase.addScreenShotCurrentStep("");
		assertTrue(c.validarCargaMasivaConDatos(), "Validacion de pantalla correcta");
		testCase.addScreenShotCurrentStep("");
		assertFalse(executeQuery(dbConecta, cq.queryShipDest()).isEmpty());
		assertFalse(executeQuery(dbConecta, cq.queyPmoTdistribServicio()).isEmpty());
		assertFalse(executeQuery(dbConecta, cq.queryTpoPedido()).isEmpty());
		cq.detalleServicios(dbConecta);
		dbConecta.close();
		testCase.addBoldTextEvidenceCurrentStep("Validacion Exitosa");

	}

	@Override
	public void beforeTest() {

	}

	@Override
	public String setPrerequisites() {
		return "\n" + "1. Edge\n" + "2.-Tener acceso a la aplicación";
	}

	@Override
	public String setTestDescription() {
		return "Realizar una serie de pruebas que nos permitan la validación de los catálogos de las tablas por cada campo a usar.";
	}

	@Override
	public String setTestDesigner() {
		return "JosueC1@hexaware.com";
	}

	@Override
	public String setTestFullName() {
		return null;
	}

	@Override
	public String setTestInstanceID() {
		return null;
	}

	@AfterTest
	@Override
	public void baseAfterTest() {
		super.baseAfterTest();
		u.quit();
	}

}
