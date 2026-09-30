package RDM.cambiosMasivosPmo.hu012;

import static org.testng.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.sikuli.script.Pattern;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import RDM.util.CambiosMasivos;
import modelo.BaseExecution;
import utils.SikuliUtil;
import utils.selenium.IETest;
import utils.selenium.SeleniumUtil;

/**
 * DESCRIPCIÓN: Realizar una prueba exploratoria donde nos permita visualizar la opcion Respaldar Datos PMO
 * 
 * NOMBRE DE CASOS DE PRUEBA MANUAL QUE SE ABARCAN:
 * - MTC_FT_001_VisualizacionDeLaOpcionRespaldarDatosPMO
 * 
 * PREREQUISITOS:
 * 1. Edge
 * 2.-Tener acceso a la aplicación
 * 
 * INSUMOS:
 * Perfil: Coordinador y/o Analista de Sistemas
 * Usuario
 * Contraseña
 * RUTA
 * PLAZA
 * 
 * @author Josué Cruz García
 * @date 03/Enero/2025
 */

public class ATC_FT_001_VisualizacionDeLaOpcionRespaldarDatosPMO extends BaseExecution {

	SeleniumUtil u = new SeleniumUtil(new IETest());
	
	@Test(dataProvider = "data-provider")
	public void ATC_FT_001_VisualizacionDeLaOpcionRespaldarDatosPMO_test(HashMap<String, String> data) throws Exception {

		/*
		 * Variables
		 */

		/*
		 * UTILERIAS
		 */
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
		addStep("Ingresar a RDM como Coordinador y/o Analista de Sistemas"
				+ "Capturar:"
				+ "- Usuario"
				+ "- Contraseña"
				+ "Y dar clic al boton de Logon");
		System.out.println("PASO 2");
		c.logInCambiosMasivos();
		// PASO 3
		addStep("Dar clic al menu: Support Functions > Reports > XXFC-PMO_Cambios_Masivos ");
		System.out.println("PASO 3");
		c.clickPmoCambiosMasivos();
		assertTrue(c.validarPantallaInicio(),"Pantalla validada");
		testCase.addScreenShotCurrentStep(" ");
		// PASO 4
		addStep("Ingresa los filtros:\n"
				+ "- Plaza\n"
				+ "- Ruta");
		assertTrue(c.validarOpcionRespaldarDatos(),"Opción: Respaldar Datos, validada");
		testCase.addScreenShotCurrentStep(" ");
		// PASO 5
		addStep("En la sección de Tipo de movimiento Validar que se visualice la opción:\n"
				+ "\n"
				+ "- Respaldar Datos PMO");
		assertTrue(c.validarBotonRespaldarDatos(), "Botones validados");
		testCase.addScreenShotCurrentStep(" ");
		testCase.addBoldTextEvidenceCurrentStep("Validacion Exitosa");
	}

	@Override
	public void beforeTest() {

	}

	@Override
	public String setPrerequisites() {
		return "1. Edge\n"
				+ "2.-Tener acceso a la aplicación";
	}

	@Override
	public String setTestDescription() {
		return "Realizar una prueba exploratoria donde nos permita visualizar la opcion \"Respaldar Datos PMO\"";
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
